package com.ucj.diary.data.dtos.requests;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class RegisterEntryRequest {
    private String tittle;
    private String body;

    private String diaryName;
}
