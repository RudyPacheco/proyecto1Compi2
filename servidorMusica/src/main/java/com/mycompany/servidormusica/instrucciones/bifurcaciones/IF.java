package com.mycompany.servidormusica.instrucciones.bifurcaciones;

import com.mycompany.servidormusica.declaracionAsignacion.TipoDato;
import com.mycompany.servidormusica.instrucciones.Instruccion;
import com.mycompany.servidormusica.manejoErrores.ErrorSemantico;
import com.mycompany.servidormusica.tablaSimbolos.TablaSimbolo;
import com.mycompany.servidormusica.declaracionAsignacion.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author andaryus
 */
public class IF extends Instruccion implements Serializable {

    private ArrayList<Instruccion> instruccions;
    private Operacion condicion;
    private TablaSimbolo tablaSimbol;
    private Else sentenciaElse;
    private IF sentenciaElseIf;

    public IF(ArrayList<Instruccion> instruccions, Operacion condicion, TablaSimbolo tablaSimbol) {
        this.instruccions = instruccions;
        this.condicion = condicion;
        this.tablaSimbol = tablaSimbol;
    }

    @Override
    public void ejecutar(ArrayList<ErrorSemantico> errorsSemanticos) {
        if (valorBoolean(errorsSemanticos)) {
            this.instruccions.forEach(inst -> {
                inst.ejecutar(errorsSemanticos);
            });
        } else {
            if (null == sentenciaElseIf && null != this.sentenciaElse) {
                this.sentenciaElse.ejecutar(errorsSemanticos);
            } else {
                if (null != sentenciaElseIf) {
                    this.sentenciaElseIf.ejecutar(errorsSemanticos);
                }
            }
        }
    }

    @Override
    public void ReferenciaTabla(TablaSimbolo tabla) {
        this.tablaSimbol.setTablaSimbolPadre(tabla);
        if (null != this.sentenciaElseIf) {
            this.sentenciaElseIf.ReferenciaTabla(tabla);
        }
        if (null != this.sentenciaElse) {
            this.sentenciaElse.ReferenciaTabla(tabla);
        }
        instruccions.forEach(inst -> {
            inst.ReferenciaTabla(tablaSimbol);
        });
    }

    private boolean valorBoolean(ArrayList<ErrorSemantico> errorsSemanticos) {
        Expresion dato = this.condicion.ejecutar(errorsSemanticos, tablaSimbol);
        if (dato.getTipoDato() != TipoDato.BOOLEAN) {

            errorsSemanticos.add(new ErrorSemantico(dato.getToken(), "La expresion no es valida, la condicional debe de ser tipo boolean"));
            return true;
        }
        return dato.isBooleano();
    }

    public ArrayList<Instruccion> getInstruccions() {
        return instruccions;
    }

    public void setInstruccions(ArrayList<Instruccion> instruccions) {
        this.instruccions = instruccions;
    }

    public Operacion getCondicion() {
        return condicion;
    }

    public void setCondicion(Operacion condicion) {
        this.condicion = condicion;
    }

    public TablaSimbolo getTablaSimbol() {
        return tablaSimbol;
    }

    public void setTablaSimbol(TablaSimbolo tablaSimbol) {
        this.tablaSimbol = tablaSimbol;
    }

    public Else getSentenciaElse() {
        return sentenciaElse;
    }

    public void setSentenciaElse(Else sentenciaElse) {
        this.sentenciaElse = sentenciaElse;
    }

    public IF getSentenciaElseIf() {
        return sentenciaElseIf;
    }

    public void setSentenciaElseIf(IF sentenciaElseIf) {
        this.sentenciaElseIf = sentenciaElseIf;
    }

}
