package com.scalable.tracking.Validations;

import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class InputValidations {

    public static List<String> inputValidation(String originCountryId, String destinationCountryId, String weightStr, String customerName, String customerSlug) {
        List<String> errors = new ArrayList<>();

        validateCountryCode(originCountryId, "Origin country ID", errors);
        validateCountryCode(destinationCountryId, "Destination country ID", errors);
        validateWeight(weightStr, errors);
        validateNotBlank(customerName, "Customer name", errors);
        validateNotBlank(customerSlug, "Customer slug", errors);

        return errors;
    }

    private static void validateNotBlank(String value, String fieldName, List<String> errors) {
        if (value == null || value.isBlank()) {
            errors.add(fieldName + " must not be empty");
        }
    }

    private static void validateCountryCode(String value, String fieldName, List<String> errors) {
        validateNotBlank(value, fieldName, errors);
        if (!Pattern.matches("^[A-Z]{2}$", value)) {
            errors.add(fieldName + " must be a two-letter ISO 3166-1 alpha-2 code");
        }
    }

    private static void validateWeight(String value, List<String> errors) {
        validateNotBlank(value, "Weight", errors);
        if (!Pattern.matches("^\\d+\\.\\d{1,3}$|^\\d+$", value)) {
            errors.add("Weight must be a number with up to three decimal places");
        }
    }
}
