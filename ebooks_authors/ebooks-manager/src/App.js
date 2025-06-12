import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';

const baseURL = 'http://localhost:8080/ebooksdp';

const formatDate = (dateStr) => {
  if (!dateStr) return '';
  const date = new Date(dateStr);
  return isNaN(date) ? dateStr : date.toISOString().split('T')[0];
};

function App() {
  const [books, setBooks] = useState([]);
  const [authors, setAuthors] = useState([]);
  const [authorsByBook, setAuthorsByBook] = useState([]);
  const [form, setForm] = useState({
    isbn: '',
    title: '',
    category: '',
    publication_year: '',
    authorName: '',
    authorNationality: '',
    authorBirthDate: '',
    deleteBookId: '',
    deleteAuthorId: '',
    assignBookId: '',
    assignAuthorId: '',
    showAuthorsBookId: '',
  });

  const fetchBooks = () => {
    axios.get(`${baseURL}/books`)
      .then(res => setBooks(res.data))
      .catch(() => alert('Σφάλμα στην φόρτωση βιβλίων'));
  };

  const fetchAuthors = () => {
    axios.get(`${baseURL}/authors`)
      .then(res => setAuthors(res.data))
      .catch(() => alert('Σφάλμα στην φόρτωση συγγραφέων'));
  };

  const handleChange = (e) => {
    setForm({ ...form, [e.target.id]: e.target.value });
  };

  const handleAddBook = (e) => {
    e.preventDefault();
    const { isbn, title, category, publication_year } = form;
    axios.post(`${baseURL}/books`, { isbn, title, category, publication_year })
      .then(() => {
        alert('Το βιβλίο προστέθηκε!');
        setForm({ ...form, isbn: '', title: '', category: '', publication_year: '' });
        fetchBooks();
      })
      .catch(() => alert('Σφάλμα κατά την προσθήκη βιβλίου'));
  };

  const handleAddAuthor = (e) => {
    e.preventDefault();
    const { authorName, authorNationality, authorBirthDate } = form;
    axios.post(`${baseURL}/authors`, {
      name: authorName,
      nationality: authorNationality,
      birth_date: authorBirthDate
    })
      .then(() => {
        alert('Ο συγγραφέας προστέθηκε!');
        setForm({ ...form, authorName: '', authorNationality: '', authorBirthDate: '' });
        fetchAuthors();
      })
      .catch(() => alert('Σφάλμα κατά την προσθήκη συγγραφέα'));
  };

  const handleDeleteBook = (e) => {
    e.preventDefault();
    axios.delete(`${baseURL}/books/${form.deleteBookId}`)
      .then(() => {
        alert('Το βιβλίο διαγράφηκε!');
        fetchBooks();
      })
      .catch(() => alert('Σφάλμα κατά τη διαγραφή βιβλίου'));
  };

  const handleDeleteAuthor = (e) => {
    e.preventDefault();
    axios.delete(`${baseURL}/authors/${form.deleteAuthorId}`)
      .then(() => {
        alert('Ο συγγραφέας διαγράφηκε!');
        fetchAuthors();
      })
      .catch(() => alert('Σφάλμα κατά τη διαγραφή συγγραφέα'));
  };

  const handleAssignAuthor = (e) => {
  e.preventDefault();
  axios.put(`${baseURL}/books/${form.assignBookId}/authors`, [form.assignAuthorId])
    .then(() => {
      alert('Ο συγγραφέας ανατέθηκε στο βιβλίο!');
      fetchBooks();
      fetchAuthors();
    })
    .catch(() => alert('Σφάλμα κατά την ανάθεση συγγραφέα'));
};

  const handleShowAuthorsByBook = (e) => {
    e.preventDefault();
    axios.get(`${baseURL}/books/${form.showAuthorsBookId}`)
      .then(res => {
        setAuthorsByBook(res.data.authors || []);
      })
      .catch(() => alert('Σφάλμα στην εμφάνιση συγγραφέων για το βιβλίο'));
  };

  return (
    <div className="container">
      <h1>Διαχείριση Βιβλίων & Συγγραφέων</h1>

      <div className="flex-container">
        <div>
          <button onClick={fetchBooks} className="btn">Εμφάνιση Βιβλίων</button>
          <div className="list">
            {books.map(book => (
              <div className="list-item" key={book.id}>
                <b>{book.title}</b> (ID: {book.id})<br />
                <small><b>ISBN:</b> {book.isbn}</small><br />
                <small><b>Κατηγορία:</b> {book.category}</small><br />
                <small><b>Ημ. Δημοσίευσης:</b> {formatDate(book.publication_year)}</small><br />
                <small><b>Συγγραφείς:</b> {book.authors?.length ? book.authors.map(a => a.name).join(', ') : 'Κανένας'}</small>
              </div>
            ))}
          </div>
        </div>

        <div>
          <button onClick={fetchAuthors} className="btn">Εμφάνιση Συγγραφέων</button>
          <div className="list">
            {authors.map(author => (
              <div className="list-item" key={author.id}>
                <b>{author.name}</b> (ID: {author.id})<br />
                <small><b>Εθνικότητα:</b> {author.nationality}</small><br />
                <small><b>Ημ. Γέννησης:</b> {formatDate(author.birth_date)}</small><br />
              </div>
            ))}
          </div>
        </div>
      </div>

      <h2>Προσθήκη Βιβλίου</h2>
      <form onSubmit={handleAddBook}>
        <input id="isbn" placeholder="ISBN" value={form.isbn} onChange={handleChange} required />
        <input id="title" placeholder="Τίτλος" value={form.title} onChange={handleChange} required />
        <input id="category" placeholder="Κατηγορία" value={form.category} onChange={handleChange} required />
        <input id="publication_year" type="date" value={form.publication_year} onChange={handleChange} required />
        <button type="submit" className="btn">Πρόσθεσε Βιβλίο</button>
      </form>

      <h2>Προσθήκη Συγγραφέα</h2>
      <form onSubmit={handleAddAuthor}>
        <input id="authorName" placeholder="Όνομα" value={form.authorName} onChange={handleChange} required />
        <input id="authorNationality" placeholder="Εθνικότητα" value={form.authorNationality} onChange={handleChange} required />
        <input id="authorBirthDate" type="date" value={form.authorBirthDate} onChange={handleChange} required />
        <button type="submit" className="btn">Πρόσθεσε Συγγραφέα</button>
      </form>

      <h2>Διαγραφή Βιβλίου</h2>
      <form onSubmit={handleDeleteBook}>
        <input id="deleteBookId" type="number" placeholder="ID Βιβλίου" value={form.deleteBookId} onChange={handleChange} required />
        <button type="submit" className="btn">Διαγραφή Βιβλίου</button>
      </form>

      <h2>Διαγραφή Συγγραφέα</h2>
      <form onSubmit={handleDeleteAuthor}>
        <input id="deleteAuthorId" type="number" placeholder="ID Συγγραφέα" value={form.deleteAuthorId} onChange={handleChange} required />
        <button type="submit" className="btn">Διαγραφή Συγγραφέα</button>
      </form>

      <h2>Ανάθεση Συγγραφέα σε Βιβλίο</h2>
      <form onSubmit={handleAssignAuthor}>
        <input id="assignBookId" type="number" placeholder="ID Βιβλίου" value={form.assignBookId} onChange={handleChange} required />
        <input id="assignAuthorId" type="number" placeholder="ID Συγγραφέα" value={form.assignAuthorId} onChange={handleChange} required />
        <button type="submit" className="btn">Ανάθεσε Συγγραφέα</button>
      </form>

      <h2>Συγγραφείς ανά Βιβλίο</h2>
      <form onSubmit={handleShowAuthorsByBook}>
        <input id="showAuthorsBookId" type="number" placeholder="ID Βιβλίου" value={form.showAuthorsBookId} onChange={handleChange} required />
        <button type="submit" className="btn">Εμφάνισε Συγγραφείς</button>
      </form>
      <div className="list">
        {authorsByBook.length === 0
          ? <p>Δεν υπάρχουν συγγραφείς για αυτό το βιβλίο.</p>
          : authorsByBook.map(a => (
              <p key={a.id}>{a.name} (ID: {a.id}) - Εθνικότητα: {a.nationality}</p>
            ))}
      </div>
    </div>
  );
}

export default App;

