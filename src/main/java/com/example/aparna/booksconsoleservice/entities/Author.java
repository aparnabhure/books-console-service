package com.example.aparna.booksconsoleservice.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "author_by_id")
@Getter
@Setter
public class Author {

    //primarykey column to defined if key is partition key or not for cassandra
    //Multiple PK can be defined with ordinal attr
    //Partition key is that it can go on different cluster/partition/node for each ID
    @Id @PrimaryKeyColumn(name = "author_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String id;

    @Column("author_name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String name;

    @Column("author_personal_name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String personalName;

}
