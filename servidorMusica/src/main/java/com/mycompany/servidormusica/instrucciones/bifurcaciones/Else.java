package com.mycompany.servidormusica.instrucciones.bifurcaciones;
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
public class Else extends Instruccion implements Serializable{

    private ArrayList<Instruccion> instruccions;
    private TablaSimbolo tablaSimbol;

    @Override
    public void ejecutar(ArrayList<ErrorSemantico> errorsSemanticos) {
        this.instruccions.forEach(inst -> {
            inst.ejecutar(errorsSemanticos);
        });

    }

    @Override
    public void ReferenciaTabla(TablaSimbolo tabla) {
        this.tablaSimbol.setTablaSimbolPadre(tabla);
        instruccions.forEach(inst -> {
            inst.ReferenciaTabla(tablaSimbol);
        });
    }

    public Else(ArrayList<Instruccion> instruccions, TablaSimbolo tablaSimbol) {
        this.instruccions = instruccions;
        this.tablaSimbol = tablaSimbol;
    }

    public ArrayList<Instruccion> getInstruccions() {
        return instruccions;
    }

    public void setInstruccions(ArrayList<Instruccion> instruccions) {
        this.instruccions = instruccions;
    }

    public TablaSimbolo getTablaSimbol() {
        return tablaSimbol;
    }

    public void setTablaSimbol(TablaSimbolo tablaSimbol) {
        this.tablaSimbol = tablaSimbol;
    }

}
