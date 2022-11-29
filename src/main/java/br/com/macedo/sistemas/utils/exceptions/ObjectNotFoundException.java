package br.com.macedo.sistemas.utils.exceptions;

import javax.ws.rs.BadRequestException;

public class ObjectNotFoundException extends BadRequestException {

    public ObjectNotFoundException(String msg) {
        super(msg);
    }
}
