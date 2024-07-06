package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;

import ankitsingh.smartpdfeditor.R;
import ankitsingh.smartpdfeditor.model.BrushItem;
import ankitsingh.smartpdfeditor.util.BrushUtils;

public class BrushUtilsTest {

    @Test
    public void when_CallingGetBrushItems_Expect_CorrectBrushItemsReturned() {
        ArrayList<BrushItem> brushItems = BrushUtils.getInstance().getBrushItems();
        assertEquals(9, brushItems.size());

        assertTrue(brushItems.contains(new BrushItem(R.color.mb_blue)));
        assertTrue(brushItems.contains(new BrushItem(R.color.red)));
        assertTrue(brushItems.contains(new BrushItem(R.color.mb_white)));
        assertTrue(brushItems.contains(new BrushItem(R.color.mb_green)));
        assertTrue(brushItems.contains(new BrushItem(R.color.colorPrimary)));
        assertTrue(brushItems.contains(new BrushItem(R.color.colorAccent)));
        assertTrue(brushItems.contains(new BrushItem(R.color.light_gray)));
        assertTrue(brushItems.contains(new BrushItem(R.color.black)));
        assertTrue(brushItems.contains(new BrushItem(R.drawable.color_palette)));
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