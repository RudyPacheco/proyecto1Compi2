package com.mycompany.servidormusica.pista;

import com.mycompany.servidormusica.declaracionAsignacion.Asignacion;
import com.mycompany.servidormusica.declaracionAsignacion.Expresion;
import com.mycompany.servidormusica.declaracionAsignacion.ManejoArreglos;
import com.mycompany.servidormusica.declaracionAsignacion.Operacion;
import com.mycompany.servidormusica.declaracionAsignacion.TipoDato;
import com.mycompany.servidormusica.instrucciones.Instruccion;
import com.mycompany.servidormusica.instrucciones.funciones.FuncionMensaje;
import com.mycompany.servidormusica.instrucciones.funciones.Funcion;
import com.mycompany.servidormusica.instrucciones.music.ManejadorPistaMusical;
import com.mycompany.servidormusica.instrucciones.music.PistaMusical;
import com.mycompany.servidormusica.manejoErrores.ErrorSemantico;
import com.mycompany.servidormusica.manejoErrores.ErroresSingleton;
import com.mycompany.servidormusica.tablaSimbolos.TablaSimbolo;
import com.mycompany.servidormusica.tablaSimbolos.Variable;
import com.mycompany.servidormusica.token.Token;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author andaryus
 */



public class Pista implements Serializable {

    private String nombre;
    private TablaSimbolo tableSimbolGoblal;
    private ArrayList<Instruccion> instrucciones = new ArrayList<>();
    private ArrayList<ErrorSemantico> errorsSemanticos;
    private ArrayList<Funcion> funciones = new ArrayList<>();
    private Funcion funPrincipal;
    private int sizeArray = 0;
    private ArrayList<Token> extendiende;
    private PistaMusical pistaMusical;
    private String codigoFuente;

    public Pista(String nombre, TablaSimbolo tableSimbolGoblal, ArrayList<ErrorSemantico> errorsSemanticos) {
        this.nombre = nombre;
        this.tableSimbolGoblal = tableSimbolGoblal;
        this.errorsSemanticos = errorsSemanticos;
    }

    public Pista(ArrayList<Token> extendiende, String nombre, TablaSimbolo tableSimbolGoblal, ArrayList<ErrorSemantico> errorsSemanticos) {
        this.nombre = nombre;
        this.tableSimbolGoblal = tableSimbolGoblal;
        this.errorsSemanticos = errorsSemanticos;
        this.extendiende = extendiende;
        this.accionExtender();
    }

    public Pista(String codigoFuente,PistaMusical pistaMusical, ArrayList<Funcion> funciones, String nombre, TablaSimbolo tableSimbolGoblal) {
        this.nombre = nombre;
        this.tableSimbolGoblal = tableSimbolGoblal;
        this.funciones = funciones;
        this.pistaMusical = pistaMusical;
        this.codigoFuente = codigoFuente;
    }

    public void carpturarVariablesGlobales(TipoDato tipo, boolean inicializado, Operacion op) {
        if (inicializado) {
            int indexI = tableSimbolGoblal.getVariables().size();
            int indexF = indexI + tableSimbolGoblal.getIds().size();
            Asignacion asig = new Asignacion(indexI, indexF, op, tableSimbolGoblal);

            this.instrucciones.add(asig);
        }
        tableSimbolGoblal.capturarVariablesGloblase(tipo, this.nombre, inicializado);

        tableSimbolGoblal.getIds().clear();
    }

    public void capturarArregloGlobales(TipoDato tipo, int dimensionesDecla, int dimesionesAsign, ArrayList<Operacion> operations) {
        if (dimensionesDecla > 2) {
            this.errorsSemanticos.add(new ErrorSemantico(this.tableSimbolGoblal.getIds().get(0), "Los arreglos perminte ser de dos dimensiones [][]"));
        }
        int indexI = this.tableSimbolGoblal.getArreglos().size();
        int indexF = indexI + tableSimbolGoblal.getIds().size();
        Asignacion asig = new Asignacion(indexI, indexF, tableSimbolGoblal, operations);
        this.instrucciones.add(asig);
        this.tableSimbolGoblal.capturarArreglos(tipo, dimensionesDecla, this.sizeArray, dimesionesAsign);
        tableSimbolGoblal.getIds().clear();
        this.sizeArray = 0;
    }

    //TODO: incorporar la logica de la busquda de esta variable en el resto de tablas que vaya a extender
    public void capturarAsignacion(Token id, Operacion op) {
        /*buscar variable en tabla de datos*/
        int index = this.tableSimbolGoblal.varExiste(id);
        if (index != -1) {
            Asignacion asig = new Asignacion(index, index + 1, op, tableSimbolGoblal);
            this.instrucciones.add(asig);

        }
    }

    public Funcion getFuncionEspecifica(Token id, ArrayList<Operacion> parametros, boolean conRetorno) {
        Funcion fun = null;
        for (Funcion funcion : funciones) {
            if (!id.getLexeme().equals(funcion.getNombre())) {
                continue;
            }
            if (conRetorno && null == funcion.getTipoRetono()) {
                continue;
            }
            if (funcion.getParametros().size() != parametros.size()) {
                continue;
            }
            if (comprobarTipos(parametros, funcion.getParametros(), id, funcion.getTableSimbol())) {
                fun = new Funcion(funcion);
                break;
            }

        }
        if (null == fun) {
            this.errorsSemanticos.add(new ErrorSemantico(id, "Funcion no encontrada"));
        }

        return fun;
    }

