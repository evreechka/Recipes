package com.mari.recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.mari.recipes.models.Recipe;
import com.mari.recipes.models.Response;
import com.mari.recipes.repository.RecipeRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RecipeService {
    private final RecipeRepo recipeRepo;

    @Autowired
    public RecipeService(RecipeRepo recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    public long saveRecipe(Recipe recipe, UserDetails user) {
        recipe.setEmail(user.getUsername());
        recipe.setDate(LocalDateTime.now().toString());
        return recipeRepo.save(recipe).getId();
    }

    public Response getRecipe(long id) {
        return createResponse(getRecipeById(id));
    }

    @Transactional
    public void deleteRecipe(long id, UserDetails user) {
        Recipe recipe = getRecipeById(id);
        if (!recipe.getEmail().equals(user.getUsername()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You haven't permissions to delete this recipe");
        recipeRepo.deleteRecipeById(id);
    }

    public void updateRecipe(long id, Recipe newRecipe, UserDetails user) {
        Recipe oldRecipe = getRecipeById(id);
        if (!oldRecipe.getEmail().equals(user.getUsername()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You haven't permissions to update this recipe");
        oldRecipe.setName(newRecipe.getName());
        oldRecipe.setDescription(newRecipe.getDescription());
        oldRecipe.setIngredients(newRecipe.getIngredients());
        oldRecipe.setDirections(newRecipe.getDirections());
        oldRecipe.setCategory(newRecipe.getCategory());
        saveRecipe(oldRecipe, user);
    }

    public List<Response> findRecipesByName(String name) {
        return createListResponse(recipeRepo.findAllByNameContainingIgnoreCase(name).orElse(new ArrayList<>()));
    }

    public List<Response> findRecipesByCategory(String category) {
        return createListResponse(recipeRepo.findAllByCategoryIgnoreCase(category).orElse(new ArrayList<>()));
    }

    private Recipe getRecipeById(long id) {
        return recipeRepo.findRecipeById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe with id " + id + "doesn't exist"));
    }

    private List<Response> createListResponse(List<Recipe> recipes) {
        Collections.sort(recipes);
        System.out.println(recipes);
        List<Response> responses = new ArrayList<>();
        for (Recipe recipe : recipes) {
            responses.add(createResponse(recipe));
        }
        System.out.println(responses.size());
        return responses;
    }

    private Response createResponse(Recipe recipe) {
        return new Response(recipe.getName(), recipe.getDescription(), recipe.getIngredients(), recipe.getDirections(), recipe.getCategory(), recipe.getDate());
    }
}
