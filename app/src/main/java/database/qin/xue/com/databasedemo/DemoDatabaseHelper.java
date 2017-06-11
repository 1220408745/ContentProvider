package database.qin.xue.com.databasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xue.qin on 2017/5/27.
 */

public class DemoDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DemoDatabaseHelper";

    private static final int DATABASE_VERSION = 1;  //版本号
    private static final int DATABASE_VERSION_2 = 2;
    private static final String DATABASE_NAME = "demo.db";  //数据库名称
    static final String TABLE_NAME = "table_name";      //表的名称
    private static final String DEFAULT_VALUES_1 = "(1, 'zhang3', 21, 0 );";
    private static final String[] PROJECTION = {DemoContract.DemoColumns2._ID,
            DemoContract.DemoColumns2.NAME,
            DemoContract.DemoColumns2.AGE,
            DemoContract.DemoColumns2.GENDER,
            DemoContract.DemoColumns2.COUNTRY};

    /**
     * 参数3： CursorFactory 可以自定义返回Cursor ，例如加一些过滤条件
     */
    public DemoDatabaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        super(context, DATABASE_NAME, null, DATABASE_VERSION_2);
        Log.i(TAG, "DemoDatabaseHelper()");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate() "+db.getVersion());
        /*执行SQL语句创建数据库*/
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                DemoContract.DemoColumns2._ID + " INTEGER PRIMARY KEY," +
                DemoContract.DemoColumns2.NAME + " TEXT NOT NULL, " +
                DemoContract.DemoColumns2.AGE + " INTEGER NOT NULL DEFAULT 0, " +
                DemoContract.DemoColumns2.GENDER + " INTEGER NOT NULL DEFAULT 0, "+
                DemoContract.DemoColumns2.COUNTRY + " INTEGER NOT NULL DEFAULT 0 )");
        /*插入一段初始数据，也可以不插入*/
        String cs = ", "; //comma and space
        String insertMe = "INSERT INTO " + TABLE_NAME + " (" +
                DemoContract.DemoColumns2._ID + cs +
                DemoContract.DemoColumns2.NAME + cs +
                DemoContract.DemoColumns2.AGE + cs +
                DemoContract.DemoColumns2.GENDER + ") VALUES ";
        db.execSQL(insertMe + DEFAULT_VALUES_1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onUpgrade() oldVersion = "+oldVersion+" newVersion = "+newVersion);
        /*
        如果当前版本是1,那么升级为2，添加1个字段
        * */
        if (oldVersion == DATABASE_VERSION) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + DemoContract.DemoColumns2.COUNTRY + " INTEGER NOT NULL DEFAULT 0");
        }

    }

    public List<PersonStructure> getResult(int id, String name, int age, int gender) {
        List<PersonStructure> personStructures = new ArrayList<PersonStructure>();
        SQLiteDatabase db = getReadableDatabase();   //源码调用 getDatabaseLocked()-->mContext.openOrCreateDatabase() 打开或者创建数据库
        Cursor cursor = db.query(TABLE_NAME, PROJECTION,
                DemoContract.DemoColumns2._ID + " = ?", new String[]{String.valueOf(id)},
                null, null, null);
        if (cursor == null) return personStructures;
        while (cursor.moveToNext()) {
            personStructures.add(new PersonStructure(cursor));
        }
        return personStructures;
    }

    public List<PersonStructure> getResultAll() {
        List<PersonStructure> personStructures = new ArrayList<PersonStructure>();
        SQLiteDatabase db = getReadableDatabase();   //源码调用 getDatabaseLocked()-->mContext.openOrCreateDatabase() 打开或者创建数据库
        Cursor cursor = db.query(TABLE_NAME, PROJECTION,
                null, null,
                null, null, null);
        if (cursor == null) return personStructures;

        while (cursor.moveToNext()) {
            Log.i(TAG,"getResultAll() Country = "+cursor.getInt(4));
            personStructures.add(new PersonStructure(cursor));
        }
        return personStructures;
    }

    public void inSertDB(PersonStructure p) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(DemoContract.DemoColumns2._ID, p.getId());
        values.put(DemoContract.DemoColumns2.NAME, p.getName());
        values.put(DemoContract.DemoColumns2.AGE, p.getAge());
        values.put(DemoContract.DemoColumns2.GENDER, p.getGender());
        db.insert(TABLE_NAME, null, values);    //中间这个参数是否可以插入空行 null表示不可以
    }

    public void deleteDB(PersonStructure p) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete(TABLE_NAME, DemoContract.DemoColumns2._ID + " = ?", new String[]{String.valueOf(p.getId())});
    }

    public void updateDB(PersonStructure p) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(DemoContract.DemoColumns2._ID, p.getId());
        values.put(DemoContract.DemoColumns2.NAME, p.getName());
        values.put(DemoContract.DemoColumns2.AGE, p.getAge());
        values.put(DemoContract.DemoColumns2.GENDER, p.getGender());
        db.update(TABLE_NAME, values, DemoContract.DemoColumns2._ID + " = ?", new String[]{String.valueOf(p.getId())});
    }

    public List<PersonStructure> queryDB(PersonStructure p) {
        List<PersonStructure> personStructures = new ArrayList<PersonStructure>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, PROJECTION,
                DemoContract.DemoColumns2.GENDER + " = ?", new String[]{String.valueOf(p.getGender())}, null, null, null);
        if (cursor == null) return personStructures;
        while (cursor.moveToNext()) {
            personStructures.add(new PersonStructure(cursor));
        }
        return personStructures;
    }
}
