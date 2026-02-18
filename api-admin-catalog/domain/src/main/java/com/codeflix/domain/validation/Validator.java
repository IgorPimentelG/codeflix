package com.codeflix.domain.validation;

import lombok.Getter;

@Getter
public abstract class Validator {

	private final ValidationHandler handler;

	protected Validator(final ValidationHandler handler) {
		this.handler = handler;
	}

	public abstract void validate();
}
