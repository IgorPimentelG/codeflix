package com.codeflix.domain.validation.handlers;

import com.codeflix.domain.exceptions.DomainException;
import com.codeflix.domain.validation.Error;
import com.codeflix.domain.validation.ValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {

	@Override
	public ValidationHandler append(final Error error) {
		throw DomainException.with(error);
	}

	@Override
	public ValidationHandler append(final ValidationHandler handler) {
		throw DomainException.with(handler.getErrors());
	}

	@Override
	public ValidationHandler validate(Validation validation) {
		try {
			validation.validate();
		} catch (Exception ex) {
			throw DomainException.with(new Error(ex.getMessage()));
		}

		return this;
	}

	@Override
	public List<Error> getErrors() {
		return List.of();
	}
}
