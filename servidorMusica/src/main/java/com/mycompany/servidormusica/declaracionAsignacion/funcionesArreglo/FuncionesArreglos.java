package com.mycompany.servidormusica.declaracionAsignacion.funcionesArreglo;

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
public class FuncionesArreglos extends Instruccion implements Serializable {

    private TablaSimbolo tablaSimbol;
    private Token token;
    private int resultado = 0;
    private NumsOrdenamiento tipoOrden;
    private boolean isSumarizar = false;
    private String resultadoSumarizar = "";
    private boolean isLongitud = false;
    private String cadena = "";

    public FuncionesArreglos(Token token, NumsOrdenamiento tipoOrden) {
        this.token = token;
        this.tipoOrden = tipoOrden;
    }
    
    public FuncionesArreglos(Token token) {
        this.token = token;
        this.isSumarizar = true;
    }
    
    public FuncionesArreglos(Token token, boolean isLongitud) {
        this.token = token;
        this.isLongitud = isLongitud;
    }
    
    public FuncionesArreglos(String cadena, boolean isLongitud) {
        this.cadena= cadena;
        this.isLongitud = isLongitud;
    }
    
    
    

    @Override
    public void ejecutar(ArrayList<ErrorSemantico> errorsSemanticos) {
        //verificar que tipo de ordenamieto es
        //si es
        resultado = 1;
    }

    @Override
    public void ReferenciaTabla(TablaSimbolo tabla) {
        this.tablaSimbol = tabla;
    }

    public TablaSimbolo getTablaSimbol() {
        return tablaSimbol;
    }

    public void setTablaSimbol(TablaSimbolo tablaSimbol) {
        this.tablaSimbol = tablaSimbol;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }

    public NumsOrdenamiento getTipoOrden() {
        return tipoOrden;
    }

    public void setTipoOrden(NumsOrdenamiento tipoOrden) {
        this.tipoOrden = tipoOrden;
    }

    public boolean isIsSumarizar() {
        return isSumarizar;
    }

    public void setIsSumarizar(boolean isSumarizar) {
        this.isSumarizar = isSumarizar;
    }

    public String getResultadoSumarizar() {
        return resultadoSumarizar;
    }

    public void setResultadoSumarizar(String resultadoSumarizar) {
        this.resultadoSumarizar = resultadoSumarizar;
    }

    public boolean isIsLongitud() {
        return isLongitud;
    }

    public void setIsLongitud(boolean isLongitud) {
        this.isLongitud = isLongitud;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }
    
    

}
