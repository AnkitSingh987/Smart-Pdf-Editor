package ankitsingh.smartpdfeditor.util;

import static ankitsingh.smartpdfeditor.util.Constants.ADD_PASSWORD;
import static ankitsingh.smartpdfeditor.util.Constants.ADD_WATERMARK;
import static ankitsingh.smartpdfeditor.util.Constants.REMOVE_PASSWORD;
import static ankitsingh.smartpdfeditor.util.Constants.ROTATE_PAGES;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.afollestad.materialdialogs.MaterialDialog;

import ankitsingh.smartpdfeditor.R;
import ankitsingh.smartpdfeditor.interfaces.DialogCallbacks;
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

public class DialogUtils {

    public static final int EMPTY_STRING = -1;

    private DialogUtils() {
    }

    public static DialogUtils getInstance() {
        return DialogUtils.SingletonHolder.INSTANCE;
    }

    /**
     * Creates a material dialog with `Warning` title
     *
     * @param activity - activity instance
     * @param content  - content resource id
     * @return - material dialog builder
     */
    public MaterialDialog.Builder createWarningDialog(Activity activity,
                                                      int content) {
        return new MaterialDialog.Builder(activity)
                .title(R.string.warning)
                .content(content)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel);
    }

    /**
     * Creates a material dialog with `warning title` and overwrite message as content
     *
     * @param activity - activity instance
     * @return - material dialog builder
     */
    public MaterialDialog.Builder createOverwriteDialog(Activity activity) {
        return new MaterialDialog.Builder(activity)
                .title(R.string.warning)
                .content(R.string.overwrite_message)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel);
    }

    /**
     * Creates a material dialog with given title & content
     *
     * @param activity - activity instance
     * @param title    - dialog title resource id
     * @param content  - content resource id
     * @return - material dialog builder
     */
    public MaterialDialog.Builder createCustomDialog(Activity activity,
                                                     int title, int content) {
        return new MaterialDialog.Builder(activity)
                .title(title)
                .content(content)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel);
    }

    /**
     * Creates a material dialog with given title
     *
     * @param activity - activity instance
     * @param title    - dialog title resource id
     * @return - material dialog builder
     */
    public MaterialDialog.Builder createCustomDialogWithoutContent(Activity activity,
                                                                   int title) {
        return new MaterialDialog.Builder(activity)
                .title(title)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel);
    }

    /**
     * Creates dialog with animation
     *
     * @param activity - activity instance
     * @return - material dialog
     */
    public MaterialDialog createAnimationDialog(Activity activity) {
        return new MaterialDialog.Builder(activity)
                .customView(R.layout.lottie_anim_dialog, false)
                .build();
    }

    /**
     * Creates dialog with animation
     *
     * @param activity - activity instance
     * @param title    - dialog message
     * @return - material dialog
     */
    public MaterialDialog createCustomAnimationDialog(Activity activity, String title) {
        View view = LayoutInflater.from(activity).inflate(R.layout.lottie_anim_dialog, null);

        TextView dialogTitle = view.findViewById(R.id.textView);
        dialogTitle.setText(title);

        return new MaterialDialog.Builder(activity)
                .customView(view, false)
                .build();

    }

    public void showFilesInfoDialog(Activity activity, int dialogId) {
        int stringId = R.string.viewfiles_rotatepages;
        switch (dialogId) {
            case ROTATE_PAGES:
                stringId = R.string.viewfiles_rotatepages;
                break;
            case REMOVE_PASSWORD:
                stringId = R.string.viewfiles_removepassword;
                break;
            case ADD_PASSWORD:
                stringId = R.string.viewfiles_addpassword;
                break;
            case ADD_WATERMARK:
                stringId = R.string.viewfiles_addwatermark;
                break;
        }
        new MaterialDialog.Builder(activity)
                .title(R.string.app_name)
                .content(stringId)
                .positiveText(android.R.string.ok)
                .build()
                .show();
    }

    public static void showChoiceDialog(
            Context context,
            int title,
            int message,
            int positiveButtonLabel,
            int negativeButtonLabel,
            boolean cancelable,
            final DialogCallbacks callbacks
    ) {
        new AlertDialog.Builder(context)
                .setTitle(title == EMPTY_STRING ? "" : context.getString(title))
                .setMessage(message == EMPTY_STRING ? "" : context.getString(message))
                .setCancelable(cancelable)
                .setPositiveButton(positiveButtonLabel, (dialog, which) -> {
                    callbacks.onPositiveButtonClick();
                    dialog.dismiss();
                })
                .setNegativeButton(negativeButtonLabel, ((dialog, which) -> {
                    callbacks.onNegativeButtonClick();
                    dialog.dismiss();
                }))
                .show();
    }

    private static class SingletonHolder {
        static final DialogUtils INSTANCE = new DialogUtils();
    }

}