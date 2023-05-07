package nl.novi.pegasusclinicalmanagementsystem.repositories;

import nl.novi.pegasusclinicalmanagementsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}

