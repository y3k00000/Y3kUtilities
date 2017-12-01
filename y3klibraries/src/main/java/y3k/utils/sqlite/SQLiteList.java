package y3k.utils.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SQLiteList<T extends SQLiteListObject> extends SQLiteOpenHelper implements List<T> {
    private final String name;
    private final Class<T> type;
    public SQLiteList(Context context,String name, Class<T> type){
        super(context,name,null,0);
        // TODO Version
        this.name = name;
        this.type = type;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.rawQuery("create table "+this.name+" values "+SQLiteListObject.getSQLDataStructure(this.type),null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO
    }

    @Override
    public int size() {
        Cursor countCursor = this.getReadableDatabase().rawQuery("select COUNT(*) from table "+this.name,null);
        countCursor.moveToFirst();
        int size = countCursor.getInt(0);
        countCursor.close();
        return size;
    }

    @Override
    public boolean isEmpty() {
        return this.size()==0;
    }

    @Override
    public boolean contains(Object o) {
        // TODO
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new SQLiteListIterator<>(this.getReadableDatabase().rawQuery("select * from "+this.name,null),this.type);
    }

    @Override
    public Object[] toArray() {
        Object[] resultArray = new Object[this.size()];
        Iterator<T> iterator = this.iterator();
        for(int i=0;iterator.hasNext();i++){
            resultArray[i] = iterator.next();
        }
        return resultArray;
    }

    @Override
    public Object[] toArray(Object[] array) {
        Iterator<T> iterator = this.iterator();
        for(int i=0;i<array.length&&iterator.hasNext();i++){
            array[i] = iterator.next();
        }
        return array;
    }

    @Override
    public boolean add(SQLiteListObject sqLiteListObject) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        // TODO
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // TODO
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        // TODO
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        // TODO
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // TODO
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO
        return false;
    }

    @Override
    public void clear() {
        this.getWritableDatabase().rawQuery("drop table "+this.name,null);
        this.onCreate(this.getWritableDatabase());
    }

    @Override
    public T get(int index) {
        // TODO
        return null;
    }

    @Override
    public T set(int index, SQLiteListObject element) {
        // TODO
        return null;
    }

    @Override
    public void add(int index, SQLiteListObject element) {
        // TODO
    }

    @Override
    public T remove(int index) {
        // TODO
        return null;
    }

    private boolean checkIfSameType(Object obj) throws ClassCastException{
        return this.type.isInstance(obj.getClass());
    }

    @Override
    public int indexOf(Object o) {
        if(checkIfSameType(o)){
            return -1;
        }
        // TODO
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        if(checkIfSameType(o)){
            return -1;
        }
        // TODO
        return 0;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new SQLiteListIterator<>(this.getReadableDatabase().rawQuery("select * from "+this.name,null),this.type);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        // TODO 好像是這樣寫，待測試。
        return new SQLiteListIterator<>(this.getReadableDatabase().rawQuery("select * from "+this.name+" offset "+index,null),this.type);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not supported by this class!!");
    }
}
