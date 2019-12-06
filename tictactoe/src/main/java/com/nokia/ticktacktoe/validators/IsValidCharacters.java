package com.nokia.ticktacktoe.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Declaration of custom annotation for input character validation
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { CharacterValidator.class })
public @interface IsValidCharacters {
	String message() default "Incorrect Character! Please choice character between (x or o) ";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
