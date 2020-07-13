package io.naimi.cinema.Entities;

import org.springframework.data.rest.core.config.Projection;

import java.util.Collection;

@Projection(name = "p1",types = io.naimi.cinema.Entities.Projection.class)
public interface ProjectionProj {
    public Long getId();
    public double getPrix();
    public Film getFilm();
    public Salle getSalle();
    public Collection<Ticket> getTickets();
    public Sceance getSceance();
}
