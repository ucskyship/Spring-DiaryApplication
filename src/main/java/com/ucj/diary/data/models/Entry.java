package com.ucj.diary.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@Document
@NoArgsConstructor
public class Entry {
    @Id
    private String id;

    private String title;
    private String body;
    private LocalDateTime dateCreated;
}
