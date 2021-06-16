package com.example.mongodb.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class ArtWork {

    @Id
    private String id;

    private String title;

    private String artist;

    private int year;

    private int price;

    private List<String> tags;
}
