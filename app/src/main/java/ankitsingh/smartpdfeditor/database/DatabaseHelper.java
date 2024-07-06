package ankitsingh.smartpdfeditor.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import java.util.Date;

public class DatabaseHelper {
    private final Context mContext;
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
    public DatabaseHelper(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * To insert record in the database
     *
     * @param filePath      path of the file
     * @param operationType operation performed on file
     */
    public void insertRecord(String filePath, String operationType) {
        new Insert().execute(new History(filePath, new Date().toString(), operationType));
    }

    @SuppressLint("StaticFieldLeak")
    private class Insert extends AsyncTask<History, Void, Void> {

        @Override
        protected Void doInBackground(History... histories) {
            AppDatabase db = AppDatabase.getDatabase(mContext.getApplicationContext());
            db.historyDao().insertAll(histories);
            return null;
        }
    }
}