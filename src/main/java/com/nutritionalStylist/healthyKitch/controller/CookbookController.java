package com.nutritionalStylist.healthyKitch.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nutritionalStylist.healthyKitch.model.Cookbook;
import com.nutritionalStylist.healthyKitch.model.Recipe;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeDto;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeSearchDto;
import com.nutritionalStylist.healthyKitch.model.dto.Views;
import com.nutritionalStylist.healthyKitch.model.request.CookbookRecipeRequest;
import com.nutritionalStylist.healthyKitch.service.CookbookService;
import com.nutritionalStylist.healthyKitch.service.RecipeService;
import com.nutritionalStylist.healthyKitch.service.StorageService;
import com.nutritionalStylist.healthyKitch.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public Cookbook getCookbookByID(@PathVariable("cookbookID") int cookbookID) {
        System.out.println("goat here: getCookbookByID");
        Cookbook cookbook = cookbookService.findCookbookById(cookbookID).orElseThrow(IllegalArgumentException::new);
        return cookbook;
    }


    @PostMapping(value = "/addRecipeToCookbook")
    public ResponseEntity addRecipeToCookbook(@RequestBody CookbookRecipeRequest crr) {
        System.out.println("goat sshere: addRecipeToCookbook");
        cookbookService.addRecipeToCookbook(crr.getCookbookID(), crr.getRecipeID());
        return ResponseEntity.ok().build();
    }



}


