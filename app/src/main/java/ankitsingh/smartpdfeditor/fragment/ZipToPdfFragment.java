package ankitsingh.smartpdfeditor.fragment;

import static ankitsingh.smartpdfeditor.util.Constants.REQUEST_CODE_FOR_WRITE_PERMISSION;
import static ankitsingh.smartpdfeditor.util.Constants.WRITE_PERMISSIONS;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dd.morphingbutton.MorphingButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ankitsingh.smartpdfeditor.R;
import ankitsingh.smartpdfeditor.util.PermissionsUtils;
import ankitsingh.smartpdfeditor.util.RealPathUtil;
import ankitsingh.smartpdfeditor.util.ResultUtils;
import ankitsingh.smartpdfeditor.util.ZipToPdf;

public class ZipToPdfFragment extends Fragment {
    private static final int INTENT_REQUEST_PICK_FILE_CODE = 10;
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
    @BindView(R.id.selectFile)
    MorphingButton selectFileButton;
    @BindView(R.id.zip_to_pdf)
    MorphingButton convertButton;
    @BindView(R.id.progressBar)
    ProgressBar extractionProgress;

    private String mPath;
    private Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_zip_to_pdf, container, false);
        ButterKnife.bind(this, rootView);
        mActivity = getActivity();
        return rootView;
    }

    @OnClick(R.id.selectFile)
    public void showFileChooser() {
        PermissionsUtils.getInstance().checkStoragePermissionAndProceed(getContext(), this::chooseFile);
    }

    private void chooseFile() {
        String folderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/";
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        Uri myUri = Uri.parse(folderPath);
        intent.setDataAndType(myUri, "application/zip");

        startActivityForResult(Intent.createChooser(intent, getString(R.string.merge_file_select)),
                INTENT_REQUEST_PICK_FILE_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data); // Ensure to call the superclass method
        if (!ResultUtils.getInstance().checkResultValidity(resultCode, data))
            return;

        if (requestCode == INTENT_REQUEST_PICK_FILE_CODE) {
            mPath = RealPathUtil.getInstance().getRealPath(getContext(), data.getData());
            if (mPath != null) {
                convertButton.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick(R.id.zip_to_pdf)
    public void convertZipToPdf() {
        // Pre conversion tasks
        extractionProgress.setVisibility(View.VISIBLE);
        selectFileButton.blockTouch();
        convertButton.blockTouch();

        // Perform conversion in a background thread to avoid blocking the main thread
        new Thread(() -> {
            ZipToPdf.getInstance().convertZipToPDF(mPath, mActivity);

            mActivity.runOnUiThread(() -> {
                // Post conversion tasks
                extractionProgress.setVisibility(View.GONE);
                selectFileButton.unblockTouch();
                convertButton.unblockTouch();
            });
        }).start();
    }

    private void getRuntimePermissions() {
        PermissionsUtils.getInstance().requestRuntimePermissions(this,
                WRITE_PERMISSIONS,
                REQUEST_CODE_FOR_WRITE_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionsUtils.getInstance().handleRequestPermissionsResult(mActivity, grantResults,
                requestCode, REQUEST_CODE_FOR_WRITE_PERMISSION, this::chooseFile);
    }
}
