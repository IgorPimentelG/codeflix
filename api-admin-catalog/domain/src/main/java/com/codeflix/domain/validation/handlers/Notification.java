package com.codeflix.domain.validation.handlers;

import com.codeflix.domain.exceptions.DomainException;
import com.codeflix.domain.validation.Error;
import com.codeflix.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {

	private final List<Error> errors;

	private Notification(final List<Error> errors) {
		this.errors = errors;
	}

	public static Notification create() {
		return new Notification(new ArrayList<>());
	}

	public static Notification create(final Error error) {
		return new Notification(new ArrayList<>()).append(error);
	}

	public static Notification create(final Throwable t) {
		return create(new Error(t.getMessage()));
	}

	@Override
	public Notification append(Error error) {
		this.errors.add(error);
		return this;
	}

	@Override
	public Notification append(ValidationHandler handler) {
		this.errors.addAll(handler.getErrors());
		return this;
	}

	@Override
	public Notification validate(Validation validation) {
		try {
			validation.validate();
		} catch (DomainException ex) {
			this.errors.addAll(ex.getErrors());
		} catch (Throwable t) {
			this.errors.add(new Error(t.getMessage()));
		}
		return this;
	}

	@Override
	public List<Error> getErrors() {
		return this.errors;
	}
}
