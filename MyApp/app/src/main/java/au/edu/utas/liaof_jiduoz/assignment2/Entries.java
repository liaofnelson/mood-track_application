package au.edu.utas.liaof_jiduoz.assignment2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.edu.utas.liaof_jiduoz.assignment2.util.DBOpenHelper;

import static au.edu.utas.liaof_jiduoz.assignment2.util.Resoure.image2;
import static au.edu.utas.liaof_jiduoz.assignment2.util.Resoure.table_info;


public class Entries extends AppCompatActivity {

    private List<Map<String,Object>> list;
    private ListView listView;
    private EditText search;
    private SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        init_view();
    }

    //loading page
    private void init_view(){
        search=findViewById(R.id.search);

        //Text modification listener
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                String name = s.toString();
                changeClient(name);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
        //Add an adapter to the list
        list=new ArrayList<Map<String, Object>>();
        listView=findViewById(R.id.listview);
        adapter=new SimpleAdapter(this,list,
                R.layout.listview_son,new String[]{"img","tile","sth"},
                new int[]{R.id.img,
                        R.id.tile,
                        R.id.sth}
        );
        listView.setAdapter(adapter);
        //Add a click event listener to the list
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Map<String, Object> map=list.get(position);
                Intent intent=new Intent(Entries.this, Edit_Entries.class);
                intent.putExtra("date",map.get("sth").toString());
                startActivity(intent);
            }
        });
        data_read();
    }
    public void changeClient(String name){

        Map<String, Object> map = new HashMap<String, Object>();
        Cursor cursor = db.query("entries_info", table_info, "date like ? or topic_text like ?", new String[]{"%"+name+"%","%"+name+"%"}, null, null, null);

        if(cursor.moveToFirst()) {
            list.clear();
            while (true) {
                map=new HashMap<>();
                //map.put(table_info[1],image2[Integer.valueOf(cursor.getString(cursor.getColumnIndex(table_info[1])))]);
                map.put("img",image2[Integer.valueOf(cursor.getString(cursor.getColumnIndex(table_info[1])))]);

                // map.put(table_info[2],cursor.getString(cursor.getColumnIndex(table_info[2])).replaceAll("<.*>",""));
                map.put("tile",cursor.getString(cursor.getColumnIndex(table_info[2])).replaceAll("<.*>",""));

                // map.put(table_info[4],cursor.getString(cursor.getColumnIndex(table_info[4])).replaceAll("<.*>",""));
                map.put("sth",cursor.getString(cursor.getColumnIndex(table_info[4])).replaceAll("<.*>",""));
                list.add(map);
                if(!cursor.moveToNext()){
                    break;
                }
            }

        }
        adapter.notifyDataSetChanged();
        cursor.close();
    }

    private DBOpenHelper helper;
    private SQLiteDatabase db;
    //Connect to SQLite database
    private void DB_connect(){

        helper=new DBOpenHelper(this);
        db=helper.getWritableDatabase();

    }
    private void data_read(){

        DB_connect();
        Map<String,Object> map;
        helper=new DBOpenHelper(this);
        db=helper.getReadableDatabase();
        Cursor cursor = db.query("entries_info", table_info, null, null, null, null, "date");

        if(cursor.moveToFirst()) {
            while (true) {
                map=new HashMap<>();
                //map.put(table_info[1],image2[Integer.valueOf(cursor.getString(cursor.getColumnIndex(table_info[1])))]);
                map.put("img",image2[Integer.valueOf(cursor.getString(cursor.getColumnIndex(table_info[1])))]);

               // map.put(table_info[2],cursor.getString(cursor.getColumnIndex(table_info[2])).replaceAll("<.*>",""));
                map.put("tile",cursor.getString(cursor.getColumnIndex(table_info[2])).replaceAll("<img[\\s\\S]*>",""));

               // map.put(table_info[4],cursor.getString(cursor.getColumnIndex(table_info[4])).replaceAll("<.*>",""));
                map.put("sth",cursor.getString(cursor.getColumnIndex(table_info[4])).replaceAll("<img[\\s\\S]*>",""));
                list.add(map);

                if(!cursor.moveToNext()){
                    break;
                }
            }

        }
        else{
            map=new HashMap<>();
            //map.put(table_info[1],image2[Integer.valueOf(cursor.getString(cursor.getColumnIndex(table_info[1])))]);
            map.put("img",R.mipmap.user);

            // map.put(table_info[2],cursor.getString(cursor.getColumnIndex(table_info[2])).replaceAll("<.*>",""));
            map.put("tile","No title");

            // map.put(table_info[4],cursor.getString(cursor.getColumnIndex(table_info[4])).replaceAll("<.*>",""));
            map.put("sth","This person is lazy, nothing left");
            list.add(map);
            Toast.makeText(this, "Haven't started writing a diary yet.", Toast.LENGTH_LONG).show();

        }
        cursor.close();

    }

    @Override
    protected void onResume() {

        super.onResume();

    }
    @Override
    protected void onPause() {

        db.close();
        super.onPause();

    }
    @Override
    protected void onDestroy() {

        db.close();
        super.onDestroy();

    }
}
