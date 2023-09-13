package com.mycompany.servidormusica.declaracionAsignacion;

import com.mycompany.servidormusica.manejoErrores.ErrorSemantico;
import com.mycompany.servidormusica.tablaSimbolos.TablaSimbolo;

import java.io.Serializable;
import java.util.ArrayList;


/**
 *
 * @author andaryus
 */



public class Operacion implements Serializable{
    private NodoOperacion rootOperation;

    public Operacion(Expresion dato){
        this.rootOperation = new NodoOperacion(dato);
    }

    public Operacion(NodoOperacion rootOperation) {
        this.rootOperation = rootOperation;
    }

    public NodoOperacion getRootOperation() { 
        return rootOperation; 
    }


    public Expresion ejecutar(ArrayList<ErrorSemantico> errorsSemanticos, TablaSimbolo tabla){
        Expresion temp =rootOperation.ejecutarOperacion(errorsSemanticos, tabla); 
        return temp;
    }


}
