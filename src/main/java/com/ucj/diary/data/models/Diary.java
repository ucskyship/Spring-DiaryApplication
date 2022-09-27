package com.ucj.diary.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
@NoArgsConstructor
public class Diary {
    @Id
    private String id;
    private String diaryName;

    @DBRef
    private List<Entry> entries = new ArrayList<>();
}
