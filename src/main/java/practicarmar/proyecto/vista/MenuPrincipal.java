/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package practicarmar.proyecto.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.time.LocalDateTime;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import practiarmar.proyecto.modelo.Empleado;
import practiarmar.proyecto.modelo.Cliente;
import practiarmar.proyecto.modelo.Herramienta;
import practiarmar.proyecto.modelo.Proyecto;
/**
 *
 * @author crist
 */
public class MenuPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form MenuPrincipal
     */
    int xMouse, yMouse;

    public MenuPrincipal() {
        initComponents();
          ImageIcon icon = new ImageIcon("src/main/resources/logoApp.png");
        this.setIconImage(icon.getImage());
        labelInfo.setText(String.valueOf(LocalDateTime.now().toLocalDate()));
         this.setExtendedState(JFrame.MAXIMIZED_BOTH);
         System.out.println("panel content size. "+this.jpanelContent.getSize());
    }
    

    public void showEmpleadoAgregarView() {
        AgregarEmpleadoView agreEmpl = new AgregarEmpleadoView(this);
        agreEmpl.setLocation(0, 0);
        jpanelContent.removeAll();
        jpanelContent.add(agreEmpl, BorderLayout.CENTER);
        jpanelContent.revalidate();
        jpanelContent.repaint();
    }

    public void showEmpleadoView() {
        EmpleadoPanelView empl = new EmpleadoPanelView(this);
        empl.setLocation(0, 0);
        jpanelContent.removeAll();
        jpanelContent.add(empl, BorderLayout.CENTER);
        jpanelContent.revalidate();
        jpanelContent.repaint();
    }

    public void showEditarEmpleadoView(Empleado e) {
        EditarEmpleadoView emplEditar = new EditarEmpleadoView(e, this);
        emplEditar.setLocation(0, 0);
        jpanelContent.removeAll();
        jpanelContent.add(emplEditar, BorderLayout.CENTER);
        jpanelContent.revalidate();
        jpanelContent.repaint();
    }
    public void showVerEmpleadoView(Empleado e){
        VerEmpleadoView empleadoView = new VerEmpleadoView(e, this);
        empleadoView.setLocation(0, 0);
        jpanelContent.removeAll();
        jpanelContent.add(empleadoView, BorderLayout.CENTER);
        jpanelContent.revalidate();
        jpanelContent.repaint();
    }
    public  void showClienteEditar(Cliente c) {
        ClienteAgregarEditarView clienteView = new ClienteAgregarEditarView(this,c);
        clienteView.setLocation(0, 0);
        jpanelContent.removeAll();
        jpanelContent.add(clienteView, BorderLayout.CENTER);
        jpanelContent.revalidate();
        jpanelContent.repaint();
    }
    public  void showClienteView() {
         ClientePanelView clienteView = new ClientePanelView(this);
        clienteView.setLocation(0, 0);
        jpanelContent.removeAll();
        jpanelContent.add(clienteView, BorderLayout.CENTER);
        jpanelContent.revalidate();
        jpanelContent.repaint();
    }
    public  void showClienteAgregar() {
        ClienteAgregarEditarView clienteView = new ClienteAgregarEditarView(this,null);
        jpanelContent.removeAll();
        jpanelContent.add(clienteView, BorderLayout.CENTER);
        jpanelContent.revalidate();
        jpanelContent.repaint();
    }
    public void showHerramientaView(){
        HerramientaPanelView herramientaPanelView = new HerramientaPanelView(this);
        herramientaPanelView.setLocation(0, 0);
        jpanelContent.removeAll();
        jpanelContent.add(herramientaPanelView, BorderLayout.CENTER);
        jpanelContent.revalidate();
        jpanelContent.repaint();
    }
     public void showHerramientaAgregar(){
        HerramientaAgregarEditarView agregarEditarView = new HerramientaAgregarEditarView(this,null);
         agregarEditarView.setLocation(0, 0);
        jpanelContent.removeAll();
        jpanelContent.add(agregarEditarView, BorderLayout.CENTER);
        jpanelContent.revalidate();
        jpanelContent.repaint();
    }
      public void showHerramientaEditar(Herramienta h){
        HerramientaAgregarEditarView agregarEditarView = new HerramientaAgregarEditarView(this,h);
        agregarEditarView.setLocation(0, 0);
        jpanelContent.removeAll();
        jpanelContent.add(agregarEditarView, BorderLayout.CENTER);
        jpanelContent.revalidate();
        jpanelContent.repaint();
    }
     public void showHerramientaVer(Herramienta h){
        VerHerramientaView herramientaView = new VerHerramientaView(this, h);
        herramientaView.setLocation(0, 0);
        jpanelContent.removeAll();
        jpanelContent.add(herramientaView, BorderLayout.CENTER);
        jpanelContent.revalidate();
        jpanelContent.repaint();
    }
     public void showProyectoPanel(){
        ProyectoPanelView proyectoView = new ProyectoPanelView(this);
        proyectoView.setLocation(0, 0);
        jpanelContent.removeAll();
        jpanelContent.add(proyectoView, BorderLayout.CENTER);
        jpanelContent.revalidate();
        jpanelContent.repaint();
     }
     public void showProyectoAgregar(){
         //Se envia valor true
        ProyectoAgregarEditarView proyectoAgregar = new ProyectoAgregarEditarView(this,true);
        proyectoAgregar.saberSiEsEditarOAgregar();
        proyectoAgregar.setLocation(0, 0);
        jpanelContent.removeAll();
        jpanelContent.add(proyectoAgregar, BorderLayout.CENTER);
        jpanelContent.revalidate();
        jpanelContent.repaint();
     }
      public void showProyectoEditar(Proyecto proyecto){
         //Se envia valor true
        ProyectoAgregarEditarView proyectoEditar = new ProyectoAgregarEditarView(this,false);
        proyectoEditar.setProyectoAEditar(proyecto);
        proyectoEditar.saberSiEsEditarOAgregar();
        proyectoEditar.setLocation(0, 0);
        jpanelContent.removeAll();
        jpanelContent.add(proyectoEditar, BorderLayout.CENTER);
        jpanelContent.revalidate();
        jpanelContent.repaint();
     }
     public void showVerProyecto(Proyecto p){
        VerProyectoView proyectoView = new VerProyectoView(p, this);
        proyectoView.setLocation(0, 0);
        jpanelContent.removeAll();
        jpanelContent.add(proyectoView, BorderLayout.CENTER);
        jpanelContent.revalidate();
        jpanelContent.repaint();
     }
     public void showCotizarView(){
        CotizacionView cotizacionView = new CotizacionView(this);
        cotizacionView.setLocation(0, 0);
        jpanelContent.removeAll();
        jpanelContent.add(cotizacionView, BorderLayout.CENTER);
        jpanelContent.revalidate();
        jpanelContent.repaint();
     }
 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        panelSideBard = new javax.swing.JPanel();
        jpanelSideBarEmpleados = new javax.swing.JPanel();
        labelEmpleados = new javax.swing.JLabel();
        jpanelSideBarClientes = new javax.swing.JPanel();
        labelClientes = new javax.swing.JLabel();
        jpanelSideBarProyectos = new javax.swing.JPanel();
        labelProyectos = new javax.swing.JLabel();
        jpanelSideBarHerramientas = new javax.swing.JPanel();
        labelHerramientas = new javax.swing.JLabel();
        jpanelSideBarReportes = new javax.swing.JPanel();
        labelReportes = new javax.swing.JLabel();
        jpanelSideBarConfiguracion = new javax.swing.JPanel();
        labelConfiguracion = new javax.swing.JLabel();
        jpanelHeader = new javax.swing.JPanel();
        jpanelCerrar = new javax.swing.JPanel();
        labelCerrarVentana = new javax.swing.JLabel();
        jpanelInfo = new javax.swing.JPanel();
        labelInfo = new javax.swing.JLabel();
        jpanelContent = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setMaximumSize(new java.awt.Dimension(1336, 768));
        setMinimumSize(new java.awt.Dimension(1336, 768));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1336, 768));
        setResizable(false);

        panelSideBard.setBackground(new java.awt.Color(153, 153, 153));

        jpanelSideBarEmpleados.setBackground(new java.awt.Color(153, 153, 153));

        labelEmpleados.setBackground(new java.awt.Color(255, 255, 255));
        labelEmpleados.setIcon(new javax.swing.ImageIcon("C:\\Users\\crist\\OneDrive\\Documentos\\NetBeansProjects\\practiarmar\\src\\main\\resources\\empl.png")); // NOI18N
        labelEmpleados.setText("Empleados");
        labelEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEmpleadosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labelEmpleadosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labelEmpleadosMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jpanelSideBarEmpleadosLayout = new javax.swing.GroupLayout(jpanelSideBarEmpleados);
        jpanelSideBarEmpleados.setLayout(jpanelSideBarEmpleadosLayout);
        jpanelSideBarEmpleadosLayout.setHorizontalGroup(
            jpanelSideBarEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelSideBarEmpleadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpanelSideBarEmpleadosLayout.setVerticalGroup(
            jpanelSideBarEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelSideBarEmpleadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpanelSideBarClientes.setBackground(new java.awt.Color(153, 153, 153));
        jpanelSideBarClientes.setPreferredSize(new java.awt.Dimension(158, 75));

        labelClientes.setIcon(new javax.swing.ImageIcon("C:\\Users\\crist\\OneDrive\\Documentos\\NetBeansProjects\\practiarmar\\src\\main\\resources\\clientes.png")); // NOI18N
        labelClientes.setText("Clientes");
        labelClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelClientesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labelClientesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labelClientesMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jpanelSideBarClientesLayout = new javax.swing.GroupLayout(jpanelSideBarClientes);
        jpanelSideBarClientes.setLayout(jpanelSideBarClientesLayout);
        jpanelSideBarClientesLayout.setHorizontalGroup(
            jpanelSideBarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelSideBarClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpanelSideBarClientesLayout.setVerticalGroup(
            jpanelSideBarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelSideBarClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpanelSideBarProyectos.setBackground(new java.awt.Color(153, 153, 153));
        jpanelSideBarProyectos.setPreferredSize(new java.awt.Dimension(75, 55));

        labelProyectos.setIcon(new javax.swing.ImageIcon("C:\\Users\\crist\\OneDrive\\Documentos\\NetBeansProjects\\practiarmar\\src\\main\\resources\\proyecto.png")); // NOI18N
        labelProyectos.setText("Proyectos");
        labelProyectos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelProyectosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labelProyectosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labelProyectosMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jpanelSideBarProyectosLayout = new javax.swing.GroupLayout(jpanelSideBarProyectos);
        jpanelSideBarProyectos.setLayout(jpanelSideBarProyectosLayout);
        jpanelSideBarProyectosLayout.setHorizontalGroup(
            jpanelSideBarProyectosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelSideBarProyectosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelProyectos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpanelSideBarProyectosLayout.setVerticalGroup(
            jpanelSideBarProyectosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelSideBarProyectosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelProyectos, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpanelSideBarHerramientas.setBackground(new java.awt.Color(153, 153, 153));
        jpanelSideBarHerramientas.setPreferredSize(new java.awt.Dimension(75, 55));

        labelHerramientas.setIcon(new javax.swing.ImageIcon("C:\\Users\\crist\\OneDrive\\Documentos\\NetBeansProjects\\practiarmar\\src\\main\\resources\\herramientas.png")); // NOI18N
        labelHerramientas.setText("Herramientas");
        labelHerramientas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelHerramientasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labelHerramientasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labelHerramientasMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jpanelSideBarHerramientasLayout = new javax.swing.GroupLayout(jpanelSideBarHerramientas);
        jpanelSideBarHerramientas.setLayout(jpanelSideBarHerramientasLayout);
        jpanelSideBarHerramientasLayout.setHorizontalGroup(
            jpanelSideBarHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelSideBarHerramientasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelHerramientas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpanelSideBarHerramientasLayout.setVerticalGroup(
            jpanelSideBarHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelSideBarHerramientasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelHerramientas, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpanelSideBarReportes.setBackground(new java.awt.Color(153, 153, 153));
        jpanelSideBarReportes.setPreferredSize(new java.awt.Dimension(75, 55));

        labelReportes.setIcon(new javax.swing.ImageIcon("C:\\Users\\crist\\OneDrive\\Documentos\\NetBeansProjects\\practiarmar\\src\\main\\resources\\reporte.png")); // NOI18N
        labelReportes.setText("Reportes");
        labelReportes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelReportesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labelReportesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labelReportesMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jpanelSideBarReportesLayout = new javax.swing.GroupLayout(jpanelSideBarReportes);
        jpanelSideBarReportes.setLayout(jpanelSideBarReportesLayout);
        jpanelSideBarReportesLayout.setHorizontalGroup(
            jpanelSideBarReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelSideBarReportesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelReportes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpanelSideBarReportesLayout.setVerticalGroup(
            jpanelSideBarReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelSideBarReportesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelReportes, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpanelSideBarConfiguracion.setBackground(new java.awt.Color(153, 153, 153));
        jpanelSideBarConfiguracion.setPreferredSize(new java.awt.Dimension(75, 55));

        labelConfiguracion.setIcon(new javax.swing.ImageIcon("C:\\Users\\crist\\OneDrive\\Documentos\\NetBeansProjects\\practiarmar\\src\\main\\resources\\configuracion.png")); // NOI18N
        labelConfiguracion.setText("Configuracion");
        labelConfiguracion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelConfiguracionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labelConfiguracionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labelConfiguracionMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jpanelSideBarConfiguracionLayout = new javax.swing.GroupLayout(jpanelSideBarConfiguracion);
        jpanelSideBarConfiguracion.setLayout(jpanelSideBarConfiguracionLayout);
        jpanelSideBarConfiguracionLayout.setHorizontalGroup(
            jpanelSideBarConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelSideBarConfiguracionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelConfiguracion, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpanelSideBarConfiguracionLayout.setVerticalGroup(
            jpanelSideBarConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelSideBarConfiguracionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelConfiguracion, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelSideBardLayout = new javax.swing.GroupLayout(panelSideBard);
        panelSideBard.setLayout(panelSideBardLayout);
        panelSideBardLayout.setHorizontalGroup(
            panelSideBardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSideBardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSideBardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpanelSideBarHerramientas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                    .addComponent(jpanelSideBarEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpanelSideBarClientes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpanelSideBarProyectos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                    .addComponent(jpanelSideBarReportes, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                    .addComponent(jpanelSideBarConfiguracion, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelSideBardLayout.setVerticalGroup(
            panelSideBardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSideBardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpanelSideBarEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpanelSideBarClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpanelSideBarProyectos, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpanelSideBarHerramientas, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpanelSideBarReportes, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpanelSideBarConfiguracion, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpanelHeader.setBackground(new java.awt.Color(255, 102, 0));
        jpanelHeader.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jpanelHeaderMouseDragged(evt);
            }
        });
        jpanelHeader.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jpanelHeaderMousePressed(evt);
            }
        });

        jpanelCerrar.setBackground(new java.awt.Color(255, 102, 0));

        labelCerrarVentana.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCerrarVentana.setIcon(new javax.swing.ImageIcon("C:\\Users\\crist\\OneDrive\\Documentos\\NetBeansProjects\\practiarmar\\src\\main\\resources\\cerrar.png")); // NOI18N
        labelCerrarVentana.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelCerrarVentanaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labelCerrarVentanaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labelCerrarVentanaMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jpanelCerrarLayout = new javax.swing.GroupLayout(jpanelCerrar);
        jpanelCerrar.setLayout(jpanelCerrarLayout);
        jpanelCerrarLayout.setHorizontalGroup(
            jpanelCerrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelCerrarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelCerrarVentana, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpanelCerrarLayout.setVerticalGroup(
            jpanelCerrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelCerrarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelCerrarVentana, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpanelInfo.setBackground(new java.awt.Color(255, 102, 0));

        labelInfo.setText("19/02/2024");

        javax.swing.GroupLayout jpanelInfoLayout = new javax.swing.GroupLayout(jpanelInfo);
        jpanelInfo.setLayout(jpanelInfoLayout);
        jpanelInfoLayout.setHorizontalGroup(
            jpanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpanelInfoLayout.setVerticalGroup(
            jpanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelInfoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelInfo)
                .addContainerGap())
        );

        javax.swing.GroupLayout jpanelHeaderLayout = new javax.swing.GroupLayout(jpanelHeader);
        jpanelHeader.setLayout(jpanelHeaderLayout);
        jpanelHeaderLayout.setHorizontalGroup(
            jpanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpanelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 706, Short.MAX_VALUE)
                .addComponent(jpanelCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpanelHeaderLayout.setVerticalGroup(
            jpanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpanelCerrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jpanelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpanelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpanelContent.setBackground(new java.awt.Color(224, 224, 224));
        jpanelContent.setMinimumSize(new java.awt.Dimension(821, 540));
        jpanelContent.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addComponent(panelSideBard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jpanelContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jpanelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addComponent(jpanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelSideBard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpanelContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void labelCerrarVentanaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelCerrarVentanaMouseEntered
        labelCerrarVentana.setBackground(Color.red);
        labelCerrarVentana.setOpaque(true);
    }//GEN-LAST:event_labelCerrarVentanaMouseEntered

    private void labelCerrarVentanaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelCerrarVentanaMouseExited
        labelCerrarVentana.setBackground(new Color(255, 102, 0));
        labelCerrarVentana.setOpaque(true);
    }//GEN-LAST:event_labelCerrarVentanaMouseExited

    private void labelCerrarVentanaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelCerrarVentanaMouseClicked
   int response = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro de que deseas cerrar la sesión?",
                    "Confirmación de cierre",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
   if (response == JOptionPane.YES_OPTION){
        System.exit(0);
     }
    }//GEN-LAST:event_labelCerrarVentanaMouseClicked

    private void jpanelHeaderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpanelHeaderMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_jpanelHeaderMousePressed

    private void jpanelHeaderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpanelHeaderMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_jpanelHeaderMouseDragged

    private void labelEmpleadosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEmpleadosMouseEntered
        labelEmpleados.setBackground(Color.gray);
        labelEmpleados.setOpaque(true);
    }//GEN-LAST:event_labelEmpleadosMouseEntered

    private void labelEmpleadosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEmpleadosMouseExited
        labelEmpleados.setBackground(new Color(153, 153, 153));
        labelEmpleados.setOpaque(true);
    }//GEN-LAST:event_labelEmpleadosMouseExited

    private void labelEmpleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEmpleadosMouseClicked
        System.out.println("empleados vista");
        EmpleadoPanelView empl = new EmpleadoPanelView(this);
        empl.setLocation(0, 0);
        jpanelContent.removeAll();
        jpanelContent.add(empl, BorderLayout.CENTER);
        jpanelContent.revalidate();
        jpanelContent.repaint();
    }//GEN-LAST:event_labelEmpleadosMouseClicked

    private void labelClientesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelClientesMouseEntered
        labelClientes.setBackground(Color.gray);
        labelClientes.setOpaque(true);
    }//GEN-LAST:event_labelClientesMouseEntered

    private void labelClientesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelClientesMouseExited
        labelClientes.setBackground(new Color(153, 153, 153));
        labelClientes.setOpaque(true);
    }//GEN-LAST:event_labelClientesMouseExited

    private void labelClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelClientesMouseClicked
        ClientePanelView clienteView = new ClientePanelView(this);
        clienteView.setLocation(0, 0);
        clienteView.setSize(744, 550);
        jpanelContent.removeAll();
        jpanelContent.add(clienteView, BorderLayout.CENTER);
        jpanelContent.revalidate();
        jpanelContent.repaint();
    }//GEN-LAST:event_labelClientesMouseClicked

    private void labelProyectosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelProyectosMouseEntered
        labelProyectos.setBackground(Color.gray);
        labelProyectos.setOpaque(true);
    }//GEN-LAST:event_labelProyectosMouseEntered

    private void labelProyectosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelProyectosMouseExited
        labelProyectos.setBackground(new Color(153, 153, 153));
        labelProyectos.setOpaque(true);
    }//GEN-LAST:event_labelProyectosMouseExited

    private void labelProyectosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelProyectosMouseClicked
        ProyectoPanelView proyectoPanelView = new ProyectoPanelView(this);
        proyectoPanelView.setLocation(0, 0);
        proyectoPanelView.setSize(744, 550);
        jpanelContent.removeAll();
        jpanelContent.add(proyectoPanelView, BorderLayout.CENTER);
        jpanelContent.revalidate();
        jpanelContent.repaint();
    }//GEN-LAST:event_labelProyectosMouseClicked

    private void labelHerramientasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelHerramientasMouseEntered
        labelHerramientas.setBackground(Color.gray);
        labelHerramientas.setOpaque(true);
    }//GEN-LAST:event_labelHerramientasMouseEntered

    private void labelHerramientasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelHerramientasMouseExited
        labelHerramientas.setBackground(new Color(153, 153, 153));
        labelHerramientas.setOpaque(true);
    }//GEN-LAST:event_labelHerramientasMouseExited

    private void labelHerramientasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelHerramientasMouseClicked
        HerramientaPanelView herramientaPanelView = new HerramientaPanelView(this);
        herramientaPanelView.setLocation(0, 0);
        herramientaPanelView.setSize(744, 550);
        jpanelContent.removeAll();
        jpanelContent.add(herramientaPanelView, BorderLayout.CENTER);
        jpanelContent.revalidate();
        jpanelContent.repaint();
    }//GEN-LAST:event_labelHerramientasMouseClicked

    private void labelReportesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelReportesMouseEntered
        labelReportes.setBackground(Color.gray);
        labelReportes.setOpaque(true);
    }//GEN-LAST:event_labelReportesMouseEntered

    private void labelReportesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelReportesMouseExited
        labelReportes.setBackground(new Color(153, 153, 153));
        labelReportes.setOpaque(true);
    }//GEN-LAST:event_labelReportesMouseExited

    private void labelReportesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelReportesMouseClicked
        System.out.println("reportes vista");
    }//GEN-LAST:event_labelReportesMouseClicked

    private void labelConfiguracionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelConfiguracionMouseClicked
        System.out.println("configuracion vista");
    }//GEN-LAST:event_labelConfiguracionMouseClicked

    private void labelConfiguracionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelConfiguracionMouseEntered
        labelConfiguracion.setBackground(Color.gray);
        labelConfiguracion.setOpaque(true);
    }//GEN-LAST:event_labelConfiguracionMouseEntered

    private void labelConfiguracionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelConfiguracionMouseExited
        labelConfiguracion.setBackground(new Color(153, 153, 153));
        labelConfiguracion.setOpaque(true);
    }//GEN-LAST:event_labelConfiguracionMouseExited

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jpanelCerrar;
    private javax.swing.JPanel jpanelContent;
    private javax.swing.JPanel jpanelHeader;
    private javax.swing.JPanel jpanelInfo;
    private javax.swing.JPanel jpanelSideBarClientes;
    private javax.swing.JPanel jpanelSideBarConfiguracion;
    private javax.swing.JPanel jpanelSideBarEmpleados;
    private javax.swing.JPanel jpanelSideBarHerramientas;
    private javax.swing.JPanel jpanelSideBarProyectos;
    private javax.swing.JPanel jpanelSideBarReportes;
    private javax.swing.JLabel labelCerrarVentana;
    private javax.swing.JLabel labelClientes;
    private javax.swing.JLabel labelConfiguracion;
    private javax.swing.JLabel labelEmpleados;
    private javax.swing.JLabel labelHerramientas;
    private javax.swing.JLabel labelInfo;
    private javax.swing.JLabel labelProyectos;
    private javax.swing.JLabel labelReportes;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JPanel panelSideBard;
    // End of variables declaration//GEN-END:variables

}
