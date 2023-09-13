package com.mycompany.servidormusica.declaracionAsignacion;


import com.mycompany.servidormusica.manejoErrores.ErrorSemantico;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author andaryus
 */
public class CasteoOperacion implements Serializable {

    private Expresion datoD;
    private Expresion datoI;
    private TipoOperacion tipoOp;
    private ArrayList<ErrorSemantico> errorsSemanticos;

    public CasteoOperacion(ArrayList<ErrorSemantico> errorsSemanticos) {
        this.errorsSemanticos = errorsSemanticos;
    }

    public Expresion resultadoOperacion(Expresion datoD, Expresion datoL, TipoOperacion tipoOp) {
        this.datoD = datoD;
        this.datoI = datoL;
        Expresion datoResult = new Expresion(true, 0, TipoDato.ENTERO);
        datoResult.setToken(datoI.getToken());
        if (!this.datoInicializado()) {
            return datoResult;
        }
        switch (tipoOp) {
            case SUMA:
                datoResult = operacionSuma();
                break;
            case RESTA:
                datoResult = operacionResta();
                break;
            case MULTIPLICACION:
                datoResult = operacionMultplicacion();
                break;
            case DIVISION:
                datoResult = operacionDivivision();
                break;
            case MODULO:
                datoResult = operacionMod();
                break;
            case POTENCIA:
                datoResult = operacionPotencia();
                break;
            case MAYORQUE:
                datoResult = operacionMayorQue();
                break;
            case MENORQUE:
                datoResult = operacionMenorQue();
                break;
            case MAYOROIGUAL:
                datoResult = operacionMayorOIgualQue();
                break;
            case MENOROIGUAL:
                datoResult = operacionMenorOIgualQue();
                break;
            case NOTEQUALS:
                datoResult = operacionNOTEQUALS();
                break;
            case EQUALS:
                datoResult = operacionEQUALS();
                break;
            case OR:
                datoResult = operacionOR();
                break;
            case AND:
                datoResult = operacionAND();
                break;
            case NAND:
                datoResult = operacionNAND();
                break;
            case NOR:
                datoResult = operacionNOR();
                break;
            case XOR:
                datoResult = operacionXOR();
                break;
            case ISNULO:
                datoResult = isNulo();
                break;
            case NOT:
                datoResult = not();
                break;
        }
        return datoResult;
    }

    private Expresion not() {
        Expresion datoResult = new Expresion(true, 0, TipoDato.BOOLEAN);
        if (this.datoI.getTipoDato() == TipoDato.BOOLEAN) {
            datoResult.setBooleano(!this.datoI.isBooleano());
        }else{
            errorsSemanticos.add(new ErrorSemantico(this.datoI.getToken(), "El operador NOT solo hacepta datos booleanos para su funcionamiento"));
        }
        return datoResult;
    }

    private Expresion isNulo() {
        Expresion datoResult = new Expresion(true, 0, TipoDato.BOOLEAN);
        datoResult.setBooleano(this.datoI.isInicializado());
        return datoResult;
    }

