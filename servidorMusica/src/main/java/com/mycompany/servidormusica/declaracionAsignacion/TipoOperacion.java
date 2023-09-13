package com.mycompany.servidormusica.declaracionAsignacion;

import java.io.Serializable;

/**
 *
 * @author andaryus
 */
public enum TipoOperacion implements Serializable {
    SUMA,
    RESTA,
    MULTIPLICACION,
    DIVISION,
    MODULO,
    POTENCIA,
    ISNULO,
    NOT,
    OR,
    AND,
    NAND,
    NOR,
    XOR,
    MAYORQUE,
    MENORQUE,
    MAYOROIGUAL,
    MENOROIGUAL,
    NOTEQUALS,
    EQUALS,
}
