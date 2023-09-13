package com.mycompany.servidormusica.declaracionAsignacion;

import com.mycompany.servidormusica.instrucciones.funciones.Funcion;
import com.mycompany.servidormusica.manejoErrores.ErrorSemantico;
import com.mycompany.servidormusica.pista.Pista;
import com.mycompany.servidormusica.tablaSimbolos.TablaSimbolo;
import com.mycompany.servidormusica.token.Token;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author andaryus
 */

public class NodoOperacion implements Serializable {

    private Expresion dato;
    private TipoOperacion tipoOperacion;
    private NodoOperacion operacionI;
    private NodoOperacion operacionD;
    private CasteoOperacion operation;
    private boolean funcion = false;
    private Token tokenFun;
    private ArrayList<Operacion> parametros;
    private Pista pista;
    private boolean retorna = true;
    private boolean isOrdenar;

    public NodoOperacion(Token tokenFun, ArrayList<Operacion> parametros, Pista pista) {
        this.tokenFun = tokenFun;
        this.pista = pista;
        this.funcion = true;
        this.dato = new Expresion(false, 0, TipoDato.ENTERO);
        this.capturarParametros(parametros);
    }

    public NodoOperacion(Expresion dato) {
        this.dato = dato;
        this.tipoOperacion = null;
        this.operacionI = null;
        this.operacionD = null;
    }

    public NodoOperacion() {

    }

    public NodoOperacion(TipoOperacion tipoOperacion, NodoOperacion opLeft, NodoOperacion opRight) {
        this.tipoOperacion = tipoOperacion;
        this.operacionI = opLeft;
        this.operacionD = opRight;
    }

   
    public Expresion ejecutarOperacion(ArrayList<ErrorSemantico> errorsSemanticos, TablaSimbolo tabla) {
        Expresion tmp = this.dato;
        if (funcion) {
            Funcion fun = pista.getFuncionEspecifica(tokenFun, parametros, retorna);
            if (fun != null) {
                fun.ejecutar(errorsSemanticos);
                if (!fun.getTableSimbol().getVariables().isEmpty()) {
                    tmp.setInicializado(true);
                    tmp.setValorDato(fun.getTableSimbol().getVariables().get(0).getDato());

                }
            }
            return tmp;
        }
        if (this.tipoOperacion == null) {
            if (this.dato.isIsVariable()) {
                tmp.setInicializado(true);
                tmp.setValorDato(tabla.getDato(dato.getToken(), dato.getNombreVar()));
            }
            if (this.dato.isIsVarArreglo()) {
                tmp.setInicializado(true);
                tmp.setValorDato(tabla.getDatoArreglo(dato));
            }
            return tmp;
        }
        Expresion datoLeft = this.operacionI.ejecutarOperacion(errorsSemanticos, tabla);
        Expresion datoRight = this.operacionD.ejecutarOperacion(errorsSemanticos, tabla);
        operation = new CasteoOperacion(errorsSemanticos);
        return this.operation.resultadoOperacion(datoLeft, datoRight, tipoOperacion);

    }

    private void capturarParametros(ArrayList<Operacion> parameRecibidos) {
        this.parametros = new ArrayList<>();
        parameRecibidos.forEach(para -> {
            this.parametros.add(para);
        });
    }

    
    public NodoOperacion getOpLeft() {
        return operacionI;
    }

    public void setOpLeft(NodoOperacion opLeft) {
        this.operacionI = opLeft;
    }

    public NodoOperacion getOpRight() {
        return operacionD;
    }

    public void setOpRight(NodoOperacion opRight) {
        this.operacionD = opRight;
    }

    public Expresion getDato() {
        return dato;
    }

    public void setDato(Expresion dato) {
        this.dato = dato;
    }

    public TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(TipoOperacion tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }


    public CasteoOperacion getOperation() {
        return operation;
    }

    public void setOperation(CasteoOperacion operation) {
        this.operation = operation;
    }

    public boolean isFuncion() {
        return funcion;
    }

    public void setFuncion(boolean funcion) {
        this.funcion = funcion;
    }

    public Token getTokenFun() {
        return tokenFun;
    }

    public void setTokenFun(Token tokenFun) {
        this.tokenFun = tokenFun;
    }

    public ArrayList<Operacion> getParametros() {
        return parametros;
    }

    public void setParametros(ArrayList<Operacion> parametros) {
        this.parametros = parametros;
    }

    public Pista getPista() {
        return pista;
    }

    public void setPista(Pista pista) {
        this.pista = pista;
    }

    public boolean isRetorna() {
        return retorna;
    }

    public void setRetorna(boolean retorna) {
        this.retorna = retorna;
    }

}
