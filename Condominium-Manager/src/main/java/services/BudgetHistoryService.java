package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import domain.BudgetHistory;
import domain.Community;
import domain.Invoice;
import domain.Manager;
import domain.Owner;
import domain.Payment;

import repositories.BudgetHistoryRepository;

@Service
@Transactional
public class BudgetHistoryService {

	// Managed repository ---------------------------------

	@Autowired
	private BudgetHistoryRepository budgetHistoryRepository;

	// Supporting services ---------------------------------

	@Autowired
	private ManagerService managerService;

	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private NeighborsBoardService neighborsBoardService;

	// Constructors ----------------------------------------

	public BudgetHistoryService() {
		super();
	}

	// Simple CRUD methods -----------------------------------

	public BudgetHistory create(int communityId) {
		BudgetHistory result;
		Community community;
		Date initialDate;
		Date endDate;
		BudgetHistory lastBudgetHistory;
		Integer position;
		Double total;

		result = new BudgetHistory();
		community = communityService.findOne(communityId);
		lastBudgetHistory= findLastBudgetHistoryByCommunity(community.getId());
		if(lastBudgetHistory==null){
			initialDate=sumarRestarDiasFecha(new Date(System.currentTimeMillis()-1), -1);
			initialDate = new Date(System.currentTimeMillis() - 1000);
			position=1;
		}else{
			initialDate = sumarRestarDiasFecha(lastBudgetHistory.getEndDate(), +1);
			initialDate = lastBudgetHistory.getEndDate();
			position =  lastBudgetHistory.getPosition()+1;
		}
		endDate=new Date(System.currentTimeMillis()-1);
		total=community.getBudget();
		
		result.setCommunity(community);
		result.setInitialDate(initialDate);
		result.setEndDate(endDate);
		result.setPosition(position);
		result.setTotal(total);
		
		// Comprobar que es el primer budgetHistory de la comunidad
		Assert.isTrue(this.anyBudgetHistoryByCommunityAndPosition(result.getCommunity().getId(), position) == 0, "budgethistory.error.first");

		return result;
	}

	public BudgetHistory findOne(Integer budgetHistoryId) {
		BudgetHistory result;

		result = budgetHistoryRepository.findOne(budgetHistoryId);

		Assert.notNull(result);

		return result;
	}

	public Collection<BudgetHistory> findAll() {
		Collection<BudgetHistory> result;

		result = budgetHistoryRepository.findAll();

		return result;
	}

	public void save(BudgetHistory budgetHistory) {
		this.checkPrincipalManager(budgetHistory);

		budgetHistoryRepository.save(budgetHistory);
	}

	// Other business methods ------------------------------

	public void checkPrincipalManager(BudgetHistory budgetHistory) {
		Assert.notNull(budgetHistory, "error.null");

		Manager manager;
		manager = managerService.findByPrincipal();

		Assert.notNull(manager, "manager.error.null");
	}

	public Collection<BudgetHistory> findAllBudgetHistoryByCommunity(int communityId) {
		Collection<BudgetHistory> result;

		result = budgetHistoryRepository.findAllBudgetHistoryByCommunity(communityId);

		return result;
	}


	public BudgetHistory findLastBudgetHistoryByCommunity(int communityId) {
		BudgetHistory result;

		result = budgetHistoryRepository.findLastBudgetHistoryByCommunity(communityId);

		return result;
	}
	
	public BudgetHistory findLastBudgetHistoryByCommunity2(int communityId) {
		BudgetHistory result;

		result = budgetHistoryRepository.findLastBudgetHistoryByCommunity2(communityId);

		return result;
	}
	
	public Integer anyBudgetHistoryByCommunityAndPosition(int communityId ,int position) {
		Integer result;

		result = budgetHistoryRepository.anyBudgetHistoryByCommunityAndPosition(communityId,position);

		return result;
	}
	
	public Date sumarRestarDiasFecha(Date fecha, int dias){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
		return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
	}
	 
