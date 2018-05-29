package com.nutritionalStylist.healthyKitch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Optional;

@Entity
@Table(name = "MeasuredIngredient")
public class MeasuredIngredient extends NamedEntity{
    @ManyToOne
    @JoinColumn(name = "recipeID")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "metricID")
    private Metric metric;

    private String amount;

    public String amount(){
        return amount;
    }

    public void setAmount(String amount){
        this.amount = amount;
    }

    public Metric metric(){
        return metric;
    }

    public void setMetric(Metric value){
        this.metric = value;
    }

    @JsonIgnore
    public String metricDisplay(){
        return Optional.ofNullable(metric()).map(Metric::code).orElse("");
    }

    @JsonIgnore
    public String ingredientDisplay(){
        StringBuffer sb = new StringBuffer();
        sb.append(Optional.ofNullable(amount).orElse(""));
        sb.append("  ");
        sb.append(Optional.ofNullable(getName()).orElse(""));

        return sb.toString();
    }




}
