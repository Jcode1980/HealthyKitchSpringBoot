package com.nutritionalStylist.healthyKitch.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Metric")
public class Metric extends NamedEntity{
    @Column(name = "code")
    @NotEmpty
    String code;

    public String code(){
        return code;
    }

    public void setCode(String value){
        code = value;
    }
}
