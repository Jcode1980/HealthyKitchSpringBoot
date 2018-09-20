package com.nutritionalStylist.healthyKitch.model;

import javax.persistence.*;


@Entity
@DiscriminatorValue(value = "2")
public class UserProfileImage extends ImageHolder {


}

