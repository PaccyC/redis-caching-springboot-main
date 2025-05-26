package com.paccy.springbootcaching.requests;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequest {

    @NotBlank
    @Size(min = 2,max = 20)
    private String name;
    @NotBlank
    @Size(min = 2,max = 10)
    private String code;

    @NotBlank
    private int quantity;
    private double price;
}
