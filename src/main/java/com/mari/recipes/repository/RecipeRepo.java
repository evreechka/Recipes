package com.mari.recipes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.mari.recipes.models.Recipe;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepo extends CrudRepository<Recipe, Long> {
    Optional<Recipe> findRecipeById(long id);
    void deleteRecipeById(long id);
    Optional<List<Recipe>> findAllByNameContainingIgnoreCase(String name);
    Optional<List<Recipe>> findAllByCategoryIgnoreCase(String category);
}