    private Expresion operacionMayorQue() {
        Expresion datoResult = new Expresion(true, 0, TipoDato.ENTERO);
        switch (this.datoD.getTipoDato()) {
            case ENTERO:
                switch (this.datoI.getTipoDato()) {
                    case ENTERO:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getNumero() > this.datoI.getNumero());
                        break;
                    case DECIMAL:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getNumero() > this.datoI.getDecimal());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion > (mayorQ) hacepta un entero compararado con entero o decimal"));
                        break;

                }
                break;
            case DECIMAL:
                switch (this.datoI.getTipoDato()) {
                    case ENTERO:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getDecimal() > this.datoI.getNumero());
                        break;
                    case DECIMAL:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getDecimal() > this.datoI.getDecimal());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion > (mayorQ) hacepta un Decimal compararado con entero o decimal"));
                        break;
                }
                break;
            case CADENA:
                switch (this.datoI.getTipoDato()) {
                    case CADENA:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getCadena().length() > this.datoI.getCadena().length());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion > (mayorQ) hacepta una Cadena compararado con otra cadena"));
                        break;
                }
                break;
            default:
                //error
                this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion > (mayorQ) No acepta los valores ingresados, la comparacion no se puede efectuar"));
                break;
        }
        return datoResult;

    }

    private Expresion operacionMayorOIgualQue() {
        Expresion datoResult = new Expresion(true, 0, TipoDato.ENTERO);
        switch (this.datoD.getTipoDato()) {
            case ENTERO:
                switch (this.datoI.getTipoDato()) {
                    case ENTERO:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getNumero() >= this.datoI.getNumero());
                        break;
                    case DECIMAL:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getNumero() >= this.datoI.getDecimal());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion >= (mayor Que o igual) hacepta un entero compararado con entero o decimal"));
                        break;

                }
                break;
            case DECIMAL:
                switch (this.datoI.getTipoDato()) {
                    case ENTERO:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getDecimal() >= this.datoI.getNumero());
                        break;
                    case DECIMAL:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getDecimal() >= this.datoI.getDecimal());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion  >= (mayor Que o igual) hacepta un Decimal compararado con entero o decimal"));
                        break;
                }
                break;
            case CADENA:
                switch (this.datoI.getTipoDato()) {
                    case CADENA:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getCadena().length() >= this.datoI.getCadena().length());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion >= (mayor Que o igual) hacepta una Cadena compararado con otra cadena"));
                        break;
                }
                break;
            default:
                //error
                this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion >= (mayor Que o igual) No acepta los valores ingresados, la comparacion no se puede efectuar"));
                break;
        }
        return datoResult;

    }

    private Expresion operacionMenorQue() {
        Expresion datoResult = new Expresion(true, 0, TipoDato.ENTERO);
        switch (this.datoD.getTipoDato()) {
            case ENTERO:
                switch (this.datoI.getTipoDato()) {
                    case ENTERO:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getNumero() < this.datoI.getNumero());
                        break;
                    case DECIMAL:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getNumero() < this.datoI.getDecimal());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion < (menor que) hacepta un entero compararado con entero o decimal"));
                        break;

                }
                break;
            case DECIMAL:
                switch (this.datoI.getTipoDato()) {
                    case ENTERO:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getDecimal() < this.datoI.getNumero());
                        break;
                    case DECIMAL:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getDecimal() < this.datoI.getDecimal());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion < (menor que) hacepta un Decimal compararado con entero o decimal"));
                        break;
                }
                break;
            case CADENA:
                switch (this.datoI.getTipoDato()) {
                    case CADENA:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getCadena().length() < this.datoI.getCadena().length());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion < (menor que) hacepta una Cadena compararado con otra cadena"));
                        break;
                }
                break;
            default:
                //error
                this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion < (Menor que) No acepta los valores ingresados, la comparacion no se puede efectuar"));
                break;
        }
        return datoResult;

    }

    private Expresion operacionMenorOIgualQue() {
        Expresion datoResult = new Expresion(true, 0, TipoDato.ENTERO);
        switch (this.datoD.getTipoDato()) {
            case ENTERO:
                switch (this.datoI.getTipoDato()) {
                    case ENTERO:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getNumero() <= this.datoI.getNumero());
                        break;
                    case DECIMAL:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getNumero() <= this.datoI.getDecimal());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion <= (menor que o igual) hacepta un entero compararado con entero o decimal"));
                        break;

                }
                break;
            case DECIMAL:
                switch (this.datoI.getTipoDato()) {
                    case ENTERO:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getDecimal() <= this.datoI.getNumero());
                        break;
                    case DECIMAL:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getDecimal() <= this.datoI.getDecimal());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion <= (menor que o igual) hacepta un Decimal compararado con entero o decimal"));
                        break;
                }
                break;
            case CADENA:
                switch (this.datoI.getTipoDato()) {
                    case CADENA:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getCadena().length() <= this.datoI.getCadena().length());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion <= (menor que o igual) hacepta una Cadena compararado con otra cadena"));
                        break;
                }
                break;
            default:
                //error
                this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion <= (menor que o igual) No acepta los valores ingresados, la comparacion no se puede efectuar"));
                break;
        }
        return datoResult;

    }

    private Expresion operacionNOTEQUALS() {
        Expresion datoResult = new Expresion(true, 0, TipoDato.ENTERO);
        switch (this.datoD.getTipoDato()) {
            case ENTERO:
                switch (this.datoI.getTipoDato()) {
                    case ENTERO:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getNumero() != this.datoI.getNumero());
                        break;
                    case DECIMAL:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getNumero() != this.datoI.getDecimal());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion != (Diferente Que) hacepta un entero compararado con entero o decimal"));
                        break;

                }
                break;
            case DECIMAL:
                switch (this.datoI.getTipoDato()) {
                    case ENTERO:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getDecimal() != this.datoI.getNumero());
                        break;
                    case DECIMAL:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getDecimal() != this.datoI.getDecimal());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion  != (Diferente Que) hacepta un Decimal compararado con entero o decimal"));
                        break;
                }
                break;
            case CADENA:
                switch (this.datoI.getTipoDato()) {
                    case CADENA:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(!this.datoD.getCadena().equals(this.datoI.getCadena()));
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion != (Diferente Que) hacepta una Cadena compararado con otra cadena"));
                        break;
                }
                break;
            case CHAR:
                switch (this.datoI.getTipoDato()) {
                    case CHAR:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getCaracter() != this.datoI.getCaracter());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion != (Diferente Que) hacepta una Cadena compararado con otra cadena"));
                        break;
                }
                break;
            default:
                //error
                this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion) != (Diferente Que) No acepta los valores ingresados, la comparacion no se puede efectuar"));
                break;
        }
        return datoResult;

    }

    private Expresion operacionEQUALS() {
        Expresion datoResult = new Expresion(true, 0, TipoDato.ENTERO);
        switch (this.datoD.getTipoDato()) {
            case ENTERO:
                switch (this.datoI.getTipoDato()) {
                    case ENTERO:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getNumero() == this.datoI.getNumero());
                        break;
                    case DECIMAL:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getNumero() == this.datoI.getDecimal());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion == (Igual A) hacepta un entero compararado con entero o decimal"));
                        break;

                }
                break;
            case DECIMAL:
                switch (this.datoI.getTipoDato()) {
                    case ENTERO:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getDecimal() == this.datoI.getNumero());
                        break;
                    case DECIMAL:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getDecimal() == this.datoI.getDecimal());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion   == (Igual A) hacepta un Decimal compararado con entero o decimal"));
                        break;
                }
                break;
            case CADENA:
                switch (this.datoI.getTipoDato()) {
                    case CADENA:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getCadena().equals(this.datoI.getCadena()));
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion  == (Igual A) hacepta una Cadena compararado con otra cadena"));
                        break;
                }
                break;
            case CHAR:
                switch (this.datoI.getTipoDato()) {
                    case CHAR:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.datoD.getCaracter() == this.datoI.getCaracter());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion  == (Igual A) hacepta una Cadena compararado con otra cadena"));
                        break;
                }
                break;
            default:
                //error
                this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "La operacion)  == (Igual A) No acepta los valores ingresados, la comparacion no se puede efectuar"));
                break;
        }
        return datoResult;

    }

    private Expresion operacionOR() {
        Expresion datoResult = new Expresion(true, 0, TipoDato.ENTERO);
        datoResult.setTipoDato(TipoDato.BOOLEAN);
        if (this.datoD.getTipoDato() == TipoDato.BOOLEAN && this.datoI.getTipoDato() == TipoDato.BOOLEAN) {
            datoResult.setBooleano(this.datoD.isBooleano() || this.datoI.isBooleano());
        } else {
            this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "El Operador OR solo acepta comparacion entre expresiones Booleanas"));
        }

        return datoResult;
    }

    private Expresion operacionAND() {
        Expresion datoResult = new Expresion(true, 0, TipoDato.ENTERO);
        datoResult.setTipoDato(TipoDato.BOOLEAN);
        if (this.datoD.getTipoDato() == TipoDato.BOOLEAN && this.datoI.getTipoDato() == TipoDato.BOOLEAN) {
            datoResult.setBooleano(this.datoD.isBooleano() && this.datoI.isBooleano());
        } else {
            this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "El Operador AND solo acepta comparacion entre expresiones Booleanas"));
        }

        return datoResult;
    }

    private Expresion operacionNAND() {
        Expresion datoResult = new Expresion(true, 0, TipoDato.ENTERO);
        datoResult.setTipoDato(TipoDato.BOOLEAN);
        if (this.datoD.getTipoDato() == TipoDato.BOOLEAN && this.datoI.getTipoDato() == TipoDato.BOOLEAN) {
            datoResult.setBooleano(!(this.datoD.isBooleano() && this.datoI.isBooleano()));
        } else {
            this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "El Operador NAND solo acepta comparacion entre expresiones Booleanas"));
        }

        return datoResult;
    }

    private Expresion operacionNOR() {
        Expresion datoResult = new Expresion(true, 0, TipoDato.ENTERO);
        datoResult.setTipoDato(TipoDato.BOOLEAN);
        if (this.datoD.getTipoDato() == TipoDato.BOOLEAN && this.datoI.getTipoDato() == TipoDato.BOOLEAN) {
            datoResult.setBooleano(!(this.datoD.isBooleano() || this.datoI.isBooleano()));
        } else {
            this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "El Operador NOR solo acepta comparacion entre expresiones Booleanas"));
        }

        return datoResult;
    }

    private Expresion operacionXOR() {
        Expresion datoResult = new Expresion(true, 0, TipoDato.ENTERO);
        datoResult.setTipoDato(TipoDato.BOOLEAN);
        if (this.datoD.getTipoDato() == TipoDato.BOOLEAN && this.datoI.getTipoDato() == TipoDato.BOOLEAN) {
            datoResult.setBooleano((this.datoD.isBooleano() && !this.datoI.isBooleano()) || (!this.datoD.isBooleano() && this.datoI.isBooleano()));
        } else {
            this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "El Operador NOR solo acepta comparacion entre expresiones Booleanas"));
        }

        return datoResult;
    }

    private Expresion operacionSuma() {
        Expresion datoResult = new Expresion(true, 0, TipoDato.ENTERO);
        switch (this.datoD.getTipoDato()) {
            case CADENA:
                datoResult.setTipoDato(TipoDato.CADENA);
                datoResult.setCadena(sumaStringDato());
                return datoResult;
            case BOOLEAN:
                datoResult.setTipoDato(TipoDato.BOOLEAN);
                datoResult.setBooleano(this.sumaBooleanDato());
                return datoResult;
            case CHAR:
                datoResult.setTipoDato(TipoDato.CHAR);
                datoResult.setCaracter(this.sumaCharDatp());
                return datoResult;
            case DECIMAL:
                datoResult.setTipoDato(TipoDato.DECIMAL);
                datoResult.setDecimal(this.sumaDecimalDato());
                return datoResult;
            default:
                datoResult.setTipoDato(TipoDato.ENTERO);
                datoResult.setNumero(this.sumaEnteroDato());
                return datoResult;
        }

    }

    private Expresion operacionResta() {
        Expresion datoResult = new Expresion(true, 0, TipoDato.ENTERO);
        if (datoD.getTipoDato() == TipoDato.DECIMAL) {
            switch (this.datoI.getTipoDato()) {
                case CADENA:
                    //no de puede restar numero - cadena
                    this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "no de puede restar un valor numero - cadena"));
                    break;
                case BOOLEAN:
                    int tmp = this.datoI.isBooleano() ? 1 : 0;
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((datoD.getDecimal() - tmp) * 1e6));
                    return datoResult;
                case CHAR:
                    int tmpc = this.datoI.getCaracter();
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((datoD.getDecimal() - tmpc) * 1e6));
                    return datoResult;
                case DECIMAL:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((datoD.getDecimal() - datoI.getDecimal()) * 1e6));
                    return datoResult;
                default:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((datoD.getDecimal() - datoI.getNumero()) * 1e6));
                    return datoResult;
            }
        }

        if (datoD.getTipoDato() == TipoDato.ENTERO) {
            switch (this.datoI.getTipoDato()) {
                case CADENA:
                    //no de puede restar numero - cadena
                    this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "no de puede restar un valor numero - cadena"));

                    break;
                case BOOLEAN:
                    int tmp = this.datoI.isBooleano() ? 1 : 0;
                    datoResult.setNumero(datoD.getNumero() - tmp);
                    return datoResult;
                case CHAR:
                    int tmpc = this.datoI.getCaracter();
                    datoResult.setNumero(datoD.getNumero() - tmpc);
                    return datoResult;
                case DECIMAL:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((datoD.getNumero() - datoI.getDecimal()) * 1e6));
                    return datoResult;
                default:
                    datoResult.setNumero(datoD.getNumero() - datoI.getNumero());
                    return datoResult;
            }

        }
        this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "no de puede restar un valor no numerico - otro valor"));

        return datoResult;

    }

    private Expresion operacionMultplicacion() {
        Expresion datoResult = new Expresion(true, 0, TipoDato.ENTERO);
        if (datoD.getTipoDato() == TipoDato.DECIMAL) {
            switch (this.datoI.getTipoDato()) {
                case CADENA:
                    //no de puede multiplicar numero * cadena
                    this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "no de puede multiplicar un valor numero * cadena"));

                    break;
                case BOOLEAN:
                    int tmp = this.datoI.isBooleano() ? 1 : 0;
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((datoD.getDecimal() * tmp) * 1e6));
                    return datoResult;
                case CHAR:
                    int tmpc = this.datoI.getCaracter();
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((datoD.getDecimal() * tmpc) * 1e6));
                    return datoResult;
                case DECIMAL:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((datoD.getDecimal() * datoI.getDecimal()) * 1e6));
                    return datoResult;
                default:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((datoD.getDecimal() * datoI.getNumero()) * 1e6));
                    return datoResult;
            }
        }

        if (datoD.getTipoDato() == TipoDato.ENTERO) {
            switch (this.datoI.getTipoDato()) {
                case CADENA:
                    this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "no de puede multiplicar un valor numero * cadena"));
                    break;
                case BOOLEAN:
                    int tmp = this.datoI.isBooleano() ? 1 : 0;
                    datoResult.setNumero(datoD.getNumero() * tmp);
                    return datoResult;
                case CHAR:
                    int tmpc = this.datoI.getCaracter();
                    datoResult.setNumero(datoD.getNumero() * tmpc);
                    return datoResult;
                case DECIMAL:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((datoD.getNumero() * datoI.getDecimal()) * 1e6));
                    return datoResult;
                default:
                    datoResult.setNumero(datoD.getNumero() * datoI.getNumero());
                    return datoResult;
            }

        }
        this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "no de puede multiplicar un valor no numerico * otro valor"));
        return datoResult;

    }

    private Expresion operacionDivivision() {
        Expresion datoResult = new Expresion(true, 0, TipoDato.ENTERO);
        if (datoD.getTipoDato() == TipoDato.DECIMAL) {
            switch (this.datoI.getTipoDato()) {
                case CADENA:
                    this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "no de puede dividir un valor numero / cadena"));

                    break;
                case BOOLEAN:
                    int tmp = this.datoI.isBooleano() ? 1 : 0;
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((datoD.getDecimal() / tmp) * 1e6));
                    return datoResult;
                case CHAR:
                    int tmpc = this.datoI.getCaracter();
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((datoD.getDecimal() / tmpc) * 1e6));
                    return datoResult;
                case DECIMAL:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((datoD.getDecimal() / datoI.getDecimal()) * 1e6));
                    return datoResult;
                default:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((datoD.getDecimal() / datoI.getNumero()) * 1e6));
                    return datoResult;
            }
        }

        if (datoD.getTipoDato() == TipoDato.ENTERO) {
            switch (this.datoI.getTipoDato()) {
                case CADENA:
                    //no de puede restar numero - cadena
                    this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "no de puede dividir un valor numero / cadena"));

                    break;
                case BOOLEAN:
                    int tmp = this.datoI.isBooleano() ? 1 : 0;
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((datoD.getNumero() / tmp) * 1e6));
                    return datoResult;
                case CHAR:
                    int tmpc = this.datoI.getCaracter();
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((datoD.getNumero() / tmpc) * 1e6));
                    return datoResult;
                case DECIMAL:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((datoD.getNumero() / datoI.getDecimal()) * 1e6));
                    return datoResult;
                default:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((datoD.getNumero() / datoI.getNumero()) * 1e6));
                    return datoResult;
            }

        }
        this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "no de puede dividir un valor no numero / otro valor"));
        return datoResult;

    }

    private Expresion operacionMod() {
        Expresion datoResult = new Expresion(true, 0, TipoDato.ENTERO);
        if (datoD.getTipoDato() == TipoDato.DECIMAL) {
            switch (this.datoI.getTipoDato()) {
                case CADENA:
                    this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "no de puede realizar Mod a un valor numero / cadena"));

                    break;
                case BOOLEAN:
                    int tmp = this.datoI.isBooleano() ? 1 : 0;
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((datoD.getDecimal() % tmp) * 1e6));
                    return datoResult;
                case CHAR:
                    int tmpc = this.datoI.getCaracter();
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((datoD.getDecimal() % tmpc) * 1e6));
                    return datoResult;
                case DECIMAL:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((datoD.getDecimal() % datoI.getDecimal()) * 1e6));
                    return datoResult;
                default:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((datoD.getDecimal() % datoI.getNumero()) * 1e6));
                    return datoResult;
            }
        }

        if (datoD.getTipoDato() == TipoDato.ENTERO) {
            switch (this.datoI.getTipoDato()) {
                case CADENA:
                    //no de puede restar numero - cadena
                    this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "no de puede realizar Mod a un valor numero / cadena"));

                    break;
                case BOOLEAN:
                    int tmp = this.datoI.isBooleano() ? 1 : 0;
                    datoResult.setNumero(datoD.getNumero() % tmp);
                    return datoResult;
                case CHAR:
                    int tmpc = this.datoI.getCaracter();
                    datoResult.setNumero(datoD.getNumero() % tmpc);
                    return datoResult;
                case DECIMAL:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((datoD.getNumero() % datoI.getDecimal()) * 1e6));
                    return datoResult;
                default:
                    datoResult.setNumero(datoD.getNumero() % datoI.getNumero());
                    return datoResult;
            }

        }
        this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "no de puede realizar MOD a un valor no numero / otro valor"));
        return datoResult;

    }

    private Expresion operacionPotencia() {
        Expresion datoResult = new Expresion(true, 0, TipoDato.ENTERO);
        if (datoD.getTipoDato() == TipoDato.DECIMAL) {
            switch (this.datoI.getTipoDato()) {
                case CADENA:
                    this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "no de puede realizar Potnecia a un valor numero ^ cadena"));

                    break;
                case BOOLEAN:
                    this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "no de puede realizar Potnecia a un valor numero ^ boolean"));
                    break;
                case CHAR:
                    this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "no de puede realizar Potnecia a un valor numero ^ char"));
                    break;
                case DECIMAL:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round(Math.pow(datoD.getDecimal(), datoI.getDecimal()) * 1e6));
                    return datoResult;
                default:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round(Math.pow(datoD.getDecimal(), datoI.getNumero()) * 1e6));
                    return datoResult;
            }
        }

        if (datoD.getTipoDato() == TipoDato.ENTERO) {
            switch (this.datoI.getTipoDato()) {
                case CADENA:
                    this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "no de puede realizar Potnecia a un valor numero ^ cadena"));

                    break;
                case BOOLEAN:
                    this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "no de puede realizar Potnecia a un valor numero ^ boolean"));
                    break;
                case CHAR:
                    this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "no de puede realizar Potnecia a un valor numero ^ char"));
                    break;
                case DECIMAL:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round(Math.pow(datoD.getNumero(), datoI.getDecimal()) * 1e6));
                    return datoResult;
                default:
                    datoResult.setNumero((int) Math.pow(datoD.getNumero(), datoI.getNumero()));
                    return datoResult;
            }

        }
        this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "no de puede realizar POTENCIA a un valor no numero ^ otro valor"));
        return datoResult;

    }

    
    private String sumaStringDato() {
        switch (this.datoI.getTipoDato()) {
            case CADENA:
                return datoD.getCadena() + datoI.getCadena();
            case BOOLEAN:
                String tmp = datoI.isBooleano() ? "verdadero" : "falso";
                return datoD.getCadena() + tmp;
            case CHAR:
                return datoD.getCadena() + datoI.getCaracter();
            case DECIMAL:
                return datoD.getCadena() + datoI.getCaracter();
            default:
                return datoD.getCadena() + datoI.getNumero();
        }
    }

    private Boolean sumaBooleanDato() {
        //error no se puese sumar un boolean + cualquier dato
        this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "error no se puese sumar un boolean + cualquier dato"));

        return datoD.isBooleano();
    }

    private char sumaCharDatp() {
        //error no se puese dumar un char + cualquier dato
        this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "error no se puese dumar un char + cualquier dato"));

        return datoD.getCaracter();
    }

    private Double sumaDecimalDato() {
        switch (this.datoI.getTipoDato()) {
            case CADENA:
                //error no se puede sumar una decimal con una cadena
                this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "no se puede sumar una decimal con una cadena"));

                return datoD.getDecimal();
            case BOOLEAN:
                double tmp = datoI.isBooleano() ? 1 : 0;
                return datoD.getDecimal() + tmp;
            case CHAR:
                return datoD.getDecimal() + datoI.getCaracter();
            case DECIMAL:
                return datoD.getDecimal() + datoI.getDecimal();
            default:
                return datoD.getDecimal() + datoI.getNumero();
        }
    }

    private int sumaEnteroDato() {
        switch (this.datoI.getTipoDato()) {
            case CADENA:
                //error no se puede sumar una decimal con una cadena
                this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "no se puede sumar una Numero con una cadena"));

                return datoD.getNumero();
            case BOOLEAN:
                int tmp = datoI.isBooleano() ? 1 : 0;
                return datoD.getNumero() + tmp;
            case CHAR:
                return datoD.getNumero() + datoI.getCaracter();
            case DECIMAL:
                int tmps = (int) Math.round(datoI.getDecimal());
                return datoD.getNumero() + tmps;
            default:
                return datoD.getNumero() + datoI.getNumero();
        }
    }

    private boolean datoInicializado() {
        boolean inicializado = true;
        if (tipoOp != TipoOperacion.ISNULO) {
            if (!this.datoD.isInicializado()) {
                //error variable no inicalizada
                this.errorsSemanticos.add(new ErrorSemantico(datoD.getToken(), "Variable no inicializada"));
                inicializado = false;
            }
            if (!this.datoI.isInicializado()) {
                //error variable no inicalizada
                this.errorsSemanticos.add(new ErrorSemantico(datoI.getToken(), "Variable no inicializada"));
                inicializado = false;
            }
        }
        return inicializado;
    }

    public Expresion getDato1() {
        return datoD;
    }

    public void setDato1(Expresion dato1) {
        this.datoD = dato1;
    }

    public Expresion getDato2() {
        return datoI;
    }

    public void setDato2(Expresion dato2) {
        this.datoI = dato2;
    }

    public TipoOperacion getTipoOp() {
        return tipoOp;
    }

    public void setTipoOp(TipoOperacion tipoOp) {
        this.tipoOp = tipoOp;
    }

    public ArrayList<ErrorSemantico> getErrorsSemanticos() {
        return errorsSemanticos;
    }

    public void setErrorsSemanticos(ArrayList<ErrorSemantico> errorsSemanticos) {
        this.errorsSemanticos = errorsSemanticos;
    }

}
