package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;

import domain.NeighborsBoard;
import domain.Block;
import domain.Manager;
import domain.Owner;
import forms.NeighborsBoardForm;

import repositories.NeighborsBoardRepository;

@Service
@Transactional
public class NeighborsBoardService {

	// Managed repository ---------------------------------

	@Autowired
	private NeighborsBoardRepository neighborsBoardRepository;

	// Supporting services ---------------------------------
	
	@Autowired
	private BlockService blockService;
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private OwnerService ownerService;
	
	// Constructors ----------------------------------------

	public NeighborsBoardService() {
		super();
	}

	// Simple CRUD methods -----------------------------------

	public NeighborsBoard create(int blockId) {
		NeighborsBoard result;
		Block block;
		Collection<Owner> attendants;
		Date date;

		result = new NeighborsBoard();
		block = blockService.findOne(blockId);
		attendants = new ArrayList<Owner>();
		date=new Date(System.currentTimeMillis()-1);

		result.setBlock(block);
		result.setAttendants(attendants);
		result.setDate(date);

		return result;
	}


	public NeighborsBoard findOne(Integer neighborsBoardId) {
		NeighborsBoard result;

		result = neighborsBoardRepository.findOne(neighborsBoardId);

		Assert.notNull(result);

		return result;
	}

	public Collection<NeighborsBoard> findAll() {
		Collection<NeighborsBoard> result;

		result = neighborsBoardRepository.findAll();

		return result;
	}

	public void save(NeighborsBoard neighborsBoard) {
		Assert.notNull(neighborsBoard, "error.null");
		Assert.notNull(neighborsBoard.getBlock(), "error.null");
		this.checkPrincipal(neighborsBoard);
		
		Date date;
		
		date=new Date(System.currentTimeMillis()-1);
		
		neighborsBoard.setDate(date);

		neighborsBoardRepository.save(neighborsBoard);
	}
	

	
	
	// Other business methods ------------------------------

	public void checkPrincipal(NeighborsBoard neighborsBoard) {
		Assert.notNull(neighborsBoard, "error.null");

		Manager manager;

		manager = managerService.findByPrincipal();

		Assert.notNull(manager, "manager.error.null");
	}


	public Collection<NeighborsBoard> findAllNeighborsBoardByBlock(
			int blockId) {
		Collection<NeighborsBoard> result;

		result = neighborsBoardRepository.findAllNeighborsBoardByBlock(blockId);

		return result;
	}
	
	public NeighborsBoard reconstruct(NeighborsBoardForm neighborsBoardForm){
		Integer neighborsBoardId;
		NeighborsBoard neighborsBoard;
		Owner attendant;
		
		neighborsBoardId=neighborsBoardForm.getNeighborsBoardId();
		neighborsBoard=this.findOne(neighborsBoardId);
		attendant=neighborsBoardForm.getAttendant();
		
		Collection<Owner> attendants;
		attendants=neighborsBoard.getAttendants();
		attendants.add(attendant);
		neighborsBoard.setAttendants(attendants);
		
		Collection<NeighborsBoard> neighborsBoards;
		neighborsBoards=attendant.getNeighborsBoards();
		neighborsBoards.add(neighborsBoard);
		attendant.setNeighborsBoards(neighborsBoards);
		
		return neighborsBoard;
	}
	
	public Collection<Owner> findAllOwnersNotInThisNeighborsBoard(Integer neighborsBoardId){
		Collection<Owner> result;
		Collection<Owner> attendants;
		NeighborsBoard neighborsBoard;
		Block block;
		Integer blockId;
		
		neighborsBoard=this.findOne(neighborsBoardId);
		block=neighborsBoard.getBlock();
		blockId=block.getId();
		result=ownerService.findAllOwnersByBlock(blockId);
		attendants=ownerService.findAllAttendantsByNeighborsBoard(neighborsBoardId);
		
		result.removeAll(attendants);
		
		return result;
	}
	
