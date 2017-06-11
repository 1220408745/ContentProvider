package database.qin.xue.com.databasedemo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import java.util.List;

/**
 * Created by xue.qin on 2017/6/11.
 */

public class DemoContentProvider extends ContentProvider {
    private static final String TAG = "DemoContentProvider";
    public static final String AUTHORITY = "database.qin.xue.com.databasedemo";
    private DemoDatabaseHelper mHelper;
    private static final int DEMO_BASE = 1;
    private static final int DEMO_BASE_ID = 2;
    private static final int DEMO_BASE_CLOMN = 3;
    private static final UriMatcher sURLMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    /*      --常量 UriMatcher.NO_MATCH 表示不匹配任何路径的返回码
            --# 号为通配符:匹配数字
            --* 号为任意字符：匹配任意字符
    */
    static {
        sURLMatcher.addURI(AUTHORITY, "demobase", DEMO_BASE);
        sURLMatcher.addURI(AUTHORITY, "demobase/#", DEMO_BASE_ID);
        sURLMatcher.addURI(AUTHORITY, "demobase/*/#", DEMO_BASE_CLOMN);
    }

    @Override
    public boolean onCreate() {
        Log.i(TAG, "onCreate()");
        mHelper = new DemoDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Log.i(TAG, "uri.getPath() = " + uri.getPath());
        List<String> list = uri.getPathSegments();
        for (String str : list) {
            Log.i(TAG, "str = " + str);
        }
        int match = sURLMatcher.match(uri);
        Log.i(TAG, "query() match = " + match);
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (match) {
            case DEMO_BASE:
                qb.setTables(DemoDatabaseHelper.TABLE_NAME);
                break;
            case DEMO_BASE_ID:
                qb.setTables(DemoDatabaseHelper.TABLE_NAME);
                qb.appendWhere(DemoContract.DemoColumns2._ID + "=");
                qb.appendWhere(uri.getLastPathSegment());
                break;
            case DEMO_BASE_CLOMN:
                if(uri.getPathSegments().get(1).equals(DemoContract.DemoColumns2.GENDER)){
                    qb.setTables(DemoDatabaseHelper.TABLE_NAME);
                    qb.appendWhere(DemoContract.DemoColumns2.GENDER + "=");
                    qb.appendWhere(uri.getLastPathSegment());
                }else if(uri.getPathSegments().get(1).equals(DemoContract.DemoColumns2.AGE)){
                    qb.setTables(DemoDatabaseHelper.TABLE_NAME);
                    qb.appendWhere(DemoContract.DemoColumns2.AGE + "=");
                    qb.appendWhere(uri.getLastPathSegment());
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor ret = qb.query(db, projection, selection, selectionArgs,
                null, null, sortOrder);
        return ret;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

//    private Uri classification(Uri){
//        r
//    }
}
