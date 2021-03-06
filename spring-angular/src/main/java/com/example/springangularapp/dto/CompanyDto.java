package com.example.springangularapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyDto {
    private int id;
    private String name;
    private String email;
    private String password;

}
