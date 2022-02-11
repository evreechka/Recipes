package com.mari.recipes.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "RECIPE")
public class Recipe implements Comparable<Recipe> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @Column(name = "NAME")
    @NotBlank(message = "Name should not be blank")
    @NotNull
    private String name;

    @Column(name = "DESCRIPTION")
    @NotBlank(message = "Description should not be blank")
    @NotNull
    private String description;

    @Column(name = "INGREDIENTS")
    @ElementCollection
    @NotNull
    @Size(min = 1, message = "Ingredients should contain at least 1 point")
    private List<String> ingredients;

    @Column(name = "DIRECTIONS")
    @ElementCollection
    @NotNull
    @Size(min = 1, message = "Directions should contain at least 1 point")
    private List<String> directions;

    @Column(name = "CATEGORY")
    @NotBlank(message = "Category should not be blank")
    private String category;

    @Column(name = "DATE")
    private String date;

    @Column(name = "AUTHOR")
    private String email;

    @Override
    public int compareTo(Recipe recipe) {
        LocalDateTime thisDateTime = LocalDateTime.parse(this.date);
        LocalDateTime otherDateTime = LocalDateTime.parse(recipe.getDate());
        if (thisDateTime.isBefore(otherDateTime))
            return 1;
        if (thisDateTime.isAfter(otherDateTime))
            return -1;
        return 0;
    }
}
