
package com.mycompany.servidormusica.instrucciones.funciones;

import com.mycompany.servidormusica.instrucciones.Instruccion;
import com.mycompany.servidormusica.manejoErrores.ErrorSemantico;
import com.mycompany.servidormusica.tablaSimbolos.TablaSimbolo;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author andaryus
 */

public class Continuar extends Instruccion implements Serializable{

    @Override
    public void ejecutar(ArrayList<ErrorSemantico> errorsSemanticos) {
        //logica para salir de los bucles XD
    }
    

    @Override
    public void ReferenciaTabla(TablaSimbolo tabla) {
        //referenciar la tabla del padre 
    }
    
}
