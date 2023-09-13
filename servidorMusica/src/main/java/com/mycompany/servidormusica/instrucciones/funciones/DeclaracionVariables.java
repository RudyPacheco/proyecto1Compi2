package com.mycompany.servidormusica.instrucciones.funciones;

import com.mycompany.servidormusica.declaracionAsignacion.Asignacion;
import com.mycompany.servidormusica.declaracionAsignacion.Operacion;
import com.mycompany.servidormusica.instrucciones.Instruccion;
import com.mycompany.servidormusica.manejoErrores.ErrorSemantico;
import com.mycompany.servidormusica.tablaSimbolos.TablaSimbolo;
import com.mycompany.servidormusica.tablaSimbolos.Variable;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author andaryus
 */


public class DeclaracionVariables extends Instruccion implements Serializable{

    private ArrayList<Variable> variables;
    private TablaSimbolo tableSimbol;
    private boolean asignacion; 
    private Asignacion asig;
    private Operacion op;

    public DeclaracionVariables(ArrayList<Variable> variables, boolean asignacion, Operacion op) {
        this.variables = variables;
        this.asignacion = asignacion;
        this.op = op;
    }

    @Override
    public void ReferenciaTabla(TablaSimbolo tabla) {
        this.tableSimbol = tabla;
        if (asignacion) {
            int indexI = this.tableSimbol.getVariables().size();
            int indexF = indexI + this.variables.size();
            this.asig = new Asignacion(indexI, indexF, op, tableSimbol);
        }
        variables.forEach(var -> {
            this.tableSimbol.variableDeclarada(var.getToken());
            this.tableSimbol.getVariables().add(var);
        });

    }

    @Override
    public void ejecutar(ArrayList<ErrorSemantico> errorsSemanticos) {
        if (asignacion) {
            this.asig.ejecutar(errorsSemanticos);
        }
    }

    /*getter and setters*/
    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public void setVariables(ArrayList<Variable> variables) {
        this.variables = variables;
    }

    public TablaSimbolo getTableSimbol() {
        return tableSimbol;
    }

    public void setTableSimbol(TablaSimbolo tableSimbol) {
        this.tableSimbol = tableSimbol;
    }

    public boolean isAsignacion() {
        return asignacion;
    }

    public void setAsignacion(boolean asignacion) {
        this.asignacion = asignacion;
    }

    public Asignacion getAsig() {
        return asig;
    }

    public void setAsig(Asignacion asig) {
        this.asig = asig;
    }

    public Operacion getOp() {
        return op;
    }

    public void setOp(Operacion op) {
        this.op = op;
    }

}
