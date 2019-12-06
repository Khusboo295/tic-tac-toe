package com.nokia.ticktacktoe.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.nokia.ticktacktoe.enums.CharacterEnums;

import java.util.Arrays;

/**
 * Implementation logic for IsValidCharacters annotation
 */
public class CharacterValidator implements ConstraintValidator<IsValidCharacters, String> {
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean result = true;
		if (value == null)
			result = false;
		else if (Arrays.stream(CharacterEnums.values())
				.noneMatch(character -> character.getValue().equalsIgnoreCase(value))) {
			result = false;
		}
		return result;
	}
}
