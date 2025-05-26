package sn.ec2lt.library.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ec2lt.library.entity.Livre;
import sn.ec2lt.library.service.LivreService;

import java.util.List;

@RestController
@RequestMapping("/livres")
public class LivreController {
    private final LivreService livreService;

    public LivreController(LivreService livreService) {
        this.livreService = livreService;
    }

    @PostMapping
    public ResponseEntity<Livre> createLivre(@RequestBody Livre livre) {
        try {
            Livre savedLivre = livreService.saveLivre(livre);
            return new ResponseEntity<>(savedLivre, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Livre>> getLivres() {
        return new ResponseEntity<>(livreService.getLivres(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livre> getLivre(@PathVariable("id") Long id) {
        Livre livre = livreService.getLivre(id);
        if (livre == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(livre, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livre> updateLivre(@PathVariable("id") Long id, @RequestBody Livre livre) {
        Livre existingLivre = livreService.getLivre(id);
        if (existingLivre == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        livre.setId(id);
        return new ResponseEntity<>(livreService.saveLivre(livre), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivre(@PathVariable("id") Long id) {
        Livre existingLivre = livreService.getLivre(id);
        if (existingLivre == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        livreService.deleteLivre(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}