package rav.minibrowser.Contract;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import rav.minibrowser.BrowserHistory;


/**
 * Created by Madan's-PC on 7/14/2017.
 */

public class HistoryContract {

    private final Helper helper;
    Context context;

    public HistoryContract(Context context) {

        this.context = context;
        helper = new Helper(context);

    }

    public long add(String url) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.COL_URL, url);
        values.put(Constants.COL_TIME, System.currentTimeMillis());
        return db.insert(Constants.TBL_HISTORY, null, values);
    }

    public Cursor list() {
        SQLiteDatabase db = helper.getReadableDatabase();
        return db.query(Constants.TBL_HISTORY, null,null,null,null,null,Constants.COL_TIME);
    }

    public int deleteById(String id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String where = Constants.COL_ID + "=?";  // " _id = ? "
        String[] whereArgs = new String[]{id}; // 4
        return db.delete(Constants.TBL_HISTORY,where,whereArgs);
    }

    public int updateById(String whichId, String url ) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String where = Constants.COL_ID + "=?";
        String[] whereArgs = new String[]{whichId};

        ContentValues values = new ContentValues();
        values.put(Constants.COL_URL, url);
        values.put(Constants.COL_TIME, System.currentTimeMillis());

        return db.update(Constants.TBL_HISTORY, values, where, whereArgs);

    }


    public class Helper extends SQLiteOpenHelper {

        public Helper(Context context) {
            super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String creationQueryHistory = "CREATE TABLE history (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "url VARCHAR(255), " + "time LONG " + ")";
            db.execSQL(creationQueryHistory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String dropQueryHistory = "DROP TABLE IF EXISTS history";
            db.execSQL(dropQueryHistory);
            onCreate(db);
        }
    }

    interface Constants {

        public static final String TBL_HISTORY = "history";
        public static final String COL_ID = "_id";
        public static final String COL_URL = "url";
        public static final String COL_TIME = "time";
        public static final String DB_NAME = "mydb";
        public static final int DB_VERSION = 3;


    }
}

