package ankitsingh.smartpdfeditor.fragment;

import static android.app.Activity.RESULT_OK;
import static ankitsingh.smartpdfeditor.util.Constants.REQUEST_CODE_FOR_WRITE_PERMISSION;
import static ankitsingh.smartpdfeditor.util.Constants.WRITE_PERMISSIONS;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.airbnb.lottie.LottieAnimationView;
import com.dd.morphingbutton.MorphingButton;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ankitsingh.smartpdfeditor.R;
import ankitsingh.smartpdfeditor.adapter.FilesListAdapter;
import ankitsingh.smartpdfeditor.adapter.MergeFilesAdapter;
import ankitsingh.smartpdfeditor.database.DatabaseHelper;
import ankitsingh.smartpdfeditor.interfaces.BottomSheetPopulate;
import ankitsingh.smartpdfeditor.interfaces.OnBackPressedInterface;
import ankitsingh.smartpdfeditor.interfaces.OnPDFCreatedInterface;
import ankitsingh.smartpdfeditor.util.BottomSheetCallback;
import ankitsingh.smartpdfeditor.util.BottomSheetUtils;
import ankitsingh.smartpdfeditor.util.CommonCodeUtils;
import ankitsingh.smartpdfeditor.util.DialogUtils;
import ankitsingh.smartpdfeditor.util.FileUtils;
import ankitsingh.smartpdfeditor.util.MorphButtonUtility;
import ankitsingh.smartpdfeditor.util.PermissionsUtils;
import ankitsingh.smartpdfeditor.util.RealPathUtil;
import ankitsingh.smartpdfeditor.util.RemoveDuplicates;
import ankitsingh.smartpdfeditor.util.StringUtils;
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
public class RemoveDuplicatePagesFragment extends Fragment
        implements MergeFilesAdapter.OnClickListener, FilesListAdapter.OnFileItemClickedListener,
        BottomSheetPopulate, OnPDFCreatedInterface, OnBackPressedInterface {

    private static final int INTENT_REQUEST_PICKFILE_CODE = 10;
    BottomSheetBehavior mSheetBehavior;
    @BindView(R.id.lottie_progress)
    LottieAnimationView mLottieProgress;
    @BindView(R.id.selectFile)
    MorphingButton selectFileButton;
    @BindView(R.id.remove)
    MorphingButton removeDuplicateButton;
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
    @BindView(R.id.view_pdf)
    Button mViewPdf;
    private Activity mActivity;
    private String mPath;
    private MorphButtonUtility mMorphButtonUtility;
    private FileUtils mFileUtils;
    private BottomSheetUtils mBottomSheetUtils;
    private MaterialDialog mMaterialDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_remove_duplicate_pages, container, false);
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
        startActivityForResult(mFileUtils.getFileChooser(), INTENT_REQUEST_PICKFILE_CODE);
    }

    // Refactor onActivityResult() method to handle possible NullPointerExceptions
// when accessing data.getData(). Added try-catch blocks to handle exceptions
// in a better way(imo) to prevent app crashes.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK || data == null || data.getData() == null) {
            return;
        }

        if (requestCode == INTENT_REQUEST_PICKFILE_CODE) {
            try {
                // Attempt to get the real path of the selected file and update the UI
                String path = RealPathUtil.getInstance().getRealPath(getContext(), data.getData());
                setTextAndActivateButtons(path);
            } catch (NullPointerException e) {
                // If a NullPointerException occurs, log it to the console and continue
                e.printStackTrace();
            }
        }
    }

    //On click remove duplicate button
    @OnClick(R.id.remove)
    public void parse() {
        new RemoveDuplicates(mPath, this).execute();
    }

    private void resetValues() {
        mPath = null;
        mMorphButtonUtility.initializeButton(selectFileButton, removeDuplicateButton);
    }

    private void setTextAndActivateButtons(String path) {
        mPath = path;
        mMorphButtonUtility.setTextAndActivateButtons(path, selectFileButton, removeDuplicateButton);
    }

    @Override
    public void onPopulate(ArrayList<String> paths) {
        CommonCodeUtils.getInstance().populateUtil(mActivity, paths, this, mLayout, mLottieProgress, mRecyclerViewFiles);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        mMorphButtonUtility = new MorphButtonUtility(mActivity);
        mFileUtils = new FileUtils(mActivity);
        mBottomSheetUtils = new BottomSheetUtils(mActivity);
    }

    @Override
    public void onItemClick(String path) {
        mSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        setTextAndActivateButtons(path);
    }

    @Override
    public void onFileItemClick(String path) {
        mFileUtils.openFile(path, FileUtils.FileType.e_PDF);
    }

    private void viewPdfButton(String path) {
        mViewPdf.setVisibility(View.VISIBLE);
        mViewPdf.setOnClickListener(v -> mFileUtils.openFile(path, FileUtils.FileType.e_PDF));
    }

    @Override
    public void onPDFCreationStarted() {
        mMaterialDialog = DialogUtils.getInstance().createAnimationDialog(mActivity);
        mMaterialDialog.show();
    }

    @Override
    public void onPDFCreated(boolean isNewPdfCreated, String path) {
        mMaterialDialog.dismiss();
        if (!isNewPdfCreated) {
            StringUtils.getInstance().showSnackbar(mActivity, R.string.snackbar_no_duplicate_pdf);
            //Hiding View PDF button
            mViewPdf.setVisibility(View.GONE);
            return;
        }
        new DatabaseHelper(mActivity).insertRecord(path, mActivity.getString(R.string.created));
        StringUtils.getInstance().getSnackbarwithAction(mActivity,
                        R.string.snackbar_duplicate_removed)
                .setAction(R.string.snackbar_viewAction, v -> mFileUtils.openFile(path, FileUtils.FileType.e_PDF)).show();
        viewPdfButton(path);
        resetValues();
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
        PermissionsUtils.getInstance().requestRuntimePermissions(this, WRITE_PERMISSIONS, REQUEST_CODE_FOR_WRITE_PERMISSION);
    }
}
