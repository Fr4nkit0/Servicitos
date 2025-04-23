package com.customer.application.exceptions;

/**
 * Excepción que se lanza cuando no se puede encontrar un cliente en el sistema.
 *
 * Esta clase extiende RuntimeException e incluye información adicional
 * sobre el identificador del cliente que no fue encontrado.
 * También permite encadenar una causa (otra excepción que originó esta).
 *
 * Ejemplo de uso:
 * throw new CustomerNotFoundException("ID: 123");
 *
 * Con causa:
 * throw new CustomerNotFoundException(new SQLException(), "ID: 123");
 */

public class CustomerNotFoundException extends RuntimeException {
    /**
     * Identificador del cliente que no fue encontrado.
     * Puede ser un ID, email u otro dato representativo.
     */
    private final String customerIdentifier;
    /**
     * Excepción que causó esta excepción, si existe.
     */
    private final Throwable cause;

    /**
     * Constructor que acepta una causa y un identificador de cliente.
     *
     * @param cause              la excepción que causó esta excepción.
     * @param customerIdentifier el identificador del cliente no encontrado.
     */

    public CustomerNotFoundException(Throwable cause, String customerIdentifier) {
        this.customerIdentifier = customerIdentifier;
        this.cause = cause;
    }

    /**
     * Constructor que acepta únicamente el identificador del cliente.
     *
     * @param customerIdentifier el identificador del cliente no encontrado.
     */
    public CustomerNotFoundException(String customerIdentifier) {
        this.customerIdentifier = customerIdentifier;
        this.cause = null;
    }

    /**
     * Devuelve el mensaje de la excepción incluyendo el identificador del cliente
     * no encontrado.
     *
     * @return mensaje de error detallado.
     */
    @Override
    public String getMessage() {
        String message = super.getMessage();
        if (message == null) {
            message = "";
        }
        return message
                .concat("(customer not found: ")
                .concat(this.customerIdentifier)
                .concat(")");
    }

    /**
     * Devuelve el identificador del cliente no encontrado.
     *
     * @return el identificador como {@code String}.
     */

    public String getCustomerIdentifier() {
        return customerIdentifier;
    }

    /**
     * Devuelve la causa original de la excepción, si existe.
     *
     * @return la excepción que causó esta excepción, o {@code null} si no hay
     *         causa.
     */
    @Override
    public synchronized Throwable getCause() {
        return cause;
    }
}
