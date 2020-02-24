package org.launchcode.javawebdevtechjobspersistent.models;

import javax.persistence.Entity;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;

@Entity
public class Skill extends AbstractEntity {

    @NotBlank
    @Size(min = 3, max = 333)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Skill() {};


}