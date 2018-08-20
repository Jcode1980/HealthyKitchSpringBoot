package com.nutritionalStylist.healthyKitch.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.nutritionalStylist.healthyKitch.model.dto.Views;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Metric")
public class Metric extends NamedEntity{
    @JsonView({Views.DetailedView.class})
    @Column(name = "code")
    String code;

    public String code(){
        return code;
    }

    public void setCode(String value){
        code = value;
    }
}
