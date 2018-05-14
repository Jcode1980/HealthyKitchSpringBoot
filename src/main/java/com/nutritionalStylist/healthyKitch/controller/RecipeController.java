package com.nutritionalStylist.healthyKitch.controller;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


import com.nutritionalStylist.healthyKitch.model.Cuisine;
import com.nutritionalStylist.healthyKitch.model.MealType;
import com.nutritionalStylist.healthyKitch.model.NutritionalBenefit;
import com.nutritionalStylist.healthyKitch.model.Recipe;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeDto;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeSearchDto;
import com.nutritionalStylist.healthyKitch.service.RecipeService;
import com.nutritionalStylist.healthyKitch.service.StorageService;
import com.sun.deploy.net.HttpResponse;
import com.sun.xml.internal.bind.v2.util.CollisionCheckStack;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class RecipeController {
    private final RecipeService recipeService;
    private final StorageService storageService;
    private ModelMapper modelMapper;

    @Autowired
    public RecipeController(RecipeService recipeService, StorageService storageService) {
        this.storageService = storageService;
        this.recipeService = recipeService;


        modelMapper = new ModelMapper();
    }


    @GetMapping("/recipes/allRecipes")
    public Collection<RecipeDto> getAllRecipes(){
        System.out.println("got to all recipes");
        return recipeService.findAllRecipes().stream().map(recipe -> convertToDto(recipe)).
                collect(Collectors.toList());
    }

    @GetMapping("/recipes/recipes")
    public Collection<RecipeDto> searchRecipesByDTO(RecipeSearchDto searchDto) {
        //TODO do search here

        Collection<Recipe> recipes = recipeService.findRecipesUsingRecipeDTO(searchDto);
        System.out.println("found recipes : " + recipes);
        return recipes.stream().map(recipe -> convertToDto(recipe)).collect(Collectors.toList());
    }

    @GetMapping(value = "/recipes/{recipeID}")
    public Recipe getRecipeById(@PathVariable("recipeID") int recipeID) {
        return recipeService.findRecipeByID(recipeID).orElse(null);
    }



    /**
     * Update Recipe
     */
    @PutMapping(value = "/recipe/{recipeID}")
    @ResponseStatus(HttpStatus.OK)
    public void updateRecipe(@Valid @RequestBody RecipeDto recipeDto) {
        Recipe recipe;
        //TODO: investigate proper way of handling exceptions.
        try{
            recipe = convertToEntity(recipeDto);
        }catch (Exception e){
            System.out.println("something went wrong");
            return;
        }

        System.out.println("this is the recipe from mappedModel : " + recipe);

        //this.recipeService.saveRecipe(recipe);

//        // This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
//        recipeModel.get().setName(recipeRequest.getName());
//
//        this.recipeService.saveRecipe(recipeModel.get());
//        return recipeModel.get();
    }


    @GetMapping("/recipes/allMealTypes")
    public Collection<MealType> getAllMealtypes(){ return recipeService.findAllMealTypes(); }


    @GetMapping("/recipes/allNutritionalBenefits")
    public Collection<NutritionalBenefit> getAllNutritionalBenefit(){ return recipeService.findAllNutritionalBenefits(); }

    @GetMapping("/recipes/allCuisines")
    public Collection<Cuisine> getAllCuisines(){ return recipeService.findAllCuisines();}



//     @RequestMapping(method = RequestMethod.GET)
//    @ResponseBody
//    public List<PostDto> getPosts(...) {
//        //...
//        List<Post> posts = postService.getPostsList(page, size, sortDir, sort);
//        return posts.stream()
//          .map(post -> convertToDto(post))
//          .collect(Collectors.toList());
//    }
// 
//            @RequestMapping(method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.CREATED)
//    @ResponseBody
//    public PostDto createPost(@RequestBody PostDto postDto) {
//        Post post = convertToEntity(postDto);
//        Post postCreated = postService.createPost(post));
//        return convertToDto(postCreated);
//    }
// 
//            @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public PostDto getPost(@PathVariable("id") Long id) {
//        return convertToDto(postService.getPostById(id));
//    }
// 

    private RecipeDto convertToDto(Recipe recipe) {
        RecipeDto recipeDto = modelMapper.map(recipe, RecipeDto.class);

        return recipeDto;
    }

    private Recipe convertToEntity(RecipeDto recipeDto) throws ParseException {
        Recipe recipe = modelMapper.map(recipeDto, Recipe.class);

        //Enrich mapped recipe with old data.
//        if (recipeDto.getId() != null) {
//            Recipe oldRecipe = recipeService.findRecipeByID(recipeDto.getId());
//            recipe.setRedditID(oldRecipe.getRedditID());
//            recipe.setSent(oldRecipe.isSent());
//        }
        return recipe;
    }





}