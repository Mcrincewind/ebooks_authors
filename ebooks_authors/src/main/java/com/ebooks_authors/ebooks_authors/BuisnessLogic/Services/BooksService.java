package com.ebooks_authors.ebooks_authors.BuisnessLogic.Services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebooks_authors.ebooks_authors.ApiLayer.ApiDTOS.BooksDTO;
import com.ebooks_authors.ebooks_authors.DomainLayer.Entity.Authors;
import com.ebooks_authors.ebooks_authors.DomainLayer.Entity.Books;
import com.ebooks_authors.ebooks_authors.DomainLayer.Repository.AuthorsRepository;
import com.ebooks_authors.ebooks_authors.DomainLayer.Repository.BooksRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
//"Υπηρεσία βιβλίου (book service), ώστε να μπορούμε να κάνουμε ενημέρωση, διαγραφή, ανάκτηση με βάση το αναγνωριστικό (ID) και ανάκτηση όλων. 
//Επίσης, μια επιπλέον λειτουργία για να καθορίζουμε το βιβλίο με τον συγγραφέα
// για τη σχέση 'πολλά προς πολλά' (many-to-many)."
public class BooksService {

    @Autowired
    private BooksRepository booksRepo;

    @Autowired
    private AuthorsRepository AuthorsRepo;

    //Δημιουργία Βιβλίου
    public Books createBook(BooksDTO dto){
    Books books = new Books();

    books.setTitle(dto.title);
    books.setIsbn(dto.isbn);
    books.setCategory(dto.category);
    books.setPublication_year(dto.publication_year);

    if(dto.authorsIds != null && !dto.authorsIds.isEmpty()){
        List<Authors> authorsList = AuthorsRepo.findAllById(dto.authorsIds);
        books.setAuthors(new HashSet<>(authorsList));
    }
    else{
        books.setAuthors(new HashSet<>());
    }

    return booksRepo.save(books);
}

    //Αναζήτηση Όλων των Βιβλίων
    public List<Books> getAllBooks(){
        return booksRepo.findAll();
    }

    //Αναζήτηση Βιβλίου με ID
    public Books getBooksById(Integer id){
        return booksRepo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Book not found : " + id));
    }

    //Ενημέρωση Βιβλίου
    public Books updateBooks(Integer id, BooksDTO dto){
        Books books = getBooksById(id);

        books.setTitle(dto.title);
        books.setIsbn(dto.isbn);
        books.setCategory(dto.category);
        books.setPublication_year(dto.publication_year);

        if(dto.authorsIds !=null){
            Set<Authors> authors = new HashSet<>(AuthorsRepo.findAllById(dto.authorsIds));
            books.setAuthors(authors);
        }
        return booksRepo.save(books);
    }

    //Διαγραφή Βιβλίου
    public void deleteBooks(Integer id){
        booksRepo.deleteById(id);
    }

    //Ανάθεση Βιβλίου σε Συγγραφέα
    public Books assignAuthorsTBooks(Integer bookId, Set<Integer> authorsids){
        Books books = getBooksById(bookId);
        Set<Authors> exiAuthorses = books.getAuthors();
        Set<Authors> authorsToAdd = new HashSet<>(AuthorsRepo.findAllById(authorsids));
        exiAuthorses.addAll(authorsToAdd);
        books.setAuthors(authorsToAdd);

        return booksRepo.save(books);
    }

    //Εμφάνιση Συγγραφέων και Βιβλίων
    public Set<Authors> getAuthorsByBook(Integer bookId){
        Books books = getBooksById(bookId);
        return books.getAuthors();
    }

    //Καταμέτρηση βιβλίων
    public int countAuthorsByBook(Integer bookId) {
        Books books = booksRepo.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));
        return books.getAuthors() != null ? books.getAuthors().size() : 0;
    }

}
