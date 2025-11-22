package CRUD;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GeneradorPDF {

    /**
     * Genera un PDF con el detalle completo de la venta:
     * - Encabezado con cliente, empleado y fecha
     * - Tabla con todos los materiales vendidos (cada fila es un ATRIBUTOSVenta)
     * - Total general al final
     */
    public static void generarStickerPDF(int idVenta, String cliente, String empleado, java.sql.Date fecha, List<ATRIBUTOSVenta> detalle) {
        String nombreArchivo = "venta_" + idVenta + ".pdf";

        try {
            File archivo = new File(nombreArchivo);
            PdfWriter writer = new PdfWriter(archivo);
            PdfDocument pdf = new PdfDocument(writer);
            Document doc = new Document(pdf);

            // --- Encabezado ---
            doc.add(new Paragraph("Sticker de Venta")
                    .setBold()
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER));

            doc.add(new Paragraph("Cliente: " + cliente));
            doc.add(new Paragraph("Empleado: " + empleado));
            doc.add(new Paragraph("Fecha: " + fecha.toString()));
            doc.add(new Paragraph("\n"));

            // --- Tabla de materiales ---
            Table tabla = new Table(UnitValue.createPercentArray(new float[]{4, 2, 3, 3}))
                    .useAllAvailableWidth();

            tabla.addHeaderCell(new Cell().add(new Paragraph("Material")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Cant.")));
            tabla.addHeaderCell(new Cell().add(new Paragraph("P. Unitario")));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Subtotal")));

            double totalGeneral = 0;

            for (ATRIBUTOSVenta v : detalle) {
                tabla.addCell(v.getNombre_material());
                tabla.addCell(String.valueOf(v.getCantidad()));
                tabla.addCell(String.format("$%.2f", v.getPrecio_unitario()));
                tabla.addCell(String.format("$%.2f", v.getSubtotal()));
                totalGeneral += v.getSubtotal();
            }

            doc.add(tabla);
            doc.add(new Paragraph("\n"));

            // --- Total general ---
            doc.add(new Paragraph("TOTAL: $" + String.format("%.2f", totalGeneral))
                    .setBold()
                    .setTextAlignment(TextAlignment.RIGHT));

            doc.close();
            System.out.println("PDF generado: " + nombreArchivo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}