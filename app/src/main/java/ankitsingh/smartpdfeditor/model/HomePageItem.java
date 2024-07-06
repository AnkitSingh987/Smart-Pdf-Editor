package ankitsingh.smartpdfeditor.model;

public class HomePageItem {

    private final int mIconId;
    private final int mDrawableId;
    private final int mTitleString;

    /**
     * Constructor for home page item
     *
     * @param iconId      - icon drawable id
     * @param drawableId  - drawable id of the Home Page Item
     * @param titleString - title string resource id of home page item
     */
    public HomePageItem(int iconId, int drawableId, int titleString) {
        mIconId = iconId;
        mDrawableId = drawableId;
        mTitleString = titleString;
    }

    public int getNavigationItemId() {
        return mIconId;
    }

    public int getTitleString() {
        return mTitleString;
    }

    public int getmDrawableId() {
        return mDrawableId;
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