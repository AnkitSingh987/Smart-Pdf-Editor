package ankitsingh.smartpdfeditor.fragment.texttopdf;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import ankitsingh.smartpdfeditor.R;
import ankitsingh.smartpdfeditor.interfaces.Enhancer;
import ankitsingh.smartpdfeditor.model.EnhancementOptionsEntity;
import ankitsingh.smartpdfeditor.model.TextToPDFOptions;
import ankitsingh.smartpdfeditor.util.DefaultTextWatcher;
import ankitsingh.smartpdfeditor.util.DialogUtils;
import ankitsingh.smartpdfeditor.util.StringUtils;

/**
 * An {@link Enhancer} that lets you add and remove passwords
 */
public class PasswordEnhancer implements Enhancer {
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
    private final Activity mActivity;
    private final EnhancementOptionsEntity mEnhancementOptionsEntity;
    private final TextToPDFOptions.Builder mBuilder;
    private TextToPdfContract.View mView;

    PasswordEnhancer(@NonNull final Activity activity,
                     @NonNull final TextToPdfContract.View view,
                     @NonNull final TextToPDFOptions.Builder builder) {
        mActivity = activity;
        mBuilder = builder;
        mBuilder.setPasswordProtected(false);
        mEnhancementOptionsEntity = new EnhancementOptionsEntity(
                mActivity, R.drawable.baseline_enhanced_encryption_24, R.string.set_password);
        mView = view;
    }

    @Override
    public void enhance() {
        MaterialDialog.Builder builder = DialogUtils.getInstance().createCustomDialogWithoutContent(mActivity,
                R.string.set_password);
        final MaterialDialog dialog = builder
                .customView(R.layout.custom_dialog, true)
                .neutralText(R.string.remove_dialog)
                .build();

        final View positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        final View neutralAction = dialog.getActionButton(DialogAction.NEUTRAL);
        final EditText passwordInput = dialog.getCustomView().findViewById(R.id.password);
        passwordInput.setText(mBuilder.getPassword());
        passwordInput.addTextChangedListener(
                new DefaultTextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        positiveAction.setEnabled(s.toString().trim().length() > 0);
                    }
                });

        positiveAction.setOnClickListener(v -> {
            if (StringUtils.getInstance().isEmpty(passwordInput.getText())) {
                StringUtils.getInstance().showSnackbar(mActivity, R.string.snackbar_password_cannot_be_blank);
            } else {
                mBuilder.setPassword(passwordInput.getText().toString());
                mBuilder.setPasswordProtected(true);
                onPasswordAdded();
                dialog.dismiss();
            }
        });

        if (StringUtils.getInstance().isNotEmpty(mBuilder.getPassword())) {
            neutralAction.setOnClickListener(v -> {
                mBuilder.setPassword(null);
                onPasswordRemoved();
                mBuilder.setPasswordProtected(false);
                dialog.dismiss();
                StringUtils.getInstance().showSnackbar(mActivity, R.string.password_remove);
            });
        }
        dialog.show();
        positiveAction.setEnabled(false);
    }

    @Override
    public EnhancementOptionsEntity getEnhancementOptionsEntity() {
        return mEnhancementOptionsEntity;
    }

    private void onPasswordAdded() {
        mEnhancementOptionsEntity
                .setImage(mActivity.getResources().getDrawable(R.drawable.baseline_done_24));
        mView.updateView();
    }

    private void onPasswordRemoved() {
        mEnhancementOptionsEntity
                .setImage(mActivity.getResources().getDrawable(R.drawable.baseline_enhanced_encryption_24));
        mView.updateView();
    }
}
