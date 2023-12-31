/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.servidormusica.interfaz;

import com.mycompany.servidormusica.analizadores.LexerLista;
import com.mycompany.servidormusica.analizadores.LexerPista;
import com.mycompany.servidormusica.analizadores.ParserLista;
import com.mycompany.servidormusica.analizadores.parser;
import com.mycompany.servidormusica.archivos.manejoArchivos;
import com.mycompany.servidormusica.funcionUI.ManejadorTablas;
import com.mycompany.servidormusica.funcionUI.NumeroLinea;
import com.mycompany.servidormusica.instrucciones.funciones.FuncionMensaje;
import com.mycompany.servidormusica.instrucciones.music.ManejadorPistaMusical;
import com.mycompany.servidormusica.instrucciones.music.PistaMusical;
import com.mycompany.servidormusica.instrucciones.music.preparadorhiloRepro;
import com.mycompany.servidormusica.manejoErrores.ErrorLexico;
import com.mycompany.servidormusica.manejoErrores.ErrorSemantico;
import com.mycompany.servidormusica.manejoErrores.ErrorSintactico;
import com.mycompany.servidormusica.manejoErrores.ErroresSingleton;
import com.mycompany.servidormusica.pista.Lista;
import com.mycompany.servidormusica.pista.Pista;
import com.mycompany.servidormusica.pista.PistasCompiladas;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 *
 * @author andaryus
 */
public class ventanaPrincipal extends javax.swing.JFrame {

    private parser parser;
    private LexerPista lexer;
    private manejoArchivos manejArchivos;
    ArrayList<Pista> pistasSeleccionadas;
    ArrayList<Lista> listasSeleccionadas;
    preparadorhiloRepro prepararRepro;

    private boolean reproduciendo = false;
    private Lista listaenReproduccion = new Lista();
    private Lista listasEnModificacion = new Lista();
    private boolean yoTeDetube = false;
//    private final ReporLexico reportLex = new ReporLexico();
//    private final ReporteErrorSintactico reportSintactio = new ReporteErrorSintactico();
//    private final ReportErroSemantico reportSemantico = new ReportErroSemantico();
    private final ManejadorTablas manejadorTable = new ManejadorTablas();
//    private PistasActivacion pistaActivacion;
    private ParserLista parserlis;
    private LexerLista lexerLis;
    private final NumeroLinea numeroLineaPista;
    private final NumeroLinea numeroLineListas;
    private boolean VistaPista = true;

