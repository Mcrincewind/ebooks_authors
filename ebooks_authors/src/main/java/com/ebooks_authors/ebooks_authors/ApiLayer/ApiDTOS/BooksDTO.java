package com.ebooks_authors.ebooks_authors.ApiLayer.ApiDTOS;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//Books DTO (Data Transfer Object) με κατάλληλα validation για errors, ώστε να βοηθήσουμε τον χρήστη στη σωστή
// συμπλήρωση και για να επικοινωνούμε πλέον με το public (interface) έξω από το πρόγραμμα.
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BooksDTO {
    public  Integer id;

    @NotBlank(message = "Το ISBN είναι υποχρεωτικό.")
    @Size(min =10, max =13, message ="Το ISBN αναγκαστικά πρέπει να έχει 10 με 13 χαρακτήρες, παρακαλώ.")
    public String isbn;

    @NotBlank(message =  "Ο τίτλος είναι υποχρεωτικός.")
    public String title;

    @NotBlank(message = "Η κατηγορία είναι υποχρεωτική.")
    public String category;

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate publication_year;

    public Set<Integer> authorsIds;

}
