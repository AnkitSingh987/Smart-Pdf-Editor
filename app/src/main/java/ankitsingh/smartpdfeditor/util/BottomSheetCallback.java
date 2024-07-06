package ankitsingh.smartpdfeditor.util;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;


public class BottomSheetCallback extends BottomSheetBehavior.BottomSheetCallback {

    private final ImageView mUpArrow;
    private final boolean mIsAdded;

    public BottomSheetCallback(ImageView mUpArrow, boolean mIsFragmentAdded) {
        this.mUpArrow = mUpArrow;
        this.mIsAdded = mIsFragmentAdded;
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
    @Override
    public void onStateChanged(@NonNull View bottomSheet, int newState) {
    }

    @Override
    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        //ensure the fragment is currently added to its activity
        if (mIsAdded) {
            animateBottomSheetArrow(slideOffset);
        }
    }

    /**
     * animates the upArrow icon when sliding the bottom sheet
     *
     * @param slideOffset offset to use to sync the animation with bottom sheet sliding
     */
    private void animateBottomSheetArrow(float slideOffset) {
        if (slideOffset >= 0 && slideOffset <= 1) {
            mUpArrow.setRotation(slideOffset * -180);
        } else if (slideOffset >= -1 && slideOffset < 0) {
            mUpArrow.setRotation(slideOffset * 180);
        }
    }
}