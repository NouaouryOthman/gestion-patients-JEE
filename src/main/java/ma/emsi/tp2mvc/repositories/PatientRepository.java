package ma.emsi.tp2mvc.repositories;


import ma.emsi.tp2mvc.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    public Page<Patient> findByNomContains(String name, Pageable pageable);
    public List<Patient> findByMalade(boolean malade);
    public List<Patient> findByNomContainsAndMalade(String name, boolean malade);
}
