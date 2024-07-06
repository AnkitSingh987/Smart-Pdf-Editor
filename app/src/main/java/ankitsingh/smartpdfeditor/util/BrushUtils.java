package ankitsingh.smartpdfeditor.util;

import java.util.ArrayList;

import ankitsingh.smartpdfeditor.R;
import ankitsingh.smartpdfeditor.model.BrushItem;

public class BrushUtils {

    private BrushUtils() {

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
    public static BrushUtils getInstance() {
        return BrushUtils.SingletonHolder.INSTANCE;
    }

    public ArrayList<BrushItem> getBrushItems() {
        ArrayList<BrushItem> brushItems = new ArrayList<>();
        brushItems.add(new BrushItem(R.color.mb_white));
        brushItems.add(new BrushItem(R.color.red));
        brushItems.add(new BrushItem(R.color.mb_blue));
        brushItems.add(new BrushItem(R.color.mb_green));
        brushItems.add(new BrushItem(R.color.colorPrimary));
        brushItems.add(new BrushItem(R.color.colorAccent));
        brushItems.add(new BrushItem(R.color.light_gray));
        brushItems.add(new BrushItem(R.color.black));
        brushItems.add(new BrushItem(R.drawable.color_palette));
        return brushItems;
    }

    private static class SingletonHolder {
        static final BrushUtils INSTANCE = new BrushUtils();
    }
}