package com.sas.reports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sas.app.XMLUtils;
import com.sas.entity.Owner;
import com.sas.entity.Parts;
import com.sas.entity.ServiceOrder;
import com.sas.entity.Vehicle;
import com.sas.entity.Work;

public class PDFEstimateWriter {
	private ServiceOrder sOrder;
	private Document document;
	private String invoiceFileName;
	private FileOutputStream fos;
	private final String pdf_TAB = "\u00a0\u00a0\u00a0";

	private final Font font06 = new Font(FontFamily.HELVETICA, 6);
	private final Font font08 = new Font(FontFamily.HELVETICA, 8);
	private final Font courier_11N = new Font(Font.FontFamily.COURIER, 11, Font.NORMAL);
	private final Font roman_times_9B = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
	private final Font roman_times_9N = new Font(FontFamily.TIMES_ROMAN, 9);
	private final Font roman_times_7N = new Font(FontFamily.TIMES_ROMAN, 7);
	private final Font roman_times_30N = new Font(FontFamily.TIMES_ROMAN, 30);
	private final String FINE_PRINT = "	private final  String _SIGNATURE";
	private final String _SIGNATURE = "	private final  String _SIGNATURE";

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private final NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.CANADA);
	private final int nLines = 30;
	private double itemCosts;
	private double labourCosts;
	private double totalDuration;
	private double serviceTotal;
	private List<Work> workList;

	public PDFEstimateWriter(List<Work> incompleteWork, ServiceOrder sorder2) {
		this.workList = incompleteWork;
		this.sOrder = sorder2;
		this.invoiceFileName = XMLUtils.DEF_INVOICE_PATH + "//Est" + sorder2.getJobID() + ".pdf";
	//
		labourCosts = 0.;
		totalDuration = 0.;
		serviceTotal = 0.;
		createInvoiceReport();
	}

	private void createInvoiceReport() {
		setDefaults();
		if ((fos = getFile(this.invoiceFileName)) == null)
			return;
		try {
			PdfWriter.getInstance(document, fos);
			this.document.open();
			writeMetaData();
			addServiceContents();
			this.document.close();
			// displayFile(this.invoiceFileName);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 */
	private void addServiceContents() {
		try {
			document.add(createHeaderDetails());
		//	document.add(createInvoiceDetails());
		//	document.add(createSubTable());
		} catch (DocumentException e) {
			System.out.println("ERROR:  PDF Write!");
		}
	}

	private PdfPTable createSubTable() throws DocumentException {
		PdfPTable table = new PdfPTable(3);
		table.setTotalWidth(new float[] { 580f, 70f, 70f });

		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append(sOrder.getComment());
		table.addCell(createCell(sbuilder.toString(), font08, PdfPCell.LEFT, 1, 1, Element.ALIGN_LEFT));

		sbuilder = new StringBuilder();
		sbuilder.append("Parts");
		table.addCell(createCell(sbuilder.toString(), font08, PdfPCell.BOX, 1, 1, Element.ALIGN_RIGHT));
		sbuilder = new StringBuilder();
		sbuilder.append(currency.format(new Double(itemCosts)));
		table.addCell(createCell(sbuilder.toString(), font08, PdfPCell.BOX, 1, 1, Element.ALIGN_RIGHT));

		table.addCell(createCell("\u00a0", font08, PdfPCell.LEFT, 1, 1, Element.ALIGN_LEFT));
		sbuilder = new StringBuilder();
		sbuilder.append("Labour");
		table.addCell(createCell(sbuilder.toString(), font08, PdfPCell.BOX, 1, 1, Element.ALIGN_RIGHT));
		sbuilder = new StringBuilder();
		sbuilder.append(currency.format(new Double(labourCosts)));
		table.addCell(createCell(sbuilder.toString(), font08, PdfPCell.BOX, 1, 1, Element.ALIGN_RIGHT));

		table.addCell(createCell("\u00a0", font08, PdfPCell.LEFT, 1, 1, Element.ALIGN_LEFT));
		sbuilder = new StringBuilder();
		sbuilder.append("Sub Total");
		table.addCell(createCell(sbuilder.toString(), font08, PdfPCell.BOX, 1, 1, Element.ALIGN_RIGHT));
		sbuilder = new StringBuilder();
		sbuilder.append(currency.format(itemCosts + labourCosts));
		table.addCell(createCell(sbuilder.toString(), font08, PdfPCell.BOX, 1, 1, Element.ALIGN_RIGHT));

		table.addCell(createCell("\u00a0", font08, PdfPCell.LEFT, 1, 1, Element.ALIGN_LEFT));
		sbuilder = new StringBuilder();
		sbuilder.append("Sales Tax");
		table.addCell(createCell(sbuilder.toString(), font08, PdfPCell.BOX, 1, 1, Element.ALIGN_RIGHT));
		double saleTax = (labourCosts + itemCosts) * (.08f + .05f);
		sbuilder = new StringBuilder();
		sbuilder.append(currency.format(new Double(saleTax)));
		table.addCell(createCell(sbuilder.toString(), font08, PdfPCell.BOX, 1, 1, Element.ALIGN_RIGHT));

		table.addCell(createCell("\u00a0", font08, PdfPCell.LEFT + PdfPCell.BOTTOM, 1, 1, Element.ALIGN_LEFT));
		sbuilder = new StringBuilder();
		sbuilder.append("Total");
		table.addCell(createCell(sbuilder.toString(), font08, PdfPCell.BOX, 1, 1, Element.ALIGN_RIGHT));
		serviceTotal = saleTax + (labourCosts + itemCosts);
		sbuilder = new StringBuilder();
		sbuilder.append(currency.format(new Double(serviceTotal)));
		table.addCell(createCell(sbuilder.toString(), font08, PdfPCell.BOX, 1, 1, Element.ALIGN_RIGHT));

		table.addCell(createCell("Sign Here:", font06, PdfPCell.LEFT + PdfPCell.BOTTOM, 1, 1, Element.ALIGN_LEFT));
		sbuilder = new StringBuilder();
		sbuilder.append("Personnel: ").append("loggedInUser()");
		table.addCell(
				createCell(sbuilder.toString(), font06, PdfPCell.LEFT + PdfPCell.BOTTOM, 1, 1, Element.ALIGN_LEFT));
		table.addCell(createCell("\u00a0", font06, PdfPCell.RIGHT + PdfPCell.BOTTOM, 1, 1, Element.ALIGN_LEFT));

		sbuilder = new StringBuilder();
		sbuilder.append(FINE_PRINT).append("\n").append(_SIGNATURE).append("\n").append("Thank You for your business");
		table.addCell(
				createCell(sbuilder.toString(), font06, PdfPCell.LEFT + PdfPCell.BOTTOM, 3, 1, Element.ALIGN_BOTTOM));
		table.addCell(createCell("\u00a0", font06, PdfPCell.BOTTOM, 1, 1, Element.ALIGN_RIGHT));
		table.addCell(createCell("\u00a0", font06, PdfPCell.RIGHT + PdfPCell.BOTTOM, 1, 1, Element.ALIGN_LEFT));
		//
		return table;
	}

	/**
	 * Print Invoice Ddetails
	 * 
	 * @return
	 * @throws DocumentException
	 */
	public PdfPTable createInvoiceDetails() throws DocumentException {
		float[] fColumns = new float[] { 60f, 520f, 70f, 70f };
		int nCols = fColumns.length, nRows = nLines;
		StringBuilder sb = null;
		PdfPTable table = new PdfPTable(nCols);
		table.setTotalWidth(fColumns);

		sb = new StringBuilder();
		sb.append("Quantity");
		table.addCell(createCell(sb.toString(), roman_times_9N, PdfPCell.BOX, 1, 1, Element.ALIGN_CENTER));
		sb = new StringBuilder();
		sb.append("Description");
		table.addCell(createCell(sb.toString(), roman_times_9N, PdfPCell.BOX, 1, 1, Element.ALIGN_CENTER));
		sb = new StringBuilder();
		sb.append("Unit Price");
		table.addCell(createCell(sb.toString(), roman_times_9N, PdfPCell.BOX, 1, 1, Element.ALIGN_CENTER));
		sb = new StringBuilder();
		sb.append("Unit Total");
		table.addCell(createCell(sb.toString(), roman_times_9N, PdfPCell.BOX, 1, 1, Element.ALIGN_CENTER));

		List<Work> workList = this.sOrder.getWorkList();
		for (Work work : workList) {
			Parts item = work.getParts();
			if (item != null) {
				if (item.getInventory().getPartNumber().equals(XMLUtils.DEF_PART_NUMBER)) {
					sb = new StringBuilder();
					sb.append(item.getQuantity().toString());
					table.addCell(createCell(sb.toString(), roman_times_9N, PdfPCell.LEFT, 1, 1, Element.ALIGN_CENTER));
					//
					sb = new StringBuilder();
					sb.append("PN:\t").append(XMLUtils.DEF_PART_NUMBER).append("\u00a0-\u00a0")
							.append(item.getDescription());
					table.addCell(createCell(sb.toString(), roman_times_9N, PdfPCell.LEFT, 1, 1, Element.ALIGN_LEFT));
					//
					sb = new StringBuilder();
					sb.append(currency.format(item.getPrice()));
					table.addCell(createCell(sb.toString(), roman_times_9N, PdfPCell.BOX, 1, 1, Element.ALIGN_RIGHT));
					//
					sb = new StringBuilder();
					double dltotal = item.getPrice() * item.getQuantity();
					sb.append(currency.format(dltotal));
					table.addCell(createCell(sb.toString(), roman_times_9N, PdfPCell.BOX, 1, 1, Element.ALIGN_RIGHT));
					itemCosts += dltotal;
					nRows -= 2;
				}
			}
			//
			sb = new StringBuilder();
			sb.append(work.getDuration().toString());
			table.addCell(createCell(sb.toString(), roman_times_9N, PdfPCell.BOX, 1, 1, Element.ALIGN_CENTER));
			//
			sb = new StringBuilder();
			sb.append(work.getWorkDesc());
			table.addCell(createCell(sb.toString(), roman_times_9N, PdfPCell.LEFT + PdfPCell.BOTTOM, 1, 1,
					Element.ALIGN_LEFT));
			//
			sb = new StringBuilder();
			sb.append(currency.format(labourRate(work)));
			table.addCell(createCell(sb.toString(), roman_times_9N, PdfPCell.BOX, 1, 1, Element.ALIGN_RIGHT));
			//
			sb = new StringBuilder();
			sb.append(currency.format(work.getWorkCost()));
			table.addCell(createCell(sb.toString(), roman_times_9N, PdfPCell.BOX, 1, 1, Element.ALIGN_RIGHT));

			totalDuration += work.getDuration();
			labourCosts += work.getWorkCost();
			nRows -= 2;
		}
		// compute remaining rows to be left empty
		PdfPCell cell;
		Phrase[][] cells = new Phrase[nRows][nCols];
		for (int r = 0; r < nRows; r++) {
			for (int c = 0; c < nCols; c++) {
				cells[r][c] = new Phrase("\u00a0");
				cell = new PdfPCell(cells[r][c]);
				table.addCell(cell);
			}
		}
		return table;
	}

	/**
	 * 
	 * @param work
	 * @return
	 */
	private Float labourRate(Work work) {
		Float rate = work.getWorkCost() / work.getDuration() - 1;
		if (rate < new Float(XMLUtils.COMPANY_LABOURRATE_REG) && rate > new Float(XMLUtils.COMPANY_LABOURRATE_SPE))
			return new Float(XMLUtils.COMPANY_LABOURRATE_REG);
		else if (rate < new Float(XMLUtils.COMPANY_LABOURRATE_SPE) && rate > new Float(XMLUtils.COMPANY_LABOURRATE_XTR))
			return new Float(XMLUtils.COMPANY_LABOURRATE_SPE);
		else
			return new Float(XMLUtils.COMPANY_LABOURRATE_XTR);
	}

	// tmp = "PN:" + XMLUtils.DEF_PART_NUMBER + "\u00a0-\u00a0" +
	// item.getDescription();
	// tmp += "\n\u00a0\u00a0\u00a0-\u00a0" + "war.getWarranty()";
	// tmp += ", " + "war.getRPeriod()" + ", " + "war.getLDistance()";
	// table.addCell(createCell(tmp, roman_times_9N, PdfPCell.LEFT, 1, 1,
	// Element.ALIGN_LEFT));
	// tmp = currency.format(item.getPrice());
	// table.addCell(createCell(tmp, roman_times_9N, PdfPCell.BOX, 1, 1,
	// Element.ALIGN_RIGHT));
	//
	// double dltotal = item.getPrice() * item.getQuantity();
	// tmp = currency.format(dltotal);
	// table.addCell(createCell(tmp, roman_times_9N, PdfPCell.BOX, 1, 1,
	// Element.ALIGN_RIGHT));
	// itemCosts += dltotal;
	// nRows -= 2;
	/**
	 * 
	 * @return
	 * @throws DocumentException
	 */
	private PdfPTable createHeaderDetails() throws DocumentException {
		PdfPTable table = new PdfPTable(3);
		table.setTotalWidth(new float[] { 250f, 220f, 250f });

		StringBuilder sbuffer = new StringBuilder();
		sbuffer.append(XMLUtils.COMPANY_OUTLET);
		table.addCell(createCell(sbuffer.toString(), roman_times_30N, PdfPCell.BOTTOM, 3, 2, Element.ALIGN_LEFT));
		sbuffer = new StringBuilder();
		sbuffer.append(XMLUtils.COMPANY_ADDRESS0);
		table.addCell(createCell(sbuffer.toString(), roman_times_7N, PdfPCell.NO_BORDER, 1, 1, Element.ALIGN_RIGHT));
		sbuffer = new StringBuilder();
		sbuffer.append(XMLUtils.COMPANY_CITY);
		table.addCell(createCell(sbuffer.toString(), roman_times_7N, PdfPCell.NO_BORDER, 1, 1, Element.ALIGN_RIGHT));
		sbuffer = new StringBuilder();
		sbuffer.append(XMLUtils.COMPANY_FAX);
		table.addCell(createCell(sbuffer.toString(), roman_times_7N, PdfPCell.BOTTOM, 1, 1, Element.ALIGN_RIGHT));

		Vehicle vehicle = this.sOrder.getVehicle();
		Owner owner = vehicle.getOwner();
		sbuffer = new StringBuilder();
		sbuffer.append("In: ").append(sdf.format(this.sOrder.getSDate()));
		table.addCell(createCell(sbuffer.toString(), roman_times_9N, PdfPCell.LEFT, 1, 1, Element.ALIGN_LEFT));
		sbuffer = new StringBuilder();
		sbuffer.append("Out: ");//.append(sdf.format(this.sOrder.getEDate()));
		table.addCell(createCell(sbuffer.toString(), roman_times_9N, PdfPCell.NO_BORDER, 1, 1, Element.ALIGN_CENTER));
		sbuffer = new StringBuilder();
		sbuffer.append("Job #:\t").append(this.sOrder.getJobID());
		table.addCell(createCell(sbuffer.toString(), roman_times_9N, PdfPCell.RIGHT, 1, 1, Element.ALIGN_RIGHT));

		sbuffer = new StringBuilder();
		sbuffer.append(owner.getFirstname()).append(", ")
				.append(owner.getLastname()/* .substring(0, 1) */);
		table.addCell(createCell(sbuffer.toString(), roman_times_9N, PdfPCell.LEFT, 1, 2, Element.ALIGN_LEFT));
		// table.addCell(createCell(pdf_TAB, roman_times_9N, PdfPCell.NO_BORDER,
		// 1, 1, Element.ALIGN_CENTER));
		sbuffer = new StringBuilder();
		sbuffer.append("VIN:\t").append(vehicle.getVIN());
		table.addCell(createCell(sbuffer.toString(), roman_times_9N, PdfPCell.RIGHT, 1, 1, Element.ALIGN_RIGHT));

		sbuffer = new StringBuilder();
		String tmp = owner.getAddress().getOther();
		sbuffer.append((!tmp.isEmpty()) ? tmp + " - " : "");
		sbuffer.append(owner.getAddress().getStreet());
		table.addCell(createCell(sbuffer.toString(), roman_times_9N, PdfPCell.LEFT, 1, 2, Element.ALIGN_LEFT));
		sbuffer = new StringBuilder();
		sbuffer.append("Plate Nr:\t").append(vehicle.getPlate());
		table.addCell(createCell(sbuffer.toString(), roman_times_9N, PdfPCell.RIGHT, 1, 1, Element.ALIGN_RIGHT));

		sbuffer = new StringBuilder();
		sbuffer.append(owner.getAddress().getCity()).append(", ").append(owner.getAddress().getRegion().getName());
		table.addCell(createCell(sbuffer.toString(), roman_times_9N, PdfPCell.LEFT, 1, 2, Element.ALIGN_LEFT));
		sbuffer = new StringBuilder();
		sbuffer.append("Odometer:\t").append(sOrder.getOdometer()).append("Km");
		table.addCell(createCell(sbuffer.toString(), roman_times_9N, PdfPCell.RIGHT, 1, 1, Element.ALIGN_RIGHT));

		sbuffer = new StringBuilder();
		sbuffer.append("Phone:\t").append(owner.getPhone());
		tmp = owner.getPhoneOther();
		sbuffer.append((!tmp.isEmpty()) ? ",\t" + owner.getPhoneOther() : "");
		table.addCell(createCell(sbuffer.toString(), roman_times_9N, PdfPCell.LEFT, 1, 2, Element.ALIGN_LEFT));
		sbuffer = new StringBuilder();
		sbuffer.append("TAX NUMBER");
		table.addCell(createCell(sbuffer.toString(), roman_times_9N, PdfPCell.RIGHT, 1, 1, Element.ALIGN_RIGHT));

		// table.addCell(createCell(pdf_TAB, roman_times_9N, PdfPCell.NO_BORDER,
		// 1, 1, Element.ALIGN_LEFT));
		// table.addCell(createCell(pdf_TAB, roman_times_9N, PdfPCell.NO_BORDER,
		// 1, 1, Element.ALIGN_CENTER));
		// table.addCell(createCell(pdf_TAB, roman_times_9N, PdfPCell.NO_BORDER,
		// 1, 1, Element.ALIGN_RIGHT));

		return table;
	}

	private void setDefaults() {
		this.document = new Document(PageSize.LETTER);
		float marginLeftRight = 10f, marginTop = 50f, marginBottom = 25f;
		document.setMargins(marginLeftRight, marginLeftRight, marginTop, marginBottom);
		document.setMarginMirroring(true);
	}

	private FileOutputStream getFile(String string) {
		FileOutputStream fos = null;
		File file = new File(string);
		try {
			// create file -if not exist
			// if exist and opened, close and set size to zero
			// same as UNIX touch
			// FileUtils.touch(file);
			fos = new FileOutputStream(file);
		} catch (IOException e) {
			fos = null;
			// FXmlUtils.showAlert(n, owner, title, message);.alert("File is
			// opened");
			// LOG.error(e.getLocalizedMessage(), e);
		}
		return fos;
	}

	/**
	 * 
	 * @param content
	 *            - cell content
	 * @param font
	 *            - font
	 * @param border
	 *            - border
	 * @param row
	 *            - rowspan
	 * @param col
	 *            - colspan
	 * @param align
	 *            - alignment
	 * @return
	 */
	public PdfPCell createCell(String content, Font font, int border, int row, int col, int align) {
		PdfPCell cell = new PdfPCell(new Phrase(content, font));
		cell.setBorder(border);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setHorizontalAlignment(align);
		return cell;
	}

	/**
	 * 
	 * @param fname
	 * @throws IOException
	 */
	// private void displayFile(String fname) throws IOException {
	// File f = new File(fname);
	// try {
	// if (f.exists()) {
	// Process process = Runtime.getRuntime()
	// .exec("rundll32 url.dll, FileProtocolHandler " + f.getAbsolutePath());
	// process.waitFor();
	// }
	// } catch (Exception e) {
	// desktopDisplaySolution(f);
	// }
	// }

	/**
	 * 
	 * @param file
	 * @throws IOException
	 */
	// private void desktopDisplaySolution(File file) throws IOException {
	// if (file.exists()) {
	// if (Desktop.isDesktopSupported()) {
	// Desktop.getDesktop().open(file);
	// } else {
	// System.out.println("Awt Desktop is not supported!");
	// }
	// } else {
	// System.out.println("File does not exist!");
	// }
	// }

	/**
	 * 
	 */
	private void writeMetaData() {
		document.addTitle(XMLUtils.APPLICATION_TITLE);
		document.addSubject(XMLUtils.APPLICATION_TITLE);
		document.addCreationDate();
		document.addAuthor(XMLUtils.AUTHOR_NAME);
		document.addCreator(XMLUtils.AUTHOR_NAME);
	}
}
