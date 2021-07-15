package ma.emsi.tp2mvc;

import ma.emsi.tp2mvc.entities.Patient;
import ma.emsi.tp2mvc.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class Tp2MvcApplication implements CommandLineRunner {

    @Autowired
    private PatientRepository patientRepository;
    public static void main(String[] args) {
        SpringApplication.run(Tp2MvcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        patientRepository.save(new Patient(null, "Othman", new Date(), 2000, false));
        patientRepository.save(new Patient(null, "Naoufal", new Date(), 2300, true));
        patientRepository.save(new Patient(null, "Zakariae", new Date(), 3000, false));
        patientRepository.save(new Patient(null, "Fahde", new Date(), 4000, false));
        patientRepository.save(new Patient(null, "Ikram", new Date(), 2600, true));
        patientRepository.save(new Patient(null, "Asmaa", new Date(), 3200, false));
        patientRepository.findAll().forEach(p->{
            System.out.println(p.toString());
        });
        System.out.println("-------------------------------------------");
        Patient patient = patientRepository.findById(3L).get();
        System.out.println(patient.getNom());
        System.out.println("-------------------------------------------");
        Page<Patient> listePatients = patientRepository.findByNomContains("o", PageRequest.of(0, 2));
        listePatients.forEach(p->{
            System.out.println(p);
        });
        System.out.println("-------------------------------------------");
        List<Patient> listePatients1 = patientRepository.findByMalade(false);
        listePatients.forEach(p->{
            System.out.println(p);
        });
        System.out.println("-------------------------------------------");
        List<Patient> listePatients2 = patientRepository.findByNomContainsAndMalade("a", false);
        listePatients.forEach(p->{
            System.out.println(p);
        });
        System.out.println("-------------------------------------------");
        //patientRepository.deleteById(3L);
        Page<Patient> pagePatients = patientRepository.findAll(PageRequest.of(0, 2));
        System.out.println(pagePatients.getTotalPages());
        pagePatients.getContent().forEach(p->{
            System.out.println(p);
        });
    }
}
