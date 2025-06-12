package com.ebooks_authors.ebooks_authors.ApiLayer.ApiDTOS;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//Author DTO (Data Transfer Object) με κατάλληλα validation για errors, ώστε να βοηθήσουμε τον χρήστη στη σωστή
// συμπλήρωση και για να επικοινωνούμε πλέον με το public (interface) έξω από το πρόγραμμα.
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorsDTO {
    private Integer id;

    @NotBlank(message = "Το όνομα είναι υποχρεωτικό.")
    public String name;

    @NotBlank(message = "Η εθνικότητα είναι υποχρεωτική.")
    public String nationality;

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate birth_date;

    public Set<Integer> bookIds;
}
