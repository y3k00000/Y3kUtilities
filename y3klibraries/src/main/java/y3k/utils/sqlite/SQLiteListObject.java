package y3k.utils.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

public abstract class SQLiteListObject {
    private SQLiteListObject(Cursor cursor){
        this.onCreate(cursor);
    }
    public abstract void onCreate(Cursor cursor);
    public abstract void onDestroy();
    public abstract String getSQLDataStructure();
    public abstract ContentValues getSQLDataValues();

    public final static <T extends SQLiteListObject> T parse(Cursor cursor, Class<T> type){
        return null;
    }

    public final static <T extends SQLiteListObject> String getSQLDataStructure(Class<T> type){
        return "()";
    }

    public final static <T extends SQLiteListObject> ContentValues getSQLDataValues(Class<T> type){
        return new ContentValues();
    }
}
