package com.nutritionalStylist.healthyKitch.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nutritionalStylist.healthyKitch.model.Cookbook;
import com.nutritionalStylist.healthyKitch.model.Recipe;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeDto;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeSearchDto;
import com.nutritionalStylist.healthyKitch.model.dto.Views;
import com.nutritionalStylist.healthyKitch.service.CookbookService;
import com.nutritionalStylist.healthyKitch.service.RecipeService;
import com.nutritionalStylist.healthyKitch.service.StorageService;
import com.nutritionalStylist.healthyKitch.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/cookbooks")
public class CookbookController {
    private Logger log = Logger.getLogger(RecipeController.class);
    private final CookbookService   cookbookService;

    @Autowired
    public CookbookController(CookbookService cookbookService) {
        this.cookbookService = cookbookService;
    }

    @JsonView(Views.DetailedView.class)
    @GetMapping(value = "/{cookbookID}")
    public Cookbook getCookbookByID(@PathVariable("cookbookID") int recipeID) {
        Cookbook cookbook = cookbookService.findCookbookById(recipeID).orElseThrow(IllegalArgumentException::new);
        return cookbook;
    }
}


