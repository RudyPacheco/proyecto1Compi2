package com.mycompany.servidormusica.instrucciones.bifurcaciones;

import com.mycompany.servidormusica.declaracionAsignacion.Expresion;
import com.mycompany.servidormusica.declaracionAsignacion.TipoDato;
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

public class Switch extends Instruccion implements Serializable{

    private ArrayList<validarSwitch> casos;
    private Variable varSwintch;
    private TablaSimbolo tablaSimbol;

    public Switch(ArrayList<validarSwitch> casos, Variable varSwintch, TablaSimbolo tablaSimbol) {
        this.casos = casos;
        this.varSwintch = varSwintch;
        this.tablaSimbol = tablaSimbol;
    }
    
    

    @Override
    public void ejecutar(ArrayList<ErrorSemantico> errorsSemanticos) {
        Expresion dato = this.tablaSimbol.getDato(varSwintch.getToken(), varSwintch.getNombre());
        for (validarSwitch caso : casos) {
            caso.setDatoSwintch(dato);
            caso.ejecutar(errorsSemanticos);
            if (caso.isSeRealizoAccion()) {
                break;
            }
        }
        
    }

    @Override
    public void ReferenciaTabla(TablaSimbolo tabla) {
        this.tablaSimbol.setTablaSimbolPadre(tabla);
        this.casos.forEach(caso ->{
            caso.ReferenciaTabla(tablaSimbol);
        });
    }

    public ArrayList<validarSwitch> getCasos() {
        return casos;
    }

    public void setCasos(ArrayList<validarSwitch> casos) {
        this.casos = casos;
    }

    public Variable getVarSwintch() {
        return varSwintch;
    }

    public void setVarSwintch(Variable varSwintch) {
        this.varSwintch = varSwintch;
    }

    public TablaSimbolo getTablaSimbol() {
        return tablaSimbol;
    }

    public void setTablaSimbol(TablaSimbolo tablaSimbol) {
        this.tablaSimbol = tablaSimbol;
    }

    
    
}
