package ankitsingh.smartpdfeditor.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class EnhancementOptionsEntity {
    private Drawable mImage;
    private String mName;

    public EnhancementOptionsEntity(Drawable image, String name) {
        this.mImage = image;
        this.mName = name;
    }

    public EnhancementOptionsEntity(Context context, int imageId, String name) {
        this.mImage = context.getResources().getDrawable(imageId);
        this.mName = name;
    }

    public EnhancementOptionsEntity(Context context, int resourceId, int stringId) {
        this.mImage = context.getResources().getDrawable(resourceId);
        this.mName = context.getString(stringId);
    }

    public Drawable getImage() {
        return mImage;
    }

    public void setImage(Drawable image) {
        this.mImage = image;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
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