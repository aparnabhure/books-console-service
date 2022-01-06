package com.example.aparna.booksconsoleservice.repository;

import com.example.aparna.booksconsoleservice.entities.Book;
import com.example.aparna.booksconsoleservice.entities.UserBook;
import com.example.aparna.booksconsoleservice.entities.UserBooksPrimaryKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBooksRepo extends CassandraRepository<UserBook, UserBooksPrimaryKey> {

}
