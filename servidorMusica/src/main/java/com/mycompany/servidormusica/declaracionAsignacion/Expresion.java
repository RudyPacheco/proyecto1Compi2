package com.mycompany.servidormusica.declaracionAsignacion;

import com.mycompany.servidormusica.manejoErrores.ErrorSemantico;
import com.mycompany.servidormusica.tablaSimbolos.TablaSimbolo;
import com.mycompany.servidormusica.token.Token;
import java.io.Serializable;
import java.util.ArrayList;
/**
 *
 * @author andaryus
 */
public class Expresion implements Serializable{

    private String cadena;
    private double decimal;
    private int numero = 0;
    private char caracter;
    private boolean booleano;
    private Token token;
    private String nombreVar = "";
    private boolean isVariable;
    private boolean inicializado;
    private TipoDato tipoDato = TipoDato.ENTERO;
    private boolean isVarArreglo = false;
    private ArrayList<Operacion> operaciones;

    public Expresion(boolean inicializado, TipoDato tipoDato) {
        this.inicializado = inicializado;
        this.tipoDato = tipoDato;
    }

    public Expresion(boolean inicializado) {
        this.inicializado = inicializado;
    }

    public Expresion(boolean inicializado, int entero, TipoDato tipo) {
        this.inicializado = inicializado;
        this.numero = entero;
        this.tipoDato = tipo;
        this.token = new Token(0, 0);
    }

    public Expresion(boolean inicializado, Token token, TipoDato tipo) {
        this.inicializado = inicializado;
        this.tipoDato = tipo;
        this.token = token;
        if (token != null) {
            this.convertirDato(token.getLexeme());
        }
    }

    public Expresion(boolean inicializado, Token token, TipoDato tipo, String nombVar) {
        this.inicializado = inicializado;
        this.tipoDato = tipo;
        this.token = token;
        this.nombreVar = nombVar;
        if (token != null) {
            this.convertirDato(token.getLexeme());
        }
        this.isVariable = true;
    }

    public Expresion(boolean inicializado, Token token, TipoDato tipo, ArrayList<Operacion> operaciones) {
        this.inicializado = inicializado;
        this.tipoDato = tipo;
        this.token = token;
        this.operaciones = operaciones;
        if (token != null) {
            this.convertirDato(token.getLexeme());
        }
        this.isVarArreglo = true;
    }

    private void convertirDato(String yytext) {
        switch (this.tipoDato) {
            case CADENA:
                this.cadena = yytext;
                break;
            case BOOLEAN:
                this.booleano = yytext.equalsIgnoreCase("verdadero");
                break;
            case CHAR:
                this.caracter = yytext.charAt(0);
                break;
            case DECIMAL:
                try {
                this.decimal = Double.parseDouble(yytext);
            } catch (NumberFormatException e) {
                this.decimal = 0;
            }
            break;
            default:
                try {
                this.numero = Integer.parseInt(yytext);
            } catch (NumberFormatException e) {
                this.numero = 0;
            }
            break;
        }
    }

    public ArrayList<Integer> getIndices(ArrayList<ErrorSemantico> erros, TablaSimbolo tabla) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (Operacion operacione : operaciones) {
            Expresion tmp = operacione.ejecutar(erros, tabla);
            if (tmp.getTipoDato() != TipoDato.ENTERO) {
                erros.add(new ErrorSemantico(token, "Los indices deben ser de tipo entero"));
                indices.add(1);
            } else {
                if (tmp.getNumero() < 0) {
                    erros.add(new ErrorSemantico(token, "Los indices deben ser de mayores a 0"));
                    indices.add(1);
                }else{
                    indices.add(tmp.getNumero());
                }
            }
        }

        return indices;
    }
    
    public void setValorDato(Expresion dato){
        this.tipoDato = dato.getTipoDato();
         switch (dato.getTipoDato()) {
            case CADENA:
                cadena = dato.getCadena();
                break;
            case BOOLEAN:
                this.booleano = dato.isBooleano();
                break;
            case CHAR:
                this.caracter = dato.getCaracter();
                break;
            case DECIMAL:
                this.decimal = dato.getDecimal();
                break;
            default:
                this.numero = dato.getNumero();
                break;
        }
    }

    /*espacio para getters y settesrs*/
    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public double getDecimal() {
        return decimal;
    }

    public void setDecimal(double decimal) {
        this.decimal = decimal;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public char getCaracter() {
        return caracter;
    }

    public void setCaracter(char caracter) {
        this.caracter = caracter;
    }

    public boolean isBooleano() {
        return booleano;
    }

    public void setBooleano(boolean booleano) {
        this.booleano = booleano;
    }

    public String getNombreVar() {
        return nombreVar;
    }

    public void setNombreVar(String nombreVar) {
        this.nombreVar = nombreVar;
    }

    public boolean isInicializado() {
        return inicializado;
    }

    public void setInicializado(boolean inicializado) {
        this.inicializado = inicializado;
    }

    public TipoDato getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(TipoDato tipoDato) {
        this.tipoDato = tipoDato;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public boolean isIsVariable() {
        return isVariable;
    }

    public void setIsVariable(boolean isVariable) {
        this.isVariable = isVariable;
    }

    public boolean isIsVarArreglo() {
        return isVarArreglo;
    }

    public void setIsVarArreglo(boolean isVarArreglo) {
        this.isVarArreglo = isVarArreglo;
    }

    @Override
    public String toString() {
        return "Dato{" + "cadena=" + cadena + ", decimal=" + decimal + ", numero=" + numero + ", caracter=" + caracter + ", booleano=" + booleano + ", token=" + token + ", nombreVar=" + nombreVar + ", inicializado=" + inicializado + ", tipoDato=" + tipoDato + '}';
    }

}
