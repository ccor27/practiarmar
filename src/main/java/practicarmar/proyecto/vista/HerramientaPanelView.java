/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package practicarmar.proyecto.vista;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import practiarmar.proyecto.controlador.HerramientaPanelViewController;
import practiarmar.proyecto.modelo.Herramienta;
import practiarmar.proyecto.persistence.exceptions.NonexistentEntityException;
/**
 *
 * @author crist
 */
public class HerramientaPanelView extends javax.swing.JPanel {

    /**
     * Creates new form HerramientaPanelView
     */
    private MenuPrincipal menu;
    private HerramientaPanelViewController controller;
    private DefaultTableModel model;
    private List<Herramienta> herramientas;
    private Herramienta herramientaSeleccionada=null;
    
    public HerramientaPanelView(MenuPrincipal menu) {
        System.out.println("HerramientaPanelView debbug before call the controller...");
        this.menu=menu;
        this.controller=new HerramientaPanelViewController();
        System.out.println("HerramientaPanelView debbug after call the contrller...");
        initComponents();
        model = (DefaultTableModel) tblHerramientas.getModel();
        llenarTablaHerramientas();
    }

    private void llenarTablaHerramientas(){
        model.setRowCount(0);
        obtenerHerramientas();
        Object rowData[] = new Object[5];
        for (Herramienta herramienta : herramientas) {
            rowData[0] = herramienta.getId();
            rowData[1] = herramienta.getNombre();
            rowData[2] = herramienta.getCategoria().toString();
            model.addRow(rowData);
        }
    }
    private void obtenerHerramientas(){
        this.herramientas=controller.obtenerHerramientas();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        txtFiltrar = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHerramientas = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnVer = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(744, 550));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Herramientas");

        jLabel2.setText("Filtrar");

        txtFiltrar.setBorder(null);
        txtFiltrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFiltrarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFiltrarKeyTyped(evt);
            }
        });

        tblHerramientas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Categoria"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblHerramientas.setShowHorizontalLines(true);
        tblHerramientas.setShowVerticalLines(true);
        tblHerramientas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHerramientasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHerramientas);

        btnAgregar.setIcon(new javax.swing.ImageIcon("C:\\Users\\crist\\OneDrive\\Documentos\\NetBeansProjects\\practiarmar\\src\\main\\resources\\agregar.png")); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon("C:\\Users\\crist\\OneDrive\\Documentos\\NetBeansProjects\\practiarmar\\src\\main\\resources\\editar.png")); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnVer.setIcon(new javax.swing.ImageIcon("C:\\Users\\crist\\OneDrive\\Documentos\\NetBeansProjects\\practiarmar\\src\\main\\resources\\ver.png")); // NOI18N
        btnVer.setText("Ver");
        btnVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon("C:\\Users\\crist\\OneDrive\\Documentos\\NetBeansProjects\\practiarmar\\src\\main\\resources\\eliminar.png")); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                        .addGap(578, 578, 578)))
                .addGap(14, 14, 14))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txtFiltrar)
                .addGap(244, 244, 244))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jSeparator2)
                .addGap(244, 244, 244))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1)
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(54, 54, 54))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jSeparator1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(8, 8, 8)))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(4, 4, 4))
                    .addComponent(txtFiltrar))
                .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 10, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(40, 40, 40)
                        .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(40, 40, 40)
                        .addComponent(btnVer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(40, 40, 40)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(87, 87, 87)))
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtFiltrarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltrarKeyReleased
        String filtro = txtFiltrar.getText();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);
        tblHerramientas.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(filtro));
    }//GEN-LAST:event_txtFiltrarKeyReleased

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        menu.showHerramientaAgregar();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if(herramientaSeleccionada!=null){
            menu.showHerramientaEditar(herramientaSeleccionada);
            
        }else{
            JOptionPane.showMessageDialog(null, "Debe seleccionar una herramienta!");
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerActionPerformed
          if(herramientaSeleccionada!=null){
            menu.showHerramientaVer(herramientaSeleccionada);
        }else{
            JOptionPane.showMessageDialog(null, "Debe seleccionar una herramienta!");
        }
    }//GEN-LAST:event_btnVerActionPerformed
/**
 * metodo que solo permite el ingreso de números y letras en le campo de filtro
 * @param evt 
 */
    private void txtFiltrarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltrarKeyTyped
             char c = evt.getKeyChar();
    if (!Character.isLetterOrDigit(c)) {
        evt.consume();
    }
    }//GEN-LAST:event_txtFiltrarKeyTyped

    private void tblHerramientasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHerramientasMouseClicked
        Long id = (Long) model.getDataVector().elementAt(tblHerramientas.convertRowIndexToModel(tblHerramientas.getSelectedRow())).get(0);
        for (Herramienta herramienta : herramientas) {
             if(herramienta.getId().equals(id)){
                 herramientaSeleccionada=herramienta;
                 System.out.println("herramienta seleccionada heramientaPanelView tabla");
                 break;
             }
        }
    }//GEN-LAST:event_tblHerramientasMouseClicked

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
         if(herramientaSeleccionada!=null){
             try {
                 controller.removeHerramienta(herramientaSeleccionada.getId());
             } catch (NonexistentEntityException ex) {
                 Logger.getLogger(HerramientaPanelView.class.getName()).log(Level.SEVERE, null, ex);
                 JOptionPane.showMessageDialog(this, "Ocurrio un error al intentar eliminar la herramienta!\n"+ex.getLocalizedMessage());
             }
            JOptionPane.showMessageDialog(this, "Herramienta eliminada con exito!");
            llenarTablaHerramientas();
        }else{
            JOptionPane.showMessageDialog(null, "Debe seleccionar una herramienta!");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnVer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable tblHerramientas;
    private javax.swing.JTextField txtFiltrar;
    // End of variables declaration//GEN-END:variables
}
