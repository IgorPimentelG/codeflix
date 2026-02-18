package com.codeflix.domain.category;

import com.codeflix.domain.validation.Error;
import com.codeflix.domain.validation.ValidationHandler;
import com.codeflix.domain.validation.Validator;

public class CategoryValidator extends Validator {

	private static final int NAME_MIN_LENGTH = 3;
	private static final int NAME_MAX_LENGTH = 255;

	private final Category category;

	public CategoryValidator(final Category category, final ValidationHandler handler) {
		super(handler);
		this.category = category;
	}

	@Override
	public void validate() {
		checkNameConstraints();
		checkDescriptionConstraints();
	}

	private void checkNameConstraints() {
		final var name = category.getName();

		if (name == null) {
			getHandler().append(new Error("Name should not be null."));
			return;
		}

		if (name.isBlank()) {
			getHandler().append(new Error("Name should not be empty."));
			return;
		}

		final var length = name.trim().length();
		if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
			getHandler().append(new Error("Name must be between 3 and 255 characters."));
		}
	}

	private void checkDescriptionConstraints() {
		final var description = category.getDescription();

		if (description != null && description.isBlank()) {
			getHandler().append(new Error("Description should not be empty."));
			return;
		}
	}
}
