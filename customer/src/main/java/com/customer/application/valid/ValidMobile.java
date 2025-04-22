package com.customer.application.valid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = MobileValidator.class) // Apunta a la clase validadora
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMobile {

    String message() default "{invalidMobile}"; // Mensaje por defecto

    Class<?>[] groups() default {}; // Se usa para agrupar validaciones

    Class<? extends Payload>[] payload() default {}; // Se usa para transportar información adicional

}
