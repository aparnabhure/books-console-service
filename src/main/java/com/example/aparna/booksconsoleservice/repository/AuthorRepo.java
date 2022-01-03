package com.example.aparna.booksconsoleservice.repository;

import com.example.aparna.booksconsoleservice.entities.Author;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepo extends CassandraRepository<Author, String> {

}
