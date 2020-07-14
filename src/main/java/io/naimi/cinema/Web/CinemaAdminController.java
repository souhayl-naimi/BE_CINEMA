package io.naimi.cinema.Web;

import io.naimi.cinema.DAO.*;
import io.naimi.cinema.Entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class CinemaAdminController {
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private SceanceRepository sceanceRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CategorieRepository categorieRepository;

    @RequestMapping(value = "/admin", method = GET)
    public String admin() {
        return "admin";
    }

    @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    public String login(){return "loginPage";}

    /*-----------------------CITIES-----------------------------*/
    @RequestMapping(value = "/cities", method = GET)
    public String cities(Model model,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "6") int size,
                         @RequestParam(name = "name", defaultValue = "") String name) {
        Page<Ville> villePage = villeRepository.findByNomContainsIgnoreCase(name, PageRequest.of(page, size));
        model.addAttribute("result", villePage.getTotalElements());
        model.addAttribute("villeList", villePage.getContent());
        model.addAttribute("pages", new int[villePage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("name", name);
        model.addAttribute("size", size);
        return "cities";
    }
    @RequestMapping(value = "/deleteCity", method = RequestMethod.POST)
    public String deleteCity(Long id, int page, int size, String name) {
        villeRepository.deleteById(id);
        return "redirect:/cities?page=" + page + "&size=" + size + "&name=" + name + "";
    }
    @RequestMapping(value = "/formCity", method = RequestMethod.GET)
    public String formPatient(Model model) {
        model.addAttribute("city", new Ville());
        return "formCity";
    }

    @RequestMapping(value = "editCity", method = RequestMethod.GET)
    public String editPatient(Model model, Long id) {
        Ville city = villeRepository.findById(id).get();
        System.out.println(id);
        model.addAttribute("city", city);
        return "formCity";
    }

    @PostMapping(value = "saveCity")
    public String savePatient(@Valid Ville city, BindingResult bindingResult, Model model) {
        model.addAttribute("saved", city);
        if (bindingResult.hasErrors()) return "formCity";
        villeRepository.save(city);
        return "confirmationCity";

    }

    /*-----------------------CINEMAS-----------------------------*/


    @RequestMapping(value = "/cinmas", method = GET)
    public String cinemas(Model model,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "6") int size,
                          Long id) {
        //Page<Cinema> cinemaPage = cinemaRepository.findByNomContainsIgnoreCase(name, PageRequest.of(page, size));
        Page<Cinema> cinemaPage = cinemaRepository.findByVille_Id(id, PageRequest.of(page, size));
        model.addAttribute("result", cinemaPage.getTotalElements());
        model.addAttribute("cinemaList", cinemaPage.getContent());
        model.addAttribute("pages", new int[cinemaPage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("villeid", id);
        return "cinemas";
    }
    @RequestMapping(value = "/deleteCinema", method = RequestMethod.POST)
    public String deleteCinema(Long id, int page, int size) {
        cinemaRepository.deleteById(id);
        return "redirect:/cinemas?page=" + page + "&size=" + size + "";
    }
    @RequestMapping(value = "/formCinema", method = RequestMethod.GET)
    public String formCinema(Model model) {
        List<Ville> villeList = villeRepository.findAll();
        model.addAttribute("cinema", new Cinema());
        model.addAttribute("villeList",villeList);
        return "formCinema";
    }

    @RequestMapping(value = "editCinema", method = RequestMethod.GET)
    public String editCinema(Model model, Long id) {
        Cinema cinema = cinemaRepository.findById(id).get();
        List<Ville> villeList = villeRepository.findAll();
        model.addAttribute("villeList",villeList);
        model.addAttribute("cinema", cinema);
        return "formCinema";
    }

    @PostMapping(value = "saveCinema")
    public String saveCinema(@Valid Cinema cinema, BindingResult bindingResult, Model model) {
        model.addAttribute("saved", cinema);
        if (bindingResult.hasErrors()) return "formCinema";
        cinemaRepository.save(cinema);
        return "confirmationCinema";

    }

    /*-----------------------SALLES-----------------------------*/


    @RequestMapping(value = "/salls", method = GET)
    public String salles(Model model,
                          @RequestParam(name = "page", defaultValue = "0") int page,
                          @RequestParam(name = "size", defaultValue = "6") int size,
                          Long id) {
        Page<Salle> sallePage = salleRepository.findByCinema_Id(id, PageRequest.of(page, size));
        model.addAttribute("result", sallePage.getTotalElements());
        model.addAttribute("salleList", sallePage.getContent());
        model.addAttribute("pages", new int[sallePage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        return "salles";
    }
    @RequestMapping(value = "/deleteSalle", method = RequestMethod.POST)
    public String deleteSalle(Long id, int page, int size) {
        salleRepository.deleteById(id);
        return "redirect:/salls?page=" + page + "&size=" + size + "";
    }

    @RequestMapping(value = "/formSalle", method = RequestMethod.GET)
    public String formSalle(Model model) {
        List<Cinema> cinemaList = cinemaRepository.findAll();
        model.addAttribute("salle", new Salle());
        model.addAttribute("cinemaList",cinemaList);
        return "formSalle";
    }

    @RequestMapping(value = "editSalle", method = RequestMethod.GET)
    public String editSalle(Model model, Long id) {
        Salle salle = salleRepository.findById(id).get();
        List<Cinema> cinemaList = cinemaRepository.findAll();
        model.addAttribute("cinemaList",cinemaList);
        model.addAttribute("salle", salle);
        return "formSalle";
    }

    @PostMapping(value = "saveSalle")
    public String saveSalle(@Valid Salle salle, BindingResult bindingResult, Model model) {
        model.addAttribute("saved", salle);
        if (bindingResult.hasErrors()) {
            return "formSalle";
        }
        salleRepository.save(salle);

        for(int i=1; i<=salle.getNombrePlace();i++)
        {
            Place place = new Place(null,i,0,0,0,salle,null);
            placeRepository.save(place);
        }
        return "confirmationSalle";

    }

    /*-----------------------Projections-----------------------------*/

    @RequestMapping(value = "/projs", method = GET)
    public String projs(Model model,
                          @RequestParam(name = "page", defaultValue = "0") int page,
                          @RequestParam(name = "size", defaultValue = "6") int size,
                          Long id) {
        Page<Projection> projectionPage = projectionRepository.findBySalle_Id(id, PageRequest.of(page, size));
        model.addAttribute("result", projectionPage.getTotalElements());
        model.addAttribute("projectionList", projectionPage.getContent());
        model.addAttribute("pages", new int[projectionPage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        return "projections";
    }
    @RequestMapping(value = "/deleteProj", method = RequestMethod.POST)
    public String deleteProj(Long id, int page, int size) {
        projectionRepository.deleteById(id);
        return "redirect:/projs?page=" + page + "&size=" + size + "";
    }

    @RequestMapping(value = "/formProjections", method = RequestMethod.GET)
    public String formProj(Model model) {
        List<Salle> salleList = salleRepository.findAll();
        List<Film> filmList = filmRepository.findAll();
        List<Sceance> sceanceList = sceanceRepository.findAll();
        model.addAttribute("salleList",salleList);
        model.addAttribute("sceanceList",sceanceList);
        model.addAttribute("filmList",filmList);
        model.addAttribute("projection", new Projection());
        return "formProjections";
    }

    @RequestMapping(value = "editProj", method = RequestMethod.GET)
    public String editProjections(Model model, Long id) {
        Projection projection = projectionRepository.findById(id).get();
        List<Salle> salleList = salleRepository.findAll();
        List<Film> filmList = filmRepository.findAll();
        List<Sceance> sceanceList = sceanceRepository.findAll();
        model.addAttribute("salleList",salleList);
        model.addAttribute("sceanceList",sceanceList);
        model.addAttribute("filmList",filmList);
        model.addAttribute("projection", projection);
        return "formProjections";
    }

    @PostMapping(value = "saveProj")
    public String saveProj(@Valid Projection projection, BindingResult bindingResult, Model model) {
        model.addAttribute("saved", projection);
        if (bindingResult.hasErrors()) {
            return "formProjections";
        }
        projectionRepository.save(projection);
        projection.getSalle().getPlaces().forEach(place->{
            Ticket ticket = new Ticket();
            ticket.setPlace(place);
            ticket.setPrix(projection.getPrix());
            ticket.setProjection(projection);
            ticket.setReserved(false);
            ticketRepository.save(ticket);
        });
        return "confirmationProj";

    }
    /*-----------------------Places-----------------------------*/

    @RequestMapping(value = "/placs", method = GET)
    public String placs(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "6") int size,
                        Long id) {
        Page<Place> placePage = placeRepository.findBySalle_Id(id, PageRequest.of(page, size));
        model.addAttribute("result", placePage.getTotalElements());
        model.addAttribute("placeList", placePage.getContent());
        model.addAttribute("pages", new int[placePage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        return "places";
    }
    @RequestMapping(value = "/deletePlace", method = RequestMethod.POST)
    public String deletePlace(Long id, int page, int size) {
        placeRepository.deleteById(id);
        return "redirect:/placs?page=" + page + "&size=" + size + "";
    }
    @RequestMapping(value = "/formPlace", method = RequestMethod.GET)
    public String formPlace(Model model) {
        List<Salle> salleList = salleRepository.findAll();
        model.addAttribute("salleList",salleList);
        model.addAttribute("place", new Place());
        return "formPlace";
    }
    @RequestMapping(value = "editPlace", method = RequestMethod.GET)
    public String editPlace(Model model, Long id) {
        Place place = placeRepository.findById(id).get();
        List<Salle> salleList = salleRepository.findAll();
        model.addAttribute("salleList",salleList);
        model.addAttribute("place", place);
        return "formPlace";
    }

    @PostMapping(value = "savePlace")
    public String saveProj(@Valid Place place, BindingResult bindingResult, Model model) {
        model.addAttribute("saved", place);
        if (bindingResult.hasErrors()) return "formPlace";
        placeRepository.save(place);
        return "confirmationPlace";

    }
    /*-----------------------TICKETS-----------------------------*/

    @RequestMapping(value = "/ticks", method = GET)
    public String ticks(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "6") int size,
                        Long id) {
        Page<Ticket> ticketPage = ticketRepository.findByProjection_Id(id, PageRequest.of(page, size));
        model.addAttribute("result", ticketPage.getTotalElements());
        model.addAttribute("ticketList", ticketPage.getContent());
        model.addAttribute("pages", new int[ticketPage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        return "tickets";
    }
    @RequestMapping(value = "/deleteTicket", method = RequestMethod.POST)
    public String deleteTickets(Long id, int page, int size) {
        ticketRepository.deleteById(id);
        return "redirect:/ticks?page=" + page + "&size=" + size + "";
    }
    /*-----------------------Categories-----------------------------*/

    @RequestMapping(value = "/cats", method = GET)
    public String cats(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "6") int size) {
        Page<Categorie> categoriePage = categorieRepository.findAll(PageRequest.of(page, size));
        model.addAttribute("result", categoriePage.getTotalElements());
        model.addAttribute("categorieList", categoriePage.getContent());
        model.addAttribute("pages", new int[categoriePage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        return "categories";
    }
    @RequestMapping(value = "/deleteCategorie", method = RequestMethod.POST)
    public String deleteCats(Long id, int page, int size) {
        categorieRepository.deleteById(id);
        return "redirect:/cats?page=" + page + "&size=" + size + "";
    }
    @RequestMapping(value = "/formCategories", method = RequestMethod.GET)
    public String formCat(Model model) {
        model.addAttribute("categorie", new Categorie());
        return "formCategories";
    }
    @RequestMapping(value = "editCategorie", method = RequestMethod.GET)
    public String editCategorie(Model model, Long id) {
        Categorie categorie = categorieRepository.findById(id).get();
        model.addAttribute("categorie",categorie );
        return "formCategories";
    }

    @PostMapping(value = "saveCategory")
    public String saveCategory(@Valid Categorie categorie, BindingResult bindingResult, Model model) {
        model.addAttribute("saved", categorie);
        if (bindingResult.hasErrors()) return "formPlace";
        categorieRepository.save(categorie);
        return "confirmationCategorie";

    }
    /*-----------------------Sceances-----------------------------*/

    @RequestMapping(value = "/scs", method = GET)
    public String scs(Model model,
                       @RequestParam(name = "page", defaultValue = "0") int page,
                       @RequestParam(name = "size", defaultValue = "6") int size) {
        Page<Sceance> sceancePage = sceanceRepository.findAll(PageRequest.of(page, size));
        model.addAttribute("result", sceancePage.getTotalElements());
        model.addAttribute("sceanceList", sceancePage.getContent());
        model.addAttribute("pages", new int[sceancePage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        return "sceances";
    }
    @RequestMapping(value = "/deleteSceance", method = RequestMethod.POST)
    public String deleteSceances(Long id, int page, int size) {
        sceanceRepository.deleteById(id);
        return "redirect:/scs?page=" + page + "&size=" + size + "";
    }
    @RequestMapping(value = "/formSceance", method = RequestMethod.GET)
    public String formSceances(Model model) {
        model.addAttribute("sceance", new Sceance());
        return "formSceance";
    }
    @RequestMapping(value = "editSceance", method = RequestMethod.GET)
    public String editSceance(Model model, Long id) {
        Sceance sceance = sceanceRepository.findById(id).get();
        model.addAttribute("sceance",sceance );
        return "formSceance";
    }

    @PostMapping(value = "saveSceance")
    public String saveSceance(@Valid Sceance sceance, BindingResult bindingResult, Model model) {
        model.addAttribute("saved", sceance);
        if (bindingResult.hasErrors()) return "formSceance";
        sceanceRepository.save(sceance);
        return "confirmationSceance";

    }
    /*-----------------------Films-----------------------------*/
    @RequestMapping(value = "/fils", method = GET)
    public String fils(Model model,
                          @RequestParam(name = "page", defaultValue = "0") int page,
                          @RequestParam(name = "size", defaultValue = "6") int size,
                          Long id) {
        Page<Film> filmPage = filmRepository.findByCategorie_Id(id, PageRequest.of(page, size));
        model.addAttribute("result", filmPage.getTotalElements());
        model.addAttribute("filmList", filmPage.getContent());
        model.addAttribute("pages", new int[filmPage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        return "films";
    }
    @RequestMapping(value = "/deleteFilm", method = RequestMethod.POST)
    public String deleteFilm(Long id, int page, int size) {
        filmRepository.deleteById(id);
        return "redirect:/fils?page=" + page + "&size=" + size + "";
    }
    @RequestMapping(value = "/formFilm", method = RequestMethod.GET)
    public String formFilm(Model model) {
        List<Categorie> categorieList = categorieRepository.findAll();
        model.addAttribute("film", new Film());
        model.addAttribute("categorieList",categorieList);
        return "formFilm";
    }

    @RequestMapping(value = "editFilm", method = RequestMethod.GET)
    public String editFilm(Model model, Long id) {
        Film film = filmRepository.findById(id).get();
        List<Categorie> categorieList = categorieRepository.findAll();
        model.addAttribute("categorieList",categorieList);
        model.addAttribute("film", film);
        return "formFilm";
    }

    @PostMapping(value = "saveFilm")
    public String saveFilm(@Valid Film film, BindingResult bindingResult, Model model) {
        model.addAttribute("saved", film);
        if (bindingResult.hasErrors()) return "formFilm";
        filmRepository.save(film);
        return "confirmationFilm";

    }




}
