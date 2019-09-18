package com.example.SpringBootFileuploadDownload;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderPageEvent extends PdfPageEventHelper {
	public void onStartPage(PdfWriter writer, Document document) {
		Rectangle rect = writer.getBoxSize("art");

		ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
				new Phrase("Retro.Report : Sprint - 10"), rect.getRight() / 2, rect.getTop() + 15, 0);
	}

}