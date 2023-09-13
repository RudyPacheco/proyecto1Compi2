package com.mycompany.servidormusica.instrucciones.bifurcaciones;

import com.mycompany.servidormusica.declaracionAsignacion.Asignacion;
import com.mycompany.servidormusica.declaracionAsignacion.Expresion;
import com.mycompany.servidormusica.declaracionAsignacion.Operacion;
import com.mycompany.servidormusica.declaracionAsignacion.TipoDato;
import com.mycompany.servidormusica.instrucciones.Instruccion;
import com.mycompany.servidormusica.manejoErrores.ErrorSemantico;
import com.mycompany.servidormusica.tablaSimbolos.TablaSimbolo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author andaryus
 */

public class For extends Instruccion implements Serializable{

    private Instruccion declaracion;
    private TablaSimbolo tableSimbol;
    private ArrayList<Instruccion> instruccions;
    private Asignacion incremetDecremet;
    private Operacion condicion;

    public For(Instruccion declaracion, TablaSimbolo tableSimbol, Asignacion incremetDecremet, Operacion condicion) {
        this.declaracion = declaracion;
        this.tableSimbol = tableSimbol;
        this.incremetDecremet = incremetDecremet;
        this.condicion = condicion;
    }

    @Override
    public void ejecutar(ArrayList<ErrorSemantico> errorsSemanticos) {
        this.declaracion.ejecutar(errorsSemanticos);
        while (comprobarCondicion(errorsSemanticos)) {
            this.instruccions.forEach(inst -> {
                inst.ejecutar(errorsSemanticos);
            });
            incremetDecremet.ejecutar(errorsSemanticos);
        }
    }

    @Override
    public void ReferenciaTabla(TablaSimbolo tabla) {
        this.tableSimbol.setTablaSimbolPadre(tabla);
        this.declaracion.ReferenciaTabla(tableSimbol);
        this.incremetDecremet.ReferenciaTabla(tableSimbol);
        this.instruccions.forEach(inst -> {
            inst.ReferenciaTabla(tableSimbol);
        });
    }

    private boolean comprobarCondicion(ArrayList<ErrorSemantico> errorsSemanticos) {
        Expresion dato = condicion.ejecutar(errorsSemanticos, tableSimbol);
        if (dato.getTipoDato() != TipoDato.BOOLEAN) {

            errorsSemanticos.add(new ErrorSemantico(dato.getToken(), "La expresion no es valida, la condicional debe de ser de tipo boolean"));
            return false;
        }
        return dato.isBooleano();
    }

    public Operacion getCondicion() {
        return condicion;
    }

    public void setCondicion(Operacion condicion) {
        this.condicion = condicion;
    }

    public Instruccion getDeclaracion() {
        return declaracion;
    }

    public void setDeclaracion(Instruccion declaracion) {
        this.declaracion = declaracion;
    }

    public TablaSimbolo getTableSimbol() {
        return tableSimbol;
    }

    public void setTableSimbol(TablaSimbolo tableSimbol) {
        this.tableSimbol = tableSimbol;
    }

    public ArrayList<Instruccion> getInstruccions() {
        return instruccions;
    }

    public void setInstruccions(ArrayList<Instruccion> instruccions) {
        this.instruccions = instruccions;
    }

    public Asignacion getIncremetDecremet() {
        return incremetDecremet;
    }

    public void setIncremetDecremet(Asignacion incremetDecremet) {
        this.incremetDecremet = incremetDecremet;
    }

}
