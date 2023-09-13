package com.mycompany.servidormusica.pista;

import com.mycompany.servidormusica.archivos.manejoArchivos;
import com.mycompany.servidormusica.instrucciones.music.PistaMusical;
import com.mycompany.servidormusica.manejoErrores.ErrorSemantico;
import com.mycompany.servidormusica.manejoErrores.ErroresSingleton;
import com.mycompany.servidormusica.token.Token;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author andaryus
 */
public class PistasCompiladas implements Serializable {

    private static PistasCompiladas instance;
    private ArrayList<Pista> pistas = new ArrayList<>();
    private ArrayList<Lista> listas = new ArrayList<>();

    public static PistasCompiladas getInstancePistasActivacion() {
        if (instance == null) {
            instance = new PistasCompiladas();
        }
        return instance;
    }

    public void push(Pista pista, ArrayList<ErrorSemantico> errorsSemanticos) {
        if (errorsSemanticos.isEmpty()) {

            if (pista.getPistaMusical() != null) {
                pushPista(pista);
//
            }
        }
    }

    private void pushPista(Pista pistaInser) {
        int index = -1;
        Pista pst = new Pista(pistaInser.getCodigoFuente(), pistaInser.getPistaMusical(), pistaInser.getFunciones(), pistaInser.getNombre(), pistaInser.getTableSimbolGoblal());
        for (int i = 0; i < this.pistas.size(); i++) {
            if (this.pistas.get(i).getNombre().equals(pistaInser.getNombre())) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            this.pistas.set(index, pst);
        } else {
            this.pistas.add(pst);
        }

    }

    public boolean sobreEscribir(String nombre, ArrayList<ErrorSemantico> errorsSemanticos) {
        boolean sobreEscribir = true;
        if (errorsSemanticos.isEmpty()) {
            if (this.pistas.isEmpty()) {
                return sobreEscribir;
            }
            for (Pista pista : pistas) {
                if (pista.getNombre().equals(nombre)) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "La pista ya existe, deseas sobrescribirla?", "Sobreeescritura", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    switch (respuesta) {
                        case JOptionPane.YES_OPTION:
                            sobreEscribir = true;
                            break;
                        case JOptionPane.NO_OPTION:
                            sobreEscribir = false;
                            break;
                        case JOptionPane.CANCEL_OPTION:
                            sobreEscribir = false;
                            break;
                        default:
                            sobreEscribir = true;
                            break;
                    }
                    break;
                }
            }

        } else {
            sobreEscribir = false;
        }

        return sobreEscribir;
    }

    public Pista getPistaExtends(Token id) {
        Pista pist = null;
        for (Pista pista : pistas) {
            if (pista.getNombre().equals(id.getLexeme())) {
                pist = pista;
                break;
            }
        }

        return pist;
    }

    public void guardarNuevaLista(String name) {
        boolean guardar = true;
        for (Lista lista : listas) {
            if (lista.getNombre().equals(name)) {
                guardar = false;
                JOptionPane.showMessageDialog(null, " Lista ya existente");
                break;
            }
        }
        if (guardar) {
            Lista lis = new Lista(name);
            this.listas.add(lis);
            JOptionPane.showMessageDialog(null, " Lista Creada con exito");

            this.guardarEnBinario();
        }
    }

    public void guardarNuevaLista(Lista listNueva) {
        System.out.println("Guardando lista");
        boolean guardar = true;
        for (Lista lista : listas) {
            if (lista.getNombre().equals(listNueva.getNombre())) {
                JOptionPane.showMessageDialog(null, " La lista ya existe");
                ErroresSingleton.getInstance().getErroresSemanticos().add(new ErrorSemantico(new Token(listNueva.getNombre(), 0, 0), "Pista ya Existente"));
                guardar = false;
                break;
            }
        }
        if (guardar) {
            Lista lis = new Lista(listNueva.getListasMusicales(), listNueva.getNombre());
            for (PistaMusical pista : listNueva.getListasMusicales()) {
                System.out.println(pista.getNombre());
            }
            this.listas.add(lis);
            JOptionPane.showMessageDialog(null, " Lista Creada con exito!!");
            this.guardarEnBinario();
        }
    }

    public void guardarEnBinario() {
        manejoArchivos archivos = new manejoArchivos();
        archivos.guardarListasPistas("Listas.bin", listas);
        archivos.guardarPistas("Pistas.bin", pistas);
    }

    public void eliminarPistaLista(String nombre, int indexLista) {
        for (Lista lista : listas) {
            if (lista.getNombre().equals(nombre)) {
                lista.getListasMusicales().remove(indexLista);
                break;
            }
        }
    }

    public PistaMusical getpista(Token id) {
        PistaMusical pistTmp = null;
        if (listas.isEmpty()) {
            return null;
        }

        ArrayList<Pista> pistas = leerPistas("Pistas.bin");

        for (Pista PistaMu : pistas) {
            if (PistaMu.getPistaMusical().getNombre().equals(id.getLexeme())) {
                pistTmp = PistaMu.getPistaMusical();
                break;
            }
        }
        if (pistTmp == null) {
            ErroresSingleton.getInstance().getErroresSemanticos().add(new ErrorSemantico(id, "Pista no existente"));
        }

        return pistTmp;
    }

    public ArrayList<Pista> leerPistas(String nombreArchivo) {
        ArrayList<Pista> pistas = new ArrayList<>();
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            //validacion de archivo
            return pistas;
        }
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            pistas = (ArrayList<Pista>) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error la lectura del archivo de las pistas");
        }
        return pistas;
    }

    public ArrayList<Lista> getListas() {
        return listas;
    }

    public void setListas(ArrayList<Lista> listas) {
        this.listas = listas;
    }

    public ArrayList<Pista> getPistas() {
        return pistas;
    }

    public void setPistas(ArrayList<Pista> pistas) {
        this.pistas = pistas;
    }

}
