package ankitsingh.smartpdfeditor.fragment;

import static android.app.Activity.RESULT_OK;
import static android.os.ParcelFileDescriptor.MODE_READ_ONLY;
import static ankitsingh.smartpdfeditor.util.Constants.REQUEST_CODE_FOR_WRITE_PERMISSION;
import static ankitsingh.smartpdfeditor.util.Constants.WRITE_PERMISSIONS;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.dd.morphingbutton.MorphingButton;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ankitsingh.smartpdfeditor.R;
import ankitsingh.smartpdfeditor.adapter.FilesListAdapter;
import ankitsingh.smartpdfeditor.adapter.MergeFilesAdapter;
import ankitsingh.smartpdfeditor.interfaces.BottomSheetPopulate;
import ankitsingh.smartpdfeditor.interfaces.OnBackPressedInterface;
import ankitsingh.smartpdfeditor.util.BottomSheetCallback;
import ankitsingh.smartpdfeditor.util.BottomSheetUtils;
import ankitsingh.smartpdfeditor.util.CommonCodeUtils;
import ankitsingh.smartpdfeditor.util.FileUtils;
import ankitsingh.smartpdfeditor.util.MorphButtonUtility;
import ankitsingh.smartpdfeditor.util.PermissionsUtils;
import ankitsingh.smartpdfeditor.util.RealPathUtil;
import ankitsingh.smartpdfeditor.util.SplitPDFUtils;
import ankitsingh.smartpdfeditor.util.StringUtils;
import ankitsingh.smartpdfeditor.util.ViewFilesDividerItemDecoration;

