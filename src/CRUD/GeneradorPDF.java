package CRUD;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.kernel.colors.ColorConstants;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GeneradorPDF {

    public static void generarStickerPDF(ATRIBUTOSVenta venta, List<ATRIBUTOSDetalleVenta> detalles, String clienteNombre, String empleadoNombre) {
        String nombreArchivo = "venta_" + venta.getId_venta() + ".pdf";

        try {
            File archivo = new File(nombreArchivo);
            PdfWriter writer = new PdfWriter(archivo);
            PdfDocument pdf = new PdfDocument(writer);
            Document doc = new Document(pdf);

            // Encabezado
            doc.add(new Paragraph("Sticker de Venta")
                    .setBold()
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER));

            doc.add(new Paragraph("Cliente: " + clienteNombre));
            doc.add(new Paragraph("Empleado: " + empleadoNombre));
            doc.add(new Paragraph("Fecha: " + venta.getFecha().toString()));
            doc.add(new Paragraph("\n"));

            // Tabla de materiales
            Table tabla = new Table(UnitValue.createPercentArray(new float[]{4, 2, 3, 3}))
                    .useAllAvailableWidth();

            tabla.addHeaderCell(new Cell().add("Material").setBackgroundColor(ColorConstants.LIGHT_GRAY));
            tabla.addHeaderCell(new Cell().add("Cant."));
            tabla.addHeaderCell(new Cell().add("P. Unitario"));
            tabla.addHeaderCell(new Cell().add("Subtotal"));

            for (ATRIBUTOSDetalleVenta d : detalles) {
                String nombreMaterial = obtenerNombreMaterialPorId(d.getId_material());
                tabla.addCell(nombreMaterial);
                tabla.addCell(String.valueOf(d.getCantidad()));
                tabla.addCell(String.format("$%.2f", d.getPrecio_unitario()));
                tabla.addCell(String.format("$%.2f", d.getSubtotal()));
            }

            doc.add(tabla);
            doc.add(new Paragraph("\n"));

            // Total
            doc.add(new Paragraph("TOTAL: $" + String.format("%.2f", venta.getTotal()))
                    .setBold()
                    .setTextAlignment(TextAlignment.RIGHT));

            doc.close();
            System.out.println("PDF generado: " + nombreArchivo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Simulación: reemplaza con consulta real si tienes acceso a la tabla de materiales
    private static String obtenerNombreMaterialPorId(int idMaterial) {
        return switch (idMaterial) {
            case 1 -> "Cemento";
            case 2 -> "Arena";
            case 3 -> "Grava";
            default -> "Material #" + idMaterial;
        };
    }
}