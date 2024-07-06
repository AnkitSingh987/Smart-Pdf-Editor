package ankitsingh.smartpdfeditor.model;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
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
public class Watermark {
    private String mWatermarkText;
    private int mRotationAngle;
    private BaseColor mTextColor;
    private int mTextSize;
    private Font.FontFamily mFontFamily;
    private int mFontStyle;

    public String getWatermarkText() {
        return mWatermarkText;
    }

    public void setWatermarkText(String watermarkText) {
        this.mWatermarkText = watermarkText;
    }

    public int getRotationAngle() {
        return mRotationAngle;
    }

    public void setRotationAngle(int rotationAngle) {
        this.mRotationAngle = rotationAngle;
    }

    public BaseColor getTextColor() {
        return mTextColor;
    }

    public void setTextColor(BaseColor textColor) {
        this.mTextColor = textColor;
    }

    public int getTextSize() {
        return mTextSize;
    }

    public void setTextSize(int textSize) {
        this.mTextSize = textSize;
    }

    public Font.FontFamily getFontFamily() {
        return mFontFamily;
    }

    public void setFontFamily(Font.FontFamily fontFamily) {
        this.mFontFamily = fontFamily;
    }

    public int getFontStyle() {
        return mFontStyle;
    }

    public void setFontStyle(int fontStyle) {
        this.mFontStyle = fontStyle;
    }
}
