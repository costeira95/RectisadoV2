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
import rectisadov2.model.Compras;
import rectisadov2.model.ECredito;
import rectisadov2.model.Gestor;
//import rectisado.view.Dialogo;
/**
 *
 * @author Costeira
 */
public class GerarPDF extends Document {
    private ArrayList<String> cells;
    private double totalCredito;
    private double totalDebito;
    private PdfPTable table;
    private String nome;
    private int i;
    
    public GerarPDF(TableView tableView, String nome) throws IOException {
        cells = new ArrayList<>();
        i = 0;
        totalCredito = 0;
        totalDebito = 0;
        this.nome = nome;
        table = new PdfPTable(new float[]{2, 3, 7, 2, 2, 3});
        try {
            criarTopo();
            criarTitulos();
            createCell("Extracto", 15, 6, Rectangle.BOTTOM | Rectangle.TOP, Element.ALIGN_CENTER);   
            Iterator<String> itCells = cells.iterator();
            while(itCells.hasNext()) {
                createCell(itCells.next(), 10, 0, Rectangle.BOTTOM, Element.ALIGN_CENTER);
            }
            
            Iterator it = tableView.getItems().iterator();
            Compras compras = new Compras();
            
            while(it.hasNext()) {
                compras = (Compras)it.next();
                criarCelulaPorCompra(compras);
                if(compras.getTipoCredito().equals(ECredito.CREDITO)) totalCredito+= compras.getValor();
                else if(compras.getTipoCredito().equals(ECredito.DEBITO)) totalDebito+= compras.getValor();
            }            
            createCell("Total Crédito: " + String.valueOf(totalCredito), 20, 6, Rectangle.TOP | Rectangle.BOTTOM, 0);
            createCell("Total Débito: " + String.valueOf(totalDebito), 5, 6, 0, 0);
            table.setWidthPercentage(100);

            add(table);
            close();
            Desktop.getDesktop().open(new File("documento.pdf"));
        } catch (FileNotFoundException | DocumentException ex) {
            //new Dialogo("Gerar Pdf", "Já tem um ficheiro PDF aberto, por favor encerre para gerar um novo!", "/rectisado/images/error.png");
        }
        
    }
    
    /***********************************************************
     * Cria uma celula para uma compra
     * @param compra 
     */
    private void criarCelulaPorCompra(Compras compra) {
        createCell(String.valueOf(++i), 10, 0, 0, Element.ALIGN_CENTER);
        createCell(compra.getData().toString(), 5, 0, 0, Element.ALIGN_CENTER);
        createCell(compra.getDescricao(), 5, 0, 0, Element.ALIGN_CENTER);
        createCell(String.valueOf(compra.getRequesicao()), 5, 0, 0, Element.ALIGN_CENTER);
        createCell(compra.getTipoCredito().toString(), 5, 0, 0, Element.ALIGN_CENTER);
        createCell(String.valueOf(compra.getValor()), 5, 0, 0, Element.ALIGN_CENTER);
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
        PdfWriter.getInstance(this, new FileOutputStream("documento.pdf"));
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
        cells.add("Reg.");
        cells.add("Data");
        cells.add("Desc");
        cells.add("Requ.");
        cells.add("Trans.");
        cells.add("Valor");
    }
    
}
