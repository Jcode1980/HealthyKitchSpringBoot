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


}
