package com.nutritionalStylist.healthyKitch.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.nutritionalStylist.healthyKitch.model.dto.Views;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="IngredientSubHeading")
public class IngredientSubHeading extends NamedEntity{

    @JsonView({Views.DetailedView.class})
    @NotNull
    private Integer sortID;

    public Integer getSortID() {
        return sortID;
    }

    public void setSortID(Integer sortID) {
        this.sortID = sortID;
    }
}
