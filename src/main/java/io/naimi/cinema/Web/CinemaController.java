package io.naimi.cinema.Web;

import io.naimi.cinema.DAO.FilmRepository;
import io.naimi.cinema.DAO.TicketRepository;
import io.naimi.cinema.DAO.VilleRepository;
import io.naimi.cinema.Entities.Film;
import io.naimi.cinema.Entities.Ticket;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin("*")
public class CinemaController {

    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private TicketRepository ticketRepository;


    @GetMapping(path = "/imageFilm/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] imageFilm(@PathVariable(name = "id") Long id) throws Exception {
        Film film = filmRepository.findById(id).get();
        String photoName = film.getPhoto();
        File file = new File(System.getProperty("user.home") + "/cinema/images/" + photoName);
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }

    @PostMapping("/pay")
    public List<Ticket> pay(@RequestBody TicketForm ticketForm) {
        List<Ticket> ticketsPurchased = new ArrayList<>();
        ticketForm.getIds().forEach(ticket -> {
            Ticket tick = ticketRepository.findById(ticket).get();
            tick.setReserved(true);
            tick.setCodePay(ticketForm.getCodePay());
            tick.setNomClient(ticketForm.getNomClient());
            ticketRepository.save(tick);
            ticketsPurchased.add(tick);
        });
        return ticketsPurchased;
    }
}

@Data
class TicketForm {
    private String nomClient;
    private Integer codePay;
    private List<Long> ids = new ArrayList<>();
}
