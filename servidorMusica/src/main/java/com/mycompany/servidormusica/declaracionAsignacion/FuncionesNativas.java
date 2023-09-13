/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servidormusica.declaracionAsignacion;

import com.mycompany.servidormusica.declaracionAsignacion.funcionesArreglo.FuncionesArreglos;
import com.mycompany.servidormusica.instrucciones.music.SentenciaReproducir;
import com.mycompany.servidormusica.manejoErrores.ErrorSemantico;
import com.mycompany.servidormusica.tablaSimbolos.TablaSimbolo;
import java.io.Serializable;
import java.util.ArrayList;


/**
 *
 * @author andaryus
 */
public class FuncionesNativas extends NodoOperacion implements Serializable{
    
    private SentenciaReproducir reproducir;
    private FuncionesArreglos ordenar;
    
    public FuncionesNativas(){
        
    }

    public void expresionReproducir(SentenciaReproducir reproducir){
        this.reproducir= reproducir;
    }
    
    public void expresionOrdenar( FuncionesArreglos ordenar){
        this.ordenar = ordenar;
    }
    
    @Override
    public Expresion ejecutarOperacion(ArrayList<ErrorSemantico> errorsSemanticos, TablaSimbolo tabla) {
        Expresion tmp = new Expresion(true, 0, TipoDato.ENTERO);
        if (reproducir != null) {
            this.reproducir.ReferenciaTabla(tabla);
            this.reproducir.ejecutar(errorsSemanticos);
            tmp.setNumero(this.reproducir.getMilis());
        }
        if (ordenar != null) {
           this.ordenar.setTablaSimbol(tabla);
           this.ordenar.ejecutar(errorsSemanticos);
            if (this.ordenar.isIsSumarizar()) {
               tmp.setTipoDato(TipoDato.CADENA);
               tmp.setCadena(this.ordenar.getResultadoSumarizar());
            }else{
                tmp.setNumero(this.ordenar.getResultado());
            }
        }
        return tmp;
    }

    public SentenciaReproducir getReproducir() {
        return reproducir;
    }

    public void setReproducir(SentenciaReproducir reproducir) {
        this.reproducir = reproducir;
    }

    public FuncionesArreglos getOrdenar() {
        return ordenar;
    }

    public void setOrdenar(FuncionesArreglos ordenar) {
        this.ordenar = ordenar;
    }
    
    
    
    
    
}
