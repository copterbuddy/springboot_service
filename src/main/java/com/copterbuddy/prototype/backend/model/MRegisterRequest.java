package com.copterbuddy.prototype.backend.model;

import lombok.Data;

@Data
public class MRegisterRequest {
    private String email;
    private String password;
    private String name;
}
