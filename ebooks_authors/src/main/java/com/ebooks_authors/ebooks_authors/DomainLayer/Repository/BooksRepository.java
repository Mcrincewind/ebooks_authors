package com.ebooks_authors.ebooks_authors.DomainLayer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks_authors.ebooks_authors.DomainLayer.Entity.Books;
//Repository για το Books με JpaRepository θα μας βοηθήσει ώστε να κάνουμε set, get, update, delete με αυτά που θα μας δώσει για το service κ.λπ.
@Repository
public interface BooksRepository extends JpaRepository<Books, Integer> {
}
