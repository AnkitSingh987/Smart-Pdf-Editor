package ankitsingh.smartpdfeditor.util;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import ankitsingh.smartpdfeditor.R;
import ankitsingh.smartpdfeditor.adapter.WhatsNewAdapter;
import ankitsingh.smartpdfeditor.model.WhatsNew;
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
public class WhatsNewUtils {

    public static WhatsNewUtils getInstance() {
        return WhatsNewUtils.SingletonHolder.INSTANCE;
    }

    /**
     * Display dialog with whats new
     *
     * @param context - current context
     */
    public void displayDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.fragment_whats_new);
        RecyclerView rv = dialog.findViewById(R.id.whatsNewListView);
        TextView title = dialog.findViewById(R.id.title);
        Button continueButton = dialog.findViewById(R.id.continueButton);
        continueButton.setText(R.string.whatsnew_continue);
        title.setText(R.string.whatsnew_title);
        try {

            JSONObject obj = new JSONObject(WhatsNewUtils.getInstance().loadJSONFromAsset(context));
            WhatsNewAdapter whatsNewAdapter = new WhatsNewAdapter(context, extractItemsFromJSON(obj));
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            rv.setLayoutManager(layoutManager);
            rv.setAdapter(whatsNewAdapter);
            dialog.show();
            continueButton.setOnClickListener(view -> dialog.dismiss());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load data from json file
     *
     * @param context - current context
     * @return - json
     */
    private String loadJSONFromAsset(Context context) {
        String json;
        try {
            InputStream is = context.getAssets().open("whatsnew.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     * Extract what's new items by parsing json
     *
     * @param object - json object to be parsed
     * @return list of whatsnew items
     * @throws JSONException - invalid JSON
     */
    private ArrayList<WhatsNew> extractItemsFromJSON(JSONObject object) throws JSONException {

        ArrayList<WhatsNew> whatsNewList;
        JSONArray data = object.getJSONArray("data");
        whatsNewList = new ArrayList<>();

        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonObject = data.getJSONObject(i);
            String newTitle = jsonObject.getString("title");
            String newContent = jsonObject.getString("content");
            String iconLocation = jsonObject.getString("icon");
            WhatsNew whatsNew = new WhatsNew(newTitle, newContent, iconLocation);
            whatsNewList.add(whatsNew);
        }

        return whatsNewList;
    }

    private static class SingletonHolder {
        static final WhatsNewUtils INSTANCE = new WhatsNewUtils();
    }
}
