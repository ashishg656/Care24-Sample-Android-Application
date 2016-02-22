package ashish.sample.care24.com.care24sample.database;

import android.provider.BaseColumns;

/**
 * Created by ashis_000 on 2/22/2016.
 */
public class DBWrapper {

    public DBWrapper() {
    }

    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "feed";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_URI = "uri";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
            FeedEntry._ID + " INTEGER PRIMARY KEY," +
            FeedEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
            FeedEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP + FeedEntry.COLUMN_NAME_URI + TEXT_TYPE + COMMA_SEP + " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
}