	public Collection<Owner> findAllAttendantsByNeighborsBoard(Integer neighborsBoardId){
		Collection<Owner> result;
		
		result=ownerService.findAllAttendantsByNeighborsBoard(neighborsBoardId);
		
		return result;
	}
	
	public String YYYYMMDDtoDDMMYYYY(Date date){
		Assert.notNull(date);
		
		SimpleDateFormat formatter;
		String result;
		
		formatter = new SimpleDateFormat("dd/MM/yyyy");
		result = formatter.format(date);
		
		return result;
	}
	
	public void PDF(Integer neighborsBoardId, HttpServletResponse response) throws Exception{
		NeighborsBoard neighborsBoard;
		Date date;
		String printDate;
		String record;
		Collection<Owner> attendants;
		Document document = new Document();
		
		neighborsBoard=findOne(neighborsBoardId);
		date=neighborsBoard.getDate();
		printDate=YYYYMMDDtoDDMMYYYY(date);
		record=neighborsBoard.getRecord();
		attendants=findAllAttendantsByNeighborsBoard(neighborsBoardId);
		
        response.setContentType("application/pdf");
        try{
        	PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			
	        Font fontTitle=new Font();
	        fontTitle.setSize(20);
	        fontTitle.setStyle(Font.BOLD | Font.UNDERLINE);
	        
	        Font fontSubTitle=new Font();
	        fontSubTitle.setSize(15);
	        fontSubTitle.setStyle(Font.BOLD);
	        
	        Image imagen = Image.getInstance(this.getClass().getClassLoader().getResource("minilogo.png"));
	        imagen.setAbsolutePosition(480f, 725f);
	        imagen.scaleToFit(80, 80);
	        document.add(imagen);
	        
	        
	        if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
	        	document.add(new Paragraph("Neighbors board's record", fontTitle));
	        }else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
	        	document.add(new Paragraph("Acta de la junta de vecinos", fontTitle));
	        }
	        document.add(new Paragraph("\n"));
	        if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
	        	document.add(new Paragraph("Block's code: ", fontSubTitle));
	        	document.add(new Paragraph(neighborsBoard.getBlock().getCode()));
	        }else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
	        	document.add(new Paragraph("Código del bloque: ", fontSubTitle));
	        	document.add(new Paragraph(neighborsBoard.getBlock().getCode()));
	        }
	        document.add(new Paragraph("\n"));
	        if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
	        	document.add(new Paragraph("Date: ", fontSubTitle));
	        	document.add(new Paragraph(printDate));
	        }else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
	        	document.add(new Paragraph("Fecha: ", fontSubTitle));
	        	document.add(new Paragraph(printDate));
	        }
	        document.add(new Paragraph("\n"));
	        if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
	        	document.add(new Paragraph("Record:", fontSubTitle));
	        }else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
	        	document.add(new Paragraph("Acta:", fontSubTitle));
	        }
	        document.add(new Paragraph(record));
	        document.add(new Paragraph("\n"));
	        if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
	        	document.add(new Paragraph("Attendants:", fontSubTitle));
	        }else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
	        	document.add(new Paragraph("Asistentes:", fontSubTitle));
	        }
	        int cont=0;
	        for(Owner o:attendants){
	        	cont++;
	        	document.add(new Paragraph(cont+")- "+o.getIdentifier()));
	        }
	        
	        document.add(new Paragraph("\n"));
	        if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("English")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("inglés")){
	        	document.add(new Paragraph("Number of attendants:", fontSubTitle));
	        	document.add(new Paragraph(String.valueOf(cont)));
	        }else if(LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish")
	        		|| LocaleContextHolder.getLocale().getDisplayLanguage().equals("español")){
	        	document.add(new Paragraph("Número de asistentes:", fontSubTitle));
	        	document.add(new Paragraph(String.valueOf(cont)));
	        }
	        
	        
	        document.close();
        }catch(Exception exception){
        	throw new Exception("The file could not be generated");
        }
	}
	
}
