/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practiarmar.proyecto.services;

/*import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfDocument;*/
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import practiarmar.proyecto.modelo.Item;
import practiarmar.proyecto.modelo.ItemCotizacion;

/**
 *
 * @author crist
 */
public class PdfCreator {

   
  //  private InputStream logoStream;
    private String desktopPath;
    private Path folderPath;
    private NumberFormat currencyFormatter;

    public PdfCreator() {
       // this.logoStream = getClass().getClassLoader().getResourceAsStream("practiarmarLogo.PNG");
        //this.logoStream = "\\src\\main\\resources\\practiarmarLogo.PNG";
        this.desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
        this.currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    }

    //terminar, precio total con un cero de mas, no se ve el logo
    public void createPdfFileToAdminWithEmployees(
            double manoDeObra, double precioMateriales, double precioTotal, int numEmpleados,
            double pagoPorEmpleado, String nombreCliente, List<ItemCotizacion> items, String descripcion,
            LocalDate fechaEntrega
    ) throws IOException, DocumentException {

        Document document = new Document();

        this.folderPath = Path.of(desktopPath, "cotizaciones_practiarmar");
        Files.createDirectories(folderPath);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File pdfFile = new File(folderPath.toString(), "cotizacion_admin_" + nombreCliente +"_"+timeStamp +".pdf");
        FileOutputStream fos = new FileOutputStream(pdfFile);

        PdfWriter writer = PdfWriter.getInstance(document, fos);
        document.open();
        // Adjusting the Logo and Title
        PdfPTable headerTable = new PdfPTable(1);  // Only one column for the logo
        headerTable.setWidthPercentage(100);

        // Logo Cell
        Image logo = Image.getInstance(getLogoStream().readAllBytes());
        logo.scaleToFit(150f, 150f);  // Adjust size while maintaining aspect ratio
        logo.setAlignment(Element.ALIGN_CENTER);  // Center the logo

        PdfPCell logoCell = new PdfPCell(logo, true);
        logoCell.setBorder(Rectangle.NO_BORDER);
        logoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerTable.addCell(logoCell);

        // Add spacing after the logo
        PdfPCell emptyCell = new PdfPCell(new Phrase(" "));
        emptyCell.setBorder(Rectangle.NO_BORDER);
        emptyCell.setMinimumHeight(50f);  // Adjust the height to add more space
        headerTable.addCell(emptyCell);

        document.add(headerTable);
        document.add(Chunk.NEWLINE);

        // "Cotización para" and "Fecha"
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);

        PdfPCell cotizacionCell = new PdfPCell(new Paragraph("Cotización para: " + nombreCliente, new Font(Font.FontFamily.HELVETICA, 12)));
        cotizacionCell.setBorder(Rectangle.NO_BORDER);
        cotizacionCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        infoTable.addCell(cotizacionCell);

        PdfPCell fechaCell = new PdfPCell(new Paragraph("Fecha: " + LocalDate.now().toString(), new Font(Font.FontFamily.HELVETICA, 12)));
        fechaCell.setBorder(Rectangle.NO_BORDER);
        fechaCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        infoTable.addCell(fechaCell);

        // Add spacing after the infoTable
        PdfPCell emptyCellInfoTable = new PdfPCell(new Phrase(" "));
        emptyCellInfoTable.setBorder(Rectangle.NO_BORDER);
        emptyCellInfoTable.setMinimumHeight(50f);  // Adjust the height to add more space
        infoTable.addCell(emptyCellInfoTable);

        document.add(infoTable);
        document.add(Chunk.NEWLINE);
        
