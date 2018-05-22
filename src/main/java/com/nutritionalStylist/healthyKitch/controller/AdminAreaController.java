package com.nutritionalStylist.healthyKitch.controller;


import com.nutritionalStylist.healthyKitch.service.RecipeService;
import com.nutritionalStylist.healthyKitch.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api/AdminArea")
public class AdminAreaController {
    private final RecipeService recipeService;

    @Autowired
    public AdminAreaController(RecipeService recipeService) {
        this.recipeService = recipeService;

    }

    @PostMapping("/addImageToMealType/{mealTypeID}")
    public String addImageToMealType(@RequestParam("file") MultipartFile file, @PathVariable int mealTypeID,
                                   RedirectAttributes redirectAttributes) {

        try{
            recipeService.addImageToMealType(mealTypeID, file);
        }catch (Exception e){
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }
}
