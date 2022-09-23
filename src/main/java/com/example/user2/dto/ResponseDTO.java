package com.example.user2.dto;

import com.example.user2.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDTO {
    private String message;
    private Object object;

    public ResponseDTO(String string, String response) {
        this.message = string;
        this.object = response;
    }

    public ResponseDTO(String string, User details) {
        this.message = string;
        this.object = details;
    }

    public ResponseDTO(String string, Object response) {
        this.message = string;
        this.object = response;
    }


}
