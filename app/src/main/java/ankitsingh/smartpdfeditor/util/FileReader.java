package ankitsingh.smartpdfeditor.util;

import android.content.Context;
import android.net.Uri;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;

import java.io.InputStream;

public abstract class FileReader {
    Context mContext;
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
    public FileReader(Context context) {
        mContext = context;
    }

    void read(Uri uri, Document document, Font myfont) {
        try {
            InputStream inputStream;
            inputStream = mContext.getContentResolver().openInputStream(uri);
            if (inputStream == null)
                return;
            createDocumentFromStream(uri, document, myfont, inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void createDocumentFromStream(
            Uri uri, Document document, Font myfont, InputStream inputStream) throws Exception;
}