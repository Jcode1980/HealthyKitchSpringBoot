package com.nutritionalStylist.healthyKitch.service;

import com.google.common.collect.Lists;
import com.nutritionalStylist.healthyKitch.exception.ResourceNotFoundException;
import com.nutritionalStylist.healthyKitch.image.ImageHandler;
import com.nutritionalStylist.healthyKitch.model.*;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeSearchDto;
import com.nutritionalStylist.healthyKitch.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
@Transactional
public class RecipeServiceImpl implements RecipeService {
    @PersistenceContext
    private EntityManager entityManager;

    private Logger log = Logger.getLogger(RecipeServiceImpl.class);
    private RecipeRepository recipeRepository;
    private MealTypeRepository mealTypeRepository;
    private NutritionalBenefitRepository nutritionalBenefitRepository;
    private CuisineRepository cuisineRepository;
    private DietaryCategoryRepository dietaryCategoryRepository;
    private RecipeStatusRepository recipeStatusRepository;
    private StorageService storageService ;
    private RecipeImageRepository recipeImageRepository;
    private MetricRepository metricRepository;
    private RecipeReviewRepository recipeReviewRepository;
    private ImageHandler imageHandler;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, MealTypeRepository mealTypeRepository,NutritionalBenefitRepository nutritionalBenefitRepository,
                             CuisineRepository cuisineRepository, DietaryCategoryRepository dietaryCategoryRepository,
                             StorageService storageService, RecipeImageRepository recipeImageRepository, MetricRepository metricRepository,
                            RecipeReviewRepository recipeReviewRepository, ImageHandler imageHandler, RecipeStatusRepository recipeStatusRepository){
        this.recipeRepository  = recipeRepository;
        this.mealTypeRepository  = mealTypeRepository;
        this.nutritionalBenefitRepository  = nutritionalBenefitRepository;
        this.cuisineRepository = cuisineRepository;
        this.dietaryCategoryRepository = dietaryCategoryRepository;
        this.storageService = storageService;
        this.recipeImageRepository = recipeImageRepository;
        this.metricRepository = metricRepository;
        this.recipeReviewRepository = recipeReviewRepository;
        this.imageHandler = imageHandler;
        this.recipeStatusRepository = recipeStatusRepository;


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
        System.out.println("ttthis is the search DTO: " + searchDto.getRecipeStatusID() );
        return recipeRepository.getRecipeUsingSearchDTO(searchDto);
//
//        if(recipeDto.getMealTypesID() != null) {
//            Collection<Integer> mealTypesID =  new ArrayList(recipeDto.getMealTypesID()) ;
//            mealTypes = makeCollection(mealTypeRepository.findAllById(mealTypesID));
//        }else{
//            mealTypes = Collections.EMPTY_LIST;
//        }
//        log.info("these are the meal Types: "+ mealTypes);
//        log.info("this is the search String: " + searchString);
//
//        if(mealTypes.size() == 0){
//            return recipeRepository.findByNameLike(recipeDto.getSearchString());
//        }
//        else{
//            return recipeRepository.findByNameLikeAndMealTypesIn(recipeDto.getSearchString(), mealTypes);
//        }
    }

