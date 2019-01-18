package com.nutritionalStylist.healthyKitch.repository;

import com.nutritionalStylist.healthyKitch.image.ImageHandler;
import com.nutritionalStylist.healthyKitch.model.UserProfileImage;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProfileImageRepository extends PagingAndSortingRepository<UserProfileImage,Integer> {

}
