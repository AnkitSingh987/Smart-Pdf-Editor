package ankitsingh.smartpdfeditor.model;

import ja.burhanrashid52.photoeditor.PhotoFilter;

public class FilterItem {

    private int mImageId;
    private String mName;
    private PhotoFilter mFilter;

    /**
     * Filter item model
     *
     * @param imageId - id of image to be set
     * @param name    - filter mName
     */
    public FilterItem(int imageId, String name, PhotoFilter filter) {
        this.mImageId = imageId;
        this.mName = name;
        this.mFilter = filter;
    }

    public int getImageId() {
        return mImageId;
    }

    public void setImageId(int imageId) {
        this.mImageId = imageId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public PhotoFilter getFilter() {
        return mFilter;
    }

    public void setFilter(PhotoFilter filter) {
        this.mFilter = filter;
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