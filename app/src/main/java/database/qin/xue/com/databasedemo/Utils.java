package database.qin.xue.com.databasedemo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by xue.qin on 2017/5/27.
 */

public class Utils {
    private static final String TAG="Utils";
    public static boolean isNumeric(String str) {
        if (str.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static void ErrorToastShow(Context context, String errorMessage) {
        Log.i(TAG,"ErrorToastShow ()" +errorMessage);
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
    }

}
