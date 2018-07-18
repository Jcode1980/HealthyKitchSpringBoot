package com.nutritionalStylist.healthyKitch.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="RecipeReview")
public class RecipeReview extends BaseEntity{
    @Column(name = "createdDate")
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "recipeID")
    private Recipe recipe;

    @Column(name = "rating")
    private BigDecimal rating;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @Column(name = "comment")
    private String comment;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
