package com.example.deldrugs.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDTO {

    private Timestamp timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
