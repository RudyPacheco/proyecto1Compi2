
package com.mycompany.servidormusica.manejoErrores;

import com.mycompany.servidormusica.token.Token;
import java.io.Serializable;

/**
 *
 * @author andaryus
 */


public class ErrorSintactico extends Error implements Serializable {
    
    public ErrorSintactico(Token token, String descripcion) {
        super(token, descripcion);
    }
    
}
