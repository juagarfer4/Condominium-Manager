package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.util.ServiceException;

import domain.Payment;
import domain.Community;
import domain.Manager;
import forms.GoogleSheetForm;

import repositories.PaymentRepository;
import security.LoginService;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletResponse;

@Service
@Transactional
public class PaymentService {

	// Managed repository ---------------------------------

	@Autowired
	private PaymentRepository paymentRepository;

	// Supporting services ---------------------------------

	@Autowired
	private CommunityService communityService;

	@Autowired
	private ManagerService managerService;

	// Constructors ----------------------------------------

	public PaymentService() {
		super();
	}

	// Simple CRUD methods -----------------------------------

	public Payment create(int communityId) {
		Community community;

		community = communityService.findOne(communityId);

		Assert.notNull(community, "payment.error.community");

		Payment result;

		result = new Payment();

		result.setCommunity(community);

		return result;
	}

	public Payment findOne(Integer paymentId) {
		Payment result;

		result = paymentRepository.findOne(paymentId);

		Assert.notNull(result);

		return result;
	}

	public Collection<Payment> findAll() {
		Collection<Payment> result;

		result = paymentRepository.findAll();

		return result;
	}

	public void save(Payment payment) {
		Assert.notNull(payment, "error.null");

		this.checkPrincipal(payment);

		paymentRepository.save(payment);

		communityService.decreaseBudget(payment);
	}

	public void save(List<Payment> payments) {
		Assert.notNull(payments, "error.null");

		for (int i = 0; i < payments.size(); i++) {
			Payment payment = payments.get(i);

			this.save(payment);

			communityService.decreaseBudget(payment);

		}
	}

	// Other business methods ------------------------------

	public void checkPrincipal(Payment payment) {
		Assert.notNull(payment);

		Manager manager;

		manager = managerService.findByPrincipal();

		Assert.notNull(manager);
	}

	public Collection<Payment> findAllPaymentByCommunity(int communityId) {
		Collection<Payment> result;

		result = paymentRepository.findAllPaymentByCommunity(communityId);

		return result;
	}

	public Collection<Payment> findAllPaymentByCommunityAndMonth(
			int communityId, Date date) {
		Collection<Payment> result;

		result = paymentRepository.findAllPaymentByCommunityAndMonth(
				communityId, date.getMonth() + 1);

		return result;
	}

	public Collection<Payment> findAllPaymentByCommunityAndYear(
			int communityId, int year) {
		Collection<Payment> result;

		result = paymentRepository.findAllPaymentsByCommunityAndYear(
				communityId, year);

		return result;
	}

	public Double findAllTotalAmountPaymentByCommunityAndYear(int communityId,
			int year) {
		Double result;

		result = paymentRepository
				.findAllTotalAmountPaymentsByCommunityAndYear(communityId, year);

		if(result == null){
			result = 0.0;
		}
		
		return result;
	}

	public Collection<Payment> findAllPaymentsByCommunityCurrentYear(
			int communityId) {
		Collection<Payment> result;

		result = paymentRepository
				.findAllPaymentsByCommunityCurrentYear(communityId);

		return result;
	}

	public Double findAllPaymentsAmountByCommunityCurrentYear(int communityId) {
		Double result;

		result = paymentRepository
				.findAllPaymentsAmountByCommunityCurrentYear(communityId);

		return result;
	}

	public Collection<Payment> findAllPaymentsBetweenDates(Integer communityId,
			Date initialDate, Date endDate) {
		Collection<Payment> result;

		result = paymentRepository.findAllPaymentsBetweenDates(communityId,
				initialDate, endDate);

		return result;
	}

	public Double findPaymentAmountBetweenDates(Integer communityId,
			Date initialDate, Date endDate) {
		Double result;

		result = paymentRepository.findPaymentAmountBetweenDates(communityId,
				initialDate, endDate);
		LoginService ls = new LoginService();
		return result;
	}

