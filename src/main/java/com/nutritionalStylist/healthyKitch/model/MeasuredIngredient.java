package com.nutritionalStylist.healthyKitch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.nutritionalStylist.healthyKitch.model.dto.Views;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Optional;

@SuppressWarnings("unused")
@Entity
@Table(name = "MeasuredIngredient")
public class MeasuredIngredient extends NamedEntity{

    @JsonView({Views.DetailedView.class})
    @ManyToOne
    @JoinColumn(name = "metricID")
    private Metric metric;

    @JsonView({Views.DetailedView.class})
    private String amount;

    @NotNull
    private Integer sortID;

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

    public Integer getSortID() { return sortID; }

    public void setSortID(Integer sortID) { this.sortID = sortID; }

    @JsonIgnore
    private String metricDisplay(){
        return Optional.ofNullable(metric()).map(Metric::code).orElse("");
    }

    @JsonIgnore
    public String ingredientDisplay(){
        StringBuffer sb = new StringBuffer();
        sb.append(Optional.ofNullable(amount).orElse(""));
        sb.append("  ");
        sb.append(Optional.ofNullable(getName()).orElse(""));
        sb.append("  ");
        sb.append(metricDisplay());
        return sb.toString();
    }




}
