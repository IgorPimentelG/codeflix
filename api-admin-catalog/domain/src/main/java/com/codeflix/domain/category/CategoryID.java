package com.codeflix.domain.category;

import com.codeflix.domain.Identifier;
import lombok.Getter;
import java.util.UUID;

@Getter
public class CategoryID extends Identifier {

	private final String value;

	private CategoryID(final String value) {
		this.value = value;
	}

	public static CategoryID unique() {
		return new CategoryID(UUID.randomUUID().toString());
	}

	public static CategoryID from(final String id) {
		return new CategoryID(id);
	}

	public static CategoryID from(final UUID id) {
		return new CategoryID(id.toString());
	}


}
