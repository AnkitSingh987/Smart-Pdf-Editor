package ankitsingh.smartpdfeditor.util;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ankitsingh.smartpdfeditor.adapter.ViewFilesAdapter;
import ankitsingh.smartpdfeditor.interfaces.EmptyStateChangeListener;
import ankitsingh.smartpdfeditor.model.PDFFile;
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
/**
 * AsyncTask used to populate the list of elements in the background
 */
public class PopulateList extends AsyncTask<Void, Void, Void> {

    private final int mCurrentSortingIndex;
    private final EmptyStateChangeListener mEmptyStateChangeListener;
    private final DirectoryUtils mDirectoryUtils;
    private final ViewFilesAdapter mAdapter;
    private final Handler mHandler;
    @Nullable
    private final String mQuery;

    /**
     * Instantiates populate list object
     *
     * @param adapter                  - mAdapter to be notified with new data
     * @param emptyStateChangeListener - set appropriate view on no results
     * @param directoryUtils           - directory utils object
     * @param index                    - sorting order
     * @param mQuery                   - to filter pdf files, {@code null} to get all
     */
    public PopulateList(ViewFilesAdapter adapter,
                        EmptyStateChangeListener emptyStateChangeListener,
                        DirectoryUtils directoryUtils, int index, @Nullable String mQuery) {
        this.mAdapter = adapter;
        mCurrentSortingIndex = index;
        mEmptyStateChangeListener = emptyStateChangeListener;
        this.mQuery = mQuery;
        mHandler = new Handler(Looper.getMainLooper());
        mDirectoryUtils = directoryUtils;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        populateListView();
        return null;
    }

    /**
     * Populate data into listView
     */
    private void populateListView() {
        ArrayList<File> pdfFiles;
        if (TextUtils.isEmpty(mQuery)) {
            pdfFiles = mDirectoryUtils.getPdfFromOtherDirectories();
        } else {
            pdfFiles = mDirectoryUtils.searchPDF(mQuery);
        }
        if (pdfFiles == null)
            mHandler.post(mEmptyStateChangeListener::showNoPermissionsView);
        else if (pdfFiles.size() == 0) {
            mHandler.post(mEmptyStateChangeListener::setEmptyStateVisible);
            mHandler.post(() -> mAdapter.setData(null));
        } else {
            mHandler.post(mEmptyStateChangeListener::setEmptyStateInvisible);
            FileSortUtils.getInstance().performSortOperation(mCurrentSortingIndex, pdfFiles);
            List<PDFFile> pdfFilesWithEncryptionStatus = getPdfFilesWithEncryptionStatus(pdfFiles);
            mHandler.post(mEmptyStateChangeListener::hideNoPermissionsView);
            mHandler.post(() -> mAdapter.setData(pdfFilesWithEncryptionStatus));
            mHandler.post(mEmptyStateChangeListener::filesPopulated);
        }
    }

    /**
     * checks the encryption status of the files using {@link PDFUtils#isPDFEncrypted(String)}
     *
     * @param files files to get statuses
     * @return new list of {@link PDFFile} with encrypted status set
     */
    @WorkerThread
    private List<PDFFile> getPdfFilesWithEncryptionStatus(@NonNull List<File> files) {
        List<PDFFile> pdfFiles = new ArrayList<>(files.size());
        for (File file : files) {
            pdfFiles.add(new PDFFile(file, mAdapter.getPDFUtils().isPDFEncrypted(file.getPath())));
        }
        return pdfFiles;
    }
}