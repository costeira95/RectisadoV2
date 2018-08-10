/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rectisadov2.model.PDF;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.control.TableView;
import rectisadov2.model.Cliente;
/**
 *
 * @author Costeira
 */
public class GerarPDFTodos extends Document {
    private ArrayList<String> cells;
    private PdfPTable table;
    private String nome;
    
    public GerarPDFTodos(TableView tableView, String nome, LocalDate dataInicio, LocalDate dataFim, String tipoUtilizador) throws IOException {
        cells = new ArrayList<>();
        this.nome = nome;
        table = new PdfPTable(new float[]{2, 3, 7, 2});
        try {
            criarTopo();
            criarTitulos();
            createCell("Extracto", 15, 6, Rectangle.BOTTOM | Rectangle.TOP, Element.ALIGN_CENTER);   
            Iterator<String> itCells = cells.iterator();
            while(itCells.hasNext()) {
                createCell(itCells.next(), 10, 0, Rectangle.BOTTOM, Element.ALIGN_CENTER);
            }
            
            Iterator<Cliente> it = tableView.getItems().iterator();     
            while(it.hasNext()) {
                criarCelulaPorCliente(it.next(), dataInicio, dataFim, tipoUtilizador);
            }
            table.setWidthPercentage(100);

            add(table);
            close();
            Desktop.getDesktop().open(new File("documentoTodos.pdf"));
        } catch (FileNotFoundException | DocumentException ex) {
            //new Dialogo("Gerar Pdf", "Já tem um ficheiro PDF aberto, por favor encerre para gerar um novo!", "/rectisado/images/error.png");
        }
        
    }
    
    /***********************************************************
     * Cria uma celula para uma compra
     * @param compra 
     */
    private void criarCelulaPorCliente(Cliente cliente, LocalDate dataInicio, LocalDate dataFim, String tipoUtilizador) {
        createCell(cliente.getNome(), 5, 0, 0, Element.ALIGN_CENTER);
        createCell(Double.toString(cliente.totalDebito(tipoUtilizador, dataInicio, dataFim)), 5, 0, 0, Element.ALIGN_CENTER);
        createCell(Double.toString(cliente.totalCredito(tipoUtilizador, dataInicio, dataFim)), 5, 0, 0, Element.ALIGN_CENTER);
        createCell(Double.toString(cliente.SaldoCliente(tipoUtilizador, dataInicio, dataFim)), 5, 0, 0, Element.ALIGN_CENTER);
    }
    
    /*******************************************************
     * Cria uma celula para o dumento
     * @param text
     * @param padding
     * @param colSpan
     * @param border 
     */
    
    private void createCell(String text,int padding, int colSpan, int border, int aligment) {
        PdfPCell cell = new PdfPCell(new Paragraph(text, new Font(Font.FontFamily.HELVETICA, 10)));
        cell.setColspan(colSpan);
        cell.setBorder(border);
        cell.setPaddingTop(padding);
        cell.setHorizontalAlignment(aligment);
        table.addCell(cell);
    }
    
    /***********************************************
     * Cria o topo do documento
     * @throws FileNotFoundException
     * @throws DocumentException 
     */
    
    private void criarTopo() throws FileNotFoundException, DocumentException {
        PdfWriter.getInstance(this, new FileOutputStream("documentoTodos.pdf"));
        open();
        add(new Phrase("Rectisado", new Font(Font.FontFamily.HELVETICA, 20)));
        add(new Paragraph("Cliente: " + nome,
                     new Font(Font.FontFamily.HELVETICA, 12)));
        add(new Paragraph("Data: " + LocalDate.now(), new Font(Font.FontFamily.HELVETICA, 12)));
        add(new Paragraph(" "));
    }
    
    /*****************************
     * 
     * Função para preencher o array
     * com titulos
     */
    private void criarTitulos() {
        cells.add("Nome");
        cells.add("Debito");
        cells.add("Credito");
        cells.add("Saldo");
    }
    
}
