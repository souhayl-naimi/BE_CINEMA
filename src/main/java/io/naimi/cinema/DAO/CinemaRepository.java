package io.naimi.cinema.DAO;

import io.naimi.cinema.Entities.Cinema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface CinemaRepository extends JpaRepository<Cinema,Long> {
    public Page<Cinema> findByNomContainsIgnoreCase(String nom, Pageable pageable);
    public Page<Cinema> findByVille_Id(Long id, Pageable pageable);
}
