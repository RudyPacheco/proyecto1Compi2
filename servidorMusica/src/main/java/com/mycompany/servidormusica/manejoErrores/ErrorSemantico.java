
package com.mycompany.servidormusica.manejoErrores;

import com.mycompany.servidormusica.token.Token;
import java.io.Serializable;

/**
 *
 * @author andaryus
 */


public class ErrorSemantico extends Error implements Serializable{
    
    public ErrorSemantico(Token token, String descripcion) {
        super(token, descripcion);
    }
    
    
    
}