	public void PDF(@RequestParam int budgetHistoryId, HttpServletResponse response) throws Exception{
		Document document = new Document();
		BudgetHistory budgetHistory;
		Integer communityId;
		Double oldBudget;
		Date initialDate;
		String printInitialDate;
		Date endDate;
		String printEndDate;
		Double total;
		String name;
		Collection<Invoice> unpaidInvoices;
		Integer numberOfUnpaidInvoices;
		Collection<Owner> ownersOfUnpaidInvoices;
		Integer numberOfOwnersOfUnpaidInvoices;
		Collection<Invoice> paidInvoices;
		Integer numberOfPaidInvoices;
		Collection<Owner> ownersOfPaidInvoices;
		Integer numberOfOwnersOfPaidInvoices;
		Double unpaid;
		Double paid;
		Collection<Payment> payments;
		Double payment;
		
		budgetHistory=findOne(budgetHistoryId);
		communityId=budgetHistory.getCommunity().getId();
		oldBudget=0.0;
		if(budgetHistory.getPosition()>1){
			oldBudget=findLastBudgetHistoryByCommunity2(communityId).getTotal();
		}
		initialDate=budgetHistory.getInitialDate();
		printInitialDate=neighborsBoardService.YYYYMMDDtoDDMMYYYY(initialDate);
		endDate=budgetHistory.getEndDate();
		printEndDate=neighborsBoardService.YYYYMMDDtoDDMMYYYY(endDate);
		total=budgetHistory.getTotal();
		name=budgetHistory.getCommunity().getName();
		unpaidInvoices=invoiceService.findAllUnpaidInvoicesBetweenDates(communityId);
		numberOfUnpaidInvoices=invoiceService.findNumberOfUnpaidInvoicesBetweenDates(communityId);
		ownersOfUnpaidInvoices=invoiceService.findAllOwnersOfUnpaidInvoicesBetweenDates(communityId);
		numberOfOwnersOfUnpaidInvoices=ownersOfUnpaidInvoices.size();
		paidInvoices=invoiceService.findAllPaidInvoicesBetweenDates(communityId, initialDate, endDate);
		numberOfPaidInvoices=invoiceService.findNumberOfPaidInvoicesBetweenDates(communityId, initialDate, endDate);
		ownersOfPaidInvoices=invoiceService.findAllOwnersOfPaidInvoicesBetweenDates(communityId, initialDate, endDate);
		numberOfOwnersOfPaidInvoices=ownersOfPaidInvoices.size();
		unpaid=invoiceService.findUnpaidAmountBetweenDates(communityId);
		if(unpaid==null){
			unpaid=0.0;
		}
		paid=invoiceService.findPaidAmountBetweenDates(communityId, initialDate, endDate);
		if(paid==null){
			paid=0.0;
		}
		payments=paymentService.findAllPaymentsBetweenDates(communityId, initialDate, endDate);
		payment=paymentService.findPaymentAmountBetweenDates(communityId, initialDate, endDate);
		if(payment==null){
			payment=0.0;
		}
		
        response.setContentType("application/pdf");
        try{
        	PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			
	        Font fontTitle=new Font();
	        fontTitle.setSize(20);
	        fontTitle.setStyle(Font.BOLD | Font.UNDERLINE);
	        
	        Font fontTitle2=new Font();
	        fontTitle2.setSize(17);
	        fontTitle2.setStyle(Font.BOLD | Font.UNDERLINE);
	        
	        Font red=new Font();
	        red.setColor(BaseColor.RED);
	        
	        Font fontSubTitle=new Font();
	        fontSubTitle.setSize(15);
	        fontSubTitle.setStyle(Font.BOLD);
	        
	        Font fontBlueSubTitle=new Font();
	        fontSubTitle.setSize(15);
	        fontSubTitle.setStyle(Font.BOLD);
	        fontBlueSubTitle.setColor(BaseColor.BLUE);
	        
	        Image imagen = Image.getInstance(this.getClass().getClassLoader().getResource("minilogo.png"));
	        imagen.setAbsolutePosition(480f, 735f);
	        imagen.scaleToFit(80, 80);
	        document.add(imagen);
	        
	        if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
	        	document.add(new Paragraph("Budget history from "+printInitialDate+" to "+printEndDate, fontTitle));
	        }else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
	        	document.add(new Paragraph("Historial presup. desde "+printInitialDate+" hasta "+printEndDate, fontTitle2));
	        }
	        document.add(new Paragraph("\n"));
	        
