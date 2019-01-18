package com.nutritionalStylist.healthyKitch.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nutritionalStylist.healthyKitch.config.security.NoEncoder;
import com.nutritionalStylist.healthyKitch.exception.ResourceNotFoundException;
import com.nutritionalStylist.healthyKitch.model.*;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeDto;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeSearchDto;
import com.nutritionalStylist.healthyKitch.model.dto.UserDto;
import com.nutritionalStylist.healthyKitch.model.dto.Views;
import com.nutritionalStylist.healthyKitch.service.*;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/session/recipe")
public class SessionController {
    @PersistenceContext
    private EntityManager entityManager;

    private Logger log = Logger.getLogger(SessionController.class);

    private final RecipeService recipeService;
    private final StorageService storageService;
    private final UserService userService;
    private final EmailService emailService;
    private final CookbookService cookbookService;

    @Autowired
    private NoEncoder bcryptEncoder;

    @Autowired
    public SessionController(RecipeService recipeService, StorageService storageService, UserService userService, EmailService emailService,
        CookbookService cookbookService) {
        this.storageService = storageService;
        this.recipeService = recipeService;
        this.userService = userService;
        this.emailService = emailService;
        this.cookbookService = cookbookService;
    }

    /**
     * Update Recipe
     */
    @PutMapping(value = "/{recipeID}")
    @ResponseStatus(HttpStatus.OK)
    public void updateRecipe(@Valid @RequestBody RecipeDto recipeDto ) {
        log.info("updateRecipe");
        Recipe recipe;
        //TODO: investigate proper way of handling exceptions.
        try{
            recipe = RecipeDto.convertToEntity(recipeDto);



            //re setting the created by date and user when DTO returns null for those values
            Optional<Recipe> foundRecipeOpt = recipeService.findRecipeByID(recipeDto.getId());

            Recipe foundRecipe = foundRecipeOpt.get();
            if(recipe.getCreated() == null) {
                recipe.setCreated(foundRecipe.getCreated());
            }

            if(recipe.getCreatedby() == null){
                recipe.setCreatedby(foundRecipe.getCreatedby());
            }

            if(recipe.getRecipeStatus() == null){
                recipe.setRecipeStatus(foundRecipe.getRecipeStatus());
            }


            log.info("ready in mins?? " + recipeDto.getReadyInMins());
            log.info("how many ingredients: " + recipeDto.getMeasuredIngredients().toArray().length);
            if(recipeDto.getMeasuredIngredients().toArray().length > 0) {

                log.info("thisssss is the recipe from mappedModel DTO : " + ((MeasuredIngredient) recipeDto.getMeasuredIngredients().toArray()[0]).metric());
            }
            else{
                log.info("No ingredients found");
            }
        }catch (Exception e){
            log.info("something went wrong when converting dto to recipe");
            e.printStackTrace();
            return;
        }

        //log.info("thisssss is the recipe from mappedModel : " + ((MeasuredIngredient)recipe.getMeasuredIngredients().toArray()[0]).metric());

        this.recipeService.updateRecipe(recipe);
        //this.recipeService.saveRecipe(recipe);

//        // This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
//        recipeModel.get().setName(recipeRequest.getName());
//
//        this.recipeService.saveRecipe(recipeModel.get());
//        return recipeModel.get();
    }



