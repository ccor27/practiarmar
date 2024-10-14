/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package practicarmar.proyecto.vista;

import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import practiarmar.proyecto.controlador.ProyectoAgregarEditarViewController;
import practiarmar.proyecto.modelo.Item;
import practiarmar.proyecto.modelo.Empleado;
import practiarmar.proyecto.modelo.Cliente;
import practiarmar.proyecto.modelo.EstadoProyecto;
import practiarmar.proyecto.modelo.Foto;
import practiarmar.proyecto.modelo.Proyecto;
import practiarmar.proyecto.persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author crist
 */
public class ProyectoAgregarEditarView extends javax.swing.JPanel {

    /**
     * Creates new form ProyectoAgregarView
     */
    private final ProyectoAgregarEditarViewController controller;
    private List<Item> items;
    private Item itemSeleccionado;
    private Empleado empleadoDisponibleSeleccionado;
    private Empleado empleadoProyectoSeleccionado;
    private List<Empleado> empleadosProyecto;
    private List<Empleado> empleadosDisponibles;
    private List<Cliente> clientes;
    private Cliente clienteSeleccionado;
    private DecimalFormat decimalFormat;
    private DefaultTableModel modelItemTable;
    private DefaultTableModel modelEmpleadosDisponiblesTable;
    private DefaultTableModel modelEmpleadosProyectoTable;
    private DefaultTableModel modelFotosProyectoTable;
    private double precioMateriales = 0.0;
    private List<Foto> fotosProyecto;
    private EstadoProyecto estadoProyecto;
    private Date fechaEntrega;
    private MenuPrincipal menu;
    private boolean esUnNuevoProyecto;
    private Proyecto proyectoAEditar;

    public ProyectoAgregarEditarView(MenuPrincipal menu, boolean esUnNuevoProyecto) {
        this.menu = menu;
        this.esUnNuevoProyecto = esUnNuevoProyecto;
        controller = new ProyectoAgregarEditarViewController();
        decimalFormat = new DecimalFormat("#,##0");
        fotosProyecto = new ArrayList<>();
        items = new ArrayList<>();
        empleadosProyecto = new ArrayList<>();
        itemSeleccionado = null;
        empleadoDisponibleSeleccionado = null;
        empleadoProyectoSeleccionado = null;
        clienteSeleccionado = null;
        fechaEntrega = null;
        proyectoAEditar = null;
        initComponents();
        jformattextPagoEmpleado.setText("0");
        modelItemTable = (DefaultTableModel) tblItems.getModel();
        modelEmpleadosDisponiblesTable = (DefaultTableModel) tblListEmpleado.getModel();
        modelEmpleadosProyectoTable = (DefaultTableModel) tblEmpleadosProyecto.getModel();
        modelFotosProyectoTable = (DefaultTableModel) tblFotos.getModel();
        jformatGroup();
        rellenarTablaEmpleados();
        rellenarComboCliente();
    }

    public void setProyectoAEditar(Proyecto proyecto) {
        this.proyectoAEditar = proyecto;
    }

