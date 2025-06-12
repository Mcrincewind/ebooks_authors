package com.ebooks_authors.ebooks_authors.DomainLayer.Entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//Αυτό είναι το entity για τη δημιουργία του πίνακα Books, όπως βρίσκεται στη βάση μας, με κατάλληλα annotations και many-to-many σχέση, ώστε να το χρησιμοποιήσουμε.
public class Books {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (name = "isbn")
    private String isbn;

    @Column (name = "title")
    private String title;

    @Column (name = "category")
    private String category;

    @Column(name = "publication_year")
    private LocalDate publication_year;

    @ManyToMany
    @JoinTable(
        name = "books_authors",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id"))

        @JsonManagedReference
        private Set<Authors> authors = new HashSet<>();

}
