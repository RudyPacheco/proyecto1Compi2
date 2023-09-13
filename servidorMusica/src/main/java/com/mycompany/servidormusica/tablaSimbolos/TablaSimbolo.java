package com.mycompany.servidormusica.tablaSimbolos;

import com.mycompany.servidormusica.declaracionAsignacion.Expresion;
import com.mycompany.servidormusica.manejoErrores.ErrorSemantico;
import com.mycompany.servidormusica.token.Token;

import com.mycompany.servidormusica.declaracionAsignacion.Arreglo;
import com.mycompany.servidormusica.declaracionAsignacion.TipoDato;

import java.io.Serializable;

import java.util.ArrayList;

/**
 *
 * @author andaryus
 */

public class TablaSimbolo implements Serializable {

    private ArrayList<Token> ids = new ArrayList<>();
    private ArrayList<Variable> variables = new ArrayList<>();
    private ArrayList<ErrorSemantico> erros;
    private TablaSimbolo tablaSimbolPadre;
    private boolean isFuncion;
    private ArrayList<Arreglo> arreglos = new ArrayList<>();

    public TablaSimbolo(ArrayList<ErrorSemantico> erros) {
        this.erros = erros;
        this.isFuncion = false;
    }

    public TablaSimbolo(ArrayList<ErrorSemantico> erros, boolean isFuncion) {
        this.isFuncion = isFuncion;
        this.erros = erros;
    }

    public void capturarVariablesGloblase(TipoDato tipo, String funcionPadre, boolean inicializado) {
        ids.forEach(id -> {
            Variable tmp = new Variable(id, tipo, funcionPadre, inicializado);
            variables.add(tmp);
        });
    }

    public void capturarArreglos(TipoDato tipo, int dimensionesDecla, int maxIndice, int maxIndiceFila) {
        ids.forEach(id -> {
            Arreglo arreglo = new Arreglo(id, tipo, dimensionesDecla, maxIndice, maxIndiceFila);
            this.arreglos.add(arreglo);
        });
    }

    public void capturarIds(Token id) {
        boolean capturar = true;
        if (this.ids.isEmpty()) {
            if (!this.variableDeclarada(id)) {
                ids.add(id);
            }

        } else {
            for (Token tok : ids) {
                if (tok.getLexeme().equals(id.getLexeme())) {
                    //error semantico, variable ya declarada antes
                    this.erros.add(new ErrorSemantico(id, "Variable ya declarada"));
                    capturar = false;
                    break;
                }
            }
            if (capturar && !this.variableDeclarada(id)) {
                this.ids.add(id);
            }

        }

    }

    public void capturarIdsFuncion(Token id) {
        boolean capturar = true;
        if (this.ids.isEmpty()) {
            ids.add(id);
        } else {
            for (Token tok : ids) {
                if (tok.getLexeme().equals(id.getLexeme())) {
                
                    this.erros.add(new ErrorSemantico(id, "Variable ya declarada"));
                    capturar = false;
                    break;
                }
            }
            if (capturar) {
                this.ids.add(id);
            }

        }

    }


    public void capturarIdsArreglos(Token id) {
        boolean capturar = true;
        if (this.ids.isEmpty()) {
            if (!this.arregloYaDeclarado(id)) {
                ids.add(id);
            }
        } else {
            for (Token tok : ids) {
                if (tok.getLexeme().equals(id.getLexeme())) {
                    //error semantico, variable ya declarada antes
                    this.erros.add(new ErrorSemantico(id, "Arreglo ya declarado"));
                    capturar = false;
                    break;
                }
            }
            if (capturar && !this.arregloYaDeclarado(id)) {
                this.ids.add(id);
            }

        }

    }

