package com.example.springangularapp.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDto {
    private int id;
    private String text;
    private double value;
    private String companyName;
    private String clientName;
}
