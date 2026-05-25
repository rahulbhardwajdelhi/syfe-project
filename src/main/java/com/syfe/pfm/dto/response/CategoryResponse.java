package com.syfe.pfm.dto.response;

import com.syfe.pfm.model.CategoryType;

// json field shows as "custom" not isCustom - jackson thing, tests check for custom
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

    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }
}
