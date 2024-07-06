package ankitsingh.smartpdfeditor.util;

import static ankitsingh.smartpdfeditor.util.Constants.pdfDirectory;
import static ankitsingh.smartpdfeditor.util.Constants.tempDirectory;

import android.app.Activity;
import android.net.Uri;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import ankitsingh.smartpdfeditor.R;
import ankitsingh.smartpdfeditor.activity.MainActivity;

public class ZipToPdf {
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
    private static final int BUFFER_SIZE = 4096;

    private ZipToPdf() {
        // Private constructor to prevent instantiation
    }

    public static ZipToPdf getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Converts zip file to PDF
     *
     * @param path    - path of zip file
     * @param context - current context
     */
    public void convertZipToPDF(String path, Activity context) {
        ArrayList<Uri> imageUris = new ArrayList<>();
        DirectoryUtils.makeAndClearTemp();
        String dest = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() +
                pdfDirectory + tempDirectory;

        File destDir = new File(dest);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        try (FileInputStream fileInputStream = new FileInputStream(path);
             ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(fileInputStream))) {

            ZipEntry zipEntry;
            int folderPrefix = 0;

            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String zipEntryName = zipEntry.getName().toLowerCase();

                if (zipEntry.isDirectory()) {
                    folderPrefix++;
                } else if (zipEntryName.endsWith(".jpg") || zipEntryName.endsWith(".png")) {
                    String newFileName = File.separator + zipEntryName;
                    int index = zipEntryName.lastIndexOf(File.separator);

                    if (index != -1)
                        newFileName = zipEntryName.substring(index);

                    if (folderPrefix != 0)
                        newFileName = newFileName.replace(File.separator, File.separator + folderPrefix + "- ");

                    File newFile = new File(dest + newFileName);
                    if (!newFile.getParentFile().exists()) {
                        newFile.getParentFile().mkdirs();
                    }

                    imageUris.add(Uri.fromFile(newFile));

                    try (FileOutputStream fileOutputStream = new FileOutputStream(newFile);
                         BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, BUFFER_SIZE)) {

                        byte[] buffer = new byte[BUFFER_SIZE];
                        int count;
                        while ((count = zipInputStream.read(buffer)) != -1) {
                            bufferedOutputStream.write(buffer, 0, count);
                        }
                    }
                }
            }

            if (imageUris.size() == 0) {
                StringUtils.getInstance().showSnackbar(context, R.string.error_no_image_in_zip);
                return;
            }

            ((MainActivity) context).convertImagesToPdf(imageUris);

        } catch (IOException e) {
            e.printStackTrace();
            StringUtils.getInstance().showSnackbar(context, R.string.error_open_file);
        }
    }

    private static class SingletonHolder {
        static final ZipToPdf INSTANCE = new ZipToPdf();
    }
}
