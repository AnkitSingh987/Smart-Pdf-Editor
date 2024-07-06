package ankitsingh.smartpdfeditor.util;

import android.os.AsyncTask;

import ankitsingh.smartpdfeditor.interfaces.OnTextToPdfInterface;
import ankitsingh.smartpdfeditor.model.TextToPDFOptions;
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
public class TextToPdfAsync extends AsyncTask<Object, Object, Object> {
    private final TextToPDFUtils mTexttoPDFUtil;
    private final TextToPDFOptions mTextToPdfOptions;
    private final String mFileExtension;
    private final OnTextToPdfInterface mOnPDFCreatedInterface;
    private boolean mSuccess;

    /**
     * This is a public constructor responsible for initializing the path of the actual
     * file, the PDFUtils instance for the file, the options for text to Pdf, the file
     * extension, and the OnTextToPdfInterface instance.
     *
     * @param textToPDFutil         is the PDFUtils instance for the file.
     * @param textToPDFOptions      is the options for text to Pdf.
     * @param fileextension         is the file extension name string.
     * @param onPDFCreatedInterface is the OnTextToPdfInterface instance.
     */
    public TextToPdfAsync(TextToPDFUtils textToPDFutil, TextToPDFOptions textToPDFOptions,
                          String fileextension, OnTextToPdfInterface onPDFCreatedInterface) {
        this.mTexttoPDFUtil = textToPDFutil;
        this.mTextToPdfOptions = textToPDFOptions;
        this.mFileExtension = fileextension;
        this.mOnPDFCreatedInterface = onPDFCreatedInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mSuccess = true;
        mOnPDFCreatedInterface.onPDFCreationStarted();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            mTexttoPDFUtil.createPdfFromTextFile(mTextToPdfOptions, mFileExtension);
        } catch (Exception e) {
            mSuccess = false;
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        mOnPDFCreatedInterface.onPDFCreated(mSuccess);
    }

}