	public void arrayBytesToCsv(byte[] arrayBytes, int communityId) {
		List<String> linesList;
		linesList = new ArrayList<String>();

		String text = new String(arrayBytes);
		String[] lines = text.split("\n");

		for (String s : lines) {
			linesList.add(s);
		}
		linesList.remove(0);

		for (String s : linesList) {
			String[] words = s.split(",");
			Payment p = new Payment();
			p.setCommunity(communityService.findOne(communityId));
			p.setAmount(Double.parseDouble(words[0]));
			p.setDescription(words[1]);
			
			String pattern = "dd/MM/yyyy";
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			Date date;
			try {
				date = format.parse(words[2]);
				p.setPaymentMoment(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			
			this.save(p);
		}

	}

	@SuppressWarnings({ "static-access", "deprecation" })
	public void googleSheetsApiDownload(GoogleSheetForm googleSheetForm)
			throws IOException {

		String sheetUrlFirst = "https://spreadsheets.google.com/feeds/list/";
		String sheetUrlLast = "/default/public/values";
		String[] firstCut, secondCut;
		String key;
		String sheetUrl;
		Integer communityId;
		String urlSheet;

		communityId = googleSheetForm.getCommunityId();
		urlSheet = googleSheetForm.getUrlSheet();

		try {

			// Extracion de la key
			firstCut = urlSheet.split("/d/");
			secondCut = firstCut[1].split("/");
			key = secondCut[0];

			sheetUrl = sheetUrlFirst + key + sheetUrlLast;

			SpreadsheetService service = new SpreadsheetService("Sheet1");
			// Use this String as url
			URL url = new URL(sheetUrl);

			// Get Feed of Spreadsheet url
			ListFeed lf = service.getFeed(url, ListFeed.class);

			// Iterate over feed to get cell value
			for (ListEntry le : lf.getEntries()) {
				CustomElementCollection cec = le.getCustomElements();
				Payment payment;
				payment = this.create(communityId);
				// Pass column name to access it's cell values
				String val = cec.getValue("amount");
				Double valCast = Double.valueOf(val);
				payment.setAmount(valCast);
				String val2 = cec.getValue("description");
				payment.setDescription(val2);
				
				
				String val3 = cec.getValue("date");
				
				String pattern = "dd/MM/yyyy";
				SimpleDateFormat format = new SimpleDateFormat(pattern);
				Date date;
				try {
					date = format.parse(val3);
					 payment.setPaymentMoment(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				this.save(payment);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public void downloadFileCSV(HttpServletResponse response) throws IOException, URISyntaxException {
		
		ClassLoader classloader = Thread.currentThread()
				.getContextClassLoader();
		URL resource = classloader.getResource("paymentExample.csv");
		Paths.get(resource.toURI()).toFile();
		File file = Paths.get(resource.toURI()).toFile();

		if (!file.exists()) {
			String errorMessage = "Sorry. The file you are looking for does not exist";
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
			outputStream.close();
			return;
		}

		String mimeType = URLConnection
				.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}

		response.setContentType(mimeType);

		/*
		 * "Content-Disposition : inline" will show viewable types [like
		 * images/text/pdf/anything viewable by browser] right on browser while
		 * others(zip e.g) will be directly downloaded [may provide save as
		 * popup, based on your browser setting.]
		 */
		response.setHeader("Content-Disposition",
				String.format("inline; filename=\"" + file.getName() + "\""));

		/*
		 * "Content-Disposition : attachment" will be directly download, may
		 * provide save as popup, based on your browser setting
		 */
		// response.setHeader("Content-Disposition",
		// String.format("attachment; filename=\"%s\"", file.getName()));

		response.setContentLength((int) file.length());

		InputStream inputStream = new BufferedInputStream(new FileInputStream(
				file));

		// Copy bytes from source to destination(outputstream in this example),
		// closes both streams.
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}
	
	public void downloadFileGoogleDrive(HttpServletResponse response) throws IOException, URISyntaxException {
		
		ClassLoader classloader = Thread.currentThread()
				.getContextClassLoader();
		URL resource = classloader.getResource("pago2.gsheet");
		Paths.get(resource.toURI()).toFile();
		File file = Paths.get(resource.toURI()).toFile();

		if (!file.exists()) {
			String errorMessage = "Sorry. The file you are looking for does not exist";
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
			outputStream.close();
			return;
		}

		String mimeType = URLConnection
				.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}

		response.setContentType(mimeType);

		/*
		 * "Content-Disposition : inline" will show viewable types [like
		 * images/text/pdf/anything viewable by browser] right on browser while
		 * others(zip e.g) will be directly downloaded [may provide save as
		 * popup, based on your browser setting.]
		 */
		response.setHeader("Content-Disposition",
				String.format("inline; filename=\"" + file.getName() + "\""));

		/*
		 * "Content-Disposition : attachment" will be directly download, may
		 * provide save as popup, based on your browser setting
		 */
		// response.setHeader("Content-Disposition",
		// String.format("attachment; filename=\"%s\"", file.getName()));

		response.setContentLength((int) file.length());

		InputStream inputStream = new BufferedInputStream(new FileInputStream(
				file));

		// Copy bytes from source to destination(outputstream in this example),
		// closes both streams.
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}

}
