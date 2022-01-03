package com.example.aparna.booksconsoleservice.repository;

import com.example.aparna.booksconsoleservice.entities.Book;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends CassandraRepository<Book, String> {

}
