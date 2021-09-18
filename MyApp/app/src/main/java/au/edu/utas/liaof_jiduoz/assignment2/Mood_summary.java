package au.edu.utas.liaof_jiduoz.assignment2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import au.edu.utas.liaof_jiduoz.assignment2.util.DBOpenHelper;

import static au.edu.utas.liaof_jiduoz.assignment2.util.DBOpenHelper.create_table;
import static au.edu.utas.liaof_jiduoz.assignment2.util.Resoure.table_info;

public class Mood_summary extends AppCompatActivity {

    private TextView num1,num2,num3,num4,num5,num6,num7,num8;
    int num[]=new int[]{0,0,0,0,0,0,0,0};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mood_summary);
        init_view();
        data_read();
    }

    private void init_view(){

        num1=findViewById(R.id.num1);
        num2=findViewById(R.id.num2);
        num3=findViewById(R.id.num3);
        num4=findViewById(R.id.num4);
        num5=findViewById(R.id.num5);
        num6=findViewById(R.id.num6);
        num7=findViewById(R.id.num7);
        num8=findViewById(R.id.num8);
    }

    private DBOpenHelper helper;
    private SQLiteDatabase db;
    private void data_read(){


        helper = new DBOpenHelper(this);
        db = helper.getReadableDatabase();
        db.execSQL(create_table);
        Cursor cursor = db.query("entries_info", table_info, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            while (true) {

                num[Integer.valueOf(cursor.getString(cursor.getColumnIndex(table_info[1])))]++;
                if(!cursor.moveToNext()){
                    break;
                }
            }
        }
        num1.setText(String.valueOf(num[0]));
        num2.setText(String.valueOf(num[1]));
        num3.setText(String.valueOf(num[2]));
        num4.setText(String.valueOf(num[3]));
        num5.setText(String.valueOf(num[4]));
        num6.setText(String.valueOf(num[5]));
        num7.setText(String.valueOf(num[6]));
        num8.setText(String.valueOf(num[7]));
        cursor.close();

    }
}
