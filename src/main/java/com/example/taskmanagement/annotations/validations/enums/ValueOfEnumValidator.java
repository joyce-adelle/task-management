package com.example.taskmanagement.annotations.validations.enums;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, CharSequence> {
    private List<String> acceptedValues;

    private boolean checkWithUpperCase;

    @Override
    public void initialize(ValueOfEnum annotation) {

        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
        this.checkWithUpperCase = annotation.checkWithUpperCase();

    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        return checkWithUpperCase ? acceptedValues.contains(value.toString().toUpperCase()) : acceptedValues.contains(value.toString());

    }

}
