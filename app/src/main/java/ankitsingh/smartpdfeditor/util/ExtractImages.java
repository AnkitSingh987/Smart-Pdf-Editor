package ankitsingh.smartpdfeditor.util;

import static ankitsingh.smartpdfeditor.util.FileUtils.getFileNameWithoutExtension;
import static ankitsingh.smartpdfeditor.util.ImageUtils.saveImage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfImageObject;

import java.io.IOException;
import java.util.ArrayList;

import ankitsingh.smartpdfeditor.interfaces.ExtractImagesListener;
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
public class ExtractImages extends AsyncTask<Void, Void, Void> {
    private final String mPath;
    private final ExtractImagesListener mExtractImagesListener;
    private int mImagesCount = 0;
    private ArrayList<String> mOutputFilePaths;

    public ExtractImages(String mPath, ExtractImagesListener mExtractImagesListener) {
        this.mPath = mPath;
        this.mExtractImagesListener = mExtractImagesListener;
        mOutputFilePaths = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mExtractImagesListener.extractionStarted();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        mOutputFilePaths = new ArrayList<>();
        mImagesCount = 0;
        try {
            PdfReader reader = new PdfReader(mPath);
            PdfObject obj;
            for (int i = 1; i <= reader.getXrefSize(); i++) {
                obj = reader.getPdfObject(i);
                if (obj != null && obj.isStream()) {
                    PRStream stream = (PRStream) obj;
                    PdfObject type = stream.get(PdfName.SUBTYPE); //get the object type
                    if (type != null && type.toString().equals(PdfName.IMAGE.toString())) {
                        PdfImageObject pio = new PdfImageObject(stream);
                        byte[] image = pio.getImageAsBytes();
                        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0,
                                image.length);
                        String filename = getFileNameWithoutExtension(mPath) +
                                "_" + (mImagesCount + 1);
                        String path = saveImage(filename, bmp);
                        if (path != null) {
                            mOutputFilePaths.add(path);
                            mImagesCount++;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mExtractImagesListener.updateView(mImagesCount, mOutputFilePaths);
    }
}