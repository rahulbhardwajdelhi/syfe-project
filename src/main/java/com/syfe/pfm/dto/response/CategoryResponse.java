package com.syfe.pfm.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syfe.pfm.model.CategoryType;

// expose this as isCustom to match the assignment response examples
public class CategoryResponse {

    private String name;
    private CategoryType type;
    private boolean custom;

    public CategoryResponse() {
    }

    public CategoryResponse(String name, CategoryType type, boolean custom) {
        this.name = name;
        this.type = type;
        this.custom = custom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryType getType() {
        return type;
    }

    public void setType(CategoryType type) {
        this.type = type;
    }

    @JsonProperty("isCustom")
    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }
}
