package com.nutritionalStylist.healthyKitch.model;

import javax.persistence.*;

@Entity
@Table(name = "Instruction")
public class Instruction extends BaseEntity{
    @Column(name = "descText")
    protected  String descText;

    @ManyToOne
    @JoinColumn(name = "recipeID")
    protected Recipe recipe;

    @Column
    protected String sortID;

    public String getDescText() {
        return descText;
    }

    public void setDescText(String descText) {
        this.descText = descText;
    }

    public String getSortID() {
        return sortID;
    }

    public void setSortID(String sortID) {
        this.sortID = sortID;
    }
}
