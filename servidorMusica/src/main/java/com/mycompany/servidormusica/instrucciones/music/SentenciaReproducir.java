package com.mycompany.servidormusica.instrucciones.music;

import com.mycompany.servidormusica.declaracionAsignacion.Expresion;
import com.mycompany.servidormusica.declaracionAsignacion.Operacion;
import com.mycompany.servidormusica.declaracionAsignacion.TipoDato;
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
public class SentenciaReproducir extends Instruccion implements Serializable {

    private String Nota;
    private Operacion octava;
    private Operacion milisegundos;
    private Operacion canal;
    private TablaSimbolo TablaSimbol;
    private String notasCalculadas = "";
    private boolean isEsperar = false;
    private int milis = 0;

    public SentenciaReproducir(String Nota, Operacion octava, Operacion milisegundos, Operacion canal) {
        this.Nota = Nota;
        this.octava = octava;
        this.milisegundos = milisegundos;
        this.canal = canal;
    }

    public SentenciaReproducir(String Nota, Operacion milisegundos, Operacion canal) {
        this.Nota = Nota;
        this.isEsperar = true;
        this.milisegundos = milisegundos;
        this.canal = canal;
    }

    @Override
    public void ejecutar(ArrayList<ErrorSemantico> errorsSemanticos) {

        int valCanal = valorCanal(errorsSemanticos);
        milis = ejecutarValorMilis(errorsSemanticos);
        int oct = 0;

        if (!isEsperar) {
            int octavaCalculada = ValorOctava(errorsSemanticos);
            oct = octavaCalculada;
            this.agregarOtava(octavaCalculada);
        }

     //   System.out.println("OCT  " + oct);
        int notaMIDI = convertidorNOTA(Nota, oct, errorsSemanticos);
       // System.out.println("Nota agregada " + notaMIDI);
        notaLista notaL = new notaLista(notaMIDI, milis, valCanal);
        ManejadorPistaMusical.getPistaMusical().agregarACanal(notaL, valCanal, milis);
        this.notasCalculadas = "";
    }

    @Override
    public void ReferenciaTabla(TablaSimbolo tabla) {
        this.TablaSimbol = tabla;
    }

    private int ValorOctava(ArrayList<ErrorSemantico> errorsSemanticos) {
        int resultado = 1;

        Expresion valorOctava = this.octava.ejecutar(errorsSemanticos, TablaSimbol);
        if (valorOctava.getTipoDato() != TipoDato.ENTERO) {
            errorsSemanticos.add(new ErrorSemantico(new Token("Octava", 0, 0), "El parametro octava solo admite valores enteros"));
            return resultado;
        }
        if (valorOctava.getNumero() < 0 && valorOctava.getNumero() > 8) {
            errorsSemanticos.add(new ErrorSemantico(new Token("Octava", 0, 0), "Exedio los limites, El parametro octava solo puede estar entre 1 y 8"));
            return resultado;
        }
        resultado = valorOctava.getNumero();
        System.out.println(resultado);
        return resultado;
    }

    private int valorCanal(ArrayList<ErrorSemantico> errorsSemanticos) {
        int resultado = 1;

        Expresion tmpOctava = this.canal.ejecutar(errorsSemanticos, TablaSimbol);
        if (tmpOctava.getTipoDato() != TipoDato.ENTERO) {
            errorsSemanticos.add(new ErrorSemantico(new Token("Canal", 0, 0), "El parametro canal solo admite valores enteros"));
            return resultado;
        }
        if (tmpOctava.getNumero() < 0) {
            errorsSemanticos.add(new ErrorSemantico(new Token("Canal", 0, 0), "Exedio el limite, el parametro Canal no acepta valores menores a 0"));
            return resultado;
        }
        resultado = tmpOctava.getNumero();
        return resultado;
    }

    private int ejecutarValorMilis(ArrayList<ErrorSemantico> errorsSemanticos) {
        int resultado = 1;

        Expresion tmpOctava = this.milisegundos.ejecutar(errorsSemanticos, TablaSimbol);
        if (tmpOctava.getTipoDato() != TipoDato.ENTERO) {
            errorsSemanticos.add(new ErrorSemantico(new Token("Milisegundos", 0, 0), "La sentencia Reproducir en el parametro Milis no acepta valors no numericos enteros"));
            return resultado;
        }
        if (tmpOctava.getNumero() < 0) {
            errorsSemanticos.add(new ErrorSemantico(new Token("Milisegundos", 0, 0), "La sentencia Reproducir en el parametro Milis no acepta valores menos a 0"));
            return resultado;
        }
        resultado = tmpOctava.getNumero();
        return resultado;
    }

    private int convertidorNOTA(String nota, int ocatava, ArrayList<ErrorSemantico> errorsSemanticos) {

        if (nota.equals("Z")) {
            //esperar

        } else {
            // Mapeo de nombres de notas a valores MIDI
            String[] noteNames = {"Do", "DO#", "Re", "Re#", "Mi", "Fa", "Fa#", "Sol", "Sol#", "La", "La#", "Si"};

            int baseMIDIValue = 60; // Valor MIDI para la nota C4 (do central)

            // Encuentra la posición de la nota en el arreglo
            int notePosition = -1;
            for (int i = 0; i < noteNames.length; i++) {
                if (noteNames[i].equalsIgnoreCase(nota)) {
                    notePosition = i;
                    break;
                }
            }

            if (notePosition != -1) {
                // Calcula el valor MIDI sumando la posición de la nota al valor base y ajusta por la octava
                int midiValue = baseMIDIValue + notePosition + (ocatava - 4) * 12;
                return midiValue;
            } else {
                // Si el nombre de la nota no se encuentra, retorna -1 para indicar un error
                errorsSemanticos.add(new ErrorSemantico(new Token("Octava", 0, 0), "Exedio los limites, El parametro octava solo puede estar entre 1 y 8"));

                return -1;
            }

        }

        return 1;
    }

    private void agregarOtava(int octava) {
        this.notasCalculadas += "" + octava;
    }

    public String getNota() {
        return Nota;
    }

    public void setNota(String Nota) {
        this.Nota = Nota;
    }

    public Operacion getOctava() {
        return octava;
    }

    public void setOctava(Operacion octava) {
        this.octava = octava;
    }

    public Operacion getMilisegundos() {
        return milisegundos;
    }

    public void setMilisegundos(Operacion milisegundos) {
        this.milisegundos = milisegundos;
    }

    public Operacion getCanal() {
        return canal;
    }

    public void setCanal(Operacion canal) {
        this.canal = canal;
    }

    public TablaSimbolo getTablaSimbol() {
        return TablaSimbol;
    }

    public void setTablaSimbol(TablaSimbolo TablaSimbol) {
        this.TablaSimbol = TablaSimbol;
    }

    public String getNotasCalculadas() {
        return notasCalculadas;
    }

    public void setNotasCalculadas(String notasCalculadas) {
        this.notasCalculadas = notasCalculadas;
    }

    public int getMilis() {
        return milis;
    }

    public void setMilis(int milis) {
        this.milis = milis;
    }

}
