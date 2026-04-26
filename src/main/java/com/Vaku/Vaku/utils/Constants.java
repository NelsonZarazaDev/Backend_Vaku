package com.Vaku.Vaku.utils;

import lombok.Getter;

@Getter
public enum Constants {
    CHILD_ALREADY_EXISTS("El nino ya existe"),
    PARENT_ALREADY_EXISTS("El padre de familia ya existe"),
    EMPLOYEE_ALREADY_EXISTS("El empleado ya existe"),
    EMAIL_ALREADY_EXISTS("El email ya existe"),
    PHONE_ALREADY_EXISTS("El telefono ya existe"),
    DOCUMENT_ALREADY_EXISTS("El documento ya existe"),
    EMAIL_EMPTY("El email no puede estar vacio"),
    PHONE_EMPTY("El telefono no puede estar vacio"),
    PASSWORD_EMPTY("La contrasena no puede estar vacia"),
    INVALID_ROLE("El rol no es valido"),
    INVALID_EMAIL_FORMAT("El email no tiene un formato valido"),
    INVALID_PHONE_FORMAT("El telefono no tiene un formato valido"),
    INVALID_DOCUMENT_FORMAT("El documento no tiene un formato valido"),
    CHILD_NOT_EXISTS("El nino no existe"),
    INVENTORIE_NOT_EXISTS("El inventario no existe"),
    CREDENTIAL_INVALID("Credenciales invalidas!");

    private final String message;

    Constants(String s) {
        this.message = s;
    }
}
