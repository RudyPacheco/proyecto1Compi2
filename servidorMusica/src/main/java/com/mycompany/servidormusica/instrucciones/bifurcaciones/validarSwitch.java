
package com.mycompany.servidormusica.instrucciones.bifurcaciones;

import com.mycompany.servidormusica.declaracionAsignacion.Expresion;
import com.mycompany.servidormusica.instrucciones.Instruccion;
import com.mycompany.servidormusica.manejoErrores.ErrorSemantico;
import com.mycompany.servidormusica.tablaSimbolos.TablaSimbolo;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author andaryus
 */
public class validarSwitch extends Instruccion implements Serializable{
    
    private ArrayList<Instruccion> instrucciones;
    private TablaSimbolo tablaSimbol;
    private Expresion dato;
    private Expresion datoSwintch;
    private boolean seRealizoAccion;

    public validarSwitch(ArrayList<Instruccion> instruccions, Expresion dato) {
        this.instrucciones = instruccions;
        this.dato = dato;
    }
    
      
    @Override
    public void ejecutar(ArrayList<ErrorSemantico> errorsSemanticos) {
        if (comprobarCaso(errorsSemanticos)) {
            this.instrucciones.forEach(inst ->{
                inst.ejecutar(errorsSemanticos);
            });
            this.seRealizoAccion = true;
        }        
    }

    @Override
    public void ReferenciaTabla(TablaSimbolo tabla) {
        this.tablaSimbol = tabla;
        this.instrucciones.forEach(inst ->{
            inst.ReferenciaTabla(tablaSimbol);
        });
    }
    
    private boolean comprobarCaso(ArrayList<ErrorSemantico> errorsSemanticos){
        if (null == this.dato) {
            return true;
        }
        if (this.dato.isIsVariable()) {
            this.dato.setValorDato(this.tablaSimbol.getDato(dato.getToken(), dato.getNombreVar()));
        }
        if (this.dato.getTipoDato() == this.datoSwintch.getTipoDato()) {
            return this.comprobarDato();
        }else{
            errorsSemanticos.add(new ErrorSemantico(this.dato.getToken(), "Error en el valor de la sentencia switch"));
            return true;
        }
    }
    
    private boolean comprobarDato(){
        switch(this.dato.getTipoDato()){
            case BOOLEAN:
                return this.dato.isBooleano() && this.datoSwintch.isBooleano();
            case CADENA:
                return this.dato.getCadena().equals(this.datoSwintch.getCadena());
            case CHAR:
                return this.dato.getCaracter() == this.datoSwintch.getCaracter();
            case DECIMAL:
                return this.dato.getDecimal() == this.datoSwintch.getDecimal();
            default:
                return this.dato.getNumero() == this.datoSwintch.getNumero();
        }
    }

    public ArrayList<Instruccion> getInstruccions() {
        return instrucciones;
    }

    public void setInstruccions(ArrayList<Instruccion> instruccions) {
        this.instrucciones = instruccions;
    }

    public TablaSimbolo getTablaSimbol() {
        return tablaSimbol;
    }

    public void setTablaSimbol(TablaSimbolo tablaSimbol) {
        this.tablaSimbol = tablaSimbol;
    }

   
    public Expresion getDato() {
        return dato;
    }

    public Expresion getDatoSwintch() {
        return datoSwintch;
    }

    public void setDatoSwintch(Expresion datoSwintch) {
        this.datoSwintch = datoSwintch;
    }

    public void setDato(Expresion dato) {
        this.dato = dato;
    }

    public boolean isSeRealizoAccion() {
        return seRealizoAccion;
    }

    public void setSeRealizoAccion(boolean seRealizoAccion) {
        this.seRealizoAccion = seRealizoAccion;
    }
    
    
    
    
    
    
  
}
