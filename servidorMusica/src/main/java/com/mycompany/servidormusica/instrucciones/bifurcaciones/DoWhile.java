package com.mycompany.servidormusica.instrucciones.bifurcaciones;

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
public class DoWhile extends Instruccion implements Serializable{

    private ArrayList<Instruccion> instruccions;
    private Operacion condicion;
    private TablaSimbolo tablaSimbol;

    public DoWhile(ArrayList<Instruccion> instruccions, Operacion condicion, TablaSimbolo tablaSimbol) {
        this.instruccions = instruccions;
        this.condicion = condicion;
        this.tablaSimbol = tablaSimbol;
    }
    
    

    @Override
    public void ReferenciaTabla(TablaSimbolo tabla) {
        this.tablaSimbol.setTablaSimbolPadre(tabla);
        instruccions.forEach(inst -> {
            inst.ReferenciaTabla(tablaSimbol);
        });
    }

    @Override
    public void ejecutar(ArrayList<ErrorSemantico> errorsSemanticos) {
        boolean condicionv = false;
        do {
            instruccions.forEach(inst -> {
                inst.ejecutar(errorsSemanticos);
            });
            condicionv = this.valorBoolean(errorsSemanticos);
            
        } while (condicionv);
    }

    private boolean valorBoolean(ArrayList<ErrorSemantico> errorsSemanticos) {
        Expresion dato = this.condicion.ejecutar(errorsSemanticos, tablaSimbol);
        if (dato.getTipoDato() != TipoDato.BOOLEAN) {

            errorsSemanticos.add(new ErrorSemantico(dato.getToken(), "La expresion no es valida, la expresion debe de ser de tipo boolean"));
            return false;
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
    
    

}
