package com.example.aparna.booksconsoleservice.repository;

import com.example.aparna.booksconsoleservice.entities.BooksByUser;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksByUserRepo extends CassandraRepository<BooksByUser, String> {
    Slice<BooksByUser> findAllById(String id, Pageable pageable);
}
