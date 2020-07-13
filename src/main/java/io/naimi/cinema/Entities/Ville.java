package io.naimi.cinema.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
@Table(name = "villes")
public class Ville {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private double altitude,longtitude,latitude;
    @OneToMany(cascade = CascadeType.REMOVE,mappedBy = "ville")
    private Collection<Cinema> cinemas;
}
