/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidormusica.instrucciones.music;

import java.io.Serializable;

/**
 *
 * @author andaryus
 */
public class notaLista implements Serializable{
    
    private int nota;
    private int ocatava;
    private int milisegundos;
    private int canal;

    public notaLista() {
    }

    public notaLista(int nota, int milisegundos, int canal) {
        this.nota = nota;
        this.milisegundos = milisegundos;
        this.canal = canal;
    }

    public int getOcatava() {
        return ocatava;
    }

    public void setOcatava(int ocatava) {
        this.ocatava = ocatava;
    }

    
    
    
    
    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public int getMilisegundos() {
        return milisegundos;
    }

    public void setMilisegundos(int milisegundos) {
        this.milisegundos = milisegundos;
    }

    public int getCanal() {
        return canal;
    }

    public void setCanal(int canal) {
        this.canal = canal;
    }

    
    
    
    
    
    
}
