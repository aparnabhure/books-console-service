package com.example.aparna.booksconsoleservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchDocResult {
    String key;
    String title;
    @JsonProperty("first_publish_year")
    int publishYear;
    @JsonProperty("cover_i")
    String coverId;
    @JsonProperty("author_name")
    List<String> authorNames;
    @JsonProperty("author_key")
    List<String> authorIds;
}
