package com.ucj.diary.data.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateEntryRequest {
    private String oldTittle;
    private String tittle;
    private String body;
}
