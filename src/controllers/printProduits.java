package controllers;


import Main.Shop;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import static controllers.Controller.targetFolder;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import models.Product;
 
public class printProduits extends Controller{
 
    private String RESULT;
    public static final int numberColumn = 7, numberElement = 8;
    private int taille;
    private String str;
    
    private ArrayList<Product> print;
    private String[] tab = new String[]{"a","b","c","d","e","f","g","h","i","j","k"
                             ,"l","m","n","o","p","q","r","s","t","u"
                             ,"v","w","x","y","z","0","1","2","3","4"
                             ,"5","6","7","8","9","-"};
    
    
    public printProduits(){}

    public ArrayList<Product> getPrint() {
        return print;
    }

    public void setPrint(ArrayList<Product> print) {
        this.print = print;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    
 
    class TableHeader extends PdfPageEventHelper {
        String header;
        PdfTemplate total;
 
        public void setHeader(String header) {
            this.header = header;
        }
 
        @Override
        public void onOpenDocument(PdfWriter writer, Document document) {
            total = writer.getDirectContent().createTemplate(30, 16);
        }
 
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfPTable table = new PdfPTable(3);
            try {
                if(document.getPageSize().getRotation()==0){
                    document.setPageSize(PageSize.A4.rotate());
                    if(writer.getPageNumber()!=1){
                        table.setWidths(new int[]{24, 24, 2});
                        table.setTotalWidth(527);
                        table.setLockedWidth(true);
                        table.getDefaultCell().setFixedHeight(20);
                        table.getDefaultCell().setBorder(Rectangle.BOTTOM);
                        table.addCell(header);
                        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(String.format("Page %d /", writer.getPageNumber()));
                        PdfPCell cell = new PdfPCell(Image.getInstance(total));
                        cell.setBorder(Rectangle.BOTTOM);
                        table.addCell(cell);
                        table.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
                    }
                }
                else{
                        table.setWidths(new int[]{24, 24, 2});
                        table.setTotalWidth(748);
                        table.setLockedWidth(true);
                        table.getDefaultCell().setFixedHeight(20);
                        table.getDefaultCell().setBorder(Rectangle.BOTTOM);
                        table.addCell(header);
                        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(String.format("Page %d /", writer.getPageNumber()));
                        PdfPCell cell = new PdfPCell(Image.getInstance(total));
                        cell.setBorder(Rectangle.BOTTOM);
                        table.addCell(cell);
                        table.writeSelectedRows(0, -1, 50, 560, writer.getDirectContent());
                }
            }
            catch(DocumentException de) {
                throw new ExceptionConverter(de);
            }
        }
 
        @Override
        public void onCloseDocument(PdfWriter writer, Document document) {
            ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
                    new Phrase(String.valueOf(writer.getPageNumber() - 1)),
                    2, 2, 0);
        }
    }
     
    public void createPdf(String filename){
        int q = taille / numberElement;
        int r = taille % numberElement;
        int pages;
        if(r == 0) pages = q + 1;
        else pages = q + 2;
        
        System.out.println("   "+pages);
        
        try {
            FontSelector selector = new FontSelector();
            Font f1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 20);
            selector.addFont(f1);
            
            Document document = new Document(PageSize.A4, 36, 36, 60, 36);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(RESULT));
            TableHeader event = new TableHeader();
            writer.setPageEvent(event);
            document.open();
            for(int j = pages; j > 0; j--) {
                if(j==pages){
                    Image image = Image.getInstance(new URL(logo));
                    image.scaleAbsolute(200f, 200f);//550f, 750f);
                    image.setAlignment(Image.ALIGN_CENTER);
                    document.add(image);
                    
                    Paragraph paDate = new Paragraph(dateActuelle());
                    paDate.setAlignment(Paragraph.ALIGN_CENTER);
                    paDate.setSpacingAfter(200f);
                    
                    Paragraph paMagasin = new Paragraph(magasin);
                    paMagasin.setAlignment(Paragraph.ALIGN_CENTER);
                    paDate.setSpacingAfter(30f);
                    
                    Paragraph paAddresse = new Paragraph("Email : "+addresse);
                    paAddresse.setAlignment(Paragraph.ALIGN_CENTER);
                    paDate.setSpacingAfter(30f);
                    
                    Paragraph paTel = new Paragraph("Téléphone : "+telephone);
                    paTel.setAlignment(Paragraph.ALIGN_CENTER);
                    paDate.setSpacingAfter(30f);
                    
                    Paragraph paLieu = new Paragraph("Site : "+lieu);
                    paLieu.setAlignment(Paragraph.ALIGN_CENTER);
                    paDate.setSpacingAfter(30f);
                    
                    document.add(paDate);
                    document.add(paMagasin);
                    document.add(paAddresse);
                    document.add(paTel);
                    document.add(paLieu);
                    document.newPage();
                }
                else{
                    affichage(document, selector, str, pages - 1 - j);
                }
            }
            document.close();
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    
    private String dateActuelle(){
        Date actuelle = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        
        String dat = dateFormat.format(actuelle);
        return dat;
    }
    
    public String generateName(){
        String nomPdf = "";
        for(int i = 0; i < 16; i++){
            int rand = (int) (Math.random()*(tab.length));
            nomPdf += tab[rand];
        }
        Date actuelle = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String dat = dateFormat.format(actuelle);
        nomPdf += dat+".pdf";
        nomPdf = nomPdf.replace('\\', '/');
        return nomPdf;
    }
    
    private void affichage(Document document, FontSelector selector, String str, int k) throws DocumentException, BadElementException, IOException{
        Phrase phr = selector.process(str);
        Paragraph p = new Paragraph(phr);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        p.setSpacingAfter(50f);
        
        PdfPTable table = new PdfPTable(7);
        table.setWidths(new int[]{1,2,1,1,1,2,1});
        PdfPCell cell = null;
        if(k*numberElement < taille){
            cell = new PdfPCell(new Phrase("Code produit"));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Nom"));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Code fournisseur"));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Quantité"));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Prix"));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Categorie"));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Photo"));
            table.addCell(cell);
            cell = new PdfPCell();
            cell.setRowspan(2);
        }
        
        for(int i = k*numberElement; i < k*numberElement + numberElement; i++){
            for(int aw = 0; aw < numberColumn; aw++){
                if(aw % numberColumn == 0){
                    if(i < taille) cell.setPhrase(new Phrase(print.get(i).getCodeString()));
                }
                else if(aw % numberColumn == 1){
                    if(i < taille) cell.setPhrase(new Phrase(print.get(i).getNom()));
                }
                else if(aw % numberColumn == 2){
                    if(i < taille) cell.setPhrase(new Phrase(print.get(i).getCodefournisseur()));
                }
                else if(aw % numberColumn == 3){
                    if(i < taille) cell.setPhrase(new Phrase(print.get(i).getQuantite()+""));
                }
                else if(aw % numberColumn == 4){
                    if(i < taille) cell.setPhrase(new Phrase(print.get(i).getPrix()+""));
                }
                else if(aw % numberColumn == 5){
                    if(i < taille) cell.setPhrase(new Phrase(print.get(i).getNomcategorie()));
                }
                else{
                    if(i < taille) {
                        String pathImageInServer = targetFolder+print.get(i).getCodeProduit()+"/"+print.get(i).getPictures().get(0);
                        URL urlImage = new URL(pathImageInServer);
                        Image img = Image.getInstance(urlImage);
                        cell.setImage(img);
                    }
                }
                if( i < taille) table.addCell(cell);
                else{
                    break;
                }
            }
        }
        document.add(p);
        document.add(table);
        document.newPage();
    }
    
    public void print(){
        taille = print.size();
        createPdf(RESULT);
    }
}