    /**
     * Create Recipe
     */
    @PostMapping(value = "/createRecipe")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public Integer createRecipe(@Valid @RequestBody RecipeDto recipeDto) {
        Recipe recipe;
        //TODO: investigate proper way of handling exceptions.

//        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String name = user.getUsername(); //get logged in username
//        log.info("Person logged in is: " + user.getFullName() );

        log.info("this is the recipe DTO from measured Ingredient : " + recipeDto.getMeasuredIngredients());
        log.info("this is the recipe DTO from Cusines : " + recipeDto.getCuisines());
        log.info("this is the recipe DTO from Dietary Category : " + recipeDto.getDietaryCategories());

        try{
            recipe = RecipeDto.convertToEntity(recipeDto);
        }catch (Exception e){
            log.info("something went wrong when converting dto to recipe");
            return null;
        }

        log.info("this is the recipe from mappedModel : " + recipe);
        //log.info("this is the instructions from mappedModel : " + recipe.getInstructions().size());
        //log.info("this is the measuredIngredients : " + recipe.getMeasuredIngredients().size());
        //List<NutritionalBenefit> nutritionBenefits = new ArrayList<>(recipe.getNutritionalBenefits());
        //nutritionBenefits.stream().forEach(nutritionalBenefit -> {entityManager.persist(nutritionalBenefit);});

        //Recipe Status set on client side.
//        if(recipe)
//        recipe.setRecipeStatus(entityManager.find(RecipeStatus.class, 1));

        recipeService.saveRecipe(recipe);
        return recipe.getId();


        //this.recipeService.saveRecipe(recipe);

//        // This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
//        recipeModel.get().setName(recipeRequest.getName());
//
//        this.recipeService.saveRecipe(recipeModel.get());
//        return recipeModel.get();
    }



    /*
     {
        "comment": "string",
        "id": 0,
        "new": true,
        "rating": 0,
        "user": {
            "id"
        }
    }

     */


    /**
     * Post Review
     */
    @PostMapping(value = "/review/{recipeID}")
    @ResponseStatus(HttpStatus.OK)
    public RecipeReview createReview(@PathVariable("recipeID") Integer recipeID, @Valid @RequestBody RecipeReview recipeReviewDTO) throws Exception {

        recipeReviewDTO.setUser(getAuthenticatedUser());

        RecipeReview newReview = recipeService.addReviewForRecipe(recipeID, recipeReviewDTO);


        log.info("this is the recipeReview: " + newReview);
        log.info("this is the recipeReviews user: " + newReview.getUser().getFullName());

        return newReview;
    }

    /**
     * Update Review
     */
    @PutMapping(value = "/review/{reviewID}")
    @ResponseStatus(HttpStatus.OK)
    public void updateReview(@PathVariable("reviewID") Integer reviewID, @Valid @RequestBody RecipeReview recipeReviewDTO){
        recipeService.updateReview(recipeReviewDTO);
    }

    @GetMapping(value = "/loggedInUser")
    public UserDto currentUser() {
        return UserDto.convertToDto(getAuthenticatedUser());
    }


    @JsonView(Views.ListView.class)
    @GetMapping("/myRecipes")
    public Collection<RecipeDto> searchRecipesByDTO(RecipeSearchDto searchDto) {
        log.info("status for search is ? " + searchDto.getRecipeStatusID());

        //Could this logic be moved into the recipeService???
        User user = getAuthenticatedUser();
        searchDto.setCreatedByUserID(user.getId());

        Collection<Recipe> recipes = recipeService.findRecipesUsingRecipeDTO(searchDto);
        System.out.println("found recipes : " + recipes);
        return recipes.stream().map(recipe -> RecipeDto.convertToDto(recipe)).collect(Collectors.toList());
    }


