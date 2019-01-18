package com.nutritionalStylist.healthyKitch.service;

import com.nutritionalStylist.healthyKitch.model.Cookbook;
import com.nutritionalStylist.healthyKitch.model.Recipe;
import com.nutritionalStylist.healthyKitch.model.User;

import java.util.Collection;
import java.util.List;

public interface CookbookService {
    Cookbook createCookbookForUser(User user);

    void saveCookbook(Cookbook cookbook);

    void addRecipeToCookbook(Integer cookbook, Integer recipe) throws IllegalArgumentException;

    Collection<Cookbook> cookbooksForUser(User user);

}
