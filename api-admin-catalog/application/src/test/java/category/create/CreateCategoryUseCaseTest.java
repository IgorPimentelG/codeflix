package category.create;

import com.codeflix.application.category.create.CreateCategoryCommand;
import com.codeflix.application.category.create.DefaultCreateCategoryUseCase;
import com.codeflix.domain.category.CategoryGateway;
import com.codeflix.domain.exceptions.DomainException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

	@InjectMocks
	private DefaultCreateCategoryUseCase useCase;

	@Mock
	private CategoryGateway categoryGateway;

	@Test
	public void givenValidCommand_whenCallsCreateCategory_shouldReturnCategoryID() {
		final var expectedName = "Filme";
		final var expectedDescription = "A categoria mais assistida";
		final var expectedIsActive = true;

		when(categoryGateway.create(Mockito.any())).thenAnswer(returnsFirstArg());

		final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);
		final var output = useCase.execute(command);

		assertNotNull(output);
		assertNotNull(output.id());

		verify(categoryGateway, times(1)).create(argThat((category ->
			Objects.equals(expectedName, category.getName()) &&
			Objects.equals(expectedDescription, category.getDescription()) &&
			Objects.equals(expectedIsActive, category.isActive()) &&
			Objects.nonNull(category.getCreatedAt()) &&
			Objects.nonNull(category.getUpdatedAt()) &&
			Objects.isNull(category.getDeletedAt())
		)));
	}

	@Test
	public void givenInvalidCommandName_whenCallsCreateCategory_whenShouldReturnDomainException() {
		final String expectedName = null;
		final var expectedDescription = "A categoria mais assistida";
		final var expectedIsActive = true;
		final var expectedErrorMessage = "Name should not be null.";
		final var expectedErrorCount = 1;

		final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);
		final var exception = assertThrows(DomainException.class, () -> useCase.execute(command));

		assertEquals(expectedErrorMessage, exception.getMessage());
		assertEquals(expectedErrorCount, exception.getErrors().size());

		verify(categoryGateway, times(0)).create(any());
	}

	@Test
	public void givenInvalidCommandInactiveCategory_whenCallsCreateCategory_whenShouldReturnInactiveCategoryId() {
		final var expectedName = "Filmes";
		final var expectedDescription = "A categoria mais assistida";
		final var expectedIsActive = false;

		when(categoryGateway.create(Mockito.any())).thenAnswer(returnsFirstArg());

		final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);
		final var output = useCase.execute(command);

		assertNotNull(output);
		assertNotNull(output.id());

		verify(categoryGateway, times(1)).create(argThat((category ->
			Objects.equals(expectedName, category.getName()) &&
				Objects.equals(expectedDescription, category.getDescription()) &&
				Objects.equals(expectedIsActive, category.isActive()) &&
				Objects.nonNull(category.getCreatedAt()) &&
				Objects.nonNull(category.getUpdatedAt()) &&
				Objects.nonNull(category.getDeletedAt())
		)));
	}

	@Test
	public void givenValidCommand_whenGatewayThrowsRandomException_whenShouldReturnException() {
		final var expectedName = "Filmes";
		final var expectedDescription = "A categoria mais assistida";
		final var expectedIsActive = false;
		final var expectedErrorMessage = "Gateway error";

		when(categoryGateway.create(any())).thenThrow(new IllegalStateException(expectedErrorMessage));

		final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);
		final var exception = assertThrows(IllegalStateException.class, () -> useCase.execute(command));

		assertEquals(expectedErrorMessage, exception.getMessage());
		verify(categoryGateway, times(1)).create(any());
	}
}
