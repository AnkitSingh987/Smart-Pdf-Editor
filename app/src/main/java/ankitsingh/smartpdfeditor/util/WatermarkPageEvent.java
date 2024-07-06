package ankitsingh.smartpdfeditor.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import ankitsingh.smartpdfeditor.model.Watermark;
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
class WatermarkPageEvent extends PdfPageEventHelper {
    private Watermark mWatermark;
    private Phrase mPhrase;

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte canvas = writer.getDirectContent();
        float x = (document.getPageSize().getLeft() + document.getPageSize().getRight()) / 2;
        float y = (document.getPageSize().getTop() + document.getPageSize().getBottom()) / 2;
        ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, mPhrase, x, y, mWatermark.getRotationAngle());
    }

    public Watermark getWatermark() {
        return mWatermark;
    }

    public void setWatermark(Watermark watermark) {
        this.mWatermark = watermark;
        this.mPhrase = new Phrase(mWatermark.getWatermarkText(),
                new Font(mWatermark.getFontFamily(), mWatermark.getTextSize(),
                        mWatermark.getFontStyle(), mWatermark.getTextColor()));
    }
}