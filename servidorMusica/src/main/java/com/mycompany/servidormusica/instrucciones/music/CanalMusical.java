package com.mycompany.servidormusica.instrucciones.music;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author andaryus
 */
public class CanalMusical implements Serializable {

    private int canal;
    private ArrayList<notaLista> notasCanal = new ArrayList<>();
    private int milisTotal = 0;

    public CanalMusical(int canal, notaLista nota, int milisTotal) {
        this.canal = canal;
        this.milisTotal = milisTotal;
        anadirNota(nota,milisTotal);

    }

    public ArrayList<notaLista> getNotasCanal() {
        return notasCanal;
    }

    public void anadirNota(notaLista nota,int milis) {
        this.notasCanal.add(nota);
        this.milisTotal=this.milisTotal+milis;
    }

    public int getCanal() {
        return canal;
    }

    public void setCanal(int canal) {
        this.canal = canal;
    }

    public int getMilisTotal() {
        return milisTotal;
    }

    public void setMilisTotal(int milisTotal) {
        this.milisTotal = milisTotal;
    }

}
