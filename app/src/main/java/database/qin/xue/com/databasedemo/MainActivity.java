package database.qin.xue.com.databasedemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener,AdapterView.OnItemSelectedListener{
    private static final String TAG = "MainActivity";
    private Button mButtonAdd;
    private Button mButtonDelete;
    private Button mButtonUpdate;
    private Button mButtonQuery;
    private TextView mTextViewResultQuery;
    private TextView mTextViewResultAll;
    private EditText mEditTextID;
    private EditText mEditTextName;
    private EditText mEditTextAge;
    private Spinner mSpinnerGender;
    private int mSelectedGender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonAdd = (Button) findViewById(R.id.add);
        mButtonDelete = (Button) findViewById(R.id.delete);
        mButtonUpdate = (Button) findViewById(R.id.update);
        mButtonQuery = (Button) findViewById(R.id.query);
        mTextViewResultQuery = (TextView) findViewById(R.id.showResult_query);
        mTextViewResultAll = (TextView) findViewById(R.id.showResult_all);
        mEditTextID = (EditText) findViewById(R.id.person_id);
        mEditTextName = (EditText) findViewById(R.id.person_name);
        mEditTextAge = (EditText) findViewById(R.id.person_age);
        mSpinnerGender = (Spinner) findViewById(R.id.person_gender);
        mSpinnerGender.setOnItemSelectedListener(this);
        mButtonAdd.setOnClickListener(this);
        mButtonDelete.setOnClickListener(this);
        mButtonUpdate.setOnClickListener(this);
        mButtonQuery.setOnClickListener(this);
    }

    private String getQueryResultAll() {
        DemoDatabaseHelper helper = new DemoDatabaseHelper(this);
        List<PersonStructure> list = helper.getResultAll();
        StringBuilder builder = new StringBuilder("id\t\tage\t\tgender\t\tname\n");

        for (PersonStructure p : list) {
            builder.append(p);
        }
        return builder.toString();
    }

    private String getQueryResult(PersonStructure p) {
        DemoDatabaseHelper helper = new DemoDatabaseHelper(this);
        List<PersonStructure> list = helper.queryDB(p);
        Log.i(TAG,"list.size() = "+list.size());
        StringBuilder builder = new StringBuilder("id\t\tage\t\tgender\t\tname\n");

        for (PersonStructure per : list) {
            builder.append(per);
        }
        return builder.toString();
    }

    private PersonStructure getInputPerson() {
        PersonStructure p = new PersonStructure();
        String strId = mEditTextID.getText().toString();
        if (strId != null && Utils.isNumeric(strId)) {
            p.setId(Integer.parseInt(strId));
        } else {
            Utils.ErrorToastShow(this,getResources().getString(R.string.empty_id_error));
        }
        String StrName = mEditTextName.getText().toString();
        if (StrName != null&&StrName.length()!=0) {
            p.setName(StrName);
        } else {
            Utils.ErrorToastShow(this,getResources().getString(R.string.empty_name_error));
        }
        String strAge = mEditTextAge.getText().toString();
        if (strAge != null && Utils.isNumeric(strAge)) {
            p.setAge(Integer.parseInt(strAge));
        } else {
            Utils.ErrorToastShow(this,getResources().getString(R.string.empty_age_error));
        }
        p.setGender(mSelectedGender);

        return p;
    }




    private void insertDB(PersonStructure p) {
        if (p != null) {
            DemoDatabaseHelper helper = new DemoDatabaseHelper(this);
            helper.inSertDB(p);
        }
    }

    private void updateDB(PersonStructure p) {
        if (p != null) {
            DemoDatabaseHelper helper = new DemoDatabaseHelper(this);
            helper.updateDB(p);
        }
    }

    private void deleteDB(PersonStructure p) {
        if (p != null) {
            DemoDatabaseHelper helper = new DemoDatabaseHelper(this);
            helper.deleteDB(p);
        }
    }

    @Override
    public void onClick(View v) {
        PersonStructure p = getInputPerson();
        switch (v.getId()) {
            case R.id.add:
                if (p.getId() != -1) {
                    insertDB(p);
                }
                mTextViewResultQuery.setText("");
                mTextViewResultAll.setText(getQueryResultAll());
                break;
            case R.id.delete:
                if (p.getId() != -1) {
                    deleteDB(p);
                }
                mTextViewResultQuery.setText("");
                mTextViewResultAll.setText(getQueryResultAll());
                break;
            case R.id.update:
                if (p.getId() != -1) {
                    updateDB(p);
                }
                mTextViewResultQuery.setText("");
                mTextViewResultAll.setText(getQueryResultAll());
                break;
            case R.id.query:
                mTextViewResultQuery.setText(getQueryResult(p));
                mTextViewResultAll.setText(getQueryResultAll());
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mSelectedGender = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
