package com.mycompany.servidormusica.instrucciones.funciones;

import com.mycompany.servidormusica.declaracionAsignacion.Expresion;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author andaryus
 */

public class FuncionMensaje implements Serializable{

    private static FuncionMensaje instance;
    private ArrayList<String> mensajes = new ArrayList<>();

    public static FuncionMensaje getInstanceMensajes() {
        if (instance == null) {
            instance = new FuncionMensaje();
        }
        return instance;
    }

    public ArrayList<String> getMensajes() {
        return mensajes;
    }

    public void setMensajes(ArrayList<String> mensajes) {
        this.mensajes = mensajes;
    }

    public void push(Expresion dato) {
        switch (dato.getTipoDato()) {
            case ENTERO:
                this.mensajes.add("" + dato.getNumero());
                break;
            case DECIMAL:
                this.mensajes.add("" + dato.getDecimal());
                break;
            case CADENA:
                this.mensajes.add("" + dato.getCadena());
                break;
            case CHAR:
                this.mensajes.add("" + dato.getCaracter());
                break;
            case BOOLEAN:
                this.mensajes.add("" + dato.isBooleano());
                break;

        }

    }
    
    public String getContenido(){
        String result = "";
        result = mensajes.stream().map(mensaje -> mensaje +"\n").reduce(result, String::concat);
        
        return result;
    }

}
