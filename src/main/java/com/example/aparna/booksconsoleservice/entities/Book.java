package com.example.aparna.booksconsoleservice.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Table(value = "book_by_id")
@Getter
@Setter
public class Book {

    //primarykey column to defined if key is partition key or not for cassandra
    //Multiple PK can be defined with ordinal attr
    //Partition key is that it can go on different cluster/partition/node for each ID
    @Id @PrimaryKeyColumn(name = "book_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String id;

    @Column("book_name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String name;

    @Column("description")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String description;

    @Column("covers")
    @CassandraType(type = CassandraType.Name.LIST, typeArguments = CassandraType.Name.TEXT)
    private List<String> covers;

    @Column("authors")
    @CassandraType(type = CassandraType.Name.MAP, typeArguments = {CassandraType.Name.TEXT, CassandraType.Name.TEXT})
    private Map<String, String> authors;

    @Column("published_date")
    @CassandraType(type = CassandraType.Name.DATE)
    private LocalDate publishedDate;
}
