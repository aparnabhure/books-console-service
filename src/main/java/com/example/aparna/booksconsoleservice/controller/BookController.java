package com.example.aparna.booksconsoleservice.controller;

import com.example.aparna.booksconsoleservice.entities.Book;
import com.example.aparna.booksconsoleservice.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class BookController {

    private final String COVER_IMAGE_ROOT = "http://covers.openlibrary.org/b/id/";
    @Autowired
    BookRepo bookRepo;

    @GetMapping(value = "/books/{bookId}")
    private String getBookById(@PathVariable String bookId, Model model){
        Optional<Book> opt = bookRepo.findById(bookId);
        if(opt.isPresent()){
            Book book = opt.get();
            String coverImageUrl = "/images/no_image.png";
            if (book.getCovers() != null && !book.getCovers().isEmpty()) {
                coverImageUrl = COVER_IMAGE_ROOT + book.getCovers().get(0) + "-L.jpg";
            }
            model.addAttribute("coverImage", coverImageUrl);
            model.addAttribute("book", book);
            return "book";
        }
        return "book-not-found";
    }
}
