package com.nutritionalStylist.healthyKitch.controller;

import java.text.ParseException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import com.nutritionalStylist.healthyKitch.model.*;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeDto;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeSearchDto;
import com.nutritionalStylist.healthyKitch.service.RecipeService;
import com.nutritionalStylist.healthyKitch.service.StorageService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@CrossOrigin(origins = "http://adolfotrove.ddns.net:4010")
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    private final RecipeService recipeService;
    private final StorageService storageService;
    private ModelMapper modelMapper;

    @Autowired
    public RecipeController(RecipeService recipeService, StorageService storageService) {
        this.storageService = storageService;
        this.recipeService = recipeService;

        modelMapper = new ModelMapper();
        PropertyMap<Recipe, RecipeDto> recipeMap = new PropertyMap<Recipe, RecipeDto>() {
            protected void configure() {
                //map(source.getDefaultImage(, destination.getDefaultImageID());
                map(source.sortedMeasuredIngredients(), destination.getMeasuredIngredients());
                map(source.getInstructions(), destination.getInstructions());
                map(source.sortedDietaryCategories(), destination.getDietaryCategories());
            }
        };

        modelMapper.addMappings(recipeMap);
    }


    @GetMapping("/allRecipes")
    public Collection<RecipeDto> getAllRecipes(){
        System.out.println("got to all recipes");
        return recipeService.findAllRecipes().stream().map(recipe -> convertToDto(recipe)).
                collect(Collectors.toList());
    }

    @GetMapping("/recipes")
    public Collection<RecipeDto> searchRecipesByDTO(RecipeSearchDto searchDto) {
        Collection<Recipe> recipes = recipeService.findRecipesUsingRecipeDTO(searchDto);
        System.out.println("found recipes : " + recipes);
        return recipes.stream().map(recipe -> convertToDto(recipe)).collect(Collectors.toList());
    }

    @GetMapping(value = "/{recipeID}")
    public RecipeDto getRecipeById(@PathVariable("recipeID") int recipeID) {
        Recipe recipe = recipeService.findRecipeByID(recipeID).orElseThrow(IllegalArgumentException::new);
        return convertToDto(recipe);
    }

    /**
     * Update Recipe
     */
    @PutMapping(value = "/{recipeID}")
    @ResponseStatus(HttpStatus.OK)
    public void updateRecipe(@Valid @RequestBody RecipeDto recipeDto) {
        Recipe recipe;
        //TODO: investigate proper way of handling exceptions.
        try{
            recipe = convertToEntity(recipeDto);
        }catch (Exception e){
            System.out.println("something went wrong when converting dto to recipe");
            return;
        }

        System.out.println("thisssss is the recipe from mappedModel : " + recipe);

        this.recipeService.saveRecipe(recipe);

//        // This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
//        recipeModel.get().setName(recipeRequest.getName());
//
//        this.recipeService.saveRecipe(recipeModel.get());
//        return recipeModel.get();
    }


    /**
     * Create Recipe
     */
    @PostMapping(value = "/{recipeID}")
    @ResponseStatus(HttpStatus.OK)
    public void createRecipe(@Valid @RequestBody RecipeDto recipeDto) {
        Recipe recipe;
        //TODO: investigate proper way of handling exceptions.
        try{
            recipe = convertToEntity(recipeDto);
        }catch (Exception e){
            System.out.println("something went wrong when converting dto to recipe");
            return;
        }

        System.out.println("this is the recipe from mappedModel : " + recipe);
        System.out.println("this is the instructions from mappedModel : " + recipe.getInstructions());
        recipeService.saveRecipe(recipe);
        //this.recipeService.saveRecipe(recipe);

//        // This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
//        recipeModel.get().setName(recipeRequest.getName());
//
//        this.recipeService.saveRecipe(recipeModel.get());
//        return recipeModel.get();
    }


    @GetMapping("/allMealTypes")
    public Collection<MealType> getAllMealtypes(){ return recipeService.findAllMealTypes(); }


    @GetMapping("/allMetrics")
    public Collection<Metric> getAllMetrics(){ return recipeService.findAllMetrics(); }


    @GetMapping("/allNutritionalBenefits")
    public Collection<NutritionalBenefit> getAllNutritionalBenefit(){ return recipeService.findAllNutritionalBenefits(); }

    @GetMapping("/allCuisines")
    public Collection<Cuisine> getAllCuisines(){ return recipeService.findAllCuisines();}

    @GetMapping("/allDietaryCategories")
    public Collection<DietaryCategory> getAllDietaryCategories(){ return recipeService.findAllDietaryCategories();}



    @PostMapping("/UploadRecipeImage/{recipeID}")
    public String handleFileUpload(@PathVariable("recipeID") int recipeID, @RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        System.out.println("got here UploadRecipeImage");
        try{
            recipeService.addImageToRecipe(recipeID, file);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }


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