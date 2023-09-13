/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidormusica.instrucciones.music;

import java.util.ArrayList;

/**
 *
 * @author andaryus
 */
public class preparadorhiloRepro {

    ArrayList<hiloReproducir> hilosReproducir = new ArrayList<>();

    public void prepararReproduccion(PistaMusical pista) {

        ArrayList<CanalMusical> canales = pista.getCanales();

        for (CanalMusical canale : canales) {
            System.out.println("Canal 1 ");
            ArrayList<notaLista> tmp = canale.getNotasCanal();

            for (notaLista lista : tmp) {
                System.out.println("notas 1");
                System.out.println(lista.getNota());
            }
        }

        for (CanalMusical canale : canales) {
            hiloReproducir h1 = new hiloReproducir();
            h1.agregarMusica(canale.getNotasCanal());

            hilosReproducir.add(h1);

        }

        Reproducir();

    }

    public void Reproducir() {
        for (hiloReproducir reproducir : hilosReproducir) {
            //   System.out.println("Canal");
            ArrayList<notaLista> tmp = reproducir.notas;
            for (notaLista lista : tmp) {
                //          System.out.println("notas");
                //        System.out.println(lista.getNota());
            }

            reproducir.start();
        }
    }

}
