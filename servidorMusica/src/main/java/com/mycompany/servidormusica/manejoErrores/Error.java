package com.mycompany.servidormusica.manejoErrores;

import com.mycompany.servidormusica.token.Token;
import java.io.Serializable;

/**
 *
 * @author andaryus
 */


public class Error implements Serializable{

    protected Token token;
    protected String descripcion;
    
    protected Error(Token token, String descripcion) {
        this.token = token;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
    
    

}
