package ashish.sample.care24.com.care24sample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ashish.sample.care24.com.care24sample.objects.FeedObject;

public class FeedDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Feed.db";

    public FeedDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBWrapper.SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBWrapper.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addDataToDatabase(FeedObject feedObject) {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] projection = {
                DBWrapper.FeedEntry._ID,
                DBWrapper.FeedEntry.COLUMN_NAME_TITLE,
                DBWrapper.FeedEntry.COLUMN_NAME_ENTRY_ID,
                DBWrapper.FeedEntry.COLUMN_NAME_URI,
        };

        for (FeedObject.FeedSingleObject obj : feedObject.getData()) {
            ContentValues values = new ContentValues();
            values.put(DBWrapper.FeedEntry.COLUMN_NAME_ENTRY_ID, obj.getId());
            values.put(DBWrapper.FeedEntry.COLUMN_NAME_TITLE, obj.getTitle());
            values.put(DBWrapper.FeedEntry.COLUMN_NAME_URI, obj.getUri());

            Cursor cursor = db.query(DBWrapper.FeedEntry.TABLE_NAME, projection, DBWrapper.FeedEntry.COLUMN_NAME_ENTRY_ID + "=?", new String[]{obj.getId()}, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
                db.update(DBWrapper.FeedEntry.TABLE_NAME, values, DBWrapper.FeedEntry.COLUMN_NAME_ENTRY_ID + "=?", new String[]{obj.getId()});
            } else {
                db.insert(DBWrapper.FeedEntry.TABLE_NAME, null, values);
            }
        }

        db.close();
    }

    public List<FeedObject.FeedSingleObject> getAllFeedData() {
        List<FeedObject.FeedSingleObject> data = new ArrayList<FeedObject.FeedSingleObject>();
        String selectQuery = "SELECT  * FROM " + DBWrapper.FeedEntry.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                FeedObject.FeedSingleObject feed = new FeedObject().new FeedSingleObject();
                feed.setId(cursor.getString(1));
                feed.setTitle(cursor.getString(2));
                feed.setUri(cursor.getString(3));

                data.add(feed);
            } while (cursor.moveToNext());
        }
        return data;
    }
}