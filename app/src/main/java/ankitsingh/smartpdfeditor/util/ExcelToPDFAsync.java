package ankitsingh.smartpdfeditor.util;

import android.os.AsyncTask;

import com.aspose.cells.FileFormatType;
import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.PdfSecurityOptions;
import com.aspose.cells.Workbook;

import ankitsingh.smartpdfeditor.interfaces.OnPDFCreatedInterface;
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
public class ExcelToPDFAsync extends AsyncTask<Void, Void, Void> {
    private final OnPDFCreatedInterface mOnPDFCreatedInterface;
    private final String mPath;
    private final boolean mIsPasswordProtected;
    private final String mDestPath;
    private final String mPassword;
    private boolean mSuccess;

    /**
     * This public constructor is responsible for initializing the path of actual file,
     * the destination path and the onPDFCreatedInterface instance.
     *
     * @param parentPath   is the path of the actual excel file to be converted.
     * @param destPath     is the path of the destination pdf file.
     * @param onPDFCreated is the onPDFCreatedInterface instance.
     */
    public ExcelToPDFAsync(String parentPath, String destPath,
                           OnPDFCreatedInterface onPDFCreated, boolean isPasswordProtected, String password) {
        mPath = parentPath;
        mDestPath = destPath;
        this.mOnPDFCreatedInterface = onPDFCreated;
        mIsPasswordProtected = isPasswordProtected;
        mPassword = password;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mSuccess = true;
        mOnPDFCreatedInterface.onPDFCreationStarted();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            final Workbook workbook = new Workbook(mPath);
            if (mIsPasswordProtected) {
                PdfSaveOptions saveOption = new PdfSaveOptions();
                saveOption.setSecurityOptions(new PdfSecurityOptions());
                saveOption.getSecurityOptions().setUserPassword(mPassword);
                saveOption.getSecurityOptions().setOwnerPassword(mPassword);
                saveOption.getSecurityOptions().setExtractContentPermission(false);
                saveOption.getSecurityOptions().setPrintPermission(false);
                workbook.save(mDestPath, saveOption);
            } else {
                workbook.save(mDestPath, FileFormatType.PDF);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mSuccess = false;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mOnPDFCreatedInterface.onPDFCreated(mSuccess, mPath);
    }
}
