package sn.ec2lt.library.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ec2lt.library.entity.Emprunt;
import sn.ec2lt.library.entity.Livre;
import sn.ec2lt.library.service.EmpruntService;
import sn.ec2lt.library.service.LivreService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/emprunts")
public class EmpruntController {
    private final EmpruntService empruntService;
    private final LivreService livreService;

    public EmpruntController(EmpruntService empruntService, LivreService livreService) {
        this.empruntService = empruntService;
        this.livreService = livreService;
    }

    @PostMapping
    public ResponseEntity<Emprunt> createEmprunt(@RequestBody Emprunt emprunt) {
        Livre livre = livreService.getLivre(emprunt.getIdLivre());
        if (livre != null && livre.isDisponible()) {
            livre.setDisponible(false);
            livreService.saveLivre(livre);
            emprunt.setDateEmprunt(new Date());
            return new ResponseEntity<>(empruntService.saveEmprunt(emprunt), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/retour/{id}")
    public ResponseEntity<Emprunt> retournerEmprunt(@PathVariable("id") Long id) {
        Emprunt emprunt = empruntService.getEmprunt(id);
        if (emprunt != null && emprunt.getDateRetour() == null) {
            emprunt.setDateRetour(new Date());
            Livre livre = livreService.getLivre(emprunt.getIdLivre());
            livre.setDisponible(true);
            livreService.saveLivre(livre);
            return new ResponseEntity<>(empruntService.saveEmprunt(emprunt), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/utilisateur/{userId}")
    public ResponseEntity<List<Emprunt>> getEmpruntsByUser(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(empruntService.getEmpruntsByUser(userId), HttpStatus.OK);
    }

}
