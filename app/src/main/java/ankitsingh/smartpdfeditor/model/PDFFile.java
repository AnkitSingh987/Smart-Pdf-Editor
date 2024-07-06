package ankitsingh.smartpdfeditor.model;

import java.io.File;
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
public class PDFFile {
    private File mPdfFile;
    private boolean mIsEncrypted;

    public PDFFile(File mPdfFile, boolean mIsEncrypted) {
        this.mPdfFile = mPdfFile;
        this.mIsEncrypted = mIsEncrypted;
    }

    public File getPdfFile() {
        return mPdfFile;
    }

    public void setPdfFile(File mPdfFile) {
        this.mPdfFile = mPdfFile;
    }

    public boolean isEncrypted() {
        return mIsEncrypted;
    }

    public void setEncrypted(boolean mIsEncrypted) {
        this.mIsEncrypted = mIsEncrypted;
    }
}
