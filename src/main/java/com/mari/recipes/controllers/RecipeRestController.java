package com.mari.recipes.controllers;

import com.mari.recipes.models.Recipe;
import com.mari.recipes.models.Response;
import com.mari.recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class RecipeRestController {
    private final RecipeService recipeService;

    @Autowired
    public RecipeRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/api/recipe/new")
    Map<String, Long> postRecipe(@Valid @RequestBody Recipe recipe, @AuthenticationPrincipal UserDetails user) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        System.out.println(user);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long recipeId = recipeService.saveRecipe(recipe, userDetails);
        return Map.of("id", recipeId);
    }

    @GetMapping("/api/recipe/{id}")
    Response sendRecipe(@PathVariable long id) {
        return recipeService.getRecipe(id);
    }

    @DeleteMapping("/api/recipe/{id}")
    void deleteRecipe(@PathVariable long id, @AuthenticationPrincipal UserDetails user) {

        recipeService.deleteRecipe(id, (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Recipe is deleted");
    }

    @PutMapping("/api/recipe/{id}")
    void updateRecipe(@PathVariable long id, @Valid @RequestBody Recipe recipe, @AuthenticationPrincipal UserDetails user) {
        recipeService.updateRecipe(id, recipe, (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Recipe is updated");
    }
    @GetMapping("/api/recipe/search")
    List<Response> findRecipeByCategory(@RequestParam(value = "name", required = false) String name,  @RequestParam(value = "category", required = false) String category) {
        if (name == null && category == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "1 parameter was expected but 0 was found");
        if (name != null && category != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "1 parameter was expected but 2 was found");
        if (name != null) {
            validateBlank(name, "Name");
            return recipeService.findRecipesByName(name);
        }
        validateBlank(category, "Category");
        return recipeService.findRecipesByCategory(category);

    }
    private void validateBlank(String param, String name) {
        if (param.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, name + " should not be blank");
        }
    }

}
