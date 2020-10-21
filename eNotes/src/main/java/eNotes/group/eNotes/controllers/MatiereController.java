package eNotes.group.eNotes.controllers;

import eNotes.group.eNotes.models.Admin;
import eNotes.group.eNotes.models.Filiere;
import eNotes.group.eNotes.repository.FiliereRepository;
import eNotes.group.eNotes.repository.MatiereRepository;
import eNotes.group.eNotes.models.Matiere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/matiere")
public class MatiereController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    MatiereRepository matiereRepository;

    @Autowired
    FiliereRepository filiereRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Matiere>> getAll(HttpServletRequest request) {
        return new ResponseEntity<>(matiereRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Matiere> getByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(matiereRepository.findMatiereByNom(name), HttpStatus.OK);
    }

    @GetMapping("/idMatier/{id}")
    public ResponseEntity<Matiere> getByIdMatiere(@PathVariable("id") String idMatiere) {
        return new ResponseEntity<>(matiereRepository.findMatiereById(idMatiere), HttpStatus.OK);
    }

    @GetMapping("/idEtudiant/{id}")
    public ResponseEntity<List<Matiere>> getByIdEtudiant(@PathVariable("id") String idEtudiant) {
        return new ResponseEntity<>(matiereRepository.findMatiereByIdEtudiant(idEtudiant), HttpStatus.OK);
    }

    @GetMapping("/ineEtudiant/{ine}")
    public ResponseEntity<List<Matiere>> getByIneEtudiant(@PathVariable("ine") String ineEtudiant) {
        return new ResponseEntity<>(matiereRepository.findMatiereByIneEtudiant(ineEtudiant), HttpStatus.OK);
    }

    @GetMapping("/idProf/{id}")
    public ResponseEntity<List<Matiere>> getByIdProf(@PathVariable("id") String idProf) {
        return new ResponseEntity<>(matiereRepository.findMatiereByIdProf(idProf), HttpStatus.OK);
    }

    @GetMapping("/ineProf/{ine}")
    public ResponseEntity<List<Matiere>> getByIneProf(@PathVariable("ine") String ineProf) {
        return new ResponseEntity<>(matiereRepository.findMatiereByIneProf(ineProf), HttpStatus.OK);
    }


    @GetMapping("/idFiliere/{id}")
    public ResponseEntity<List<Matiere>> getByIdFiliere(@PathVariable("id") String idFiliere) {
        return new ResponseEntity<>(matiereRepository.findMatiereByIdFiliere(idFiliere), HttpStatus.OK);
    }

    @GetMapping("/nomFiliere/{nom}")
    public ResponseEntity<List<Matiere>> getByNomFiliere(@PathVariable("nom") String nomFiliere) {
        return new ResponseEntity<>(matiereRepository.findMatiereByNomFiliere(nomFiliere), HttpStatus.OK);
    }


    @GetMapping("/note")
    public double getBynote(@RequestBody Matiere matire) {
        return matire.calculNotes();
    }

    @PostMapping("/addMatiereByFiliere/{idFiliere}")
    public ResponseEntity<Matiere> addMatiereByIdFiliere(@PathVariable( value= "idFiliere") String idFiliere,@RequestBody Matiere matiere){
        try {
            Filiere filiere= filiereRepository.findFiliereById(idFiliere);
            matiere.setIdFiliere(idFiliere);
            matiere.setNomFiliere(filiere.getNom());
            matiereRepository.save(matiere);
            return new ResponseEntity<>(matiere, HttpStatus.CREATED);
        } catch (Exception e){
            LOGGER.debug("la création de la matiere dans la  filiere est en échec");
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }
    @PostMapping("/addMatiere")
    public ResponseEntity<Matiere> addMatiere(@RequestBody Matiere matiere){
        try {
            matiereRepository.save(matiere);
            return new ResponseEntity<>(matiere, HttpStatus.CREATED);
        } catch (Exception e){
            LOGGER.debug("la création de la matiere est en échec");
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("allMatiere")
    public ResponseEntity<List<Matiere>> getAllMatiere(){
        return new ResponseEntity<>(matiereRepository.findAll(),HttpStatus.OK);
    }
    @PutMapping("/updateMatiere")
    public ResponseEntity<Matiere> updateMatiere(@RequestBody Matiere matiere){
        try {
            matiereRepository.save(matiere);
            return new ResponseEntity<>(matiere, HttpStatus.CREATED);
        } catch (Exception e){
            LOGGER.debug("la création de la filiere est en échec");
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/addNoteDs/{idMatiere}")
    public void addNoteDs(@PathVariable(value="idMatiere") String idMatiere, double notesDs){
        Matiere matiere= matiereRepository.findMatiereById(idMatiere);
        matiere.setHasDs(true);
        matiere.setNoteDs(notesDs);
        matiereRepository.save(matiere);
    }

    @DeleteMapping("/supprimerNoteDs/{idMatiere}")
    public void suppNoteDs (@PathVariable(value="idMatiere") String idMatiere){
        Matiere matiere= matiereRepository.findMatiereById(idMatiere);
        matiere.setHasDs(false);
        matiereRepository.save(matiere);
    }

    @PostMapping("/addNotePartiel/{idMatiere}")
    public void addNotePartiel(@PathVariable(value="idMatiere") String idMatiere, double notePartiel){
        Matiere matiere= matiereRepository.findMatiereById(idMatiere);
        matiere.setNotePartiel(notePartiel);
        matiereRepository.save(matiere);
    }

    @PostMapping("/calculNoteFinal/{idMatiere}")
    public void notePartiel(@PathVariable(value="idMatiere") String idMatiere){
        Matiere matiere= matiereRepository.findMatiereById(idMatiere);
        matiere.calculNotes();
        matiereRepository.save(matiere);
    }


    @DeleteMapping(value = "/deleteMatiereByid/{idMatiere}")
    public String deleteMatiere(@PathVariable(value = "idMatiere") String idMatiere) {
        LOGGER.debug("Suppression matiere par id matiere : {}.", idMatiere);
        try {
            matiereRepository.deleteMatiereById(idMatiere);
            LOGGER.debug("La  matiere  : {}.", idMatiere + "a été supprimée");
            return ("La  matiere  : {}." + idMatiere + "a été supprimée");
        }catch (Exception e){
            return "La matiere " + idMatiere + "n'existe pas pour etre supprimée";
        }
    }

}