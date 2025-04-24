package com.commons.valid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MobileValidator implements ConstraintValidator<ValidMobile, String> {
    private static final String MOBILE_REGEX = "^\\+[1-9][0-9]{1,3}\\s?[0-9]{7,15}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // esto lo tiene que validar otra Anotacion
        }
        // Usamos la expresión regular para validar el formato del número
        Pattern pattern = Pattern.compile(MOBILE_REGEX);
        Matcher matcher = pattern.matcher(value);

        // El número de teléfono debe coincidir con el patrón
        return matcher.matches();
    }
}