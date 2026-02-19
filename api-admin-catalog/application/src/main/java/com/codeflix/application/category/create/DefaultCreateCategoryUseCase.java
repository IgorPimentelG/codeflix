package com.codeflix.application.category.create;

import com.codeflix.domain.category.Category;
import com.codeflix.domain.category.CategoryGateway;
import com.codeflix.domain.validation.handlers.Notification;
import io.vavr.API;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

	private final CategoryGateway categoryGateway;

	@Override
	public Either<Notification, CreateCategoryOutput> execute(final CreateCategoryCommand command) {
		final var category = Category.newCategory(command.name(), command.description(), command.isActive());
		final var notification = Notification.create();
		category.validate(notification);
		return notification.hasError() ? API.Left(notification) : create(category);
	}

	private Either<Notification, CreateCategoryOutput> create(Category category) {
		return API.Try(() -> categoryGateway.create(category))
			.toEither()
			.bimap(Notification::create, CreateCategoryOutput::from);
	}
}
