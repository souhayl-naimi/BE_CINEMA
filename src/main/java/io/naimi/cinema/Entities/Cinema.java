package io.naimi.cinema.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity @Data @AllArgsConstructor @NoArgsConstructor @ToString
@Table(name = "cinemas")
public class Cinema{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private double altitude,longtitude,latitude;
    private  int nombreSalles;
    @ManyToOne
    private Ville ville;
    @OneToMany(cascade = CascadeType.REMOVE,mappedBy = "cinema")
    private Collection<Salle> salles;
}
