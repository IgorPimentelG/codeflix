package com.codeflix.domain;

import com.codeflix.domain.validation.ValidationHandler;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@Getter
@EqualsAndHashCode
public abstract class Entity<ID extends Identifier> {

	protected final ID id;

	public abstract void validate(ValidationHandler handler);

	protected Entity(final ID id) {
		Objects.requireNonNull(id, "ID should not be null");
		this.id = id;
	}
}
