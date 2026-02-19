package com.codeflix.application.category.create;

import com.codeflix.application.UseCase;
import com.codeflix.domain.validation.handlers.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase extends UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>> {}
