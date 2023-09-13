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
public class ManejoArreglos extends Instruccion implements Serializable {

    private TablaSimbolo tablaSimbol;
    private ArrayList<Operacion> operaciones;
    private boolean esDeclaracionGlobla = false;
    private ArrayList<Token> ids;
    private TipoDato tipoArreglo;
    private boolean inizializado;
    private int dimensionCreacion;
    private int dimensionAsignada;
    private ArrayList<ErrorSemantico> errorsSemanticos;
    private int indexI;
    private int indexF;
    private int indexMax;

    public ManejoArreglos(int indexMax, ArrayList<Operacion> operaciones, ArrayList<Token> ids, TipoDato tipoArreglo, int dimensionCreacion, int dimensionAsignada, ArrayList<ErrorSemantico> errorsSemanticos) {
        this.operaciones = operaciones;
        this.recolectarIds(ids);
        this.tipoArreglo = tipoArreglo;
        this.dimensionCreacion = dimensionCreacion;
        this.dimensionAsignada = dimensionAsignada;
        this.errorsSemanticos = errorsSemanticos;
        this.indexMax = indexMax;
        this.inizializado = true;
    }

    public ManejoArreglos(TipoDato tipoArreglo, TablaSimbolo tablaSimbol, ArrayList<Operacion> operaciones, ArrayList<Token> ids) {
        this.tablaSimbol = tablaSimbol;
        this.operaciones = operaciones;
        this.recolectarIds(ids);
        this.esDeclaracionGlobla = true;
        this.tipoArreglo = tipoArreglo;
    }

    @Override
    public void ejecutar(ArrayList<ErrorSemantico> errorsSemanticos) {
        if (esDeclaracionGlobla) {
            this.ids.forEach(id -> {
                this.tablaSimbol.getArreglos().add(new Arreglo(id, tipoArreglo, operaciones.size(), this.indicesMaximos(errorsSemanticos)));
            });
        } else {
            if (inizializado) {
                for (int i = indexI; i < indexF; i++) {
                    ArrayList<Expresion> datos = this.datosInsertar();
                    tablaSimbol.getArreglos().get(i).setDatos(datos);
                }
            } else {
                this.ids.forEach(id -> {
                    this.tablaSimbol.arregloYaDeclarado(id);
                    this.tablaSimbol.getArreglos().add(new Arreglo(id, tipoArreglo, operaciones.size(), this.indicesMaximos(errorsSemanticos)));
                });
            }
        }

    }

    @Override
    public void ReferenciaTabla(TablaSimbolo tabla) {
        this.tablaSimbol = tabla;
        if (inizializado) {
            if (this.dimensionAsignada != this.dimensionCreacion) {
                this.errorsSemanticos.add(new ErrorSemantico(ids.get(0), "Erro en la Creacion del Arreglo, la inicializacion del arreglo no cumple con las dimensiones del mismo"));
            }
            this.indexI = this.tablaSimbol.getArreglos().size();
            this.indexF = this.indexI + this.ids.size();
            this.ids.forEach(id -> {
                this.tablaSimbol.arregloYaDeclarado(id);
                this.tablaSimbol.getArreglos().add(new Arreglo(id, tipoArreglo, this.dimensionCreacion, indexMax,this.dimensionAsignada));
            });
        }

    }

    private ArrayList<Expresion> datosInsertar() {
        ArrayList<Expresion> datos = new ArrayList<>();
        this.operaciones.forEach(op -> {
            Expresion tmp = op.ejecutar(errorsSemanticos, tablaSimbol);
            if (tmp.getTipoDato() != this.tipoArreglo) {
                this.errorsSemanticos.add(new ErrorSemantico(ids.get(0), "El dato a insertar no es compatible con el tipo del arreglo"));
            }
            datos.add(tmp);
        });
        return datos;
    }

    private ArrayList<Integer> indicesMaximos(ArrayList<ErrorSemantico> errorsSemanticos) {
        ArrayList<Integer> indexMaximos = new ArrayList<>();
        this.operaciones.forEach(oper -> {
            Expresion dat = oper.ejecutar(errorsSemanticos, tablaSimbol);
            if (TipoDato.ENTERO != dat.getTipoDato()) {
                errorsSemanticos.add(new ErrorSemantico(dat.getToken(), "Los valores del tamaño del arreglo deben ser enteros"));
                indexMaximos.add(1);
            } else {
                if (dat.getNumero() <= 0) {
                    errorsSemanticos.add(new ErrorSemantico(dat.getToken(), "Los valores del tamaño del arreglo deben ser mayor a 0"));
                    indexMaximos.add(1);
                } else {
                    indexMaximos.add(dat.getNumero() - 1);
                }
            }

        });
        return indexMaximos;
    }

    private void recolectarIds(ArrayList<Token> ids) {
        this.ids = new ArrayList<>();
        ids.forEach(id -> {
            this.ids.add(id);
        });
    }

    public TablaSimbolo getTablaSimbol() {
        return tablaSimbol;
    }

    public void setTablaSimbol(TablaSimbolo tablaSimbol) {
        this.tablaSimbol = tablaSimbol;
    }

    public ArrayList<Operacion> getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(ArrayList<Operacion> operaciones) {
        this.operaciones = operaciones;
    }

    public boolean isEsDeclaracionGlobla() {
        return esDeclaracionGlobla;
    }

    public void setEsDeclaracionGlobla(boolean esDeclaracionGlobla) {
        this.esDeclaracionGlobla = esDeclaracionGlobla;
    }

    public ArrayList<Token> getIds() {
        return ids;
    }

    public void setIds(ArrayList<Token> ids) {
        this.ids = ids;
    }

    public TipoDato getTipoArreglo() {
        return tipoArreglo;
    }

    public void setTipoArreglo(TipoDato tipoArreglo) {
        this.tipoArreglo = tipoArreglo;
    }

    public boolean isInizializado() {
        return inizializado;
    }

    public void setInizializado(boolean inizializado) {
        this.inizializado = inizializado;
    }

    public int getDimensionCreacion() {
        return dimensionCreacion;
    }

    public void setDimensionCreacion(int dimensionCreacion) {
        this.dimensionCreacion = dimensionCreacion;
    }

    public int getDimensionAsignada() {
        return dimensionAsignada;
    }

    public void setDimensionAsignada(int dimensionAsignada) {
        this.dimensionAsignada = dimensionAsignada;
    }

}
