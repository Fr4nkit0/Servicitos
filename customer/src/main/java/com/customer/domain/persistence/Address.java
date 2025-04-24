package com.customer.domain.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
/**
 * Representa una dirección postal embebida dentro de una entidad.
 * Utilizada para almacenar información completa de la ubicación del cliente.
 */
public class Address {
    /**
     * País donde se encuentra la dirección.
     * Ejemplo: Argentina, Estados Unidos, España, etc.
     */
    @Column(name = "country", length = 50, nullable = false)
    private String country;

    /**
     * Estado o provincia donde se encuentra la dirección.
     * Ejemplo: Buenos Aires, California, Madrid, etc.
     */
    @Column(name = "state", length = 50, nullable = false)
    private String state;
    /**
     * Ciudad o localidad donde se encuentra la dirección.
     * Ejemplo: Rosario, Los Angeles, Barcelona, etc.
     */

    @Column(name = "city", length = 50, nullable = false)
    private String city;
    /**
     * Código postal o zip code asociado a la dirección.
     * Ejemplo: 2000, 90001, 08080, etc.
     */

    @Column(name = "postal_code", length = 20, nullable = false)
    private String postalCode;
    /**
     * Calle de la dirección.
     * Ejemplo: Av. Santa Fe, Calle Mayor, etc.
     */

    @Column(name = "street", length = 100, nullable = false)
    private String street;
    /**
     * Número de la calle o edificio.
     * Ejemplo: 123, 456B, etc.
     */

    @Column(name = "street_number", length = 10, nullable = false)
    private String streetNumber;
    /**
     * Apartamento o número de unidad, si aplica.
     * Ejemplo: 5A, P-303, etc.
     */

    @Column(name = "apartment", length = 10)
    private String apartment;
    /**
     * Piso del edificio, si aplica.
     * Ejemplo: Piso 2, 3er Piso, etc.
     */

    @Column(name = "floor", length = 5)
    private String floor;
    /**
     * Información adicional relevante sobre la dirección.
     * Ejemplo: Cerca del parque, Frente al hospital, etc.
     */

    @Column(name = "additional_info", length = 200)
    private String additionalInfo;

}