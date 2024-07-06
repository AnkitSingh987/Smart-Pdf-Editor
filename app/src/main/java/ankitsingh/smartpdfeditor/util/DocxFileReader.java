package ankitsingh.smartpdfeditor.util;

import android.content.Context;
import android.net.Uri;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.InputStream;

public class DocxFileReader extends FileReader {
    /*
     * This file is part of MyApplication.
     *
     * MyApplication is free software: you can redistribute it and/or modify
     * it under the terms of the GNU General Public License as published by
     * the Free Software Foundation, either version 3 of the License, or
     * (at your option) any later version.
     *
     * MyApplication is distributed in the hope that it will be useful,
     * but WITHOUT ANY WARRANTY; without even the implied warranty of
     * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
     * GNU General Public License for more details.
     *
     * You should have received a copy of the GNU General Public License
     * along with MyApplication. If not, see <https://www.gnu.org/licenses/>.
     */
    public DocxFileReader(Context context) {
        super(context);
    }

    @Override
    protected void createDocumentFromStream(
            Uri uri, Document document, Font myfont, InputStream inputStream) throws Exception {

        XWPFDocument doc = new XWPFDocument(inputStream);
        XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
        String fileData = extractor.getText();

        Paragraph documentParagraph = new Paragraph(fileData + "\n", myfont);
        documentParagraph.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(documentParagraph);
    }
}