	        if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
	        	document.add(new Paragraph("Community's name: ", fontSubTitle));
	        }else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
	        	document.add(new Paragraph("Nombre de la comunidad: ", fontSubTitle));
	        }
	        document.add(new Paragraph("\n"));
	        document.add(new Paragraph(name));
	        document.add(new Paragraph("\n"));
	        document.add(new Paragraph("\n"));
	        
	        
	        if(budgetHistory.getPosition()>1){
	        	if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
		        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
	        		document.add(new Paragraph("Budget from previous report: ",fontSubTitle));
	        		document.add(new Paragraph("\n"));
	        	}else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
		        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
	        		document.add(new Paragraph("Presupuesto del informe anterior: ",fontSubTitle));
	        		document.add(new Paragraph("\n"));
	        	}	
		        	
		        	if(oldBudget<0.0){
		        		document.add(new Paragraph(+oldBudget+" \u20ac",red));
		        	}else{
		        		document.add(new Paragraph(+oldBudget+" \u20ac"));
		        	}
		        	document.add(new Paragraph("\n"));
		        
	        }
	        if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
	        	document.add(new Paragraph("Payments:", fontSubTitle));
	        }else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
	        	document.add(new Paragraph("Pagos:", fontSubTitle));
	        }
	        document.add(new Paragraph("\n"));
	    
	        
	       	        
	       
	        	if(payments.size()==0){
	        		if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
	    	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
	        			document.add(new Paragraph("There are not payments for this community in this time interval"));
	        		}else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
	    	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
	    	        	document.add(new Paragraph("No existen pagos de esta comunidad en este intervalo de tiempos"));
	    	        }
	        	}else{
  
	    	        PdfPTable table = new PdfPTable(3);
	    	        table.setWidthPercentage(100);
	    	        table.setSpacingBefore(0f);
	    	        table.setSpacingAfter(0f);
	    	        
	    	      
	    	        
	    	        if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
	    	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
	    	        	PdfPCell cell1 = new PdfPCell(new Phrase("Date"));
	    	            cell1.setColspan(1);
	    	            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	            cell1.setPadding(5.0f);
	    	            cell1.setBackgroundColor(new BaseColor(204, 229, 255));
	    	            table.addCell(cell1);
	    	        	
	    	            PdfPCell cell2 = new PdfPCell(new Phrase("Description"));
	    	            cell2.setColspan(1);
	    	            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	            cell2.setPadding(5.0f);
	    	            cell2.setBackgroundColor(new BaseColor(204, 229, 255));
	    	            table.addCell(cell2);
	    	            
	    	            PdfPCell cell3 = new PdfPCell(new Phrase("Amount"));
	    	            cell3.setColspan(1);
	    	            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	            cell3.setPadding(5.0f);
	    	            cell3.setBackgroundColor(new BaseColor(204, 229, 255));
	    	            table.addCell(cell3);
	    	            
	        		}else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
	    	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
	        			PdfPCell cell1 = new PdfPCell(new Phrase("Fecha"));
	    	            cell1.setColspan(1);
	    	            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	            cell1.setPadding(5.0f);
	    	            cell1.setBackgroundColor(new BaseColor(204, 229, 255));
	    	            table.addCell(cell1);
	    	        	
	    	            PdfPCell cell2 = new PdfPCell(new Phrase("Descripción"));
	    	            cell2.setColspan(1);
	    	            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	            cell2.setPadding(5.0f);
	    	            cell2.setBackgroundColor(new BaseColor(204, 229, 255));
	    	            table.addCell(cell2);
	    	            
	    	            PdfPCell cell3 = new PdfPCell(new Phrase("Cantidad"));
	    	            cell3.setColspan(1);
	    	            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	            cell3.setPadding(5.0f);
	    	            cell3.setBackgroundColor(new BaseColor(204, 229, 255));
	    	            table.addCell(cell3);
	        	        
	    	        }
	    	        for(Payment p:payments){
	        		
	    	         table.addCell(p.getPaymentMoment().toString());
	    	         table.addCell(p.getDescription());
	    	         table.addCell(p.getAmount()+" \u20ac");
	    	        }
	    	        document.add(table);
	        	}
	      
	        document.add(new Paragraph("\n"));
	        if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
	        	Paragraph paymentParagraph=new Paragraph("Total amount: "+payment+" \u20ac");
		        paymentParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
		        document.add(paymentParagraph);
	        }else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
	        	Paragraph paymentParagraph=new Paragraph("Cantidad total: "+payment+" \u20ac");
		        paymentParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
		        document.add(paymentParagraph);
	        }
	        document.add(new Paragraph("\n"));
	        if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
	        	document.add(new Paragraph("Paid invoices:", fontSubTitle));
	        }else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
	        	document.add(new Paragraph("Facturas pagadas:", fontSubTitle));
	        }
	        document.add(new Paragraph("\n"));
	       
	        	if(payments.size()==0){
	        		if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
	    	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
	        			document.add(new Paragraph("There are not paid invoices for this community in this time interval"));
	        		}else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
	    	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
	    	        	document.add(new Paragraph("No hay facturas pagadas de esta comunidad en este intervalo de tiempo"));
	    	        }
	        	}else{

	    	        PdfPTable table2 = new PdfPTable(3);
	    	        table2.setWidthPercentage(100);
	    	        table2.setSpacingBefore(0f);
	    	        table2.setSpacingAfter(0f);
	    	        

	    	        
	    	        if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
	    	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
	    	        	PdfPCell cell1 = new PdfPCell(new Phrase("Payment moment"));
	    	            cell1.setColspan(1);
	    	            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	            cell1.setPadding(5.0f);
	    	            cell1.setBackgroundColor(new BaseColor(204, 229, 255));
	    	            table2.addCell(cell1);
	    	        	
	    	            PdfPCell cell2 = new PdfPCell(new Phrase("Property"));
	    	            cell2.setColspan(1);
	    	            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	            cell2.setPadding(5.0f);
	    	            cell2.setBackgroundColor(new BaseColor(204, 229, 255));
	    	            table2.addCell(cell2);
	    	            
	    	            PdfPCell cell3 = new PdfPCell(new Phrase("Amount"));
	    	            cell3.setColspan(1);
	    	            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	            cell3.setPadding(5.0f);
	    	            cell3.setBackgroundColor(new BaseColor(204, 229, 255));
	    	            table2.addCell(cell3);
	    	            
	        		}else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
	    	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
	        			PdfPCell cell1 = new PdfPCell(new Phrase("Fecha de pago"));
	    	            cell1.setColspan(1);
	    	            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	            cell1.setPadding(5.0f);
	    	            cell1.setBackgroundColor(new BaseColor(204, 229, 255));
	    	            table2.addCell(cell1);
	    	        	
	    	            PdfPCell cell2 = new PdfPCell(new Phrase("Propiedad"));
	    	            cell2.setColspan(1);
	    	            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	            cell2.setPadding(5.0f);
	    	            cell2.setBackgroundColor(new BaseColor(204, 229, 255));
	    	            table2.addCell(cell2);
	    	            
	    	            PdfPCell cell3 = new PdfPCell(new Phrase("Cantidad"));
	    	            cell3.setColspan(1);
	    	            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	            cell3.setPadding(5.0f);
	    	            cell3.setBackgroundColor(new BaseColor(204, 229, 255));
	    	            table2.addCell(cell3);
	        	        
	    	        }
	    	        
	        	for(Invoice pi:paidInvoices){
	        		table2.addCell(pi.getPaymentMoment().toString());
	        		if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
	    	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
	        			table2.addCell("Block: "+pi.getProperty().getBlock().getNumber()+", Floor: "+pi.getProperty().getFloor()+", Door: "+pi.getProperty().getDoor());
	        		}else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
	    	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
	        			table2.addCell("Bloque: "+pi.getProperty().getBlock().getNumber()+", Planta: "+pi.getProperty().getFloor()+", Puerta: "+pi.getProperty().getDoor());	    	        }
	        		table2.addCell(	pi.getAmount()+" \u20ac");
	        	}
	        	document.add(table2);
	        }
	        document.add(new Paragraph("\n"));
	        if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
	        	Paragraph numberOfPaidInvoicesParagraph=new Paragraph("There are "+numberOfPaidInvoices+" paid invoices by "+numberOfOwnersOfPaidInvoices+" owners");
		        numberOfPaidInvoicesParagraph.setAlignment(Paragraph.ALIGN_LEFT);
		        document.add(numberOfPaidInvoicesParagraph);
	        }else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
	        	Paragraph numberOfPaidInvoicesParagraph=new Paragraph("Hay "+numberOfPaidInvoices+" facturas pagadas por "+numberOfOwnersOfPaidInvoices+" propietarios");
		        numberOfPaidInvoicesParagraph.setAlignment(Paragraph.ALIGN_LEFT);
		        document.add(numberOfPaidInvoicesParagraph);
	        }
	        document.add(new Paragraph("\n"));
	        if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
	        	Paragraph paidParagraph=new Paragraph("Total amount: "+paid+" \u20ac");
	        	paidParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
		        document.add(paidParagraph);
	        }else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
	        	Paragraph paidParagraph=new Paragraph("Cantidad total: "+paid+" \u20ac");
	        	paidParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
		        document.add(paidParagraph);
	        }
	        document.add(new Paragraph("\n"));
	        if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
	        	document.add(new Paragraph("Unpaid invoices:", fontSubTitle));
	        }else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
	        	document.add(new Paragraph("Facturas impagadas:", fontSubTitle));
	        }
	        document.add(new Paragraph("\n"));
	        	if(unpaidInvoices.size()==0){
	        		if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
	    	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
	        			document.add(new Paragraph("There are not unpaid invoices for this community in this time interval"));
	        		}else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
	    	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
	    	        	document.add(new Paragraph("No hay facturas impagadas para esta comunidad en este intervalo de tiempo"));	    	        }
	        	}else{
	        		
	        		 PdfPTable table3 = new PdfPTable(3);
		    	        table3.setWidthPercentage(100);
		    	        table3.setSpacingBefore(0f);
		    	        table3.setSpacingAfter(0f);
		    	        

		    	        
		    	        if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
		    	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
		    	        	PdfPCell cell1 = new PdfPCell(new Phrase("Creation moment"));
		    	            cell1.setColspan(1);
		    	            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    	            cell1.setPadding(5.0f);
		    	            cell1.setBackgroundColor(new BaseColor(204, 229, 255));
		    	            table3.addCell(cell1);
		    	        	
		    	            PdfPCell cell2 = new PdfPCell(new Phrase("Property"));
		    	            cell2.setColspan(1);
		    	            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		    	            cell2.setPadding(5.0f);
		    	            cell2.setBackgroundColor(new BaseColor(204, 229, 255));
		    	            table3.addCell(cell2);
		    	            
		    	            PdfPCell cell3 = new PdfPCell(new Phrase("Amount"));
		    	            cell3.setColspan(1);
		    	            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		    	            cell3.setPadding(5.0f);
		    	            cell3.setBackgroundColor(new BaseColor(204, 229, 255));
		    	            table3.addCell(cell3);
		    	            
		        		}else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
		    	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
		        			PdfPCell cell1 = new PdfPCell(new Phrase("Fecha de creación"));
		    	            cell1.setColspan(1);
		    	            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    	            cell1.setPadding(5.0f);
		    	            cell1.setBackgroundColor(new BaseColor(204, 229, 255));
		    	            table3.addCell(cell1);
		    	        	
		    	            PdfPCell cell2 = new PdfPCell(new Phrase("Propiedad"));
		    	            cell2.setColspan(1);
		    	            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		    	            cell2.setPadding(5.0f);
		    	            cell2.setBackgroundColor(new BaseColor(204, 229, 255));
		    	            table3.addCell(cell2);
		    	            
		    	            PdfPCell cell3 = new PdfPCell(new Phrase("Cantidad"));
		    	            cell3.setColspan(1);
		    	            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		    	            cell3.setPadding(5.0f);
		    	            cell3.setBackgroundColor(new BaseColor(204, 229, 255));
		    	            table3.addCell(cell3);
		        	        
		    	        }
		    	        
		    	        for(Invoice un:unpaidInvoices){
		    	        	table3.addCell(un.getCreationMoment().toString());
		    	        	if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
		    	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
		    	        		table3.addCell("Block: "+un.getProperty().getBlock().getNumber()+", Floor: "+un.getProperty().getFloor()+", Door: "+un.getProperty().getDoor());
		    	        	}else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
		    	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
		    	        		table3.addCell("Bloque: "+un.getProperty().getBlock().getNumber()+", Planta: "+un.getProperty().getFloor()+", Puerta: "+un.getProperty().getDoor());
		    	        		}
		    	        	table3.addCell(un.getAmount()+" \u20ac");
		        	}
		        	document.add(table3);
	        }
	        document.add(new Paragraph("\n"));
	        if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
	        	Paragraph numberOfUnpaidInvoicesParagraph=new Paragraph("There are "+numberOfUnpaidInvoices+" unpaid invoices by "+numberOfOwnersOfUnpaidInvoices+" owners");
		        numberOfUnpaidInvoicesParagraph.setAlignment(Paragraph.ALIGN_LEFT);
		        document.add(numberOfUnpaidInvoicesParagraph);
	        }else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
	        	Paragraph numberOfUnpaidInvoicesParagraph=new Paragraph("Hay "+numberOfUnpaidInvoices+" facturas impagadas por "+numberOfOwnersOfUnpaidInvoices+" propietarios");
		        numberOfUnpaidInvoicesParagraph.setAlignment(Paragraph.ALIGN_LEFT);
		        document.add(numberOfUnpaidInvoicesParagraph);
	        }
	        document.add(new Paragraph("\n"));
	        if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
	        	Paragraph unpaidParagraph=new Paragraph("Total amount: "+unpaid+" \u20ac");
		        unpaidParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
		        document.add(unpaidParagraph);
	        }else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
	        	Paragraph unpaidParagraph=new Paragraph("Cantidad total: "+unpaid+" \u20ac");
		        unpaidParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
		        document.add(unpaidParagraph);
	        }
	        document.add(new Paragraph("\n"));
	        document.add(new Paragraph("\n"));
	        document.add(new Paragraph("\n"));
	        document.add(new Paragraph("\n"));
	        if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
	        	Paragraph totalParagraph=new Paragraph("Total budget: "+total+" \u20ac",fontSubTitle);
		        if(total<0.0){
		        	totalParagraph.setFont(red);
		        }
		        totalParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
		        document.add(totalParagraph);
	        }else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
	        	Paragraph totalParagraph=new Paragraph("Presupuesto total: "+total+" \u20ac");
		        if(total<0.0){
		        	totalParagraph.setFont(red);
		        }
		        totalParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
		        document.add(totalParagraph);
	        }
	        document.close();
        }catch(Exception exception){
        	throw new Exception("The file could not be generated");
        }
	}
	
}
