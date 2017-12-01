package y3k.utils.sqlite;

import android.database.Cursor;

import java.util.ListIterator;

public class SQLiteListIterator<T extends SQLiteListObject> implements ListIterator<T> {
    private final Cursor cursor;
    private final Class<T> type;
    public SQLiteListIterator(Cursor cursor, Class<T> type){
        this.cursor = cursor;
        this.type = type;
        this.cursor.moveToFirst();
    }

    @Override
    public boolean hasNext() {
        return !this.cursor.isAfterLast();
    }

    @Override
    public T next() {
        if(this.cursor.isAfterLast()){
            throw new IllegalStateException("Cursor has been fully read");
        }
        return SQLiteListObject.parse(cursor, this.type);
    }

    @Override
    public boolean hasPrevious() {
        return !this.cursor.isBeforeFirst();
    }

    @Override
    public T previous() {
        return null;
    }

    @Override
    public int nextIndex() {
        return this.cursor.getPosition()+1;
    }

    @Override
    public int previousIndex() {
        return this.cursor.getPosition()-1;
    }

    @Override
    public void remove() throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Sorry but this is not cool!!");
    }

    @Override
    public void set(SQLiteListObject sqLiteListObject) throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Sorry but this is not cool!!");
    }

    @Override
    public void add(SQLiteListObject sqLiteListObject) throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Sorry but this is not cool!!");
    }
}
