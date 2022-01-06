package com.example.aparna.booksconsoleservice.controller;

import com.example.aparna.booksconsoleservice.entities.Book;
import com.example.aparna.booksconsoleservice.entities.UserBook;
import com.example.aparna.booksconsoleservice.entities.UserBooksPrimaryKey;
import com.example.aparna.booksconsoleservice.repository.BookRepo;
import com.example.aparna.booksconsoleservice.repository.UserBooksRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class UserBooksController {

    @Autowired
    UserBooksRepo userBooksRepo;
    @Autowired
    BookRepo bookRepo;

   @PostMapping("/addUserBook")
    public ModelAndView addBookForUser(@RequestBody MultiValueMap<String, String> formData,
                                 @AuthenticationPrincipal OAuth2User principal){

       if (principal == null || principal.getAttribute("login") == null) {
           return null;
       }

       String bookId = formData.getFirst("bookId");
       if(StringUtils.isEmpty(bookId)){
           return null;
       }

       Optional<Book> optionalBook = bookRepo.findById(bookId);
       if (!optionalBook.isPresent()) {
           return new ModelAndView("redirect:/");
       }
       Book book = optionalBook.get();

       UserBook userBook  = new UserBook();
       UserBooksPrimaryKey key = new UserBooksPrimaryKey();
       String userId = principal.getAttribute("login");
       key.setUserId(userId);
       key.setBookId(bookId);

       userBook.setKey(key);

       int rating = Integer.parseInt(formData.getFirst("rating"));

       userBook.setStartDate(LocalDate.parse(formData.getFirst("startDate")));
       userBook.setCompleteDate(LocalDate.parse(formData.getFirst("completedDate")));
       userBook.setRating(rating);
       userBook.setReadingStatus(formData.getFirst("readingStatus"));

       userBooksRepo.save(userBook);

//       BooksByUser booksByUser = new BooksByUser();
//       booksByUser.setId(userId);
//       booksByUser.setBookId(bookId);
//       booksByUser.setBookName(book.getName());
//       booksByUser.setCoverIds(book.getCoverIds());
//       booksByUser.setAuthorNames(book.getAuthorNames());
//       booksByUser.setReadingStatus(formData.getFirst("readingStatus"));
//       booksByUser.setRating(rating);
//       booksByUserRepository.save(booksByUser);


       return new ModelAndView("redirect:/books/" + bookId);
   }
}
