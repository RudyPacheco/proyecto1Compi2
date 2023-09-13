package com.mycompany.servidormusica.instrucciones.funciones;

import com.mycompany.servidormusica.declaracionAsignacion.Asignacion;
import com.mycompany.servidormusica.declaracionAsignacion.Operacion;
import com.mycompany.servidormusica.instrucciones.Instruccion;
import com.mycompany.servidormusica.manejoErrores.ErrorSemantico;
import com.mycompany.servidormusica.tablaSimbolos.TablaSimbolo;
import com.mycompany.servidormusica.token.Token;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author andaryus
 */

public class Retornar extends Instruccion implements Serializable{

    private TablaSimbolo tableSimbol;
    private Operacion operation;
    private Asignacion asignacion;
    private Token token;
    private boolean realizarAccion;
    private ArrayList<ErrorSemantico> errorsSemanticos;

    public Retornar(Operacion operation, Token token, ArrayList<ErrorSemantico> errorsSemanticos) {
        this.operation = operation;
        this.token = token;
        this.realizarAccion = false;
        this.asignacion = new Asignacion(operation, new Token("varRetonoSimbolTable", token.getLine(), token.getColumn()), true);
        this.errorsSemanticos = errorsSemanticos;
    }

    @Override
    public void ejecutar(ArrayList<ErrorSemantico> errorsSemanticos) {
        if (realizarAccion) {
            this.asignacion.ejecutar(errorsSemanticos);
        }
    }

    @Override
    public void ReferenciaTabla(TablaSimbolo tabla) {
        this.tableSimbol = tabla;
        this.realizarAccion = tableSimbol.buscarRetorno("varRetonoSimbolTable");
        this.asignacion.ReferenciaTabla(this.tableSimbol);
        if (!realizarAccion) {
            this.errorsSemanticos.add(new ErrorSemantico(token, "La funcion no tiene Tipo de Retrono Definido"));
        }
    }

    public TablaSimbolo getTableSimbol() {
        return tableSimbol;
    }

    public void setTableSimbol(TablaSimbolo tableSimbol) {
        this.tableSimbol = tableSimbol;
    }

    public Operacion getOperation() {
        return operation;
    }

    public void setOperation(Operacion operation) {
        this.operation = operation;
    }

    public Asignacion getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(Asignacion asignacion) {
        this.asignacion = asignacion;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public boolean isRealizarAccion() {
        return realizarAccion;
    }

    public void setRealizarAccion(boolean realizarAccion) {
        this.realizarAccion = realizarAccion;
    }

    public ArrayList<ErrorSemantico> getErrorsSemanticos() {
        return errorsSemanticos;
    }

    public void setErrorsSemanticos(ArrayList<ErrorSemantico> errorsSemanticos) {
        this.errorsSemanticos = errorsSemanticos;
    }
    
    

}
