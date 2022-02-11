package com.mari.recipes.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Response {
    private String name;
    private String description;
    private List<String> ingredients;
    private List<String> directions;
    private String category;
    private String date;
}
