package ma.emsi.tp2mvc.web;

import ma.emsi.tp2mvc.entities.Patient;
import ma.emsi.tp2mvc.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/")
    public String index() {
        return "redirect:/patients";
    }

    @GetMapping("/patients")
    public String patients(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size,
                           @RequestParam(name = "motCle", defaultValue = "") String motCle) {
        Page<Patient> pagePatients = patientRepository.findByNomContains(motCle, PageRequest.of(page, size));
        model.addAttribute("patients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("motCle", motCle);
        model.addAttribute("size", size);
        return "patients";
    }

    @GetMapping(path = "/deletePatient")
    public String deletePatient(Long id, int page, int size, String motCle) {
        patientRepository.deleteById(id);
        return "redirect:/patients?page=" + page + "&size=" + size + "&motCle=" + motCle;
    }

    @GetMapping("/deletePatient2")
    public String deletePatient(Long id, int page, int size, String motCle, Model model) {
        patientRepository.deleteById(id);
        return patients(model, page, size, motCle);
    }

    @GetMapping("/formPatient")
    public String formPatient(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("mode", "new");
        return "formPatient";
    }

    @PostMapping("/savePatient")
    public String savePatient(Model model, @Valid Patient patient, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "formPatient";
        patientRepository.save(patient);
        model.addAttribute("patient", patient);
        return "afficher";
    }

    @GetMapping("/editPatient")
    public String editPatient(Model model, Long id) {
        Patient patient = patientRepository.findById(id).get();
        model.addAttribute("patient", patient);
        model.addAttribute("mode", "new");
        model.addAttribute("mode", "edit");
        return "formPatient";
    }

    @GetMapping("/details")
    public String detailsPatient(Model model, Long id) {
        Patient patient = patientRepository.findById(id).get();
        model.addAttribute("mode", "affichage");
        model.addAttribute("patient", patient);
        return "afficher";
    }
}
