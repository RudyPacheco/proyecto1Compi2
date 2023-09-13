/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidormusica.instrucciones.music;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

/**
 *
 * @author andaryus
 */
public class hiloReproducir extends Thread {


    ArrayList<notaLista> notas;

    @Override
    public void run() {

        try {
            playNotesWithDurations(notas);
        } catch (MidiUnavailableException ex) {
            Logger.getLogger(hiloReproducir.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(hiloReproducir.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void playNotesWithDurations(ArrayList<notaLista> notas) throws MidiUnavailableException, InterruptedException {
        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();

        MidiChannel channel = synth.getChannels()[0];

        for (notaLista nota : notas) {
            int note = nota.getNota();
            int duration = nota.getMilisegundos();

            channel.noteOn(note, 100);
            Thread.sleep(duration);
            channel.noteOff(note);

        }

        synth.close();
    }

    public void agregarMusica(ArrayList<notaLista> notas) {
        this.notas = notas;
    }
}
