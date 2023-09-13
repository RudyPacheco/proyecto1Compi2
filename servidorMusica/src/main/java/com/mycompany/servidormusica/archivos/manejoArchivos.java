package com.mycompany.servidormusica.archivos;

import com.mycompany.servidormusica.pista.Lista;
import com.mycompany.servidormusica.pista.Pista;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author andaryus
 */
public class manejoArchivos {

    public void guardarPistas(String nombreArchivo, ArrayList<Pista> pistas) {
        this.crearArvhivos(nombreArchivo);
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            salida.writeObject(pistas);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error Guardar del archivo Binario, de las Pistas :(");
        }
    }

    public void guardarListasPistas(String nombreArchivo, ArrayList<Lista> listas) {
        this.crearArvhivos(nombreArchivo);
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            salida.writeObject(listas);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error Guardar del archivo Binario, de las listas :(");
        }
    }

    public ArrayList<Pista> LeerPistas(String nombreArchivo) {
        ArrayList<Pista> pistas = new ArrayList<>();
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            //validacion de archivo
            return pistas;
        }
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            pistas = (ArrayList<Pista>) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error la lectura del archivo Binario, de las Pistas :(");
        }
        return pistas;
    }

    public ArrayList<Lista> LeerListas(String nombreArchivo) {
        ArrayList<Lista> listas = new ArrayList<>();
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            //validacion de archivo
            //System.out.println("El archivo no existe.");
            return listas;
        }
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            listas = (ArrayList<Lista>) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error la lectura del archivo Binario, de las listas :(");
        }
        return listas;
    }

    
    
    private void crearArvhivos(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error en la creacion del archivo Bin :(");
            }
        }
    }

}
