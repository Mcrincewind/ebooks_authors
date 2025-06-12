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

import com.ebooks_authors.ebooks_authors.ApiLayer.ApiDTOS.BooksDTO;
import com.ebooks_authors.ebooks_authors.BuisnessLogic.Services.BooksService;
import com.ebooks_authors.ebooks_authors.DomainLayer.Entity.Authors;
import com.ebooks_authors.ebooks_authors.DomainLayer.Entity.Books;

import jakarta.validation.Valid;
// "Η κλάση του controller για τα βιβλία είναι υπεύθυνη για τη χαρτογράφηση (mapping) και για να ζητάει από την υπηρεσία (service) να κάνει τη διαχείριση."
@RestController
@RequestMapping("/ebooksdp/books")
public class BooksController {

        @Autowired
    private BooksService booksService;

    //Δημιουργία βιβλίου
    @PostMapping
    public ResponseEntity<Books> createBook(@Valid @RequestBody BooksDTO dto) {
        Books savedBook = booksService.createBook(dto);
        return ResponseEntity.ok(savedBook);
    }

    // Λήψη Όλων των Βιβλίων
    @GetMapping
    public ResponseEntity<List<Books>> getAllBooks() {
        return ResponseEntity.ok(booksService.getAllBooks());
    }

    // Λήψη βιβλίου με ID
    @GetMapping("/{id}")
    public ResponseEntity<Books> getBookById(@PathVariable Integer id) {
        return ResponseEntity.ok(booksService.getBooksById(id));
    }

    //Ενημέρωση Βιβλίου
    @PutMapping("/{id}")
    public ResponseEntity<Books> updateBook(@PathVariable Integer id, @Valid @RequestBody BooksDTO dto) {
        Books updatedBook = booksService.updateBooks(id, dto);
        return ResponseEntity.ok(updatedBook);
    }

    // Διαγραφή Βιβλίου
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        booksService.deleteBooks(id);
        return ResponseEntity.noContent().build();
    }

    //Ανάθεση βιβλίου σε συγγραφέα
    @PutMapping("{id}/authors")
    public ResponseEntity<Books> assignAuthorsToBook(@PathVariable Integer id, @RequestBody Set<Integer> authorIds) {
        Books updatedBook = booksService.assignAuthorsTBooks(id, authorIds);
        return ResponseEntity.ok(updatedBook);
    }

    //Σου επιστρέφει τους συγγραφείς που έχουν γράψει το βιβλίο με id
    @GetMapping("/{id}/authors")
    public ResponseEntity<Set<Authors>> getAuthorsByBook(@PathVariable Integer id) {
    Set<Authors> authors = booksService.getAuthorsByBook(id);
    return ResponseEntity.ok(authors);
}

    //Πόσους συγγραφείς έχει αυτό το βιβλίο
    @GetMapping("/{id}/authors/count")
    public ResponseEntity<Integer> getAuthorsCount(@PathVariable Integer id) {
        int count = booksService.countAuthorsByBook(id);
        return ResponseEntity.ok(count);
    }

}
