package com.example.aparna.booksconsoleservice.controller;

import com.example.aparna.booksconsoleservice.entities.Book;
import com.example.aparna.booksconsoleservice.entities.SearchDocResult;
import com.example.aparna.booksconsoleservice.entities.SearchResult;
import com.example.aparna.booksconsoleservice.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class BookController {

    private final String COVER_IMAGE_ROOT = "http://covers.openlibrary.org/b/id/";
    @Autowired
    BookRepo bookRepo;
    @Autowired
    WebClient webClient;

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

    @GetMapping(value = "/books/search")
    private String searchBook(@RequestParam String query, Model model){
        Mono<SearchResult> mono = webClient.get().uri("?q={query}", query).retrieve().bodyToMono(SearchResult.class);
        SearchResult result = mono.block();
        if(result == null){
            model.addAttribute("searchResults", new ArrayList<>());
        }else {
            List<SearchDocResult> books = result.getDocs()
                .stream()
                .limit(10)
                .map(bookResult -> {
                    bookResult.setKey(bookResult.getKey().replace("/works/", ""));
                    String coverId = bookResult.getCoverId();
                    if (!StringUtils.isEmpty(coverId)) {
                        coverId = COVER_IMAGE_ROOT + coverId + "-M.jpg";
                    } else {
                        coverId = "/images/no_image.png";
                    }
                    bookResult.setCoverId(coverId);
                    return bookResult;
                })
                .collect(Collectors.toList());

            model.addAttribute("searchResults", books);
        }

//        List<Book> books = new ArrayList<>();
//        model.addAttribute("totalResult", result !=null?result.getNumFound():0);
//        if(result != null){
//            List<SearchDocResult> results = result.getDocs();
//            results.stream().limit(10).forEach(doc->{
//                Book book = new Book();
//                book.setId(doc.getKey());
//                book.setName(doc.getTitle());
//                if(!StringUtils.isEmpty(doc.getCoverId())) {
//                    book.setCovers(Collections.singletonList(doc.getCoverId()));
//                }
//
//                List<String> authorIds = doc.getAuthorIds();
//                List<String> authorNames = doc.getAuthorNames();
//                if(authorIds.size() == authorNames.size()){
//                    Map<String, String> authors = new LinkedHashMap<>();
//                    for(int i=0; i< authorIds.size(); i++){
//                        authors.put(authorIds.get(i), authorNames.get(i));
//                    }
//                    book.setAuthors(authors);
//                }
//               books.add(book);
//            });
//        }
//        model.addAttribute("searchResult", books);
        return "search";
    }
}
