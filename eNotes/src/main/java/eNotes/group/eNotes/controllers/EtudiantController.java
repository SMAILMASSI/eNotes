package eNotes.group.eNotes.controllers;

import eNotes.group.eNotes.models.Etudiant;
import eNotes.group.eNotes.models.Prof;
import eNotes.group.eNotes.repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etudiant")
public class EtudiantController {

    @Autowired
    EtudiantRepository etudiantRepository;
   /*  @PostMapping("/signin")
    public ResponseEntity<Etudiant> authenticateUser(@RequestBody Etudiant etudiant) {
        if(etudiant.equals(etudiantRepository.findEtudiantByIneAndDateNaissance(etudiant.getIne(),etudiant.getDateNaissance()))){
            System.out.println("etudiant   1 er post ="+ etudiant.getDateNaissance() + etudiant.getIne()+ etudiant.getNom());
            return new ResponseEntity<>(etudiant,HttpStatus.OK);
        }else {
            System.out.println("erreu");
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }
    */
   @PostMapping("/signin/{ine}/{dateNaissance}")
   public ResponseEntity<Etudiant> authenticateEtudiant(@PathVariable ("ine") String ine,@PathVariable("dateNaissance") String dateNaissance) {
       try{
           Etudiant etudiant= etudiantRepository.findEtudiantByIneAndDateNaissance(ine,dateNaissance);
           System.out.println("etudiant ="+ etudiant.getDateNaissance() + etudiant.getIne()+ etudiant.getNom());
           return new ResponseEntity<>(etudiant,HttpStatus.OK);
       }catch( Exception e) {
           return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
       }
   }


    @PostMapping("/addEtudiant")
    public ResponseEntity<Etudiant> addEtudiant(@RequestBody Etudiant etudiant){
        Etudiant e= etudiantRepository.save(etudiant);
        return new ResponseEntity<>(e, HttpStatus.ACCEPTED);
    }

    @GetMapping("allEtudiant")
    public ResponseEntity<List<Etudiant>> getAllEtudiant(){
        return new ResponseEntity<>(etudiantRepository.findAll(),HttpStatus.OK);
    }
}
