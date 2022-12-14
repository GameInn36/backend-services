package com.gameinn.api.gateway.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO implements Serializable {
    private Date timestamp;
    private int status;
    private String exception;
    private String message;
}