    public int varExiste(Token token) {
        int index = -1;
        for (int i = 0; i < variables.size(); i++) {
            if (variables.get(i).getNombre().equals(token.getLexeme())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            this.erros.add(new ErrorSemantico(token, "Variable no declarada"));
        }
        return index;
    }

    public Expresion getDato(Token token, String nomVar) {
        Expresion tmp = new Expresion(true, 0, TipoDato.ENTERO);
        boolean encontredo = false;
        if (!nomVar.equals("")) {
            for (Variable variable : variables) {
                if (variable.getNombre().equals(nomVar)) {
                    tmp.setValorDato(variable.getDato());
                    encontredo = true;
                    break;
                }
            }
        }
        if (!encontredo) {
            if (null == this.tablaSimbolPadre) {
                //reportar error
                this.erros.add(new ErrorSemantico(token, "Variable no declarada"));
            } else {
                tmp = this.tablaSimbolPadre.getDato(token, nomVar);
            }
        }
        return tmp;
    }


    public void asignacionValorVariable(Expresion dato, Token id) {
        boolean encontrado = false;
        for (Variable variable : variables) {
            if (variable.getNombre().equals(id.getLexeme())) {
                variable.setDato(dato, erros);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            if (null == this.tablaSimbolPadre) {
                //reportar error
                this.erros.add(new ErrorSemantico(id, "Variable no declarada"));
            } else {
                this.tablaSimbolPadre.asignacionValorVariable(dato, id);
            }
        }
    }

    public void asignacionValorArreglo(Expresion dato, Token id, int dimension, ArrayList<Integer> indices) {
        boolean encontrado = false;
        for (Arreglo arreglo : arreglos) {
            if (arreglo.getToken().getLexeme().equals(id.getLexeme())) {
                if (dimension != arreglo.getDimension()) {
                    this.erros.add(new ErrorSemantico(id, "La dimension del arreglo no coincide"));
                } else {
                    if (arreglo.indiceValido(indices, erros, id)) {
                        arreglo.agregarValor(dato, id, indices, this.erros);
                    }
                }
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            if (null == this.tablaSimbolPadre) {
                //reportar error
                this.erros.add(new ErrorSemantico(id, "Variable no declarada"));
            } else {
                this.tablaSimbolPadre.asignacionValorArreglo(dato, id, dimension, indices);
            }
        }
    }


    public boolean variableDeclarada(Token id) {
        boolean repti = false;
        for (Variable variable : variables) {
            if (variable.getNombre().equals(id.getLexeme())) {
                repti = true;
                    this.erros.add(new ErrorSemantico(id, "Variable ya declarada"));
                break;
            }
        }
        if (!repti && null != this.tablaSimbolPadre && !this.isFuncion) {
            repti = this.tablaSimbolPadre.variableDeclarada(id);
        }
        return repti;
    }

    public boolean arregloYaDeclarado(Token id) {
        boolean repti = false;
        for (Arreglo arreglo : arreglos) {
            if (arreglo.getToken().getLexeme().equals(id.getLexeme())) {
                repti = true;
                this.erros.add(new ErrorSemantico(id, " Arreglo ya declarado"));
                break;
            }
        }
        if (!repti && null != this.tablaSimbolPadre && !this.isFuncion) {
            repti = this.tablaSimbolPadre.arregloYaDeclarado(id);
        }
        return repti;
    }

    public boolean buscarRetorno(String nomVar) {
        boolean repti = false;
        if (isFuncion) {
            for (Variable variable : variables) {
                if (variable.getNombre().equals(nomVar)) {
                    repti = true;
                    break;
                }
            }
        } else {
            repti = this.tablaSimbolPadre.buscarRetorno(nomVar);
        }
        return repti;
    }

    public Expresion getDatoArreglo(Expresion dato) {
        Expresion tmp = new Expresion(true, 0, TipoDato.ENTERO);
        boolean encontredo = false;
        if (null != dato.getToken()) {
            for (Arreglo arreglo : arreglos) {
                if (arreglo.getToken().getLexeme().equals(dato.getToken().getLexeme())) {
                    ArrayList<Integer> indeces = dato.getIndices(erros, this);
                    if (arreglo.indiceValido(indeces, erros, dato.getToken())) {
                        tmp = arreglo.getDatoIndice(erros, indeces);
                    }
                    encontredo = true;
                    break;
                }
            }
        }
        if (!encontredo) {
            if (null == this.tablaSimbolPadre) {
                //reportar error
                this.erros.add(new ErrorSemantico(dato.getToken(), "Variable no declarada"));
            } else {
                tmp = this.tablaSimbolPadre.getDatoArreglo(dato);
            }
        }
        return tmp;
    }

    public void extenderDeOtraTabla(ArrayList<Variable> variables, ArrayList<Arreglo> arreglos) {
        this.variables.addAll(variables);
        this.arreglos.addAll(arreglos);

    }

    /*espacio para getters y setters*/
    public ArrayList<Token> getIds() {
        return ids;
    }

    public ArrayList<ErrorSemantico> getErros() {
        return erros;
    }

    public void setErros(ArrayList<ErrorSemantico> erros) {
        this.erros = erros;
    }

    public void setIds(ArrayList<Token> ids) {
        this.ids = ids;
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public void setVariables(ArrayList<Variable> variables) {
        this.variables = variables;
    }

    public TablaSimbolo getTablaSimbolPadre() {
        return tablaSimbolPadre;
    }

    public void setTablaSimbolPadre(TablaSimbolo tablaSimbolPadre) {
        this.tablaSimbolPadre = tablaSimbolPadre;
    }

    public boolean isIsFuncion() {
        return isFuncion;
    }

    public void setIsFuncion(boolean isFuncion) {
        this.isFuncion = isFuncion;
    }

    public ArrayList<Arreglo> getArreglos() {
        return arreglos;
    }

    public void setArreglos(ArrayList<Arreglo> arreglos) {
        this.arreglos = arreglos;
    }

}
