package ankitsingh.smartpdfeditor.util;

import static ankitsingh.smartpdfeditor.util.Constants.DEFAULT_COMPRESSION;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.itextpdf.text.Font;

import java.util.ArrayList;

import ankitsingh.smartpdfeditor.R;
import ankitsingh.smartpdfeditor.model.EnhancementOptionsEntity;
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
public class SettingsOptions {

    public static ArrayList<EnhancementOptionsEntity> getEnhancementOptions(Context context) {
        ArrayList<EnhancementOptionsEntity> options = new ArrayList<>();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        options.add(new EnhancementOptionsEntity(
                context, R.drawable.ic_compress_image,
                String.format(context.getString(R.string.image_compression_value_default),
                        sharedPreferences.getInt(DEFAULT_COMPRESSION, 30))));

        options.add(new EnhancementOptionsEntity(
                context, R.drawable.ic_page_size_24dp,
                String.format(context.getString(R.string.page_size_value_def),
                        sharedPreferences.getString(Constants.DEFAULT_PAGE_SIZE_TEXT,
                                Constants.DEFAULT_PAGE_SIZE))));

        options.add(new EnhancementOptionsEntity(
                context, R.drawable.ic_font_black_24dp,
                String.format(context.getString(R.string.font_size_value_def),
                        sharedPreferences.getInt(Constants.DEFAULT_FONT_SIZE_TEXT,
                                Constants.DEFAULT_FONT_SIZE))));

        Font.FontFamily fontFamily = Font.FontFamily.valueOf(
                sharedPreferences.getString(Constants.DEFAULT_FONT_FAMILY_TEXT,
                        Constants.DEFAULT_FONT_FAMILY));

        options.add(new EnhancementOptionsEntity(
                context, R.drawable.ic_font_family_24dp,
                String.format(context.getString(R.string.font_family_value_def),
                        fontFamily.name())));

        options.add(new EnhancementOptionsEntity(
                context, R.drawable.baseline_settings_brightness_24,
                String.format(context.getString(R.string.theme_value_def),
                        sharedPreferences.getString(Constants.DEFAULT_THEME_TEXT,
                                Constants.DEFAULT_THEME))));

        options.add(new EnhancementOptionsEntity(context,
                R.drawable.ic_aspect_ratio_black_24dp, R.string.image_scale_type));

        options.add(new EnhancementOptionsEntity(context,
                R.drawable.ic_lock_black_24dp, R.string.change_master_pwd));

        options.add(new EnhancementOptionsEntity(context,
                R.drawable.ic_format_list_numbered_black_24dp, R.string.show_pg_num));

        return options;
    }

}
