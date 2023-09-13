
package com.mycompany.servidormusica.instrucciones.funciones;

import com.mycompany.servidormusica.declaracionAsignacion.Expresion;
import com.mycompany.servidormusica.declaracionAsignacion.Operacion;
import com.mycompany.servidormusica.instrucciones.Instruccion;
import com.mycompany.servidormusica.manejoErrores.ErrorSemantico;
import com.mycompany.servidormusica.tablaSimbolos.TablaSimbolo;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author andaryus
 */

public class Mensaje extends Instruccion implements Serializable{
    
    private Operacion operation;
    private TablaSimbolo  TablaSimbol ;

    public Mensaje(Operacion operation) {
        this.operation = operation;
    }

    
    
    @Override
    public void ejecutar(ArrayList<ErrorSemantico> errorsSemanticos) {
        Expresion dato = operation.ejecutar(errorsSemanticos, TablaSimbol);
        FuncionMensaje.getInstanceMensajes().push(dato);
    }

    @Override
    public void ReferenciaTabla(TablaSimbolo tabla) {
        this.TablaSimbol = tabla;
        
    }

    public Operacion getOperation() {
        return operation;
    }

    public void setOperation(Operacion operation) {
        this.operation = operation;
    }

    public TablaSimbolo getTablaSimbol() {
        return TablaSimbol;
    }

    public void setTablaSimbol(TablaSimbolo TablaSimbol) {
        this.TablaSimbol = TablaSimbol;
    }
    
    
    
    

    
    
    
    
}
