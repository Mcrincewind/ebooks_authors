package com.ebooks_authors.ebooks_authors.DomainLayer.Entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

//Αυτό το entity είναι η δημιουργία του πίνακα Authors όπως βρίσκεται στη βάση μας με κατάλληλα annotations ώστε να το χρησιμοποιήσουμε.
public class Authors {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name" )
    private String name;

    @Column (name = "nationality")
    private String nationality;

    @Column (name = "birthDate")
    private LocalDate birth_date;

    @ManyToMany (mappedBy = "authors")
    @JsonBackReference
    private Set<Books> books = new HashSet<>();

}