        // Add Description
        Paragraph descLabel = new Paragraph("Descripción:", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
        descLabel.setSpacingBefore(20f);
        document.add(descLabel);
        //document.add(Chunk.NEWLINE);
        Paragraph desc = new Paragraph(descripcion, new Font(Font.FontFamily.HELVETICA, 10));
        desc.setSpacingBefore(5f);  // Increase space before the description text
        desc.setSpacingAfter(20f);   // Increase space after the description text to keep uniform spacing

        document.add(desc);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        //Tabla para los items
        PdfPTable tableItems = new PdfPTable(4);
        tableItems.setWidthPercentage(100);
        tableItems.setWidths(new int[]{3, 1, 1, 1});
        BaseColor colorTitles = new BaseColor(245, 130, 32);

        PdfPCell cellItem = new PdfPCell(new Phrase("Material", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        cellItem.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellItem.setBackgroundColor(colorTitles);
        tableItems.addCell(cellItem);

        PdfPCell cellDescripcion = new PdfPCell(new Phrase("Descripcion", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        cellDescripcion.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellDescripcion.setBackgroundColor(colorTitles);
        tableItems.addCell(cellDescripcion);

        PdfPCell cellCantidad = new PdfPCell(new Phrase("Cantidad", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        cellCantidad.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellCantidad.setBackgroundColor(colorTitles);
        tableItems.addCell(cellCantidad);

        PdfPCell cellPrecio = new PdfPCell(new Phrase("Precio", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        cellPrecio.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellPrecio.setBackgroundColor(colorTitles);
        tableItems.addCell(cellPrecio);

        for (ItemCotizacion item : items) {
            tableItems.addCell(new Phrase(item.getNombre(), new Font(Font.FontFamily.HELVETICA, 10)));
            tableItems.addCell(new Phrase(item.getDescripcion(), new Font(Font.FontFamily.HELVETICA, 10)));
            tableItems.addCell(new Phrase(String.valueOf(item.getCantidad()), new Font(Font.FontFamily.HELVETICA, 10)));
            tableItems.addCell(new Phrase(String.valueOf(currencyFormatter.format(item.getPrecioPorUnidad()*item.getCantidad())), new Font(Font.FontFamily.HELVETICA, 10)));
        }

        PdfPCell cellTextValorItems = new PdfPCell(new Phrase("Valor materiales", new Font(Font.FontFamily.HELVETICA, 10)));
        cellTextValorItems.setColspan(3); // Span across the first three columns
        tableItems.addCell(cellTextValorItems);

        PdfPCell cellValorItems = new PdfPCell(new Phrase(String.valueOf(currencyFormatter.format(precioMateriales)), new Font(Font.FontFamily.HELVETICA, 10)));
        tableItems.addCell(cellValorItems);

        document.add(tableItems);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        //agregar la tabla con el resto de los datos
        PdfPTable tableDatos = new PdfPTable(2);
        tableDatos.setWidths(new int[]{1, 1});

        PdfPCell headerCell = new PdfPCell(new Phrase("Datos de la cotización", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        headerCell.setBackgroundColor(colorTitles);
        headerCell.setColspan(2);
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableDatos.addCell(headerCell);

        tableDatos.addCell(new Phrase("Número empleados", new Font(Font.FontFamily.HELVETICA, 10)));
        PdfPCell numEmpleadosCell = new PdfPCell(new Phrase(String.valueOf(numEmpleados), new Font(Font.FontFamily.HELVETICA, 10)));
        numEmpleadosCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableDatos.addCell(numEmpleadosCell);

        tableDatos.addCell(new Phrase("Pago por empleado", new Font(Font.FontFamily.HELVETICA, 10)));
        PdfPCell pagoEmpeadoCell = new PdfPCell(new Phrase(String.valueOf(currencyFormatter.format(pagoPorEmpleado)), new Font(Font.FontFamily.HELVETICA, 10)));
        pagoEmpeadoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableDatos.addCell(pagoEmpeadoCell);

        tableDatos.addCell(new Phrase("Precio mano de obra", new Font(Font.FontFamily.HELVETICA, 10)));
        PdfPCell precioManoObraCell = new PdfPCell(new Phrase(String.valueOf(currencyFormatter.format(manoDeObra)), new Font(Font.FontFamily.HELVETICA, 10)));
        precioManoObraCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableDatos.addCell(precioManoObraCell);

        tableDatos.addCell(new Phrase("Precio Total", new Font(Font.FontFamily.HELVETICA, 10)));
        PdfPCell precioTotalCell = new PdfPCell(new Phrase(String.valueOf(currencyFormatter.format(precioTotal)), new Font(Font.FontFamily.HELVETICA, 10)));
        precioTotalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableDatos.addCell(precioTotalCell);

        tableDatos.addCell(new Phrase("Fecha de entrega", new Font(Font.FontFamily.HELVETICA, 10)));
        PdfPCell fechaEntregaCell = new PdfPCell(new Phrase(String.valueOf(fechaEntrega), new Font(Font.FontFamily.HELVETICA, 10)));
        fechaEntregaCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableDatos.addCell(fechaEntregaCell);

        document.add(tableDatos);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        Paragraph noteLabel = new Paragraph("Nota:", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
        document.add(noteLabel);

        Paragraph note = new Paragraph("Todo trabajo se inicia con el 50% y el saldo se cancelará al momento de la entrega total del trabajo.", new Font(Font.FontFamily.HELVETICA, 10));
        document.add(note);

        document.close();
        fos.close();

        createPdfFileToClientWithEmployees(manoDeObra, precioMateriales, precioTotal, numEmpleados, pagoPorEmpleado, nombreCliente, items, descripcion, fechaEntrega);
    }

    public void createPdfFileToAdminWithoutEmployees(double manoDeObra, double precioMateriales, double precioTotal,
            String descripcion, LocalDate fechaEntrega, String nombreCliente, List<ItemCotizacion> items) throws IOException, DocumentException {

        Document document = new Document();
        document.open();

        this.folderPath = Path.of(desktopPath, "cotizaciones_practiarmar");
        Files.createDirectories(folderPath);

        File pdfFile = new File(folderPath.toString(), "cotizacion_" + nombreCliente + ".pdf");
        FileOutputStream fos = new FileOutputStream(pdfFile);

        PdfWriter writer = PdfWriter.getInstance(document, fos);

        Image logo = Image.getInstance(getLogoStream().readAllBytes());
        logo.scaleAbsolute(100f, 100f);
        document.add(logo);

        Paragraph cotizacion = new Paragraph("Cotización para: " + nombreCliente, new Font(Font.FontFamily.HELVETICA, 12));
        document.add(cotizacion);

        // Add the date
        Paragraph fecha = new Paragraph("Fecha: 01/01/2024", new Font(Font.FontFamily.HELVETICA, 12));
        document.add(fecha);

        // Add the "Descripción" section
        Paragraph desc = new Paragraph(descripcion, new Font(Font.FontFamily.HELVETICA, 10));
        document.add(desc);

        //Tabla para los items
        PdfPTable tableItems = new PdfPTable(4);
        tableItems.setWidthPercentage(100);
        BaseColor colorTitles = new BaseColor(245, 130, 32);

        PdfPCell cellItem = new PdfPCell(new Phrase("Material", new Font(Font.FontFamily.HELVETICA, 12)));
        cellItem.setBackgroundColor(colorTitles);
        tableItems.addCell(cellItem);

        PdfPCell cellDescripcion = new PdfPCell(new Phrase("Descripcion", new Font(Font.FontFamily.HELVETICA, 12)));
        cellDescripcion.setBackgroundColor(colorTitles);
        tableItems.addCell(cellDescripcion);

        PdfPCell cellCantidad = new PdfPCell(new Phrase("Cantidad", new Font(Font.FontFamily.HELVETICA, 12)));
        cellCantidad.setBackgroundColor(colorTitles);
        tableItems.addCell(cellCantidad);

        PdfPCell cellPrecio = new PdfPCell(new Phrase("Precio", new Font(Font.FontFamily.HELVETICA, 12)));
        cellPrecio.setBackgroundColor(colorTitles);
        tableItems.addCell(cellPrecio);

        for (ItemCotizacion item : items) {
            tableItems.addCell(new Phrase(item.getNombre(), new Font(Font.FontFamily.HELVETICA, 10)));
            tableItems.addCell(new Phrase(item.getDescripcion(), new Font(Font.FontFamily.HELVETICA, 10)));
            tableItems.addCell(new Phrase(String.valueOf(item.getCantidad()), new Font(Font.FontFamily.HELVETICA, 10)));
            tableItems.addCell(new Phrase(String.valueOf(item.getPrecioPorUnidad()), new Font(Font.FontFamily.HELVETICA, 10)));
        }

        PdfPCell cellTextValorItems = new PdfPCell(new Phrase("Valor materiales", new Font(Font.FontFamily.HELVETICA, 12)));
        cellTextValorItems.setColspan(3); // Span across the first three columns
        cellTextValorItems.setBorder(Rectangle.NO_BORDER); // No border for this cell
        tableItems.addCell(cellTextValorItems);

        PdfPCell cellValorItems = new PdfPCell(new Phrase(String.valueOf(precioMateriales), new Font(Font.FontFamily.HELVETICA, 12)));
        cellValorItems.setColspan(3); // Span across the first three columns
        cellValorItems.setBorder(Rectangle.NO_BORDER); // No border for this cell
        tableItems.addCell(cellValorItems);

        //agregar la tabla con el resto de los datos
        PdfPTable tableDatos = new PdfPTable(2);

        tableDatos.setWidths(new int[]{1, 1});
        /*tableDatos.addCell(new Phrase("Precio materiales", new Font(Font.FontFamily.HELVETICA, 10)));
        tableDatos.addCell(new Phrase(String.valueOf(precioMateriales), new Font(Font.FontFamily.HELVETICA, 10)));*/

        tableDatos.addCell(new Phrase("Precio mano de obra", new Font(Font.FontFamily.HELVETICA, 10)));
        tableDatos.addCell(new Phrase(String.valueOf(manoDeObra), new Font(Font.FontFamily.HELVETICA, 10)));

        tableDatos.addCell(new Phrase("Precio Total", new Font(Font.FontFamily.HELVETICA, 10)));
        tableDatos.addCell(new Phrase(String.valueOf(precioTotal), new Font(Font.FontFamily.HELVETICA, 10)));

        tableDatos.addCell(new Phrase("Fecha de entrega", new Font(Font.FontFamily.HELVETICA, 10)));
        tableDatos.addCell(new Phrase(String.valueOf(fechaEntrega), new Font(Font.FontFamily.HELVETICA, 10)));

        document.add(tableDatos);
        document.close();
        fos.close();

    }

    private void createPdfFileToClientWithEmployees(double manoDeObra, double precioMateriales, double precioTotal, int numEmpleados,
            double pagoPorEmpleado, String nombreCliente, List<ItemCotizacion> items, String descripcion,
            LocalDate fechaEntrega) throws IOException, DocumentException {

      Document document = new Document();

        this.folderPath = Path.of(desktopPath, "cotizaciones_practiarmar");
        Files.createDirectories(folderPath);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File pdfFile = new File(folderPath.toString(), "cotizacion_client_" + nombreCliente +"_"+timeStamp +".pdf");
        FileOutputStream fos = new FileOutputStream(pdfFile);

        PdfWriter writer = PdfWriter.getInstance(document, fos);
        document.open();
        // Adjusting the Logo and Title
        PdfPTable headerTable = new PdfPTable(1);  // Only one column for the logo
        headerTable.setWidthPercentage(100);

        // Logo Cell
        Image logo = Image.getInstance(getLogoStream().readAllBytes());
        logo.scaleToFit(150f, 150f);  // Adjust size while maintaining aspect ratio
        logo.setAlignment(Element.ALIGN_CENTER);  // Center the logo

        PdfPCell logoCell = new PdfPCell(logo, true);
        logoCell.setBorder(Rectangle.NO_BORDER);
        logoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerTable.addCell(logoCell);

        // Add spacing after the logo
        PdfPCell emptyCell = new PdfPCell(new Phrase(" "));
        emptyCell.setBorder(Rectangle.NO_BORDER);
        emptyCell.setMinimumHeight(50f);  // Adjust the height to add more space
        headerTable.addCell(emptyCell);

        document.add(headerTable);
        document.add(Chunk.NEWLINE);

        // "Cotización para" and "Fecha"
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);

        PdfPCell cotizacionCell = new PdfPCell(new Paragraph("Cotización para: " + nombreCliente, new Font(Font.FontFamily.HELVETICA, 12)));
        cotizacionCell.setBorder(Rectangle.NO_BORDER);
        cotizacionCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        infoTable.addCell(cotizacionCell);

        PdfPCell fechaCell = new PdfPCell(new Paragraph("Fecha: " + LocalDate.now().toString(), new Font(Font.FontFamily.HELVETICA, 12)));
        fechaCell.setBorder(Rectangle.NO_BORDER);
        fechaCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        infoTable.addCell(fechaCell);

        // Add spacing after the infoTable
        PdfPCell emptyCellInfoTable = new PdfPCell(new Phrase(" "));
        emptyCellInfoTable.setBorder(Rectangle.NO_BORDER);
        emptyCellInfoTable.setMinimumHeight(50f);  // Adjust the height to add more space
        infoTable.addCell(emptyCellInfoTable);

        document.add(infoTable);
        document.add(Chunk.NEWLINE);
        
        // Add Description
        Paragraph descLabel = new Paragraph("Descripción:", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
        descLabel.setSpacingBefore(20f);
        document.add(descLabel);
        //document.add(Chunk.NEWLINE);
        Paragraph desc = new Paragraph(descripcion, new Font(Font.FontFamily.HELVETICA, 10));
        desc.setSpacingBefore(5f);  // Increase space before the description text
        desc.setSpacingAfter(20f);   // Increase space after the description text to keep uniform spacing

        document.add(desc);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        //Tabla para los items
        PdfPTable tableItems = new PdfPTable(4);
        tableItems.setWidthPercentage(100);
        tableItems.setWidths(new int[]{3, 1, 1, 1});
        BaseColor colorTitles = new BaseColor(245, 130, 32);

        PdfPCell cellItem = new PdfPCell(new Phrase("Material", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        cellItem.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellItem.setBackgroundColor(colorTitles);
        tableItems.addCell(cellItem);

        PdfPCell cellDescripcion = new PdfPCell(new Phrase("Descripcion", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        cellDescripcion.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellDescripcion.setBackgroundColor(colorTitles);
        tableItems.addCell(cellDescripcion);

        PdfPCell cellCantidad = new PdfPCell(new Phrase("Cantidad", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        cellCantidad.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellCantidad.setBackgroundColor(colorTitles);
        tableItems.addCell(cellCantidad);

        PdfPCell cellPrecio = new PdfPCell(new Phrase("Precio", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        cellPrecio.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellPrecio.setBackgroundColor(colorTitles);
        tableItems.addCell(cellPrecio);

        double valorTotalItemsNoMostrados = 0.0;
        for (ItemCotizacion item : items) {
            //System.out.println(item);
            if(item.isEsVisible()){
            tableItems.addCell(new Phrase(item.getNombre(), new Font(Font.FontFamily.HELVETICA, 10)));
            tableItems.addCell(new Phrase(item.getDescripcion(), new Font(Font.FontFamily.HELVETICA, 10)));
            tableItems.addCell(new Phrase(String.valueOf(item.getCantidad()), new Font(Font.FontFamily.HELVETICA, 10)));
            tableItems.addCell(new Phrase(String.valueOf(currencyFormatter.format(item.getPrecioPorUnidad()*item.getCantidad())), new Font(Font.FontFamily.HELVETICA, 10)));    
            }else{
               valorTotalItemsNoMostrados+=item.getPrecioPorUnidad()*item.getCantidad();
            }
            
        }
         
        if(valorTotalItemsNoMostrados>0){
        PdfPCell cellTextValorItemsNoMostrados = new PdfPCell(new Phrase("Materiales Misceláneos", new Font(Font.FontFamily.HELVETICA, 10)));
        cellTextValorItemsNoMostrados.setColspan(3); // Span across the first three columns
        tableItems.addCell(cellTextValorItemsNoMostrados);
        
        PdfPCell cellValorItems = new PdfPCell(new Phrase(String.valueOf(currencyFormatter.format(valorTotalItemsNoMostrados)), new Font(Font.FontFamily.HELVETICA, 10)));
        tableItems.addCell(cellValorItems);
        }
        
        PdfPCell cellTextValorItems = new PdfPCell(new Phrase("Valor materiales", new Font(Font.FontFamily.HELVETICA, 10)));
        cellTextValorItems.setColspan(3); // Span across the first three columns
        tableItems.addCell(cellTextValorItems);

        PdfPCell cellValorItems = new PdfPCell(new Phrase(String.valueOf(currencyFormatter.format(precioMateriales)), new Font(Font.FontFamily.HELVETICA, 10)));
        tableItems.addCell(cellValorItems);

        document.add(tableItems);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        
        PdfPTable tableDatos = new PdfPTable(2);
        tableDatos.setWidths(new int[]{1, 1});

        PdfPCell headerCell = new PdfPCell(new Phrase("Datos de la cotización", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        headerCell.setBackgroundColor(colorTitles);
        headerCell.setColspan(2);
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableDatos.addCell(headerCell);

        tableDatos.addCell(new Phrase("Precio mano de obra", new Font(Font.FontFamily.HELVETICA, 10)));
        manoDeObra = manoDeObra + (pagoPorEmpleado * numEmpleados);
        PdfPCell precioManoObraCell = new PdfPCell(new Phrase(String.valueOf(currencyFormatter.format(manoDeObra)), new Font(Font.FontFamily.HELVETICA, 10)));
        precioManoObraCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableDatos.addCell(precioManoObraCell);

        tableDatos.addCell(new Phrase("Precio Total", new Font(Font.FontFamily.HELVETICA, 10)));
        PdfPCell precioTotalCell = new PdfPCell(new Phrase(String.valueOf(currencyFormatter.format(precioTotal)), new Font(Font.FontFamily.HELVETICA, 10)));
        precioTotalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableDatos.addCell(precioTotalCell);

        tableDatos.addCell(new Phrase("Fecha de entrega", new Font(Font.FontFamily.HELVETICA, 10)));
        PdfPCell fechaEntregaCell = new PdfPCell(new Phrase(String.valueOf(fechaEntrega), new Font(Font.FontFamily.HELVETICA, 10)));
        fechaEntregaCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableDatos.addCell(fechaEntregaCell);

        document.add(tableDatos);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        Paragraph noteLabel = new Paragraph("Nota:", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
        document.add(noteLabel);

        Paragraph note = new Paragraph("Todo trabajo se inicia con el 50% y el saldo se cancelará al momento de la entrega total del trabajo.", new Font(Font.FontFamily.HELVETICA, 10));
        document.add(note);

        document.close();
        fos.close();


    }
    private InputStream getLogoStream() {
    return getClass().getClassLoader().getResourceAsStream("practiarmarLogo.PNG");
    }
}
