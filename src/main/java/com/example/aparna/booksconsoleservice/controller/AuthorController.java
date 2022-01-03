package com.example.aparna.booksconsoleservice.controller;

import com.example.aparna.booksconsoleservice.entities.Author;
import com.example.aparna.booksconsoleservice.entities.Book;
import com.example.aparna.booksconsoleservice.repository.AuthorRepo;
import com.example.aparna.booksconsoleservice.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class AuthorController {

    @Autowired
    AuthorRepo authorRepo;

    @GetMapping(value = "/authors/{authorId}")
    private String getAuthorById(@PathVariable String authorId, Model model){
        Optional<Author> opt = authorRepo.findById(authorId);
        if(opt.isPresent()){
            Author author = opt.get();
            model.addAttribute("author", author);
            return "author";
        }
        return "author-not-found";
    }
}