    public void saberSiEsEditarOAgregar() {
        if (esUnNuevoProyecto) {
            jlabelHeader.setText("Crear un proyecto");
            jformattextPagoEmpleado.setEnabled(false);//se deshabilita, hasta que el usuario agregue un empleado
            //Todo queda igual
        } else {

            jlabelHeader.setText("Editar un proyecto");
            btnLimpiar.setEnabled(false);
            //Se setean los datos del proyecto a editar
            // set items
            items = proyectoAEditar.getItems();
            rellenarTablaItems();
            // set empleados, si los hay
            if (!proyectoAEditar.getEmpleados().isEmpty()) {
                empleadosProyecto.addAll(proyectoAEditar.getEmpleados());
                System.out.println("empleado de la lista " + empleadosProyecto.get(0).getId() + " " + empleadosProyecto.get(0).getNombre());
                rellenarTableEmpleadosProyecto();
                jformattextPagoEmpleado.setText(decimalFormat.format(proyectoAEditar.getPrecioPorEmpleado()));
            }
            // set los datos
            txtIdProyecto.setText(String.valueOf(proyectoAEditar.getId()));
            jformattextManoDeObra.setText(decimalFormat.format(proyectoAEditar.getPrecioManoDeObra()));
            jformattextPrecioMateriales.setText(decimalFormat.format(proyectoAEditar.getPrecioMateriales()));
            jformatTotal.setText(decimalFormat.format(proyectoAEditar.getPrecioTotal()));
            dateEntrega.setDate(Date.from(proyectoAEditar.getFechaEntrega().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            dateUltimaModificacion.setDate(Date.from(proyectoAEditar.getFehcaModificacion().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            descripcionProyectoTextArea.setText(proyectoAEditar.getDescripcion());
            // set el cliente
            setClienteProyecto(proyectoAEditar.getCliente());
            //set el estado
            setEstadoProyecto(proyectoAEditar.getEstadoProyecto());
            // set las fotos
            if (!proyectoAEditar.getFotos().isEmpty()) {
                fotosProyecto = proyectoAEditar.getFotos();
                rellenarTablaFotos();
            }
        }

    }

    //TODO: revisar este metodo, no creo que funcione
    private void setClienteProyecto(Cliente cliente) {
        comboClientes.setSelectedItem(cliente.getNombre());
        comboClientes.setEnabled(false);
    }

    private void setEstadoProyecto(EstadoProyecto estado) {
        switch (estado) {
            case EN_CURSO:
                comboEstado.setSelectedIndex(1);
                break;
            case FINALIZADO:
                comboEstado.setSelectedIndex(2);
                break;
            case SIN_EMPEZAR:
                comboEstado.setSelectedIndex(3);
                break;
            case ESPERA_DE_PAGO:
                comboEstado.setSelectedIndex(4);
                break;
            case CANCELADO:
                comboEstado.setSelectedIndex(5);
                break;
            default:
                throw new AssertionError();
        }
    }

    public void rellenarTablaItems() {
        modelItemTable.setRowCount(0);
        if (items != null && !items.isEmpty()) {
            Object rowData[] = new Object[3];
            for (Item item : items) {
                rowData[0] = item.getNombre();
                rowData[1] = item.getPrecioPorUnidad();
                rowData[2] = item.getCantidad();
                modelItemTable.addRow(rowData);
            }
        }

    }

    private void rellenarTablaEmpleados() {
        modelEmpleadosDisponiblesTable.setRowCount(0);
        obtenerEmpleados();
        if (empleadosDisponibles != null && !empleadosDisponibles.isEmpty()) {
            Object rowData[] = new Object[2];
            for (Empleado empleado : empleadosDisponibles) {
                rowData[0] = empleado.getNombre();
                rowData[1] = empleado.getCodigo();
                modelEmpleadosDisponiblesTable.addRow(rowData);
            }
        }
    }

    private void obtenerEmpleados() {
        this.empleadosDisponibles = controller.obtenerEmpleados();
    }

    private void rellenarComboCliente() {
        this.clientes = controller.obtenerClientes();
        comboClientes.addItem("Seleccione");
        for (Cliente c : clientes) {
            comboClientes.addItem(c.getNombre());
        }
    }

    private void rellenarTableEmpleadosProyecto() {
        modelEmpleadosProyectoTable.setRowCount(0);
        if (empleadosProyecto != null && !empleadosProyecto.isEmpty()) {
            Object rowData[] = new Object[2];
            for (Empleado empleado : empleadosProyecto) {
                rowData[0] = empleado.getNombre();
                rowData[1] = empleado.getCodigo();
                modelEmpleadosProyectoTable.addRow(rowData);
            }

        }
    }

    private void rellenarTablaFotos() {
        modelFotosProyectoTable.setRowCount(0);
        if (fotosProyecto != null && !fotosProyecto.isEmpty()) {
            Object rowData[] = new Object[1];
            for (Foto foto : fotosProyecto) {
                rowData[0] = foto.getNombre();
                modelFotosProyectoTable.addRow(rowData);
            }
        }
    }

    /**
     * Metodo para hacer un grupo de los jformat y asi cada vez que se altere el
     * valor en cada uno, se vea reflejado en el precio total
     */
    private void jformatGroup() {
        for (JFormattedTextField field
                : Arrays.asList(jformattextManoDeObra, jformattextPagoEmpleado, jformattextPrecioMateriales)) {
            field.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    actualizarPrecioTotal();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    actualizarPrecioTotal();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    actualizarPrecioTotal();
                }
            });
        }
    }

    private void actualizarPrecioTotal() {
        double precioEmpleado
                = jformattextPagoEmpleado.getText().equals("") ? 0 : Double.parseDouble(jformattextPagoEmpleado.getText().replaceAll("\\.", ""));
        double precioManoDeObra
                = jformattextManoDeObra.getText().equals("") ? 0 : Double.parseDouble(jformattextManoDeObra.getText().replaceAll("\\.", ""));
        int numEmpleados = empleadosProyecto.size();
        //no afecta nada si el numero de empleados es cero, ya que si es cierto, el precioEmpledo tambien lo es
        double precio = (precioEmpleado * numEmpleados) + precioManoDeObra + precioMateriales;
        jformatTotal.setText(String.valueOf(precio));
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
        jlabelHeader = new javax.swing.JLabel();
        DatosTabbedPane = new javax.swing.JTabbedPane();
        itemsPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblItems = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtNombreItem = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCantidadItem = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcionItem = new javax.swing.JTextArea();
        btnAgregarItem = new javax.swing.JButton();
        btnEliminarItem = new javax.swing.JButton();
        jformattextPrecioPorUnidadItem = new javax.swing.JFormattedTextField(decimalFormat);
        empleadosPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblListEmpleado = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblEmpleadosProyecto = new javax.swing.JTable();
        btnAgregarEmpleado = new javax.swing.JButton();
        btnEliminarEmpleado = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        datos = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        comboClientes = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtIdProyecto = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        comboEstado = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        dateEntrega = new com.toedter.calendar.JDateChooser();
        jLabel17 = new javax.swing.JLabel();
        dateUltimaModificacion = new com.toedter.calendar.JDateChooser();
        jformattextManoDeObra = new javax.swing.JFormattedTextField(decimalFormat);
        jformattextPagoEmpleado = new javax.swing.JFormattedTextField(decimalFormat);
        jformattextPrecioMateriales = new javax.swing.JFormattedTextField(decimalFormat);
        jformatTotal = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        descripcionProyectoTextArea = new javax.swing.JTextArea();
        fotosPanel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblFotos = new javax.swing.JTable();
        labelImagen = new javax.swing.JLabel();
        btnAgregarFoto = new javax.swing.JButton();
        btnEliminarFoto = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(1150, 704));
        setMinimumSize(new java.awt.Dimension(1150, 704));
        setPreferredSize(new java.awt.Dimension(1150, 704));

        jPanel1.setMaximumSize(new java.awt.Dimension(1150, 704));
        jPanel1.setMinimumSize(new java.awt.Dimension(1150, 704));
        jPanel1.setPreferredSize(new java.awt.Dimension(1150, 704));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlabelHeader.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jPanel1.add(jlabelHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 303, 41));

