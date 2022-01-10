package com.example.aparna.booksconsoleservice.controller;

import com.example.aparna.booksconsoleservice.entities.Author;
import com.example.aparna.booksconsoleservice.entities.BooksByUser;
import com.example.aparna.booksconsoleservice.repository.AuthorRepo;
import com.example.aparna.booksconsoleservice.repository.BooksByUserRepo;
import com.example.aparna.booksconsoleservice.repository.UserBooksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.thymeleaf.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    BooksByUserRepo booksByUserRepo;

    private final String COVER_IMAGE_ROOT = "http://covers.openlibrary.org/b/id/";

    @GetMapping(value = "/home")
    private String home(@AuthenticationPrincipal OAuth2User principal, Model model){
        if(principal == null || principal.getAttribute("login") == null){
            return "index";
        }

        String userId = principal.getAttribute("login");
        if(!StringUtils.isEmpty(userId)){
            Slice<BooksByUser> books = booksByUserRepo.findAllById(userId, CassandraPageRequest.of(0, 50));
            List<BooksByUser> booksByUsers = books.getContent();
            booksByUsers = booksByUsers.stream().distinct().map(book -> {
                String coverImageUrl = "/images/no_image.png";
                if (!CollectionUtils.isEmpty(book.getCoverIds())) {
                    coverImageUrl = COVER_IMAGE_ROOT + book.getCoverIds().get(0) + "-M.jpg";
                }
                book.setCoverUrl(coverImageUrl);
                return book;
            }).collect(Collectors.toList());
            model.addAttribute("books", booksByUsers);
            return "home";
        }

        return "index";
    }
}