    /**
     * Creates new form ventanaPrincipal
     */
    public ventanaPrincipal() {
        initComponents();
        recargarArchivos();
        cargarArchivos();
        this.setExtendedState(this.MAXIMIZED_BOTH);
        this.jButtonReportes.setEnabled(false);
        this.numeroLineaPista = new NumeroLinea(jTextAreaEditorPista);
        this.numeroLineListas = new NumeroLinea(jTextAreaLista);
        jScrollPane1.setRowHeaderView(numeroLineaPista);
        jScrollPane4.setRowHeaderView(numeroLineListas);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButtonReportes = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaEditorPista = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaConsolaPista = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListPistas = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jListListas = new javax.swing.JList<>();
        jLabel5 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButtonReproducirPausar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabelTituloPista = new javax.swing.JLabel();
        jButtonEliminar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextAreaLista = new javax.swing.JTextArea();
        jButtonComplarLista = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel3.setLayout(new java.awt.BorderLayout());

        jButton1.setText("Compilar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1);

        jButtonReportes.setText("Ver Errores");
        jButtonReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReportesActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonReportes);

        jPanel3.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextAreaEditorPista.setColumns(20);
        jTextAreaEditorPista.setRows(5);
        jScrollPane1.setViewportView(jTextAreaEditorPista);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 910, 310));

        jTextAreaConsolaPista.setColumns(20);
        jTextAreaConsolaPista.setRows(5);
        jScrollPane2.setViewportView(jTextAreaConsolaPista);

        jPanel5.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 420, 910, 110));

        jLabel1.setText("Consola de salida");
        jPanel5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, -1, -1));

        jLabel2.setText("Editor");
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));

        jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Editor", jPanel3);

        jListPistas.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jListPistas);

        jLabel3.setText("Pistas");

        jListListas.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(jListListas);

        jLabel5.setText("Listas");

        jButton5.setText("Seleccionar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton7.setText("Ver pistas");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButtonReproducirPausar.setText("Reproducir/Pausar");
        jButtonReproducirPausar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReproducirPausarActionPerformed(evt);
            }
        });

        jLabel6.setText("Reproduccion Actual");

        jLabelTituloPista.setText("   ");

        jButtonEliminar.setText("Eliminar");
        jButtonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarActionPerformed(evt);
            }
        });

        jButton2.setText("Editar");

        jButton6.setText("Eliminar Lista");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 248, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton6)
                                        .addGap(46, 46, 46)
                                        .addComponent(jButton5))
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addComponent(jLabel6))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(38, 38, 38)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabelTituloPista)
                                            .addComponent(jButtonReproducirPausar)))))
                            .addComponent(jLabel5)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton7)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonEliminar)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)))
                .addGap(202, 202, 202))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(17, 17, 17)
                                .addComponent(jLabelTituloPista)
                                .addGap(26, 26, 26)
                                .addComponent(jButtonReproducirPausar)
                                .addGap(133, 133, 133))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5)
                            .addComponent(jButton7)
                            .addComponent(jButtonEliminar)
                            .addComponent(jButton2)
                            .addComponent(jButton6)))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(221, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Biblioteca", jPanel1);

        jTextAreaLista.setColumns(20);
        jTextAreaLista.setRows(5);
        jScrollPane4.setViewportView(jTextAreaLista);

        jButtonComplarLista.setText("Compilar");
        jButtonComplarLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonComplarListaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonComplarLista)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 816, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(215, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                .addComponent(jButtonComplarLista)
                .addGap(66, 66, 66))
        );

        jTabbedPane1.addTab("Editor Listas", jPanel6);

        getContentPane().add(jTabbedPane1);

        jMenu1.setText("File");

        jMenuItem1.setText("Crear Pista");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void recargarArchivos() {
        manejoArchivos archivos = new manejoArchivos();
        ArrayList<Pista> pistas = archivos.LeerPistas("Pistas.bin");
        ArrayList<Lista> listas = archivos.LeerListas("Listas.bin");
        PistasCompiladas.getInstancePistasActivacion().setPistas(pistas);
        PistasCompiladas.getInstancePistasActivacion().setListas(listas);
    }


    private void jButtonReproducirPausarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReproducirPausarActionPerformed
        // TODO add your handling code here:

        int pistaSel = jListPistas.getSelectedIndex();

        Pista p1 = this.pistasSeleccionadas.get(pistaSel);

        PistaMusical pistaM = p1.getPistaMusical();

        this.prepararRepro = new preparadorhiloRepro();

        this.prepararRepro.prepararReproduccion(pistaM);

    }//GEN-LAST:event_jButtonReproducirPausarActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        this.VistaPista = true;
        cargarArchivos();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        int select = jListListas.getSelectedIndex();

        mostrarPistasDeLaLista(jListPistas, select);
        this.VistaPista = false;
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButtonReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReportesActionPerformed
        // if (ErroresSingleton.getInstance().getErroresLexicos().isEmpty()) {
        // } else {
        reporteErrores errs = new reporteErrores();

        errs.setVisible(true);
        errs.mostrarErroes(manejadorTable);

        //  }
    }//GEN-LAST:event_jButtonReportesActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        Reader reader = new StringReader(jTextAreaEditorPista.getText());
        this.lexer = new LexerPista(reader);
        this.parser = new parser(lexer);
        FuncionMensaje.getInstanceMensajes().getMensajes().clear();
        ManejadorPistaMusical.getPistaMusical().getCanales().clear();
        ErroresSingleton.getInstance().clear();
        try {
            parser.parse();
            ErroresSingleton.getInstance().getErroresSemanticos().addAll(parser.getErrorsSemanticos());
            this.jTextAreaConsolaPista.setText("");
            if (ErroresSingleton.getInstance().existenErrores(jTextAreaConsolaPista)) {
                this.textoSalida();
                parser.getPista().setCodigoFuente(this.jTextAreaEditorPista.getText());
                parser.getPista().autoguardar();
                PistasCompiladas.getInstancePistasActivacion().guardarEnBinario();
                cargarArchivos();
                JOptionPane.showMessageDialog(null, "Compilado correcto");

            } else {
                JOptionPane.showMessageDialog(null, "No se logro compilar , revise el reporte de Errores  ");
                this.jButtonReportes.setEnabled(true);
                reporteErrores errs = new reporteErrores();

                errs.setVisible(true);
                errs.mostrarErroes(manejadorTable);

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se logro compilar , revise el reporte de Errores  ");
            ErroresSingleton.getInstance().getErroresSemanticos().addAll(parser.getErrorsSemanticos());
            this.jButtonReportes.setEnabled(true);
            reporteErrores errs = new reporteErrores();

            errs.setVisible(true);
            errs.mostrarErroes(manejadorTable);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButtonComplarListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonComplarListaActionPerformed

        ErroresSingleton.getInstance().clear();
        Reader reader = new StringReader(jTextAreaLista.getText());
        this.lexerLis = new LexerLista(reader);
        this.parserlis = new ParserLista(lexerLis);
        try {
            parserlis.parse();
            if (ErroresSingleton.getInstance().existenErrores(jTextAreaConsolaPista)) {
                PistasCompiladas.getInstancePistasActivacion().guardarNuevaLista(parserlis.getLista());
                parserlis.getLista().clear();
                if (ErroresSingleton.getInstance().existenErrores(jTextAreaConsolaPista)) {
                    JOptionPane.showMessageDialog(null, "Compilacion exitosa");
                    recargarArchivos();
                    cargarArchivos();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Compilacion no Exitosa, revisar los detalles de los reportes de errores");
                this.jButtonReportes.setEnabled(true);
                reporteErrores errs = new reporteErrores();

                errs.setVisible(true);
                errs.mostrarErroes(manejadorTable);

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Compilacion no Exitosa, revisar los detalles de los reportes de errores");
            this.jTextAreaConsolaPista.setText("Compilacion no Exitosa, revisar los detalles de los reportes de errores");
            this.jButtonReportes.setEnabled(true);
            reporteErrores errs = new reporteErrores();

            errs.setVisible(true);
            errs.mostrarErroes(manejadorTable);
        }
    }//GEN-LAST:event_jButtonComplarListaActionPerformed

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        // TODO add your handling code here:

        int seleccion = jListPistas.getSelectedIndex();

        this.manejArchivos = new manejoArchivos();

        if (this.VistaPista) {
            int respuesta = JOptionPane.showConfirmDialog(null, "Seguro de eliminar la pista?", "Confirmación", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {

                this.pistasSeleccionadas.remove(seleccion);

                this.manejArchivos.guardarPistas("Pistas.bin", pistasSeleccionadas);
                recargarArchivos();
                cargarArchivos();
            }
        } else {
            int respuesta = JOptionPane.showConfirmDialog(null, "Seguro de eliminar la pista de la lista?", "Confirmación", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {

                int se2 = jListListas.getSelectedIndex();

                this.listasSeleccionadas.get(se2).getListasMusicales().remove(seleccion);

                this.manejArchivos.guardarListasPistas("Listas.bin", listasSeleccionadas);
                recargarArchivos();
                cargarArchivos();
            }
        }

    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        this.manejArchivos = new manejoArchivos();
        int respuesta = JOptionPane.showConfirmDialog(null, "Seguro de eliminar la Lista", "Confirmación", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (respuesta == JOptionPane.YES_OPTION) {
            int seleccion = jListListas.getSelectedIndex();
            this.listasSeleccionadas.remove(seleccion);

            this.manejArchivos.guardarListasPistas("Listas.bin", listasSeleccionadas);
            recargarArchivos();
            cargarArchivos();
        }

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        jTextAreaEditorPista.setText("");
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    public void textoSalida() {
        String contenido = FuncionMensaje.getInstanceMensajes().getContenido();
        this.jTextAreaConsolaPista.setText(contenido);
    }

    public void cargarArchivos() {
        mostrarListasJlist(jListListas);
        mostrarPistasExistentes(jListPistas);
    }

    public void mostrarListasJlist(JList<String> jlist) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        jlist.setModel(listModel);
        ArrayList<Lista> lista = PistasCompiladas.getInstancePistasActivacion().getListas();
        this.listasSeleccionadas = lista;
        lista.forEach(lsi -> {
            listModel.addElement(lsi.getNombre());
        });
    }

    public void mostrarPistasExistentes(JList<String> jlist) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        jlist.setModel(listModel);

        ArrayList<Pista> pistas = readPistasActivacion("Pistas.bin");
        //    System.out.println("NOmbres pistas");
        for (Pista pista : pistas) {
            System.out.println(pista.getNombre());
            System.out.println(pista.getPistaMusical().getNombre());
        }

        this.pistasSeleccionadas = pistas;
        pistas.forEach(lsi -> {
            listModel.addElement(String.format(" %-15s %15s",
                    lsi.getNombre(),
                    "miliSeg: " + lsi.getPistaMusical().getMilisTotal()));

        });
//        if (!PistasCompiladas.getInstancePistasActivacion().getListas().isEmpty()) {
//            Lista lista = PistasCompiladas.getInstancePistasActivacion().getListas().get(0);
//            lista.getListasMusicales().forEach(lsi -> {
//                listModel.addElement(lsi.getNombre());
//            });
//        }
    }

    public ArrayList<Pista> readPistasActivacion(String nombreArchivo) {
        ArrayList<Pista> pistas = new ArrayList<>();
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            //validacion de archivo
            return pistas;
        }
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            pistas = (ArrayList<Pista>) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error la lectura del archivo Binario, de las Pistas :(");
        }
        return pistas;
    }

    public Lista mostrarPistasDeLaLista(JList<String> jlist, int index) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        jlist.setModel(listModel);
        Lista lista = PistasCompiladas.getInstancePistasActivacion().getListas().get(index);

        lista.getListasMusicales().forEach(lsi -> {
            System.out.println("nombres" + lsi.getNombre());
            listModel.addElement(String.format("%-15s %-15s %15s",
                    lsi.getNombre(),
                    "seg: " + convertirSegundos(lsi.getMilisTotal()),
                    "milis: " + lsi.getMilisTotal()));
        });
        return lista;
    }

    private double convertirSegundos(int milis) {
        double val = milis / 1000;
        return (double) (Math.round(val * 100.00) / 100.00);
    }

    public void imprimirErrores() {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButtonComplarLista;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonReportes;
    private javax.swing.JButton jButtonReproducirPausar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelTituloPista;
    private javax.swing.JList<String> jListListas;
    private javax.swing.JList<String> jListPistas;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextAreaConsolaPista;
    private javax.swing.JTextArea jTextAreaEditorPista;
    private javax.swing.JTextArea jTextAreaLista;
    // End of variables declaration//GEN-END:variables
}
