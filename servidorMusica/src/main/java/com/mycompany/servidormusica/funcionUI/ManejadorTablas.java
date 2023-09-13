package com.mycompany.servidormusica.funcionUI;

import com.mycompany.servidormusica.manejoErrores.ErroresSingleton;
import com.mycompany.servidormusica.pista.PistasCompiladas;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author andaryus
 */
public class ManejadorTablas {

    public void tablaErroresLexico(JTable table) {
        DefaultTableModel modelo = new DefaultTableModel();
        table.setModel(modelo);

        modelo.addColumn("Lexema");
        modelo.addColumn("Linea");
        modelo.addColumn("Columna");
        modelo.addColumn("Descripcion");
        ErroresSingleton.getInstance().getErroresLexicos().forEach(error -> {
            modelo.addRow(new Object[]{error.getToken().getLexeme(), error.getToken().getLine(), error.getToken().getColumn(), error.getDescripcion()});
        });

        this.centrarContendio(table);

        int[] anchos = {60, 60, 60, 325};
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

    }

    public void tablaErroresSintactico(JTable table) {
        DefaultTableModel modelo = new DefaultTableModel();
        table.setModel(modelo);
        modelo.addColumn("Lexema");
        modelo.addColumn("Linea");
        modelo.addColumn("Columna");
        modelo.addColumn("Descripcion");

        ErroresSingleton.getInstance().getErroresSintacticos().forEach(error -> {
            modelo.addRow(new Object[]{error.getToken().getLexeme(), error.getToken().getLine(), error.getToken().getColumn(), error.getDescripcion()});
        });

        this.centrarContendio(table);

        int[] anchos = {60, 60, 60, 400};
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

    }

    public void tablaErroresSemanticos(JTable table) {
        DefaultTableModel modelo = new DefaultTableModel();
        table.setModel(modelo);
        modelo.addColumn("Lexema");
        modelo.addColumn("Linea");
        modelo.addColumn("Columna");
        modelo.addColumn("Descripcion");

        ErroresSingleton.getInstance().getErroresSemanticos().forEach(error -> {
            modelo.addRow(new Object[]{error.getToken().getLexeme(), error.getToken().getLine(), error.getToken().getColumn(), error.getDescripcion()});
        });

        this.centrarContendio(table);

        int[] anchos = {60, 60, 60, 400};
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

    }

    public void mostarPistasActivacion(JTable table) {
        DefaultTableModel modelo = new DefaultTableModel();
        table.setModel(modelo);
        //Anadir columnas
        modelo.addColumn("NOMBRE");
        modelo.addColumn("VAR GLOBALES");
        modelo.addColumn("ARREGLOS GLOBALES");
        modelo.addColumn("FUNCIONES");

        PistasCompiladas.getInstancePistasActivacion().getPistas().forEach(pista -> {
            modelo.addRow(new Object[]{pista.getNombre(), pista.getTableSimbolGoblal().getVariables().size(), pista.getTableSimbolGoblal().getArreglos().size(), pista.getFunciones().size()});
        });

        this.centrarContendio(table);

        int[] anchos = {250, 75, 75, 75};
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }

    private void centrarContendio(JTable table) {
        // Crear un renderizador personalizado para centrar los contenidos
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Aplicar el renderizador centrado a todas las columnas de la tabla
        int columnCount = table.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

}
