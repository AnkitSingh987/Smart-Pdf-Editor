package ankitsingh.smartpdfeditor.util;

import android.app.Activity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import ankitsingh.smartpdfeditor.interfaces.BottomSheetPopulate;

public class BottomSheetUtils {

    private final Activity mContext;

    public BottomSheetUtils(Activity context) {
        this.mContext = context;
    }

    public void showHideSheet(BottomSheetBehavior sheetBehavior) {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }
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
    public void populateBottomSheetWithPDFs(BottomSheetPopulate listener) {
        new PopulateBottomSheetList(listener, new DirectoryUtils(mContext)).execute();
    }

    /**
     * Retrieves a list of available excel files on the device
     *
     * @param listener a bottom sheet listener used to inform the caller when the list of files
     *                 is available
     */
    public void populateBottomSheetWithExcelFiles(BottomSheetPopulate listener) {
        new PopulateBottomSheetListWithExcelFiles(listener, new DirectoryUtils(mContext)).execute();
    }

}
