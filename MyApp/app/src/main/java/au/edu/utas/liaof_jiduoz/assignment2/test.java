package au.edu.utas.liaof_jiduoz.assignment2;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        ListView listView=findViewById(R.id.listview);
        SimpleAdapter adapter=new SimpleAdapter(this,getData(),
                R.layout.listview_son,new String[]{"img","tile","sth"},
                new int[]{R.id.img,
                        R.id.tile,
                        R.id.sth}
                );
        listView.setAdapter(adapter);
    }
    private List<Map<String,Object>> getData(){

        List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        Map<String,Object> map=new HashMap<>();
        map.put("img",R.drawable.amazing);
        map.put("tile","1");
        map.put("sth","fasfasv");
        list.add(map);

        map=new HashMap<>();
        map.put("img",R.drawable.amazing);
        map.put("tile","1");
        map.put("sth","fasfasv");
        list.add(map);

        map=new HashMap<>();
        map.put("img",R.drawable.amazing);
        map.put("tile","1");
        map.put("sth","fasfasv");
        list.add(map);

        map=new HashMap<>();
        map.put("img",R.drawable.amazing);
        map.put("tile","1");
        map.put("sth","fasfasv");
        list.add(map);

        return list;
    }
}
