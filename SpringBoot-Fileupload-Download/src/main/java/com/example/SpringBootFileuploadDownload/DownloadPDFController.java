package com.example.SpringBootFileuploadDownload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
@RequestMapping("/download")
public class DownloadPDFController {

	private static final String EXTERNAL_FILE_PATH = "C:/dharm/";

	@GetMapping("/file/{fileName:.+}")
	public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("fileName") String fileName) throws IOException, DocumentException {
		generatePDF();
		File file = new File(EXTERNAL_FILE_PATH + fileName);

		if (file.exists()) {

			// get the mimetype
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

			response.setContentLength((int) file.length());

			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

			FileCopyUtils.copy(inputStream, response.getOutputStream());

		}

	}

	public static void generatePDF() throws FileNotFoundException, DocumentException {
		Document document = new Document();
		PdfPTable table = new PdfPTable(new float[] { 2, 1, 2 });
		
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.getDefaultCell().setFollowingIndent(20);
	    table.addCell("Went Well");
		table.addCell("To Do");
		table.addCell("Action Items");
		table.setHeaderRows(1);
		PdfPCell[] cells = table.getRow(0).getCells();
		for (int j = 0; j < cells.length; j++) {
			cells[j].setBackgroundColor(BaseColor.GRAY);
		}
		for (int i = 1; i < 5; i++) {
			table.addCell("Went Well:" + i);
			table.addCell("To Do:" + i);
			table.addCell("Action Items:" + i);
		}
		PdfWriter writer =PdfWriter.getInstance(document, new FileOutputStream("C:/dharm/sample.pdf"));
	    HeaderPageEvent event = new HeaderPageEvent();
		Rectangle rect = new Rectangle(30, 30, 550, 800);
        writer.setBoxSize("art", rect);
        writer.setPageEvent(event);
		document.open();
		document.add(table);
		document.close();
		System.out.println("Done");
	}




}
