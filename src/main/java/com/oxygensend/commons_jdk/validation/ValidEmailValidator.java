package com.oxygensend.commons_jdk.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.constraintvalidators.AbstractEmailValidator;

public class ValidEmailValidator extends AbstractEmailValidator<ValidEmail> {

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        return value == null || super.isValid(value, context) && !value.isEmpty();
    }
}
