
package com.mycompany.servidormusica.instrucciones;

import com.mycompany.servidormusica.manejoErrores.ErrorSemantico;
import com.mycompany.servidormusica.tablaSimbolos.TablaSimbolo;
import java.io.Serializable;
import java.util.ArrayList;


/**
 *
 * @author andaryus
 */
public abstract class Instruccion implements Serializable{
    
    
    public abstract void ejecutar(ArrayList<ErrorSemantico> errorsSemanticos);
    
    public abstract void ReferenciaTabla(TablaSimbolo tabla);
    
}
