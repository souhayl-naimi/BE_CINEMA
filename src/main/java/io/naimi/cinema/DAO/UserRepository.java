package io.naimi.cinema.DAO;

import io.naimi.cinema.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
