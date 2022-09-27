package com.ucj.diary.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
@NoArgsConstructor
public class User {
    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String userName;
    private String password;

    @DBRef
    private Diary diary;
}
