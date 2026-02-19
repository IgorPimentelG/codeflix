package com.codeflix.application.category.create;

import com.codeflix.domain.category.Category;
import com.codeflix.domain.category.CategoryGateway;
import com.codeflix.domain.validation.handlers.ThrowsValidationHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

	private final CategoryGateway categoryGateway;

	@Override
	public CreateCategoryOutput execute(final CreateCategoryCommand command) {
		final var category = Category.newCategory(command.name(), command.description(), command.isActive());
		category.validate(new ThrowsValidationHandler());
		return CreateCategoryOutput.from(categoryGateway.create(category));
	}
}