    public boolean comprobarTipos(ArrayList<Operacion> parametros, ArrayList<Variable> varParametros, Token id, TablaSimbolo tabla) {
        boolean conciden = true;
        int index = 0;
        for (Variable varParametro : varParametros) {
            Expresion dato = parametros.get(index).ejecutar(errorsSemanticos, tabla);
            if (varParametro.getTipo() != dato.getTipoDato()) {
                conciden = false;
                break;
            }
            tabla.asignacionValorVariable(dato, varParametro.getToken());
            index++;
        }
        return conciden;
    }

    public void referenciarTablasPadres() {
        if (ErroresSingleton.getInstance().noHayErrores()) {
            this.funciones.forEach(fun -> {
                fun.ReferenciaTabla(tableSimbolGoblal);
            });
            if (this.funPrincipal != null) {
                this.funPrincipal.ReferenciaTabla(tableSimbolGoblal);
            }
        }

    }


    public ArrayList<Operacion> unirOperaciones(ArrayList<Operacion> operations1, ArrayList<Operacion> operations2, Token token) {
        if (this.sizeArray == 0) {
            this.sizeArray = operations2.size();
        }
        if (this.sizeArray != operations1.size()) {

            this.errorsSemanticos.add(new ErrorSemantico(token, "Los datos no son del mismo tamaño"));
        }
        operations1.addAll(operations2);
        return operations1;
    }

    /*funcion que captura un arreglo sin inicializar var entero arreglo algo[2][1+2];*/
    public void captruarDeclaracionArreglo(TipoDato tipoArreglo, ArrayList<Operacion> operaciones) {
        ManejoArreglos manejador = new ManejoArreglos(tipoArreglo, this.tableSimbolGoblal, operaciones, this.tableSimbolGoblal.getIds());
        this.instrucciones.add(manejador);
        this.tableSimbolGoblal.getIds().clear();
    }

    public void autoguardar() {
        if (PistasCompiladas.getInstancePistasActivacion().sobreEscribir(nombre, errorsSemanticos)) {
            this.pistaMusical = ManejadorPistaMusical.getPistaMusical().pistaMusical(nombre, errorsSemanticos);
           
            PistasCompiladas.getInstancePistasActivacion().push(this, errorsSemanticos);
             System.out.println("Guardando --> " + this.nombre);
        }
    }

    public void accionExtender() {
        for (Token token : extendiende) {
            Pista tmpPista = PistasCompiladas.getInstancePistasActivacion().getPistaExtends(token);
            if (null == tmpPista) {
                this.errorsSemanticos.add(new ErrorSemantico(token, "No se encontro la pista a extender"));
                break;
            }
            this.tableSimbolGoblal.extenderDeOtraTabla(tmpPista.tableSimbolGoblal.getVariables(), tmpPista.tableSimbolGoblal.getArreglos());
            this.funciones.addAll(tmpPista.getFunciones());
        }
    }

    public void realizarAccionesSemanticas() {
        if (ErroresSingleton.getInstance().noHayErrores()) {
            instrucciones.forEach(instruccione -> {
                instruccione.ejecutar(errorsSemanticos);
            });
            if (null != this.funPrincipal) {
                this.funPrincipal.ejecutar(errorsSemanticos);
            }
        }
    }
    
    public void pushFuncion(Funcion fun){
        boolean capturar = true;
        for (Funcion funcione : funciones) {
            if (funcione.getNombre().equals(fun.getNombre())) {
                capturar = false;
                this.errorsSemanticos.add(new ErrorSemantico(fun.getToken(), "La funcion ya existe"));
                break;
            }
        }
        if (capturar) {
            this.funciones.add(fun);
        }

    }

    public void addInstruccion(Instruccion instruccion) {
        this.instrucciones.add(instruccion);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TablaSimbolo getTableSimbolGoblal() {
        return tableSimbolGoblal;
    }

    public void setTableSimbolGoblal(TablaSimbolo tableSimbolGoblal) {
        this.tableSimbolGoblal = tableSimbolGoblal;
    }

    public ArrayList<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(ArrayList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public ArrayList<ErrorSemantico> getErrorsSemanticos() {
        return errorsSemanticos;
    }

    public void setErrorsSemanticos(ArrayList<ErrorSemantico> errorsSemanticos) {
        this.errorsSemanticos = errorsSemanticos;
    }

    public ArrayList<Funcion> getFunciones() {
        return funciones;
    }

    public void setFunciones(ArrayList<Funcion> funciones) {
        this.funciones = funciones;
    }

    public Funcion getFunPrincipal() {
        return funPrincipal;
    }

    public void setFunPrincipal(Funcion funPrincipal) {
        this.funPrincipal = funPrincipal;
    }

    public int getSizeArray() {
        return sizeArray;
    }

    public void setSizeArray(int sizeArray) {
        this.sizeArray = sizeArray;
    }

    public PistaMusical getPistaMusical() {
        return pistaMusical;
    }

    public void setPistaMusical(PistaMusical pistaMusical) {
        this.pistaMusical = pistaMusical;
    }

    public String getCodigoFuente() {
        return codigoFuente;
    }

    public void setCodigoFuente(String codigoFuente) {
        this.codigoFuente = codigoFuente;
    }
    
    

    public void tostringDAts() {
        instrucciones.forEach(instruccione -> {
            instruccione.ejecutar(errorsSemanticos);
        });

        if (null != this.funPrincipal) {
            this.funPrincipal.ejecutar(errorsSemanticos);
        }

        this.tableSimbolGoblal.getArreglos().forEach(arr -> {
            System.out.println(arr.toString());
        });
        FuncionMensaje.getInstanceMensajes().getMensajes().forEach(ms -> {
            System.out.println(ms);
        });
        this.errorsSemanticos.forEach(err -> {
            System.out.println(err.getDescripcion() + err.getToken().getLexeme());
        });

    }

}
