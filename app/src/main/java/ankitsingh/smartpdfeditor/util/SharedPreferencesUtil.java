package ankitsingh.smartpdfeditor.util;

import android.content.SharedPreferences;

public class SharedPreferencesUtil {
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
    private SharedPreferencesUtil() {
    }

    public static SharedPreferencesUtil getInstance() {
        return SharedPreferencesUtil.SingletonHolder.INSTANCE;
    }

    /**
     * Set the default Page numbering style
     *
     * @param editor       the {@link SharedPreferences.Editor} to use for editing
     * @param pageNumStyle the page numbering style as defined in {@link Constants}
     * @param id           the id of the style
     */
    public void setDefaultPageNumStyle(SharedPreferences.Editor editor, String pageNumStyle, int id) {
        editor.putString(Constants.PREF_PAGE_STYLE, pageNumStyle);
        editor.putInt(Constants.PREF_PAGE_STYLE_ID, id);
        editor.apply();
    }

    /**
     * Clear the default Page numbering style
     *
     * @param editor the {@link SharedPreferences.Editor} to use for editing
     */
    public void clearDefaultPageNumStyle(SharedPreferences.Editor editor) {
        setDefaultPageNumStyle(editor, null, -1);
    }

    private static class SingletonHolder {
        static final SharedPreferencesUtil INSTANCE = new SharedPreferencesUtil();
    }

}
