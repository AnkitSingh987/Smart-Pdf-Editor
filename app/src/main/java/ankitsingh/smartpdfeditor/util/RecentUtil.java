package ankitsingh.smartpdfeditor.util;

import static ankitsingh.smartpdfeditor.util.Constants.RECENT_PREF;

import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
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
public class RecentUtil {

    public static RecentUtil getInstance() {
        return RecentUtil.SingletonHolder.INSTANCE;
    }

    /**
     * Returns the LinkedHashMap of Recently used feature from the shared preference.
     *
     * @param preferences - preferences object.
     * @return LinkedHashMap consisting of string as a key holding the feature view Id when it was
     * clicked and map of drawable Id and title string res Id.
     */
    public LinkedHashMap<String, Map<String, String>> getList(SharedPreferences preferences)
            throws JSONException {

        //creating the empty list.
        LinkedHashMap<String, Map<String, String>> recentList = new LinkedHashMap<>();

        //check if recent exists.
        if (preferences.contains(RECENT_PREF)) {

            JSONObject jsonObject = new JSONObject(preferences
                    .getString(RECENT_PREF, ""));

            Iterator<String> keys = jsonObject.keys();

            while (keys.hasNext()) {
                String keyVal = keys.next();
                JSONObject item = jsonObject.getJSONObject(keyVal);

                //item object
                Map<String, String> itemMap = new HashMap<>();
                String key = item.keys().next();
                itemMap.put(key, item.getString(key));

                recentList.put(keyVal, itemMap);
            }
        }
        return recentList;
    }

    /**
     * Adds the feature that was clicked to the recent bucket and updates the shared preference.
     *
     * @param preferences - shared preference object.
     * @param resId       - the view Id basically the feature that was clicked.
     * @param itemClicked - Map of Drawable Id and title string resId received from HomePageItem.
     */
    public void addFeatureInRecentList(SharedPreferences preferences,
                                       int resId, Map<String, String> itemClicked) throws JSONException {

        LinkedHashMap<String, Map<String, String>> recentList = getList(preferences);

        //remove the first item from the recent list.
        if (recentList.size() == 3) {
            //now if the list contains the particular key
            if (recentList.remove(String.valueOf(resId)) == null) {
                //bucket is full.
                recentList.remove(recentList.keySet().iterator().next()); //removes the first.
            }
        }

        recentList.put(String.valueOf(resId), itemClicked);

        //update the preferences.
        preferences.edit().putString(RECENT_PREF, new JSONObject(recentList).toString()).apply();
    }

    private static class SingletonHolder {
        static final RecentUtil INSTANCE = new RecentUtil();
    }
}