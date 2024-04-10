package com.example.appliedjavaproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="api/v1")
@CrossOrigin(origins = "*")
public class MainController {

    public static final String BOOK = "/books";
    public static final String AUTHOR = "/authors";

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRespository authorRepository;

    @GetMapping(path=BOOK)
    public @ResponseBody
    Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping(path = BOOK + "/{isbn}")
    @CrossOrigin(origins = "*")
    public @ResponseBody
    Book getBookWithId(@PathVariable String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @PostMapping(path = BOOK)
    @CrossOrigin(origins = "*")
    public @ResponseBody
    String addNewBook(@RequestParam String isbn, @RequestParam String title, @RequestParam int editionNumber, @RequestParam String copyright, @RequestParam Integer author_id){
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setEdNumber(editionNumber);
        book.setCopyright(copyright);
        Optional<Author> author = authorRepository.findById(author_id);
        if(author.isPresent()){
            book.getAuthors().add(author.get());
            bookRepository.save(book);
            return "Saved";
        }
        return "Author not found";
    }

    @PutMapping(path = BOOK + "/{isbn}")
    public @ResponseBody
    String updateBook(@PathVariable String isbn, @RequestParam String title, @RequestParam int editionNumber, @RequestParam String copyright){
        Book book = bookRepository.findByIsbn(isbn);
        if(book != null){
            book.setTitle(title);
            book.setEdNumber(editionNumber);
            book.setCopyright(copyright);
            bookRepository.save(book);
            return "Updated";
        }
        return "Book not found";
    }

    @DeleteMapping(path = BOOK + "/{isbn}")
    public @ResponseBody
    String deleteBook(@PathVariable String isbn){
        Book book = bookRepository.findByIsbn(isbn);
        if(book != null){
            bookRepository.delete(book);
            return "Deleted";
        }
        return "Book not found";
    }

    @GetMapping(path = AUTHOR)
    public @ResponseBody
    Iterable<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @GetMapping(path = AUTHOR+"/{author_id}")
    @CrossOrigin(origins = "*")
    public @ResponseBody
    Optional<Author> getAuthorWithId(@PathVariable Integer author_id){
        return authorRepository.findById(author_id);
    }

    @PostMapping(path = AUTHOR)
    public @ResponseBody
    String addNewAuthor(@RequestParam String firstName, @RequestParam String lastName){
        Author author = new Author();
        author.setAuthorFirstName(firstName);
        author.setAuthorLastName(lastName);
        authorRepository.save(author);
        return "Saved";
    }

    @PutMapping(path = AUTHOR + "/{author_id}")
    public @ResponseBody
    String updateAuthor(@PathVariable Integer author_id, @RequestParam String firstName, @RequestParam String lastName){
        Optional<Author> author = authorRepository.findById(author_id);
        System.out.println(author_id);
        if(author.isPresent()){
            Author author1 = author.get();
            author1.setAuthorFirstName(firstName);
            author1.setAuthorLastName(lastName);
            authorRepository.save(author1);
            return "updated";
        }
        return "Author not found";
    }

    @DeleteMapping(path = AUTHOR + "/{author_id}")
    public @ResponseBody
    String deleteAuthor(@PathVariable Integer author_id){
        Optional<Author> author = authorRepository.findById(author_id);
        if(author.isPresent()){
            authorRepository.delete(author.get());
            return "Deleted";
        }
        return "Author not found";
    }
}