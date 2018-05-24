package com.nutritionalStylist.healthyKitch.service;

<<<<<<< HEAD
=======
import com.nutritionalStylist.healthyKitch.enums.ImageQualityType;
>>>>>>> 9589d286daa0500875b1fa024a98b887ff57375e
import com.nutritionalStylist.healthyKitch.model.*;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeDto;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeSearchDto;
import com.nutritionalStylist.healthyKitch.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.*;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;

@Service
public class RecipeServiceImpl implements RecipeService {
    private Logger log = Logger.getLogger(RecipeServiceImpl.class);
    private RecipeRepository recipeRepository;
    private MealTypeRepository mealTypeRepository;
    private NutritionalBenefitRepository nutritionalBenefitRepository;
    private CuisineRepository cuisineRepository;
    private DietaryCategoryRepository dietaryCategoryRepository;
    private StorageService storageService ;
    private RecipeImageRepository recipeImageRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, MealTypeRepository mealTypeRepository,NutritionalBenefitRepository nutritionalBenefitRepository, CuisineRepository cuisineRepository, DietaryCategoryRepository dietaryCategoryRepository, StorageService storageService, RecipeImageRepository recipeImageRepository){
        this.recipeRepository  = recipeRepository;
        this.mealTypeRepository  = mealTypeRepository;
        this.nutritionalBenefitRepository  = nutritionalBenefitRepository;
        this.cuisineRepository = cuisineRepository;
        this.dietaryCategoryRepository = dietaryCategoryRepository;
        this.storageService = storageService;
        this.recipeImageRepository = recipeImageRepository;
    }


    @Override
    public Optional<Recipe> findRecipeByID(int id) {
        return recipeRepository.findById(id);
    }


    @Override
    public void saveRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    @Override
    public Collection<Recipe> findRecipesUsingRecipeDTO(RecipeSearchDto searchDto) {

        //Collection<MealType> mealTypes;
        //String searchString = searchDto.getSearchString();

        return recipeRepository.getRecipeUsingSearchDTO(searchDto);
//
//        if(recipeDto.getMealTypesID() != null) {
//            Collection<Integer> mealTypesID =  new ArrayList(recipeDto.getMealTypesID()) ;
//            mealTypes = makeCollection(mealTypeRepository.findAllById(mealTypesID));
//        }else{
//            mealTypes = Collections.EMPTY_LIST;
//        }
//        System.out.println("these are the meal Types: "+ mealTypes);
//        System.out.println("this is the search String: " + searchString);
//
//        if(mealTypes.size() == 0){
//            return recipeRepository.findByNameLike(recipeDto.getSearchString());
//        }
//        else{
//            return recipeRepository.findByNameLikeAndMealTypesIn(recipeDto.getSearchString(), mealTypes);
//        }
    }

    @Override
    public Collection<Recipe> findAllRecipes() {
        return (Collection<Recipe>) recipeRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Collection<MealType> findAllMealTypes() throws DataAccessException { return (Collection<MealType>) mealTypeRepository.findAll(); }

    @Override
    @Transactional(readOnly = true)
    public Collection<NutritionalBenefit> findAllNutritionalBenefits() throws DataAccessException { return (Collection<NutritionalBenefit>) nutritionalBenefitRepository.findAll(); }

    @Override
    @Transactional(readOnly = true)
    public Collection<Cuisine> findAllCuisines() throws DataAccessException { return (Collection<Cuisine>) cuisineRepository.findAll(); }



    @Override
    @Transactional(readOnly = true)
    public Collection<DietaryCategory> findAllDietaryCategories() throws DataAccessException { return (Collection<DietaryCategory>)dietaryCategoryRepository.findAll(); }


    @Override
    public void addImageToRecipe(int recipeID, MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeID);
        Recipe theRecipe = recipeOptional.get();

        HashMap<ImageQualityType, BufferedImage> imagesMap =  storageService.processAndStoreImage(theRecipe, file);
        RecipeImage recipeImage = theRecipe.createRecipeImage(fileName);

        recipeImageRepository.save(recipeImage);



        //TODO: tmp file should be removed.

        String fileExtension =  recipeImage.getOrginalImage().get().fileExtension();

//        ImageIO.write(imagesMap.get(ImageQualityType.PREVIEW), fileExtension, new java.io.File(recipeImage.displayPreviewImagePath()));
//        ImageIO.write(imagesMap.get(ImageQualityType.THUMBNAIL), fileExtension, new java.io.File(recipeImage.displayThumbnailImagePath()));
//        ImageIO.write(imagesMap.get(ImageQualityType.ORIGINAL), fileExtension, new java.io.File(recipeImage.displayOriginalImagePath()));
        recipeImage.getPreviewImage().get().processBufferedImage(imagesMap.get(ImageQualityType.PREVIEW));
        recipeImage.getThumbnailImage().get().processBufferedImage(imagesMap.get(ImageQualityType.THUMBNAIL));
        recipeImage.getOrginalImage().get().processBufferedImage(imagesMap.get(ImageQualityType.ORIGINAL));

        log.info("this is the previewPath : " + recipeImage.displayPreviewImagePath());
        log.info("this is the originalPath : " + recipeImage.displayOriginalImagePath());
        log.info("this is the thumbnailPath : " + recipeImage.displayThumbnailImagePath());


    }

    @Override
    public void addImageToMealType(int mealTypeID, MultipartFile file) throws Exception{
        //TODO: check if file is image.
        //Process the file.

        Optional<MealType> mealTypeOpt = mealTypeRepository.findById(mealTypeID);
        MealType mealType = mealTypeOpt.get();
        MealTypeFile mealTypeFile = storageService.mealTypeFileForMealType(mealType, file);
        mealType.setImage(mealTypeFile);
        mealTypeRepository.save(mealType);

    }

//    public static <E> Collection<E> makeCollection(Iterable<E> iter) {
//        Collection<E> list = new ArrayList<E>();
//        for (E item : iter) {
//            list.add(item);
//        }
//        return list;
//    }



}
