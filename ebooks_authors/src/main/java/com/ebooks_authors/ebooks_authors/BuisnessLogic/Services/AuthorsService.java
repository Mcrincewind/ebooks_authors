package com.ebooks_authors.ebooks_authors.BuisnessLogic.Services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebooks_authors.ebooks_authors.ApiLayer.ApiDTOS.AuthorsDTO;
import com.ebooks_authors.ebooks_authors.DomainLayer.Entity.Authors;
import com.ebooks_authors.ebooks_authors.DomainLayer.Entity.Books;
import com.ebooks_authors.ebooks_authors.DomainLayer.Repository.AuthorsRepository;
import com.ebooks_authors.ebooks_authors.DomainLayer.Repository.BooksRepository;

import jakarta.persistence.EntityNotFoundException;
//Αυτή είναι η κλάση Services για το Authors, η οποία θα μας κάνει όλη τη δουλειά ώστε να διαχειριστούμε τους συγγραφείς αλλά και το many-to-many (σχέση).
@Service
public class AuthorsService {
    @Autowired
    private AuthorsRepository authorsRepo;

    @Autowired 
    private BooksRepository booksRepo;

    //Δημιουργία συγγραφέα.
    public Authors createAuthor (AuthorsDTO dto){
        Authors authors = new Authors();

        authors.setName(dto.name);
        authors.setNationality(dto.nationality);
        authors.setBirth_date(dto.birth_date);

        if(dto.bookIds != null && !dto.bookIds.isEmpty()){
            Set <Books> books = new HashSet<>(booksRepo.findAllById(dto.bookIds));
            authors.setBooks(books);
        }

        return authorsRepo.save(authors);
    }

// Αναζήτηση συγγραφέα με ID
    public Authors getAuthorsById(Integer id) {
        return authorsRepo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
}

// Αναζήτηση όλων των συγγραφέων
    public List<Authors> getAllAuthors(){
        return authorsRepo.findAll();
    }

//Ενημέρωση συγγραφέα
    public Authors updateAuthors (Integer id, AuthorsDTO dto){
        Authors authors = getAuthorsById(id);

        authors.setName(dto.name);
        authors.setNationality(dto.nationality);
        authors.setBirth_date(dto.birth_date);

        if(dto.bookIds != null){
            Set<Books> books = new HashSet<>(booksRepo.findAllById(dto.bookIds));
            authors.setBooks(books);
        }

        return authorsRepo.save(authors);
    }

   //Διαγραφή συγγραφέων
    public void  deleteAuthros(Integer id){
        authorsRepo.deleteById(id);
    }

    //Αναζήτηση βιβλίων συγγραφέα
    public Set<Books> getBooksByAuthros(Integer id){
        Authors authors = getAuthorsById(id);
        return authors.getBooks();
    }

    //Αναζήτηση βιβλίου συνδεδεμένου με συγγραφέα
    public Authors assignBooksToAuthors(Integer authorsId, Set<Integer> booksIds){
        Authors authors = getAuthorsById(authorsId);
        Set<Books> books = new HashSet<>(booksRepo.findAllById(booksIds));

        if(books.isEmpty()){
            throw new EntityNotFoundException("not found books.");
        }
        authors.setBooks(books);
        return authorsRepo.save(authors);
    }

    //Καταμέτρηση συγγραφέων με βιβλία
    public int countBooksByAuthors(Integer authorsid){
        Authors authors = authorsRepo.findById(authorsid)
        .orElseThrow(() -> new EntityNotFoundException("Author not found with id " + authorsid));
        return authors.getBooks() != null ? authors.getBooks().size() : 0;
    }
}