        itemsPanel.setMaximumSize(new java.awt.Dimension(870, 340));
        itemsPanel.setPreferredSize(new java.awt.Dimension(860, 389));
        itemsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Precio unidad", "Cantidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblItems.setShowHorizontalLines(true);
        tblItems.setShowVerticalLines(true);
        tblItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblItemsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblItems);

        itemsPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 610, 338));

        jLabel2.setText("Nombre");
        itemsPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 70, 90, -1));

        txtNombreItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreItemActionPerformed(evt);
            }
        });
        itemsPanel.add(txtNombreItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(783, 70, 330, -1));

        jLabel3.setText("Cantidad");
        itemsPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 120, 100, -1));

        txtCantidadItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadItemKeyTyped(evt);
            }
        });
        itemsPanel.add(txtCantidadItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(783, 120, 330, -1));

        jLabel4.setText("Precio  por unidad");
        itemsPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 170, 110, -1));

        jLabel5.setText("Descripcion");
        itemsPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 230, 110, -1));

        txtDescripcionItem.setColumns(20);
        txtDescripcionItem.setLineWrap(true);
        txtDescripcionItem.setRows(5);
        jScrollPane2.setViewportView(txtDescripcionItem);

        itemsPanel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(784, 230, 330, 140));

        btnAgregarItem.setIcon(new javax.swing.ImageIcon("C:\\Users\\crist\\OneDrive\\Documentos\\NetBeansProjects\\practiarmar\\src\\main\\resources\\agregar.png")); // NOI18N
        btnAgregarItem.setText("Agregar");
        btnAgregarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarItemActionPerformed(evt);
            }
        });
        itemsPanel.add(btnAgregarItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 430, 117, 42));

        btnEliminarItem.setIcon(new javax.swing.ImageIcon("C:\\Users\\crist\\OneDrive\\Documentos\\NetBeansProjects\\practiarmar\\src\\main\\resources\\eliminar.png")); // NOI18N
        btnEliminarItem.setText("Eliminar");
        btnEliminarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarItemActionPerformed(evt);
            }
        });
        itemsPanel.add(btnEliminarItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 430, 117, 41));

        jformattextPrecioPorUnidadItem.setColumns(10);
        jformattextPrecioPorUnidadItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jformattextPrecioPorUnidadItemKeyTyped(evt);
            }
        });
        itemsPanel.add(jformattextPrecioPorUnidadItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(784, 170, 330, -1));

        DatosTabbedPane.addTab("Items", itemsPanel);

        empleadosPanel.setPreferredSize(new java.awt.Dimension(860, 389));
        empleadosPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblListEmpleado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Codigo"
            }
        ));
        tblListEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListEmpleadoMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblListEmpleado);

        empleadosPanel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 420, 420));

        tblEmpleadosProyecto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Codigo"
            }
        ));
        tblEmpleadosProyecto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEmpleadosProyectoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblEmpleadosProyecto);

        empleadosPanel.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 60, 420, 420));

        btnAgregarEmpleado.setIcon(new javax.swing.ImageIcon("C:\\Users\\crist\\OneDrive\\Documentos\\NetBeansProjects\\practiarmar\\src\\main\\resources\\flechaDerecha.png")); // NOI18N
        btnAgregarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarEmpleadoActionPerformed(evt);
            }
        });
        empleadosPanel.add(btnAgregarEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 180, 90, 50));

        btnEliminarEmpleado.setIcon(new javax.swing.ImageIcon("C:\\Users\\crist\\OneDrive\\Documentos\\NetBeansProjects\\practiarmar\\src\\main\\resources\\flechaIzquierda.png")); // NOI18N
        btnEliminarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarEmpleadoActionPerformed(evt);
            }
        });
        empleadosPanel.add(btnEliminarEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 260, 90, 50));

        jLabel11.setText("Empleados disponibles");
        empleadosPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, -1));

        jLabel12.setText("Empleados del proyecto");
        empleadosPanel.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 30, -1, -1));
        empleadosPanel.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 420, -1));
        empleadosPanel.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 50, 420, 10));

        DatosTabbedPane.addTab("Empleados", empleadosPanel);

        datos.setPreferredSize(new java.awt.Dimension(860, 389));
        datos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setText("Cliente");
        datos.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 60, -1, -1));

        comboClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboClientesActionPerformed(evt);
            }
        });
        datos.add(comboClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 60, 270, -1));

        jLabel7.setText("Mano de obra");
        datos.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, -1, -1));

        jLabel9.setText("Pago empleado");
        datos.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, -1, -1));

        jLabel8.setText("Precio materiales");
        datos.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, -1, -1));

        jLabel10.setText("Total ");
        datos.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 350, -1, -1));

        jLabel13.setText("Id");
        datos.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 65, -1, -1));

        txtIdProyecto.setEditable(false);
        datos.add(txtIdProyecto, new org.netbeans.lib.awtextra.AbsoluteConstraints(196, 62, 280, -1));

        jLabel15.setText("Estado");
        datos.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 130, -1, -1));

        comboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione estado", "EN CURSO", "FINALIZADO", "SIN EMPEZAR", "ESPERA DE PAGO", "CANCELADO" }));
        comboEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEstadoActionPerformed(evt);
            }
        });
        datos.add(comboEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 130, 280, -1));

        jLabel16.setText("Fecha entrega");
        datos.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 200, -1, -1));
        datos.add(dateEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 190, 280, -1));

        jLabel17.setText("Fecha ultima modificacion");
        datos.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 260, -1, -1));

        dateUltimaModificacion.setEnabled(false);
        datos.add(dateUltimaModificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 260, 280, -1));

        jformattextManoDeObra.setColumns(10);
        jformattextManoDeObra.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jformattextManoDeObra.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
        jformattextManoDeObra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jformattextManoDeObraKeyTyped(evt);
            }
        });
        datos.add(jformattextManoDeObra, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 130, 280, -1));

        jformattextPagoEmpleado.setColumns(10);
        jformattextPagoEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jformattextPagoEmpleadoKeyTyped(evt);
            }
        });
        datos.add(jformattextPagoEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, 280, -1));

        jformattextPrecioMateriales.setEditable(false);
        jformattextPrecioMateriales.setColumns(10);
        jformattextPrecioMateriales.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        datos.add(jformattextPrecioMateriales, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 280, -1));

        jformatTotal.setEditable(false);
        jformatTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jformatTotalActionPerformed(evt);
            }
        });
        datos.add(jformatTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 340, 280, -1));

        jLabel14.setText("Descripcion");
        datos.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 330, -1, -1));

        descripcionProyectoTextArea.setColumns(20);
        descripcionProyectoTextArea.setLineWrap(true);
        descripcionProyectoTextArea.setRows(5);
        descripcionProyectoTextArea.setWrapStyleWord(true);
        jScrollPane6.setViewportView(descripcionProyectoTextArea);

        datos.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 320, 280, 150));

        DatosTabbedPane.addTab("Datos", datos);

        fotosPanel.setPreferredSize(new java.awt.Dimension(860, 389));
        fotosPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblFotos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblFotos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFotosMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblFotos);

        fotosPanel.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 360, 460));

        labelImagen.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        fotosPanel.add(labelImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 40, 500, 460));

        btnAgregarFoto.setIcon(new javax.swing.ImageIcon("C:\\Users\\crist\\OneDrive\\Documentos\\NetBeansProjects\\practiarmar\\src\\main\\resources\\agregar.png")); // NOI18N
        btnAgregarFoto.setText("Agregar");
        btnAgregarFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarFotoActionPerformed(evt);
            }
        });
        fotosPanel.add(btnAgregarFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 180, 120, 40));
        btnAgregarFoto.getAccessibleContext().setAccessibleName("agreg");

        btnEliminarFoto.setIcon(new javax.swing.ImageIcon("C:\\Users\\crist\\OneDrive\\Documentos\\NetBeansProjects\\practiarmar\\src\\main\\resources\\eliminar.png")); // NOI18N
        btnEliminarFoto.setText("Eliminar");
        btnEliminarFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarFotoActionPerformed(evt);
            }
        });
        fotosPanel.add(btnEliminarFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 280, 120, 40));

        DatosTabbedPane.addTab("Fotos", fotosPanel);

        jPanel1.add(DatosTabbedPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 57, 1130, 568));
        DatosTabbedPane.getAccessibleContext().setAccessibleName("");

        btnGuardar.setIcon(new javax.swing.ImageIcon("C:\\Users\\crist\\OneDrive\\Documentos\\NetBeansProjects\\practiarmar\\src\\main\\resources\\guardar.png")); // NOI18N
        btnGuardar.setText("Guarda");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 630, 181, 53));

        btnLimpiar.setIcon(new javax.swing.ImageIcon("C:\\Users\\crist\\OneDrive\\Documentos\\NetBeansProjects\\practiarmar\\src\\main\\resources\\limpiar.png")); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        jPanel1.add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(461, 631, 183, 53));

        btnVolver.setIcon(new javax.swing.ImageIcon("C:\\Users\\crist\\OneDrive\\Documentos\\NetBeansProjects\\practiarmar\\src\\main\\resources\\volverI.png")); // NOI18N
        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });
        jPanel1.add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(724, 631, 176, 53));

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

    private void txtCantidadItemKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadItemKeyTyped
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c)) && c != '+') {
            evt.consume();
        }
    }//GEN-LAST:event_txtCantidadItemKeyTyped

    private void txtNombreItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreItemActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        if (esUnNuevoProyecto) {//Es un nuevo proyecto
            //validar items
            if (!noHayItems()) {
                //hay items
                if (datosValidados()) {
                    double manoDeObra = Double.parseDouble(jformattextManoDeObra.getText().replaceAll("\\.", ""));
                    double precioMateriales = Double.parseDouble(jformattextPrecioMateriales.getText().replaceAll("\\.", ""));
                    double total = Double.parseDouble(jformatTotal.getText());
                    LocalDate fechaEntrega = dateEntrega.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    String descripcion = descripcionProyectoTextArea.getText();
                    try {
                        if (noHayEmpleados()) {

                            //el proyecto no contara con empleados
                            if (!fotosProyecto.isEmpty()) {
                                //no se envia empleados ni el precio por empleado
                                controller.agregarProyecto(clienteSeleccionado, precioMateriales, 0, manoDeObra, total, fechaEntrega,
                                        descripcion, estadoProyecto, new ArrayList<>(), items, fotosProyecto);
                            } else {
                                //no se envia precio por empleado,empleados ni fotos
                                controller.agregarProyecto(clienteSeleccionado, precioMateriales, 0, manoDeObra, total, fechaEntrega,
                                        descripcion, estadoProyecto, new ArrayList<>(), items, new ArrayList<>());
                            }
                        } else {
                            //el proyecto contara con empleados
                            double precioPorEmpleado = Double.parseDouble(jformattextPagoEmpleado.getText().replaceAll("\\.", ""));
                            if (!fotosProyecto.isEmpty()) {
                                //se envia todo
                                controller.agregarProyecto(clienteSeleccionado, precioMateriales, precioPorEmpleado, manoDeObra, total, fechaEntrega,
                                        descripcion, estadoProyecto, empleadosProyecto, items, fotosProyecto);
                            } else {
                                //no se envian las fotos
                                controller.agregarProyecto(clienteSeleccionado, precioMateriales, precioPorEmpleado, manoDeObra, total, fechaEntrega,
                                        descripcion, estadoProyecto, empleadosProyecto, items, new ArrayList<>());//the error is here
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace(); // Log the exception
                        JOptionPane.showMessageDialog(this, "Ocurrió un error al guardar el proyecto!\n" + ex.getLocalizedMessage());
                    }

                    limpiarTodosLosCamposDespuesDeCrearProyecto();
                    JOptionPane.showMessageDialog(this, "Se ha guardado el proyecto con exito!");
                } else {
                    JOptionPane.showMessageDialog(this, "Debe rellenar todos los campos de la pestaña Datos!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Debe agregar Items al proyecto!");
            }
        } else {
            //Se va a editar un proyecto
            //se modifican los datos del proyecto, menos el cliente y el ID
            //validar items
            if (!noHayItems()) {
                //hay items
                if (datosValidados()) {
                    double manoDeObra = Double.parseDouble(jformattextManoDeObra.getText().replaceAll("\\.", ""));
                    double precioMateriales = Double.parseDouble(jformattextPrecioMateriales.getText().replaceAll("\\.", ""));
                    double total = Double.parseDouble(jformatTotal.getText());
                    LocalDate fechaEntrega = dateEntrega.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    String descripcion = descripcionProyectoTextArea.getText();
                    if (noHayEmpleados()) {
                        //el proyecto no contara con empleados
                        if (!fotosProyecto.isEmpty()) {
                            //no se envia empleados ni el precio por empleado
                            proyectoAEditar.setPrecioMateriales(precioMateriales);
                            proyectoAEditar.setPrecioPorEmpleado(0);
                            proyectoAEditar.setPrecioManoDeObra(manoDeObra);
                            proyectoAEditar.setPrecioTotal(total);
                            proyectoAEditar.setDescripcion(descripcion);
                            proyectoAEditar.setEstadoProyecto(estadoProyecto);
                            proyectoAEditar.setFechaEntrega(fechaEntrega);
                            proyectoAEditar.setFehcaModificacion(LocalDate.now());
                            proyectoAEditar.getEmpleados().clear();
                            proyectoAEditar.setItems(items);
                            proyectoAEditar.setFotos(fotosProyecto);
                            try {
                                controller.editarProyecto(proyectoAEditar);
                                limpiarTodosLosCamposDespuesDeCrearProyecto();
                                JOptionPane.showMessageDialog(this, "Se ha editado el proyecto con exito!");
                                btnVolverActionPerformed(evt);
                                //proyectoAEditar = null;
                            } catch (NonexistentEntityException ex) {
                                Logger.getLogger(ProyectoAgregarEditarView.class.getName()).log(Level.SEVERE, null, ex);
                                JOptionPane.showMessageDialog(this, "Ocurrio un error al editar el proyecto!\n" + ex.getLocalizedMessage());
                            }
                        } else {
                            //no se envia precio por empleado,empleados ni fotos
                            proyectoAEditar.setPrecioMateriales(precioMateriales);
                            proyectoAEditar.setPrecioPorEmpleado(0);
                            proyectoAEditar.setPrecioManoDeObra(manoDeObra);
                            proyectoAEditar.setPrecioTotal(total);
                            proyectoAEditar.setDescripcion(descripcion);
                            proyectoAEditar.setEstadoProyecto(estadoProyecto);
                            proyectoAEditar.setFechaEntrega(fechaEntrega);
                            proyectoAEditar.setFehcaModificacion(LocalDate.now());
                            proyectoAEditar.getEmpleados().clear();
                            proyectoAEditar.setItems(items);
                            proyectoAEditar.setFotos(new ArrayList<>());
                            try {
                                controller.editarProyecto(proyectoAEditar);
                                limpiarTodosLosCamposDespuesDeCrearProyecto();
                                JOptionPane.showMessageDialog(this, "Se ha editado el proyecto con exito!");
                                btnVolverActionPerformed(evt);
                                // proyectoAEditar = null;
                            } catch (NonexistentEntityException ex) {
                                Logger.getLogger(ProyectoAgregarEditarView.class.getName()).log(Level.SEVERE, null, ex);
                                JOptionPane.showMessageDialog(this, "Ocurrio un error al editar el proyecto!\n" + ex.getLocalizedMessage());
                            }
                        }
                    } else {
                        //el proyecto contara con empleados
                        double precioPorEmpleado = Double.parseDouble(jformattextPagoEmpleado.getText().replaceAll("\\.", ""));
                        if (!fotosProyecto.isEmpty()) {
                            //se envia todo
                            proyectoAEditar.setPrecioMateriales(precioMateriales);
                            proyectoAEditar.setPrecioPorEmpleado(precioPorEmpleado);
                            proyectoAEditar.setPrecioManoDeObra(manoDeObra);
                            proyectoAEditar.setPrecioTotal(total);
                            proyectoAEditar.setDescripcion(descripcion);
                            proyectoAEditar.setEstadoProyecto(estadoProyecto);
                            proyectoAEditar.setFechaEntrega(fechaEntrega);
                            proyectoAEditar.setFehcaModificacion(LocalDate.now());
                            proyectoAEditar.setEmpleados(empleadosProyecto);
                            proyectoAEditar.setItems(items);
                            proyectoAEditar.setFotos(fotosProyecto);
                            try {
                                controller.editarProyecto(proyectoAEditar);
                                limpiarTodosLosCamposDespuesDeCrearProyecto();
                                JOptionPane.showMessageDialog(this, "Se ha editado el proyecto con exito!");
                                btnVolverActionPerformed(evt);
                                //proyectoAEditar = null;
                            } catch (NonexistentEntityException ex) {
                                Logger.getLogger(ProyectoAgregarEditarView.class.getName()).log(Level.SEVERE, null, ex);
                                JOptionPane.showMessageDialog(this, "Ocurrio un error al editar el proyecto!\n" + ex.getLocalizedMessage());
                            }

                        } else {
                            //no se envian las fotos
                            proyectoAEditar.setPrecioMateriales(precioMateriales);
                            proyectoAEditar.setPrecioPorEmpleado(precioPorEmpleado);
                            proyectoAEditar.setPrecioManoDeObra(manoDeObra);
                            proyectoAEditar.setPrecioTotal(total);
                            proyectoAEditar.setDescripcion(descripcion);
                            proyectoAEditar.setEstadoProyecto(estadoProyecto);
                            proyectoAEditar.setFechaEntrega(fechaEntrega);
                            proyectoAEditar.setFehcaModificacion(LocalDate.now());
                            proyectoAEditar.setEmpleados(empleadosProyecto);
                            proyectoAEditar.setItems(items);
                            proyectoAEditar.setFotos(new ArrayList<>());
                            try {
                                controller.editarProyecto(proyectoAEditar);
                                limpiarTodosLosCamposDespuesDeCrearProyecto();
                                JOptionPane.showMessageDialog(this, "Se ha editado el proyecto con exito!");
                                btnVolverActionPerformed(evt);
                                //proyectoAEditar = null;
                            } catch (NonexistentEntityException ex) {
                                Logger.getLogger(ProyectoAgregarEditarView.class.getName()).log(Level.SEVERE, null, ex);
                                JOptionPane.showMessageDialog(this, "Ocurrio un error al editar el proyecto!\n" + ex.getLocalizedMessage());
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(this, "Ocurrio un error al editar el proyecto!\n" + e.getLocalizedMessage());
                            }
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Debe rellenar todos los campos de la pestaña Datos!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Debe agregar Items al proyecto!");
            }

        }

    }//GEN-LAST:event_btnGuardarActionPerformed
    private boolean noHayItems() {
        return items.isEmpty();
    }

    private boolean noHayEmpleados() {
        return empleadosProyecto.isEmpty();
    }

    private boolean datosValidados() {
        return !(jformattextManoDeObra.getText().equals("") || jformattextPagoEmpleado.getText().equals("")
                || jformattextPrecioMateriales.getText().equals("") || jformatTotal.getText().equals("")
                || estadoProyecto == null || clienteSeleccionado == null || dateEntrega.getDate() == null
                || descripcionProyectoTextArea.getText().equals(""));
    }

    private void vaciarProyetoAEditar() {

    }

    private void limpiarTodosLosCamposDespuesDeCrearProyecto() {
        items.clear();
        rellenarTablaItems();
        empleadosProyecto.clear();
        rellenarTableEmpleadosProyecto();
        jformattextManoDeObra.setText("");
        jformattextPagoEmpleado.setText("0");
        jformattextPrecioMateriales.setText("");
        jformatTotal.setText("");
        descripcionProyectoTextArea.setText("");
        dateEntrega.setCalendar(null);
        fotosProyecto.clear();
        rellenarTablaFotos();
        clienteSeleccionado = null;
        empleadoDisponibleSeleccionado = null;
        empleadoProyectoSeleccionado = null;
        estadoProyecto = null;
        itemSeleccionado = null;
        rellenarComboCliente();
        labelImagen.setIcon(null);
    }

    private void btnAgregarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarItemActionPerformed
        if (itemSeleccionado != null) {
            //editar
            if (camposItemsValidos()) {
                String nombreItem = txtNombreItem.getText();
                int cantidad = Integer.parseInt(txtCantidadItem.getText());
                String descripcion = txtDescripcionItem.getText();
                double precioUnidad = Double.parseDouble(jformattextPrecioPorUnidadItem.getText().replaceAll("\\.", ""));
                items.stream().forEach(i -> {
                    if (i.equals(itemSeleccionado)) {
                        i.setNombre(nombreItem);
                        i.setCantidad(cantidad);
                        i.setDescripcion(descripcion);
                        i.setPrecioPorUnidad(precioUnidad);
                    }
                });
                itemSeleccionado = null;
                rellenarTablaItems();
                limpiarCamposItem();
                calcularPrecioMateriales();
                JOptionPane.showMessageDialog(this, "Item agregado con exito!");
            } else {
                JOptionPane.showMessageDialog(this, "No puede dejar campos vacios!");
            }

        } else {
            //agregar
            if (camposItemsValidos()) {
                String nombreItem = txtNombreItem.getText();
                boolean nombreExiste = items.stream()
                        .anyMatch(i -> i.getNombre().equalsIgnoreCase(nombreItem));
                if (!nombreExiste) {
                    int cantidad = Integer.parseInt(txtCantidadItem.getText());
                    String descripcion = txtDescripcionItem.getText();
                    double precioUnidad = Double.parseDouble(jformattextPrecioPorUnidadItem.getText().replaceAll("\\.", ""));
                    Item item = new Item(null, nombreItem, descripcion, cantidad, precioUnidad);
                    items.add(item);
                    rellenarTablaItems();
                    limpiarCamposItem();
                    calcularPrecioMateriales();
                } else {
                    txtNombreItem.setText("");
                    JOptionPane.showMessageDialog(this, "No puede tener items con el mismo nombre!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Debe rellenar todos los campos!");
            }
        }
    }//GEN-LAST:event_btnAgregarItemActionPerformed
    private void calcularPrecioMateriales() {
        precioMateriales = items.stream()
                .mapToDouble(item -> item.getPrecioPorUnidad() * item.getCantidad())
                .sum();
        jformattextPrecioMateriales.setText(decimalFormat.format(precioMateriales));
    }
    private void tblItemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblItemsMouseClicked
        String nombreItem = (String) modelItemTable.getDataVector().elementAt(tblItems.convertRowIndexToModel(tblItems.getSelectedRow())).get(0);
        for (Item item : items) {
            if (item.getNombre().equalsIgnoreCase(nombreItem)) {
                txtNombreItem.setText(item.getNombre());
                txtCantidadItem.setText(String.valueOf(item.getCantidad()));
                txtDescripcionItem.setText(item.getDescripcion());
                jformattextPrecioPorUnidadItem.setText(String.valueOf(decimalFormat.format(item.getPrecioPorUnidad())));
                itemSeleccionado = item;
                break;
            }
        }
    }//GEN-LAST:event_tblItemsMouseClicked

    private void btnEliminarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarItemActionPerformed

        if (itemSeleccionado != null) {
            items.remove(itemSeleccionado);
            itemSeleccionado = null;
            rellenarTablaItems();
            limpiarCamposItem();
            calcularPrecioMateriales();
            JOptionPane.showMessageDialog(this, "Item eliminado con exito!");

        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un item!");
        }
    }//GEN-LAST:event_btnEliminarItemActionPerformed

    private void jformatTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jformatTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jformatTotalActionPerformed

    private void tblListEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListEmpleadoMouseClicked
        String nombre = (String) modelEmpleadosDisponiblesTable.getDataVector().elementAt(tblListEmpleado.convertRowIndexToModel(tblListEmpleado.getSelectedRow())).get(0);
        for (Empleado empleado : empleadosDisponibles) {
            if (Objects.equals(empleado.getNombre(), nombre)) {
                empleadoDisponibleSeleccionado = empleado;
            }
        }
    }//GEN-LAST:event_tblListEmpleadoMouseClicked

    private void btnAgregarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarEmpleadoActionPerformed
        if (empleadoDisponibleSeleccionado != null) {
            
            if (!empleadosProyecto.stream().anyMatch((empl -> empl.getId().equals(empleadoDisponibleSeleccionado.getId()) && empl.getNombre().equals(empleadoDisponibleSeleccionado.getNombre())))){
                empleadosProyecto.add(empleadoDisponibleSeleccionado);
                rellenarTableEmpleadosProyecto();
                rellenarTablaEmpleados();
                jformattextPagoEmpleado.setEnabled(true);//se habilita el campo para agregar el precio por empleado
                actualizarPrecioTotal();
                JOptionPane.showMessageDialog(this, "Empleado agregado al proyecto con exito!");
            } else {
                JOptionPane.showMessageDialog(this, "No puede agregar al mismo empleado 2 veces!");
                rellenarTablaEmpleados();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un empleado disponible!");
        }
        empleadoDisponibleSeleccionado = null;
    }//GEN-LAST:event_btnAgregarEmpleadoActionPerformed

    private void tblEmpleadosProyectoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmpleadosProyectoMouseClicked
        String nombre = (String) modelEmpleadosProyectoTable.getDataVector().elementAt(tblEmpleadosProyecto.convertRowIndexToModel(tblEmpleadosProyecto.getSelectedRow())).get(0);
        for (Empleado empleado : empleadosDisponibles) {
            if (Objects.equals(empleado.getNombre(), nombre)) {
                empleadoProyectoSeleccionado = empleado;
            }
        }
    }//GEN-LAST:event_tblEmpleadosProyectoMouseClicked

    private void btnEliminarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEmpleadoActionPerformed
        if (empleadoProyectoSeleccionado != null) {
            System.out.println("empleado selccionado " + empleadoProyectoSeleccionado.getId() + " " + empleadoProyectoSeleccionado.getNombre());

            boolean empleadoEliminadoDelProyecto = empleadosProyecto.removeIf(empl -> empl.getId().equals(empleadoProyectoSeleccionado.getId()) && empl.getNombre().equals(empleadoProyectoSeleccionado.getNombre()));
            if (empleadoEliminadoDelProyecto) {
                empleadoProyectoSeleccionado = null;
                rellenarTableEmpleadosProyecto();
                if (empleadosProyecto.isEmpty()) {
                    //si no hay empleados en el proyecto, se deshabilita el campo de precio por empleado
                    //y se da un valor de cero, finalmente se actualiza el precio total
                    jformattextPagoEmpleado.setText("0");
                    jformattextPagoEmpleado.setEnabled(false);
                }
                actualizarPrecioTotal();
                JOptionPane.showMessageDialog(this, "Empleado eliminado del proyecto con exito!");
            } else {
                JOptionPane.showMessageDialog(this, "Empleado a eliminar no esta en el proyecto!");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un empleado del proyecto!");
        }
    }//GEN-LAST:event_btnEliminarEmpleadoActionPerformed

    private void btnAgregarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarFotoActionPerformed

        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Formatos de archivo (*.JPG;*JPEG;*PNG)", "jpg", "jpeg", "png");
        JFileChooser archivo = new JFileChooser();
        archivo.addChoosableFileFilter(filtro);
        archivo.setDialogTitle("Abrir archivo");
        int ventana = archivo.showOpenDialog(this);
        if (ventana == JFileChooser.APPROVE_OPTION) {
            try {
                File file = archivo.getSelectedFile();
                // Read the file as bytes
                byte[] imagenBytes = Files.readAllBytes(file.toPath());
                // Create an instance of Foto
                Foto nuevaFoto = Foto.builder()
                        .nombre(file.getName())
                        .tipo(getFileExtension(file.getName()))
                        .tamano(file.length())
                        .fecha(new Date())
                        .imagen(imagenBytes)
                        .build();
                fotosProyecto.add(nuevaFoto);
                rellenarTablaFotos();
                JOptionPane.showMessageDialog(this, "Foto agregada con exito!");
            } catch (IOException ex) {
                Logger.getLogger(HerramientaAgregarEditarView.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Ocurrio un error al agregar la foto\n" + ex.getLocalizedMessage());
            }
        }

    }//GEN-LAST:event_btnAgregarFotoActionPerformed

    private void tblFotosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFotosMouseClicked
        String nombre = (String) modelFotosProyectoTable.getDataVector().elementAt(tblFotos.convertRowIndexToModel(tblFotos.getSelectedRow())).get(0);
        for (Foto foto : fotosProyecto) {
            if (foto.getNombre().equals(nombre)) {
                ImageIcon fotoIcon = new ImageIcon(foto.getImagen());
                Image scaledImage = fotoIcon.getImage().getScaledInstance(labelImagen.getWidth(), labelImagen.getHeight(), Image.SCALE_DEFAULT);
                labelImagen.setIcon(new ImageIcon(scaledImage));
                break;
            }
        }
    }//GEN-LAST:event_tblFotosMouseClicked

    private void btnEliminarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarFotoActionPerformed
        String nombre = (String) modelFotosProyectoTable.getDataVector().elementAt(tblFotos.convertRowIndexToModel(tblFotos.getSelectedRow())).get(0);
        for (int i = 0; i < fotosProyecto.size(); i++) {
            if (fotosProyecto.get(i).getNombre().equals(nombre)) {
                fotosProyecto.remove(i);
                rellenarTablaFotos();
                labelImagen.setIcon(null);
                JOptionPane.showMessageDialog(this, "Foto eliminada con exito!");
                break;
            }
        }
    }//GEN-LAST:event_btnEliminarFotoActionPerformed

    private void comboEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEstadoActionPerformed
        estadoProyecto = saberEstado((String) comboEstado.getSelectedItem());
    }//GEN-LAST:event_comboEstadoActionPerformed
    private EstadoProyecto saberEstado(String estado) {
        switch (estado) {
            case "EN CURSO":
                return EstadoProyecto.EN_CURSO;
            case "FINALIZADO":
                return EstadoProyecto.FINALIZADO;
            case "SIN EMPEZAR":
                return EstadoProyecto.SIN_EMPEZAR;
            case "ESPERA DE PAGO":
                return EstadoProyecto.ESPERA_DE_PAGO;
            case "CANCELADO":
                return EstadoProyecto.CANCELADO;
            case "Seleccione estado":
                return null;
            default:
                throw new AssertionError();
        }
    }
    private void comboClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboClientesActionPerformed
        String nombreCliente = (String) comboClientes.getSelectedItem();
        for (Cliente cliente : clientes) {
            if (cliente.getNombre().equals(nombreCliente)) {
                clienteSeleccionado = cliente;
                break;
            }
        }
    }//GEN-LAST:event_comboClientesActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed

        int response = JOptionPane.showConfirmDialog(
                this,
                "¿Estas seguro que quieres limpiar los campos?\n"
                + "esta accion eliminara todos los datos del proyecto, el proyecto quedara vacio.",
                "Confirmacion de limpieza",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            items.clear();
            rellenarTablaItems();
            empleadosProyecto.clear();
            rellenarTableEmpleadosProyecto();
            jformattextManoDeObra.setText("");
            jformattextPagoEmpleado.setText("0");
            jformattextPrecioMateriales.setText("");
            jformatTotal.setText("");
            descripcionProyectoTextArea.setText("");
            dateEntrega.setCalendar(null);
            fotosProyecto.clear();
            rellenarTablaFotos();
            clienteSeleccionado = null;
            empleadoDisponibleSeleccionado = null;
            empleadoProyectoSeleccionado = null;
            estadoProyecto = null;
            comboEstado.setSelectedIndex(0);
            rellenarComboCliente();
            itemSeleccionado = null;
        }
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        menu.showProyectoPanel();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void jformattextPagoEmpleadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jformattextPagoEmpleadoKeyTyped
           char c = evt.getKeyChar();
        if (!(Character.isDigit(c)) && c != '+') {
            evt.consume();
        }
    }//GEN-LAST:event_jformattextPagoEmpleadoKeyTyped

    private void jformattextManoDeObraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jformattextManoDeObraKeyTyped
           char c = evt.getKeyChar();
        if (!(Character.isDigit(c)) && c != '+') {
            evt.consume();
        }
    }//GEN-LAST:event_jformattextManoDeObraKeyTyped

    private void jformattextPrecioPorUnidadItemKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jformattextPrecioPorUnidadItemKeyTyped
           char c = evt.getKeyChar();
        if (!(Character.isDigit(c)) && c != '+') {
            evt.consume();
        }
    }//GEN-LAST:event_jformattextPrecioPorUnidadItemKeyTyped
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    private boolean camposItemsValidos() {
        return !(txtNombreItem.getText().equals("")
                || txtCantidadItem.getText().equals("")
                || txtDescripcionItem.getText().equals("")
                || jformattextPrecioPorUnidadItem.getText().equals(""));
    }

    private void limpiarCamposItem() {
        txtNombreItem.setText("");
        txtCantidadItem.setText("");
        txtDescripcionItem.setText("");
        jformattextPrecioPorUnidadItem.setText("");

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane DatosTabbedPane;
    private javax.swing.JButton btnAgregarEmpleado;
    private javax.swing.JButton btnAgregarFoto;
    private javax.swing.JButton btnAgregarItem;
    private javax.swing.JButton btnEliminarEmpleado;
    private javax.swing.JButton btnEliminarFoto;
    private javax.swing.JButton btnEliminarItem;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> comboClientes;
    private javax.swing.JComboBox<String> comboEstado;
    private com.toedter.calendar.JDateChooser dateEntrega;
    private com.toedter.calendar.JDateChooser dateUltimaModificacion;
    private javax.swing.JPanel datos;
    private javax.swing.JTextArea descripcionProyectoTextArea;
    private javax.swing.JPanel empleadosPanel;
    private javax.swing.JPanel fotosPanel;
    private javax.swing.JPanel itemsPanel;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JFormattedTextField jformatTotal;
    private javax.swing.JFormattedTextField jformattextManoDeObra;
    private javax.swing.JFormattedTextField jformattextPagoEmpleado;
    private javax.swing.JFormattedTextField jformattextPrecioMateriales;
    private javax.swing.JFormattedTextField jformattextPrecioPorUnidadItem;
    private javax.swing.JLabel jlabelHeader;
    private javax.swing.JLabel labelImagen;
    private javax.swing.JTable tblEmpleadosProyecto;
    private javax.swing.JTable tblFotos;
    private javax.swing.JTable tblItems;
    private javax.swing.JTable tblListEmpleado;
    private javax.swing.JTextField txtCantidadItem;
    private javax.swing.JTextArea txtDescripcionItem;
    private javax.swing.JTextField txtIdProyecto;
    private javax.swing.JTextField txtNombreItem;
    // End of variables declaration//GEN-END:variables
}