//    @Override
//    public Collection<Recipe> findAllRecipes() {
//        return (Collection<Recipe>) recipeRepository.findAll();
//    }
//

    @Override
    @Transactional(readOnly = true)
    public Collection<MealType> findAllMealTypes() throws DataAccessException {

        Collection<MealType> mealTypes = Lists.newArrayList(mealTypeRepository.findAll());
        return  mealTypes.stream().sorted(Comparator.comparing(MealType::getSortID)).collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public Collection<NutritionalBenefit> findAllNutritionalBenefits() throws DataAccessException { return (Collection<NutritionalBenefit>) nutritionalBenefitRepository.findAll(); }

    @Override
    @Transactional(readOnly = true)
    public Collection<Cuisine> findAllCuisines() throws DataAccessException { return (Collection<Cuisine>) cuisineRepository.findAll(); }

    @Override
    @Transactional(readOnly = true)
    public Collection<Metric> findAllMetrics() throws DataAccessException { return (Collection<Metric>) metricRepository.findAll(); }

    @Override
    @Transactional(readOnly = true)
    public Collection<RecipeStatus> findAllRecipeStatuses() throws DataAccessException { return (Collection<RecipeStatus>) recipeStatusRepository.findAll(); }

    @Override
    @Transactional(readOnly = true)
    public Collection<DietaryCategory> findAllDietaryCategories() throws DataAccessException { return (Collection<DietaryCategory>)dietaryCategoryRepository.findAll(); }


    @Override
    public void addImageToRecipe(int recipeID, MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        log.info("file name being uploaded: " + fileName);
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeID);
        Recipe theRecipe = recipeOptional.get();


        RecipeImage recipeImage = theRecipe.createRecipeImage(fileName);

        recipeImageRepository.save(recipeImage);

        theRecipe.setDefaultImage(recipeImage);

        log.info("the default image of the recipe is: " + recipeImage);
        recipeRepository.save(theRecipe);

          imageHandler.processAndMoveDataToFile(recipeImage, file);
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

    @Override
    public Collection<RecipeReview> reviewsForRecipe(int recipeID){
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeID);
        return recipeReviewRepository.findByRecipeOrderByCreatedDateDesc(recipeOptional.orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public RecipeReview addReviewForRecipe(Integer recipeID, RecipeReview reviewDTO) throws ResourceNotFoundException{
        Optional<Recipe> recipeOpt = recipeRepository.findById(recipeID);

        if(!recipeOpt.isPresent()){
            throw new ResourceNotFoundException("Recipe with id-" + recipeID);
        }

        Recipe recipe = recipeOpt.get();

        RecipeReview newReview = new RecipeReview();
        newReview.setRecipe(recipe);
        newReview.setRating(reviewDTO.getRating());
        newReview.setComment(reviewDTO.getComment());
        newReview.setUser(reviewDTO.getUser());

        //get all recipes and find
        recipe.setAverageRating(averageReviewForRecipe(recipe));

        //set number of reviews for recipe.
        recipe.setNumberOfReviews(numberOfReviewsForRecipe(recipe).intValue());

        recipeReviewRepository.save(newReview);
        recipeRepository.save(recipe);
        return newReview;
    }

    private BigDecimal averageReviewForRecipe(Recipe recipe){
        Query query = entityManager.createNativeQuery("select (sum(rr.rating) / count(*))\n" +
                "from recipe_review rr\n" +
                "where rr.recipeid = " + recipe.getId() + ";");

        BigDecimal calculatedRating = (BigDecimal)query.getResultList().get(0);
        BigDecimal roundedDecimal = roundToHalf(calculatedRating.doubleValue());
        //Object result = q.getSingleResult();
        System.out.println("average review is: " + roundedDecimal);
        System.out.println(roundedDecimal);
        return roundedDecimal;
    }

    private BigDecimal roundToHalf(double d) {
        double roundedDobule =  Math.round(d * 2) / 2.0;
        return new BigDecimal(roundedDobule).setScale(1);
    }



    private BigInteger numberOfReviewsForRecipe(Recipe recipe){
        Query query = entityManager.createNativeQuery("SELECT count(*) FROM recipe_review where recipeid = " + recipe.getId());
        BigInteger numReviews = (BigInteger)query.getResultList().get(0);

        System.out.println("this is the number of reviews result: ");
        System.out.println(numReviews);
        return numReviews;
    }

//    public static <E> Collection<E> makeCollection(Iterable<E> iter) {
//        Collection<E> list = new ArrayList<E>();
//        for (E item : iter) {
//            list.add(item);
//        }
//        return list;
//    }

    @Override
    public void updateReview(RecipeReview reviewDTO) throws ResourceNotFoundException{
        log.info("Going to do an update review  using merge");
        entityManager.merge(reviewDTO);
    }

    @Override
    public void updateRecipe(Recipe recipeDto) throws ResourceNotFoundException{
        log.info("Going to do an update review  using merge");
        entityManager.merge(recipeDto);

    }

    @Override
    public void deleteRecipe(int recipeID) throws ResourceNotFoundException{
        Optional<Recipe> recipeOpt = recipeRepository.findById(recipeID);

        if(!recipeOpt.isPresent()){
            throw new ResourceNotFoundException("Recipe with id-" + recipeID);
        }

        recipeOpt.get().setDeleted(new Date());
    }

    @Override
    public List<RecipeReview> reviewsForRecipeAndUser(Integer recipeID, User user){
        Optional<Recipe> recipeOpt = recipeRepository.findById(recipeID);
        if(!recipeOpt.isPresent()){
            throw new ResourceNotFoundException("Recipe with id-" + recipeID);
        }

        return recipeReviewRepository.findByRecipeAndUserOrderByCreatedDateDesc(recipeOpt.get(), user);
    }

}
