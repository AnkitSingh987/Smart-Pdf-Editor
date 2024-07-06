package ankitsingh.smartpdfeditor.util;

import android.app.Activity;
import android.content.Intent;
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
public class ResultUtils {

    private ResultUtils() {
    }

    public static ResultUtils getInstance() {
        return ResultUtils.SingletonHolder.INSTANCE;
    }

    public boolean checkResultValidity(int resultCode, Intent data) {

        return resultCode == Activity.RESULT_OK && data != null && data.getData() != null;
    }

    private static class SingletonHolder {
        static final ResultUtils INSTANCE = new ResultUtils();
    }
}
