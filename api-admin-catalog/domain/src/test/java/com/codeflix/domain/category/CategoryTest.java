package com.codeflix.domain.category;

import com.codeflix.domain.exceptions.DomainException;
import com.codeflix.domain.validation.handlers.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

	@Test
	public void givenValidParams_whenCallNewCategory_thenInstantiateACategory() {
		final var expectedName = "Filmes";
		final var expectedDescription = "A categoria mais assistida";
		final var expectedIsActive = true;

		final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

		assertNotNull(category);
		assertNotNull(category.getId());
		assertEquals(expectedName, category.getName());
		assertEquals(expectedDescription, category.getDescription());
		assertEquals(expectedIsActive, category.isActive());
		assertNotNull(category.getCreatedAt());
		assertNotNull(category.getUpdatedAt());
		assertNull(category.getDeletedAt());
	}

	@Test
	public void givenAValidFalseIsActiveDescription_whenCallNewCategory_thenShouldReturnException() {
		final var expectedName = "Filmes";
		final var expectedDescription = "A categoria mais assistida";
		final var expectedIsActive = false;

		final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

		assertNotNull(category);
		assertNotNull(category.getId());
		assertEquals(expectedName, category.getName());
		assertEquals(expectedDescription, category.getDescription());
		assertEquals(expectedIsActive, category.isActive());
		assertNotNull(category.getCreatedAt());
		assertNotNull(category.getUpdatedAt());
		assertNotNull(category.getDeletedAt());
	}

	@Test
	public void givenAnInvalidNullName_whenCallNewCategory_thenShouldReturnException() {
		final String expectedName = null;
		final var expectedDescription = "A categoria mais assistida";
		final var expectedIsActive = true;
		final var expectedErrorCount = 1;
		final var expectedErrorMessage = "Name should not be null.";

		final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
		final var exception = assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));

		assertEquals(expectedErrorCount, exception.getErrors().size());
		assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
	}

	@Test
	public void givenAnInvalidEmptyName_whenCallNewCategory_thenShouldReturnException() {
		final var expectedName = " ";
		final var expectedDescription = "A categoria mais assistida";
		final var expectedIsActive = true;
		final var expectedErrorCount = 1;
		final var expectedErrorMessage = "Name should not be empty.";

		final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
		final var exception = assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));

		assertEquals(expectedErrorCount, exception.getErrors().size());
		assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
	}

	@Test
	public void givenAnInvalidNameLengthLessThan3_whenCallNewCategory_thenShouldReturnException() {
		final var expectedName = "fi ";
		final var expectedDescription = "A categoria mais assistida";
		final var expectedIsActive = true;
		final var expectedErrorCount = 1;
		final var expectedErrorMessage = "Name must be between 3 and 255 characters.";

		final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
		final var exception = assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));

		assertEquals(expectedErrorCount, exception.getErrors().size());
		assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
	}

	@Test
	public void givenAnInvalidNameLengthMoreThan255_whenCallNewCategory_thenShouldReturnException() {
		final var expectedName = """
		    No mundo atual, a mobilidade dos capitais internacionais deve passar por modificações independentemente 
		    do sistema de formação de quadros que corresponde às necessidades. As experiências acumuladas demonstram 
		    que a execução dos pontos do programa desafia a capacidade de equalização das condições inegavelmente apropriadas.
		  """;
		final var expectedDescription = "A categoria mais assistida";
		final var expectedIsActive = true;
		final var expectedErrorCount = 1;
		final var expectedErrorMessage = "Name must be between 3 and 255 characters.";

		final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
		final var exception = assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));

		assertEquals(expectedErrorCount, exception.getErrors().size());
		assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
	}

	@Test
	public void givenAnInvalidEmptyDescription_whenCallNewCategory_thenShouldReturnException() {
		final var expectedName = "Filmes";
		final var expectedDescription = " ";
		final var expectedIsActive = true;
		final var expectedErrorCount = 1;
		final var expectedErrorMessage = "Description should not be empty.";

		final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
		final var exception = assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));

		assertEquals(expectedErrorCount, exception.getErrors().size());
		assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
	}

	@Test
	public void givenAValidActiveCategory_whenCallDeactivate_thenReturnCategoryInactivate() {
		final var expectedName = "Filmes";
		final var expectedDescription = "A categoria mais assistida";

		final var category = Category.newCategory(expectedName, expectedDescription, true);

		final var updatedAt = category.getUpdatedAt();

		assertTrue(category.isActive());
		assertNull(category.getDeletedAt());

		final var actualCategory = category.deactivate();

		assertFalse(actualCategory.isActive());
		assertNotNull(actualCategory.getDeletedAt());
		assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
	}

	@Test
	public void givenAValidInactiveCategory_whenCallActivate_thenReturnCategoryActivate() {
		final var expectedName = "Filmes";
		final var expectedDescription = "A categoria mais assistida";

		final var category = Category.newCategory(expectedName, expectedDescription, false);

		final var updatedAt = category.getUpdatedAt();


		assertFalse(category.isActive());
		assertNotNull(category.getDeletedAt());

		final var actualCategory = category.activate();

		assertTrue(actualCategory.isActive());
		assertNull(actualCategory.getDeletedAt());
		assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
	}

	@Test
	public void givenValidCategory_whenCallUpdate_thenReturnCategoryUpdated() {
		final var expectedName = "Filmes";
		final var expectedDescription = "A categoria mais assistida";
		final var expectedIsActive = true;

		final var category = Category.newCategory("Film", "A categoria", false);
		assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));

		final var createdAt = category.getCreatedAt();
		final var updatedAt = category.getUpdatedAt();

		final var actualCategory = category.update(expectedName, expectedDescription, expectedIsActive);

		assertEquals(category.getId(), actualCategory.getId());
		assertEquals(expectedName, actualCategory.getName());
		assertEquals(expectedDescription, actualCategory.getDescription());
		assertEquals(expectedIsActive, actualCategory.isActive());
		assertEquals(category.getCreatedAt(), actualCategory.getCreatedAt());
		assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
		assertNull(category.getDeletedAt());
	}

	@Test
	public void givenValidCategory_whenCallUpdateWithInvalidParams_thenReturnCategoryUpdated() {
		final String expectedName = null;
		final var expectedDescription = "A categoria mais assistida";
		final var expectedIsActive = true;

		final var category = Category.newCategory("Filmes", "A categoria", true);
		assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));

		final var createdAt = category.getCreatedAt();
		final var updatedAt = category.getUpdatedAt();

		final var actualCategory = category.update(expectedName, expectedDescription, expectedIsActive);

		assertNull(actualCategory.getName());
		assertEquals(category.getId(), actualCategory.getId());
		assertEquals(expectedDescription, actualCategory.getDescription());
		assertEquals(expectedIsActive, actualCategory.isActive());
		assertEquals(category.getCreatedAt(), actualCategory.getCreatedAt());
		assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
		assertNull(category.getDeletedAt());
	}
}
