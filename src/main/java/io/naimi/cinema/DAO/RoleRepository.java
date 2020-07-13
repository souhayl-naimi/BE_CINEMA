package io.naimi.cinema.DAO;

import io.naimi.cinema.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
