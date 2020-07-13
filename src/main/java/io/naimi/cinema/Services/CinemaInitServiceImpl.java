package io.naimi.cinema.Services;

import io.naimi.cinema.DAO.*;
import io.naimi.cinema.Entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService {
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private SceanceRepository sceanceRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private TicketRepository ticketRepository;


    @Override
    public void initVilles() {
        Stream.of("Casablanca", "Marrakesh", "Rabat", "Tanger").forEach(v -> {
            Ville ville = new Ville();
            ville.setNom(v);
            villeRepository.save(ville);
        });
    }

    @Override
    public void initCinemas() {
        villeRepository.findAll().forEach(v -> {
            Stream.of("MEGARAMA", "IMAX", "SAHARA").forEach(c -> {
                Cinema cinema = new Cinema();
                cinema.setNom(c);
                cinema.setVille(v);
                cinema.setNombreSalles(3 + (int) (Math.random() * 7));
                cinemaRepository.save(cinema);
            });
        });
    }

    @Override
    public void initSalles() {
        cinemaRepository.findAll().forEach(c -> {
            for (int i = 0; i < c.getNombreSalles(); i++) {
                Salle salle = new Salle();
                salle.setNom("Salle " + (i + 1));
                salle.setCinema(c);
                salle.setNombrePlace(15 + (int) (Math.random() * 30));
                salleRepository.save(salle);
            }
        });
    }

    @Override
    public void initPlaces() {
        salleRepository.findAll().forEach(salle -> {
            for (int i = 0; i < salle.getNombrePlace(); i++) {
                Place place = new Place();
                place.setNumero(i + 1);
                place.setSalle(salle);
                placeRepository.save(place);
            }
        });
    }

    @Override
    public void initSceances() {
        Stream.of("12:00", "15:00", "17:00", "19:00", "21:00").forEach(s -> {
            Sceance sceance = new Sceance();
            DateFormat df = new SimpleDateFormat("HH:mm");
            try {
                sceance.setHeureDebut(df.parse(s));
                sceanceRepository.save(sceance);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initCategories() {
        Stream.of("Histoire", "Action", "Fiction", "Drama").forEach(c -> {
                    Categorie categorie = new Categorie();
                    categorie.setName(c);
                    categorieRepository.save(categorie);
                }
        );
    }

    @Override
    public void initFilms() {
        double durees[] = new double[]{1, 1.5, 2, 2.5, 3};
        List<Categorie> categories = categorieRepository.findAll();
        Stream.of("12 Homme En Colere", "Forrest Gump", "Green Book", "Le Parrain", "Seigneur Des Anneaux").forEach(f -> {
            Film film = new Film();
            film.setTitre(f);
            film.setDuree(durees[new Random().nextInt(durees.length)]);
            film.setPhoto(f.replaceAll(" ", "")+".jpg");
            film.setCategorie(categories.get(new Random().nextInt(categories.size())));
            filmRepository.save(film);
        });
    }

    @Override
    public void initProjections() {
        List<Film> films = filmRepository.findAll();
        double[] prices = new double[]{30, 50,  60, 70, 90, 100};
        villeRepository.findAll().forEach(ville -> {
            ville.getCinemas().forEach(cinema -> {
                cinema.getSalles().forEach(salle -> {
                    int index = new Random().nextInt(films.size());
                    Film film = films.get(index);
                   // filmRepository.findAll().forEach(film -> {
                        sceanceRepository.findAll().forEach(sceance -> {
                            Projection projection = new Projection();
                            projection.setDateProjection(new Date());
                            projection.setFilm(film);
                            projection.setPrix(prices   [new Random().nextInt(prices.length)]);
                            projection.setSalle(salle);
                            projection.setSceance(sceance);
                            projectionRepository.save(projection);
                        });
                   // });
                });
            });
        });
    }

    @Override
    public void initTickets() {
        projectionRepository.findAll().forEach(projection -> {
            projection.getSalle().getPlaces().forEach(place -> {
                Ticket ticket = new Ticket();
                ticket.setPlace(place);
                ticket.setPrix(projection.getPrix());
                ticket.setProjection(projection);
                ticket.setReserved(false);
                ticketRepository.save(ticket);
            });
        });
    }
}
