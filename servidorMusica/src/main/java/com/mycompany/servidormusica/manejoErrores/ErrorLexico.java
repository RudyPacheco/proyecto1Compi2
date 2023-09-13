
package com.mycompany.servidormusica.manejoErrores;

import com.mycompany.servidormusica.token.Token;
import java.io.Serializable;

/**
 *
 * @author andaryus
 */

public class ErrorLexico extends Error implements Serializable{
    
    public ErrorLexico(Token token, String descripcion) {
        super(token, descripcion);
    }
    
}
