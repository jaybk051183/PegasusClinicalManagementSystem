package nl.novi.pegasusclinicalmanagementsystem.repositories;

import nl.novi.pegasusclinicalmanagementsystem.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
