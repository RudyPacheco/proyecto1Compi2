package com.mycompany.servidormusica.instrucciones.funciones;

import com.mycompany.servidormusica.declaracionAsignacion.TipoDato;
import com.mycompany.servidormusica.instrucciones.Instruccion;
import com.mycompany.servidormusica.manejoErrores.ErrorSemantico;
import com.mycompany.servidormusica.manejoErrores.ErroresSingleton;
import com.mycompany.servidormusica.tablaSimbolos.TablaSimbolo;
import com.mycompany.servidormusica.tablaSimbolos.Variable;
import com.mycompany.servidormusica.token.Token;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author andaryus
 */


public class Funcion extends Instruccion implements Serializable {

    private ArrayList<Variable> parametros;
    private TablaSimbolo tableSimbol;
    private String nombre;
    private Token token;
    private ArrayList<Instruccion> instruccions;
    private TipoDato tipoRetono = null;

    public Funcion(ArrayList<Variable> parametros, TablaSimbolo tableSimbol, String nombre, Token token, TipoDato tipoRetono) {
        this.parametros = new ArrayList<>(parametros);
        this.tableSimbol = tableSimbol;
        this.nombre = nombre;
        this.token = token;
        this.tipoRetono = tipoRetono;
        this.agregarVarRetornoATableSimbol();
        this.agregarParametroATableSimbol();

    }

    public Funcion(ArrayList<Variable> parametros, TablaSimbolo tableSimbol, String nombre, Token token) {
        this.parametros = parametros;
        this.tableSimbol = tableSimbol;
        this.nombre = nombre;
        this.token = token;
        this.agregarParametroATableSimbol();
    }

    public Funcion(ArrayList<Variable> parametros, TablaSimbolo tableSimbol, String nombre, Token token, ArrayList<Instruccion> instruccions) {
        this.parametros = parametros;
        this.tableSimbol = tableSimbol;
        this.nombre = nombre;
        this.token = token;
        this.instruccions = instruccions;
        this.agregarParametroATableSimbol();
    }

    public Funcion(Funcion fun) {
        this.parametros = fun.getParametros();
        this.tableSimbol = fun.getTableSimbol();
        this.nombre = fun.getNombre();
        this.token = fun.getToken();
        this.instruccions = fun.getInstruccions();
        this.tipoRetono = fun.getTipoRetono();
    }

    @Override
    public void ejecutar(ArrayList<ErrorSemantico> errorsSemanticos) {
        for (Instruccion instruccion : instruccions) {
            instruccion.ejecutar(errorsSemanticos);
            if (instruccion instanceof Retornar) {
                if (this.tipoRetono == null) {
                    errorsSemanticos.add(new ErrorSemantico(token, "La Funcion no tiene definido ningun tipo de retorno"));
                }
                break;
            }
        }
    }

    @Override
    public void ReferenciaTabla(TablaSimbolo tabla) {
        this.tableSimbol.setTablaSimbolPadre(tabla);
        instruccions.forEach(inst -> {
            inst.ReferenciaTabla(tableSimbol);
        });
    }

    private void agregarParametroATableSimbol() {
        ArrayList<String> valoresVistos = new ArrayList<>();
        for (Variable parametro : parametros) {
            if (valoresVistos.indexOf(parametro.getToken().getLexeme())!=-1) {
                ErroresSingleton.getInstance().getErroresSemanticos().add(new ErrorSemantico(parametro.getToken(), "El nombre del parametro esta reptido"));
                break;
            }
            valoresVistos.add(parametro.getToken().getLexeme());
            this.tableSimbol.getVariables().add(parametro);
        }

    }

    private void agregarVarRetornoATableSimbol() {
        this.tableSimbol.getVariables().add(new Variable(token, tipoRetono, "varRetonoSimbolTable"));
    }

    /*Espacio para getter y setters*/
    public ArrayList<Instruccion> getInstruccions() {
        return instruccions;
    }

    public void setInstruccions(ArrayList<Instruccion> instruccions) {
        this.instruccions = instruccions;
    }

    public ArrayList<Variable> getParametros() {
        return parametros;
    }

    public void setParametros(ArrayList<Variable> parametros) {
        this.parametros = parametros;
    }

    public TablaSimbolo getTableSimbol() {
        return tableSimbol;
    }

    public void setTableSimbol(TablaSimbolo tableSimbol) {
        this.tableSimbol = tableSimbol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public TipoDato getTipoRetono() {
        return tipoRetono;
    }

    public void setTipoRetono(TipoDato tipoRetono) {
        this.tipoRetono = tipoRetono;
    }

    @Override
    public String toString() {
        return "Funcion{" + "parametros=" + parametros + ", tableSimbol=" + tableSimbol + ", nombre=" + nombre + ", token=" + token + ", instruccions=" + instruccions + ", tipoRetono=" + tipoRetono + '}';
    }

}
