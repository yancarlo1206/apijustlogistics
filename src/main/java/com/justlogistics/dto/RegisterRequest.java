package com.justlogistics.dto;

import lombok.Data;

@Data
public class RegisterRequest {

    private String username;
    private String correo;
    private String password;
    private Integer usuarioTipoId;

    
}