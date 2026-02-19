package com.codeflix.application.category.create;

import com.codeflix.domain.category.Category;
import com.codeflix.domain.category.CategoryID;

public record CreateCategoryOutput(CategoryID id) {

	public static CreateCategoryOutput from(final Category category) {
		return new CreateCategoryOutput(category.getId());
	}
}
