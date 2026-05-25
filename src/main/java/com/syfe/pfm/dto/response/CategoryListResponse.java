package com.syfe.pfm.dto.response;

import java.util.ArrayList;
import java.util.List;

/**
 * List of categories response.
 */
public class CategoryListResponse {

    private List<CategoryResponse> categories = new ArrayList<>();

    public List<CategoryResponse> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryResponse> categories) {
        this.categories = categories;
    }
}
