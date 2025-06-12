package com.ebooks_authors.ebooks_authors.BuisnessLogic.Conrollers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ebooks_authors.ebooks_authors.ApiLayer.ApiDTOS.AuthorsDTO;
import com.ebooks_authors.ebooks_authors.BuisnessLogic.Services.AuthorsService;
import com.ebooks_authors.ebooks_authors.DomainLayer.Entity.Authors;

import jakarta.validation.Valid;


//"Η κλάση του controller για τους συγγραφείς είναι υπεύθυνη για τη χαρτογράφηση (mapping) και για να ζητάει από την υπηρεσία (service) να κάνει τη διαχείριση."
@RestController
@RequestMapping("ebooksdp/authors")
public class AuthorsController {

    @Autowired
    private AuthorsService authorsService;

    // mapping Δημιουργία Συγγραφέων
    @PostMapping
    public ResponseEntity<Authors> createAuthors(@Valid @RequestBody AuthorsDTO dto){
        return ResponseEntity.ok(authorsService.createAuthor(dto));
    }

    //Λήψη Συγγραφέων
    @GetMapping
    public ResponseEntity<List<Authors>> getAllAuthors(){
        return ResponseEntity.ok(authorsService.getAllAuthors());
    }

    //Λήψη Συγκεκριμένου Συγγραφέα
    @GetMapping("/{id}")
    public ResponseEntity<Authors> getAuthorsById(@PathVariable Integer id){
        return ResponseEntity.ok(authorsService.getAuthorsById(id));
    }
    // update author
    @PutMapping("/{id}")
    public ResponseEntity<Authors> updateAuthors(@PathVariable Integer id, @Valid @RequestBody AuthorsDTO dto){
        return ResponseEntity.ok(authorsService.updateAuthors(id, dto));
    }

    // Διαγραφή author
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthors(@PathVariable Integer id) {
        authorsService.deleteAuthros(id);
        return ResponseEntity.noContent().build();
}

    //Ανάθεση Συγγραφέα σε Βιβλίο
    @GetMapping("/{id}/books")
    public ResponseEntity<Set<?>> getBooksByAuthors(@PathVariable Integer id){
        return ResponseEntity.ok(authorsService.getBooksByAuthros(id));
    }

    //Καταμέτρηση Βιβλίων και Συγγραφέων
    @GetMapping("/{id}/books/count")
    public ResponseEntity<Integer> getBooksCount(@PathVariable Integer id){
        int count = authorsService.countBooksByAuthors(id);
        return ResponseEntity.ok(count);
    }




}
