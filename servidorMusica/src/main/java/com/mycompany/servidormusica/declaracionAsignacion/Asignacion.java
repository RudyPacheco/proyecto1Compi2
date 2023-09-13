package com.mycompany.servidormusica.declaracionAsignacion;

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

public class Asignacion extends Instruccion implements Serializable {

    private int indiceInicial;
    private int indiceFinal;
    private Operacion operation;
    private TablaSimbolo tableSimbol;
    private Token token;
    private boolean anidado = false;    
    private ArrayList<Operacion> operaciones;
    private boolean isTabla = false;
    private boolean asignacionArrayIndividual = false;

    public Asignacion(int indiceInicial, int indiceFinal, Operacion operation, TablaSimbolo table) {
        this.indiceInicial = indiceInicial;
        this.indiceFinal = indiceFinal;
        this.operation = operation;
        this.tableSimbol = table;
    }

    public Asignacion(Operacion operation, Token token, boolean anidado) {
        this.operation = operation;
        this.token = token;
        this.anidado = anidado;
    }

    public Asignacion(int indiceInicial, int indiceFinal, TablaSimbolo tableSimbol, ArrayList<Operacion> operaciones) {
        this.indiceInicial = indiceInicial;
        this.indiceFinal = indiceFinal;
        this.tableSimbol = tableSimbol;
        this.operaciones = operaciones;
        this.isTabla = true;
    }

    public Asignacion(Operacion operation, Token token, ArrayList<Operacion> operaciones) {
        this.operation = operation;
        this.token = token;
        this.operaciones = operaciones;
        this.asignacionArrayIndividual = true;
    }

    private void asignarVarDato(ArrayList<ErrorSemantico> errorsSemanticos) {
        Expresion dato = this.operation.ejecutar(errorsSemanticos, this.tableSimbol);
        if (anidado) {
            this.tableSimbol.asignacionValorVariable(dato, token);
        } else {
            for (int i = indiceInicial; i < indiceFinal; i++) {
                tableSimbol.getVariables().get(i).setDato(dato, errorsSemanticos);
            }
        }
    }

    private void inicializarArreglos(ArrayList<ErrorSemantico> errorsSemanticos) {
        boolean echoUnaves = false;
        for (int i = indiceInicial; i < indiceFinal; i++) {
            Arreglo arry = tableSimbol.getArreglos().get(i);
            if (!echoUnaves) {
                this.operaciones.forEach(op -> {
                    Expresion dato = op.ejecutar(errorsSemanticos, tableSimbol);
                    arry.recibirDato(dato, errorsSemanticos);
                });
            } else {
                arry.setDatos(tableSimbol.getArreglos().get(i - 1).getDatos());
            }
        }

    }

    private ArrayList<Integer> indicesCalculados(ArrayList<ErrorSemantico> errorsSemanticos) {
        ArrayList<Integer> indices = new ArrayList<>();
        operaciones.stream().map(operacione -> operacione.ejecutar(errorsSemanticos, tableSimbol)).forEachOrdered(dato -> {
            if (dato.getTipoDato() == TipoDato.ENTERO) {
                if (dato.getNumero() < 0) {
                    errorsSemanticos.add(new ErrorSemantico(token, "Los indices no pueden ser menores a 0 "));
                } else {
                    indices.add(dato.getNumero());
                }
            } else {
                errorsSemanticos.add(new ErrorSemantico(token, "Los indices deben ser de tipo entero"));
                indices.add(1);
            }
        });
        return indices;

    }

   @Override
    public void ReferenciaTabla(TablaSimbolo tabla) {
        this.tableSimbol = tabla;

    }



    @Override
    public void ejecutar(ArrayList<ErrorSemantico> errorsSemanticos) {
        if (asignacionArrayIndividual) {
            //al operar los indices verificar si son mayores a 0
            Expresion dato = this.operation.ejecutar(errorsSemanticos, this.tableSimbol);
            ArrayList<Integer> indices = indicesCalculados(errorsSemanticos);
            tableSimbol.asignacionValorArreglo(dato, token, operaciones.size(), indices);
        } else {
            if (isTabla) {
                this.inicializarArreglos(errorsSemanticos);
            } else {
                this.asignarVarDato(errorsSemanticos);
            }
        }
    }

    public int getIndiceInicial() {
        return indiceInicial;
    }

    public void setIndiceInicial(int indiceInicial) {
        this.indiceInicial = indiceInicial;
    }

    public int getIndiceFinal() {
        return indiceFinal;
    }

    public void setIndiceFinal(int indiceFinal) {
        this.indiceFinal = indiceFinal;
    }

    public Operacion getOperation() {
        return operation;
    }

    public void setOperation(Operacion operation) {
        this.operation = operation;
    }

    public TablaSimbolo getTableSimbol() {
        return tableSimbol;
    }

    public void setTableSimbol(TablaSimbolo tableSimbol) {
        this.tableSimbol = tableSimbol;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public boolean isAnidado() {
        return anidado;
    }

    public void setAnidado(boolean anidado) {
        this.anidado = anidado;
    }

    public ArrayList<Operacion> getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(ArrayList<Operacion> operaciones) {
        this.operaciones = operaciones;
    }

    public boolean isIsTabla() {
        return isTabla;
    }

    public void setIsTabla(boolean isTabla) {
        this.isTabla = isTabla;
    }

    public boolean isAsignacionArrayIndividual() {
        return asignacionArrayIndividual;
    }

    public void setAsignacionArrayIndividual(boolean asignacionArrayIndividual) {
        this.asignacionArrayIndividual = asignacionArrayIndividual;
    }

}
