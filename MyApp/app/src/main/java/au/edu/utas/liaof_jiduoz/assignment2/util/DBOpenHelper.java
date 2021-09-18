package au.edu.utas.liaof_jiduoz.assignment2.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    //如果不存在  创建新表
    public static final String create_table="create table if not exists entries_info ("+
            "title_str varchar(20) not null"+","+
            "mood_src int not null"+","+
            "topic_text varchar(500) "+ ","+
            "entries_text varchar(500) "+ ","+
            "date varchar(20) primary key"+","+
            "location varchar(20)"+
            ")";
    // "create table USER_INOF ("
    //            + "userid varchar(20) primary key  "+")";
    private static final int VERSION=1;

    //Database name
    private static final String DBNAME ="test";


    public DBOpenHelper(Context context){
        super(context, DBNAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(create_table);//Create user tables

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}
