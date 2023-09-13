package com.mycompany.servidormusica.instrucciones.funciones;

import com.mycompany.servidormusica.declaracionAsignacion.NodoOperacion;
import com.mycompany.servidormusica.instrucciones.Instruccion;
import com.mycompany.servidormusica.manejoErrores.ErrorSemantico;
import com.mycompany.servidormusica.tablaSimbolos.TablaSimbolo;
import java.util.ArrayList;
import java.io.Serializable;

/**
 *
 * @author andaryus
 */


public class LlamadaFuncion extends Instruccion implements Serializable{

    private NodoOperacion rootOperation;

    public LlamadaFuncion(NodoOperacion rootOperation) {
        this.rootOperation = rootOperation;
    }
    
    

    @Override
    public void ejecutar(ArrayList<ErrorSemantico> errorsSemanticos) {
        this.rootOperation.setRetorna(false);
        this.rootOperation.ejecutarOperacion(errorsSemanticos, null);
    }

    @Override
    public void ReferenciaTabla(TablaSimbolo tabla) {

    }

    public NodoOperacion getRootOperation() {
        return rootOperation;
    }

    public void setRootOperation(NodoOperacion rootOperation) {
        this.rootOperation = rootOperation;
    }
    
    

}
