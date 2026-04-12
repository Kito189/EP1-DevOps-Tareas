package com.example.ticketservice.domain;

import java.util.regex.Pattern;

public class EthicalValidator {

    private static final Pattern CREDIT_CARD_PATTERN = Pattern.compile("\\b\\d{4}[- ]?\\d{4}[- ]?\\d{4}[- ]?\\d{4}\\b");

    public static void validate(String descripcion, boolean consentimiento) {

        if (!consentimiento) {
            throw new IllegalArgumentException(
                    "ERROR ÉTICO: Se requiere el consentimiento del usuario para procesar el ticket.");
        }

        if (descripcion != null && CREDIT_CARD_PATTERN.matcher(descripcion).find()) {
            throw new IllegalArgumentException(
                    "ERROR ÉTICO: La descripción contiene información sensible (posible tarjeta de crédito).");
        }

        if (descripcion != null && descripcion.toLowerCase().contains("insulto")) {
            throw new IllegalArgumentException("ERROR ÉTICO: La descripción contiene lenguaje no permitido.");
        }
    }
}
