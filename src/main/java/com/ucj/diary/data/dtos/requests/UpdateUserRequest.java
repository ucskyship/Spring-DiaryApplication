package com.ucj.diary.data.dtos.requests;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
}
