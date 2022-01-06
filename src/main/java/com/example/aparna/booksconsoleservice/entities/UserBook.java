package com.example.aparna.booksconsoleservice.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@Table(value = "book_by_user_and_bookid")
public class UserBook {

    @PrimaryKey
    UserBooksPrimaryKey key;

    @Column("reading_status")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String readingStatus;

    @Column("start_date")
    @CassandraType(type = CassandraType.Name.DATE)
    private LocalDate startDate;

    @Column("complete_date")
    @CassandraType(type = CassandraType.Name.DATE)
    private LocalDate completeDate;

    @Column("rating")
    @CassandraType(type = CassandraType.Name.INT)
    private int rating;
}