    @PostMapping("/uploadUserProfileImage")
    public void uploadUserProfileImage(@RequestParam("file") MultipartFile file) {
        System.out.println("got here uploadUserProfileImage");
        User user = getAuthenticatedUser();

        try{
            userService.addImageToUser(user, file);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


    @PutMapping(value = "/recipe/deleteRecipe/{recipeID}")
    public ResponseEntity<?> deleteRecipe(@PathVariable("recipeID") Integer recipeID){
        ResponseEntity<?> response;
        try{
            recipeService.deleteRecipe(recipeID);
            response = ResponseEntity.ok().build();
        }catch (Exception e){
            e.printStackTrace();
            response =  ResponseEntity.badRequest().build();
        }

        return response;
    }


    private User getAuthenticatedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth details " + auth.getDetails());
        System.out.println("auth.getPrincipal()" + auth.getPrincipal().getClass().getName());
        System.out.println(auth.getPrincipal());
        UserDetails userDetails =  ((UserDetails)auth.getPrincipal());
        System.out.println("username; " + userDetails.getUsername() + " password: " + userDetails.getPassword());
        return userService.findByUsernameAndPassword(userDetails.getUsername(), userDetails.getPassword());

    }

    @PostMapping("/sendTestEmail")
    public void sendTestEmail(){
        //String[] toArray, String[] bccArray, String subject
        Mail mail = new Mail(new String[]{"j_adolfo@hotmail.com"}, new String[]{}, "johnadolfo1980@gmail.com", "Test HTML email yay");
        try{
            emailService.sendSimpleMessage(mail);
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
        }

    }


    @PostMapping("/sendEmail")
    public void sendEmail(@Valid @RequestBody Mail mail){
        log.info("sending email " + mail);
        log.info("to Array: " +  mail.getToArray());
        log.info("from: " +  mail.getFrom());

        //String[] toArray, String[] bccArray, String subject
        //Mail mail = new Mail(new String[]{"j_adolfo@hotmail.com"}, new String[]{}, "johnadolfo1980@gmail.com", "Test HTML email yay");
        try{
            emailService.sendSimpleMessage(mail);
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
        }

    }

    @GetMapping(value = "/canCreateReview/{recipeID}")
    public boolean loggedInUserCanCreateReview(@PathVariable("recipeID") Integer recipeID){
        User authenticatedUser = getAuthenticatedUser();

        log.info("go to loggedInUserCanCreateReview with recipeID: " + recipeID + " and user " + authenticatedUser.getEmail());

        Optional<Recipe> recipeOpt = recipeService.findRecipeByID(recipeID);
        if(!recipeOpt.isPresent()){
            throw new ResourceNotFoundException("Recipe with id-" + recipeID);
        }

//
//        return authenticatedUser.reviewForRecipe(recipeOpt.get()).isPresent() ? false : true;

        //should this logic be in User class??
        List<RecipeReview> recipeReviews =  recipeService.reviewsForRecipeAndUser(recipeID, authenticatedUser);
        System.out.println("Number of recipe reviews: " + recipeReviews.size());
        return recipeReviews.size() == 0;
    }

    @PostMapping(value="/changePassword")
    public  ResponseEntity<?> changePassword(@RequestParam("newPassword") String newPassword){
        ResponseEntity<?> response;

        log.info("got to change password " + newPassword);

        User user = getAuthenticatedUser();
        user.setPassword(bcryptEncoder.encode(newPassword));
        userService.save(user);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value="/updateUserDetails")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto userDto){
        User user = getAuthenticatedUser();

        user.updateUserWithUserDto(userDto);
        userService.save(user);

        return ResponseEntity.ok().build();
    }


    /**
     * Create cookbook
     */
    @PostMapping(value = "/createCookbook")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public ResponseEntity<?> createCookbook() {

        //fixme add me when done testing
        //User user = getAuthenticatedUser();
        User user = null;
        Cookbook cookbook = cookbookService.createCookbookForUser(user);
        return ResponseEntity.ok(cookbook);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/saveCookbook")
    public void saveCookbook(@Valid @RequestBody Cookbook cookbook){
        cookbookService.saveCookbook(cookbook);
    }

    @PostMapping(value = "/addRecipeToCookbook")
    @ResponseStatus(HttpStatus.OK)
    public void addRecipeToCookBook(Integer cookbookID, Integer recipeID){
        cookbookService.addRecipeToCookbook(cookbookID, recipeID);
    }

    @GetMapping(value = "/cookbooksForUser")
    public ResponseEntity<Collection<Cookbook>> cookbooksForUser(){
        //fixme add me when done testing
        //User user = getAuthenticatedUser();
        User user = null;
        Collection<Cookbook> cookbooks = cookbookService.cookbooksForUser(user);
        return ResponseEntity.ok(cookbooks);
    }
}
