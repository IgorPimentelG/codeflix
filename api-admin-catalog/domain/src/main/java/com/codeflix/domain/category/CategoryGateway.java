package com.codeflix.domain.category;

import com.codeflix.domain.pagination.Pagination;
import java.util.Optional;

public interface CategoryGateway {

	Category create(Category category);
	Category update(Category category);
	Optional<Category> findById(CategoryID id);
	Pagination<Category> findAll(CategorySearchQuery query);
	void deleteById(CategoryID id);
}
