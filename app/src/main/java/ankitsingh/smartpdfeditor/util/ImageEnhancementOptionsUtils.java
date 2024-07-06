package ankitsingh.smartpdfeditor.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

import ankitsingh.smartpdfeditor.R;
import ankitsingh.smartpdfeditor.model.EnhancementOptionsEntity;
import ankitsingh.smartpdfeditor.model.ImageToPDFOptions;

public class ImageEnhancementOptionsUtils {
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
    public ImageEnhancementOptionsUtils() {
    }

    public static ImageEnhancementOptionsUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public ArrayList<EnhancementOptionsEntity> getEnhancementOptions(Context context,
                                                                     ImageToPDFOptions pdfOptions) {
        ArrayList<EnhancementOptionsEntity> options = new ArrayList<>();
        int passwordIcon = R.drawable.baseline_enhanced_encryption_24;
        if (pdfOptions.isPasswordProtected())
            passwordIcon = R.drawable.baseline_done_24;

        options.add(new EnhancementOptionsEntity(
                context, passwordIcon, R.string.password_protect_pdf_text));

        options.add(new EnhancementOptionsEntity(
                context, R.drawable.baseline_crop_rotate_24, R.string.edit_images_text));

        options.add(new EnhancementOptionsEntity(
                context, R.drawable.ic_compress_image,
                String.format(context.getResources().getString(R.string.compress_image),
                        pdfOptions.getQualityString())));

        options.add(new EnhancementOptionsEntity(
                context, R.drawable.ic_photo_filter_black_24dp, R.string.filter_images_Text));

        options.add(new EnhancementOptionsEntity(
                context, R.drawable.ic_page_size_24dp, R.string.set_page_size_text));

        options.add(new EnhancementOptionsEntity(
                context, R.drawable.ic_aspect_ratio_black_24dp, R.string.image_scale_type));

        options.add(new EnhancementOptionsEntity(
                context, R.drawable.ic_play_circle_outline_black_24dp, R.string.preview_image_to_pdf));

        options.add(new EnhancementOptionsEntity(
                context, R.drawable.ic_border_image_black_24dp,
                String.format(context.getResources().getString(R.string.border_dialog_title),
                        pdfOptions.getBorderWidth())));

        options.add(new EnhancementOptionsEntity(
                context, R.drawable.ic_rearrange, R.string.rearrange_images));

        Drawable iconGrayScale = context.getResources().getDrawable(R.drawable.ic_photo_filter_black_24dp);
        iconGrayScale.setColorFilter(Color.GRAY, android.graphics.PorterDuff.Mode.SRC_IN);

        options.add(new EnhancementOptionsEntity(
                iconGrayScale,
                context.getResources().getString(R.string.grayscale_images)));

        options.add(new EnhancementOptionsEntity(
                context, R.drawable.ic_page_size_24dp, R.string.add_margins));

        options.add(new EnhancementOptionsEntity(
                context, R.drawable.ic_format_list_numbered_black_24dp, R.string.show_pg_num));

        options.add(new EnhancementOptionsEntity(
                context, R.drawable.ic_branding_watermark_black_24dp, R.string.add_watermark));

        options.add(new EnhancementOptionsEntity(
                context, R.drawable.ic_page_color, R.string.page_color));

        return options;
    }

    /**
     * Singleton Implementation
     */
    private static class SingletonHolder {
        private static final ImageEnhancementOptionsUtils INSTANCE = new ImageEnhancementOptionsUtils();
    }
}
