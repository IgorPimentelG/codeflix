package com.codeflix.domain.category;

import com.codeflix.domain.AggregateRoot;
import com.codeflix.domain.validation.ValidationHandler;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Setter
@Getter
public class Category extends AggregateRoot<CategoryID> {

	private String name;
	private String description;
	private boolean active;
	private Instant createdAt;
	private Instant updatedAt;
	private Instant deletedAt;

	private Category(
	  final CategoryID id,
	  final String name,
	  final String description,
	  final boolean active,
	  final Instant createdAt,
	  final Instant updatedAt,
	  final Instant deletedAt
	) {
		super(id);
		this.name = name;
		this.description = description;
		this.active = active;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	public static Category newCategory(final String name, final String description, final boolean isActive) {
		final var id = CategoryID.unique();
		final var now = Instant.now();
		final var deletedAt = isActive ? null : now;
		return new Category(id, name, description, isActive, now, now, deletedAt);
	}

	public Category update(
	  final String name,
	  final String description,
	  final boolean isActive
	) {
		this.name = name;
		this.description = description;

		if (isActive) {
			activate();
		} else {
			deactivate();
		}

		this.updatedAt = Instant.now();
		return this;
	}

	public Category deactivate() {
		if (this.deletedAt == null) {
			this.deletedAt = Instant.now();
		}

		this.active = false;
		this.updatedAt = Instant.now();

		return this;
	}

	public Category activate() {
		this.active = true;
		this.deletedAt = null;
		this.updatedAt = Instant.now();
		return this;
	}

	@Override
	public void validate(ValidationHandler handler) {
		new CategoryValidator(this, handler).validate();
	}
}