public class SplitFilesFragment extends Fragment implements MergeFilesAdapter.OnClickListener,
        FilesListAdapter.OnFileItemClickedListener, BottomSheetPopulate, OnBackPressedInterface {
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
    private static final int INTENT_REQUEST_PICKFILE_CODE = 10;
    @BindView(R.id.lottie_progress)
    LottieAnimationView mLottieProgress;
    @BindView(R.id.selectFile)
    MorphingButton selectFileButton;
    @BindView(R.id.splitFiles)
    MorphingButton splitFilesButton;
    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;
    @BindView(R.id.upArrow)
    ImageView mUpArrow;
    @BindView(R.id.downArrow)
    ImageView mDownArrow;
    @BindView(R.id.layout)
    RelativeLayout mLayout;
    @BindView(R.id.recyclerViewFiles)
    RecyclerView mRecyclerViewFiles;
    @BindView(R.id.splitted_files)
    RecyclerView mSplittedFiles;
    @BindView(R.id.splitfiles_text)
    TextView splitFilesSuccessText;
    @BindView(R.id.split_config)
    EditText mSplitConfitEditText;
    private Activity mActivity;
    private String mPath;
    private MorphButtonUtility mMorphButtonUtility;
    private FileUtils mFileUtils;
    private SplitPDFUtils mSplitPDFUtils;
    private BottomSheetUtils mBottomSheetUtils;
    private BottomSheetBehavior mSheetBehavior;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_split_files, container, false);
        ButterKnife.bind(this, rootview);
        mSheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        mSheetBehavior.setBottomSheetCallback(new BottomSheetCallback(mUpArrow, isAdded()));
        mLottieProgress.setVisibility(View.VISIBLE);
        mBottomSheetUtils.populateBottomSheetWithPDFs(this);
        getRuntimePermissions();

        resetValues();
        return rootview;
    }

    @OnClick(R.id.viewFiles)
    void onViewFilesClick(View view) {
        mBottomSheetUtils.showHideSheet(mSheetBehavior);
    }

    /**
     * Displays file chooser intent
     */
    @OnClick(R.id.selectFile)
    public void showFileChooser() {
        startActivityForResult(mFileUtils.getFileChooser(),
                INTENT_REQUEST_PICKFILE_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) throws NullPointerException {
        if (data == null || resultCode != RESULT_OK || data.getData() == null)
            return;
        if (requestCode == INTENT_REQUEST_PICKFILE_CODE) {
            //Getting Absolute Path
            String path = RealPathUtil.getInstance().getRealPath(getContext(), data.getData());
            setTextAndActivateButtons(path);
        }
    }

    @OnClick(R.id.splitFiles)
    public void parse() {
        StringUtils.getInstance().hideKeyboard(mActivity);

        ArrayList<String> outputFilePaths = mSplitPDFUtils.splitPDFByConfig(mPath,
                mSplitConfitEditText.getText().toString());
        int numberOfPages = outputFilePaths.size();
        if (numberOfPages == 0) {
            return;
        }
        String output = String.format(mActivity.getString(R.string.split_success), numberOfPages);
        StringUtils.getInstance().showSnackbar(mActivity, output);
        splitFilesSuccessText.setVisibility(View.VISIBLE);
        splitFilesSuccessText.setText(output);

        FilesListAdapter splitFilesAdapter = new FilesListAdapter(mActivity, outputFilePaths, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        mSplittedFiles.setVisibility(View.VISIBLE);
        mSplittedFiles.setLayoutManager(mLayoutManager);
        mSplittedFiles.setAdapter(splitFilesAdapter);
        mSplittedFiles.addItemDecoration(new ViewFilesDividerItemDecoration(mActivity));
        resetValues();
    }

    private void resetValues() {
        mPath = null;
        mMorphButtonUtility.initializeButton(selectFileButton, splitFilesButton);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        mMorphButtonUtility = new MorphButtonUtility(mActivity);
        mFileUtils = new FileUtils(mActivity);
        mSplitPDFUtils = new SplitPDFUtils(mActivity);
        mBottomSheetUtils = new BottomSheetUtils(mActivity);
    }

    @Override
    public void onItemClick(String path) {
        mSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        setTextAndActivateButtons(path);
    }

    private void setTextAndActivateButtons(String path) {
        mSplittedFiles.setVisibility(View.GONE);
        splitFilesSuccessText.setVisibility(View.GONE);
        mPath = path;
        String defaultSplitConfig = getDefaultSplitConfig(mPath);
        if (defaultSplitConfig != null) {
            mMorphButtonUtility.setTextAndActivateButtons(path,
                    selectFileButton, splitFilesButton);
            mSplitConfitEditText.setVisibility(View.VISIBLE);
            mSplitConfitEditText.setText(defaultSplitConfig);
        } else
            resetValues();
    }

    /**
     * Gets the total number of pages in the
     * selected PDF and returns them in
     * default format for single page pdfs
     * (1,2,3,4,5,)
     */
    private String getDefaultSplitConfig(String mPath) {
        StringBuilder splitConfig = new StringBuilder();
        ParcelFileDescriptor fileDescriptor = null;
        try {
            if (mPath != null)
                fileDescriptor = ParcelFileDescriptor.open(new File(mPath), MODE_READ_ONLY);
            if (fileDescriptor != null) {
                PdfRenderer renderer = new PdfRenderer(fileDescriptor);
                final int pageCount = renderer.getPageCount();
                for (int i = 1; i <= pageCount; i++) {
                    splitConfig.append(i).append(",");
                }
            }
        } catch (Exception er) {
            er.printStackTrace();
            StringUtils.getInstance().showSnackbar(mActivity, R.string.encrypted_pdf);
            return null;
        }
        return splitConfig.toString();
    }

    @Override
    public void onFileItemClick(String path) {
        mFileUtils.openFile(path, FileUtils.FileType.e_PDF);
    }

    @Override
    public void onPopulate(ArrayList<String> paths) {
        CommonCodeUtils.getInstance().populateUtil(mActivity, paths,
                this, mLayout, mLottieProgress, mRecyclerViewFiles);
    }

    @Override
    public void closeBottomSheet() {
        CommonCodeUtils.getInstance().closeBottomSheetUtil(mSheetBehavior);
    }

    @Override
    public boolean checkSheetBehaviour() {
        return CommonCodeUtils.getInstance().checkSheetBehaviourUtil(mSheetBehavior);
    }

    /***
     * check runtime permissions for storage and camera
     ***/
    private void getRuntimePermissions() {
        PermissionsUtils.getInstance().requestRuntimePermissions(this,
                WRITE_PERMISSIONS,
                REQUEST_CODE_FOR_WRITE_PERMISSION);
    }
}