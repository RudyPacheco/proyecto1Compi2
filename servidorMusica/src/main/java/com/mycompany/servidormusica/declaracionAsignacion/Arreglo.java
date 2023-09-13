package com.mycompany.servidormusica.declaracionAsignacion;

import com.mycompany.servidormusica.manejoErrores.ErrorSemantico;
import com.mycompany.servidormusica.token.Token;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author andaryus
 */
public class Arreglo implements Serializable{

    private Token Token;
    private TipoDato tipoArreglo;
    private int dimension;
    private ArrayList<Integer> indexMaximos = new ArrayList<>();
    private ArrayList<Expresion> datos = new ArrayList<>();
    private int size;

    public Arreglo(Token Token, TipoDato tipoArreglo, int dimension, ArrayList<Integer> indexMaximos) {
        this.Token = Token;
        this.tipoArreglo = tipoArreglo;
        this.dimension = dimension;
        this.indexMaximos = indexMaximos;
    }

    public Arreglo(Token Token, TipoDato tipoArreglo, int dimension, int indexMax, int maxIndiceFila) {
        this.Token = Token;
        this.tipoArreglo = tipoArreglo;
        this.dimension = dimension;
        this.inicializarIndices(indexMax, maxIndiceFila);
    }

    public void recibirDato(Expresion dato, ArrayList<ErrorSemantico> erros) {
        if (tipoArreglo != dato.getTipoDato()) {
            erros.add(new ErrorSemantico(Token, "El tipo del dato no conincide con el tipo del arreglo"));
        } else {
            this.datos.add(dato);
        }
    }

    public Expresion getDatoIndice(ArrayList<ErrorSemantico> erros, ArrayList<Integer> indices) {
        Expresion tmp = new Expresion(false, 0, tipoArreglo);
        if (this.datos.isEmpty()) {
            this.inicializarArreglo();
            erros.add(new ErrorSemantico(Token, "El arreglo esta vacio"));
        } else {
            if (indices.size() == 1) {
                tmp = this.datos.get(indices.get(0));
            } else {
                int indice = this.calcularIndice(indices);
                tmp = this.datos.get(indice);
                if (!tmp.isInicializado()) {
                    erros.add(new ErrorSemantico(Token, "El arreglo en el indice "+ indice+" esta vacio"));
                }
            }
        }
        return tmp;
    }


    private void inicializarIndices(int indexMax, int maxIndiceFila) {
        this.indexMaximos.add(maxIndiceFila - 1);
        this.indexMaximos.add(indexMax - 1);

    }

    public boolean indiceValido(ArrayList<Integer> indices, ArrayList<ErrorSemantico> erros, Token id) {
        boolean valid = true;
        if (indices.size() == this.indexMaximos.size()) {
            for (int i = 0; i < indices.size(); i++) {
                if (indices.get(i) > this.indexMaximos.get(i)) {
                    erros.add(new ErrorSemantico(id, "Indice fuera del tamaño del arreglo"));
                    valid = false;
                    break;
                }
            }
        } else {
            erros.add(new ErrorSemantico(id, "Exede el tamaño del arreglo"));
            valid = false;
        }
        return valid;
    }

    public void agregarValor(Expresion dato, Token id, ArrayList<Integer> indices, ArrayList<ErrorSemantico> erros) {
        if (this.datos.isEmpty()) {
            this.inicializarArreglo();
        }
        if (dato.getTipoDato() == this.tipoArreglo) {
            if (indices.size() == 1) {
                this.datos.set(indices.get(0), dato);
            } else {
                int ind = calcularIndice(indices);
                this.datos.set(ind, dato);
            }
        } else {
            erros.add(new ErrorSemantico(id, "El tipo de dato no conincide con el tipo de arreglo"));
        }

    }

    private int calcularIndice(ArrayList<Integer> indices) {
        int res = 0;
        for (int i = 1; i < this.indexMaximos.size(); i++) {
            res = indices.get(i - 1) * (this.indexMaximos.get(i) + 1) + indices.get(i);
            indices.set(i, res);
        }
        return res;
    }

    private void inicializarArreglo() {
        size = 1;
        for (int i = 0; i < this.indexMaximos.size(); i++) {
            size = size * (this.indexMaximos.get(i) + 1);
        }
        for (int i = 0; i < size; i++) {
            Expresion tmp = new Expresion(false, tipoArreglo);
            this.datos.add(tmp);
        }
    }

    public Token getToken() {
        return Token;
    }

    public void setToken(Token Token) {
        this.Token = Token;
    }

    public TipoDato getTipoArreglo() {
        return tipoArreglo;
    }

    public void setTipoArreglo(TipoDato tipoArreglo) {
        this.tipoArreglo = tipoArreglo;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public ArrayList<Integer> getIndexMaximos() {
        return indexMaximos;
    }

    public void setIndexMaximos(ArrayList<Integer> indexMaximos) {
        this.indexMaximos = indexMaximos;
    }

    public ArrayList<Expresion> getDatos() {
        return datos;
    }

    public void setDatos(ArrayList<Expresion> datos) {
        this.datos = datos;
        this.size = this.datos.size();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Arreglo{" + "Token=" + Token + ", tipoArreglo=" + tipoArreglo + ", dimension=" + dimension + ", indexMaximos=" + indexMaximos + ", datos=" + datos + '}';
    }

}
