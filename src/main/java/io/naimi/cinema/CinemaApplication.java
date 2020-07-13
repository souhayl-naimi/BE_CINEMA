package io.naimi.cinema;

import io.naimi.cinema.DAO.RoleRepository;
import io.naimi.cinema.DAO.UserRepository;
import io.naimi.cinema.Entities.*;
import io.naimi.cinema.Services.ICinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.ArrayList;
import java.util.Collection;

@SpringBootApplication
public class CinemaApplication implements CommandLineRunner {

    @Autowired
    private ICinemaInitService cinemaInitService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RepositoryRestConfiguration repositoryRestConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(CinemaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        repositoryRestConfiguration.exposeIdsFor(Film.class,Salle.class, Ticket.class);
        cinemaInitService.initVilles();
        cinemaInitService.initCinemas();
        cinemaInitService.initSalles();
        cinemaInitService.initPlaces();
        cinemaInitService.initSceances();
        cinemaInitService.initCategories();
        cinemaInitService.initFilms();
        cinemaInitService.initProjections();
        cinemaInitService.initTickets();
        Role role = new Role("ADMIN","role admin");
        roleRepository.save(role);
        Collection<Role> roles = new ArrayList<Role>();
        roles.add(role);
        User user = new User("Admin", "$2a$10$wQMdGUE/aXYOOayyPdJxduDtyDy398RUgQNGGPmnzfC5V8aESUsLK",
                true,roles);
        userRepository.save(user);
    }
}
