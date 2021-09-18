package au.edu.utas.liaof_jiduoz.assignment2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class help extends AppCompatActivity implements View.OnClickListener {

    private EditText help;
    @Override
    public void onStart(){
        super.onStart();
        getSupportActionBar().setTitle("  more help");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        Toolbar toolbar=findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        if (NavUtils.getParentActivityName(au.edu.utas.liaof_jiduoz.assignment2.help.this) != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        help=findViewById(R.id.help);
        data_read();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){


        return true;

    }

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private void data_read(){

        sp=getSharedPreferences("admin",MODE_PRIVATE);
        help.setText(sp.getString("help",help.getText().toString()));

    }
    @Override
    public void onClick(View v){

        switch (v.getId()){


            case R.id.home:
                editor=sp.edit();
                editor.clear();
                editor.putString("help",help.getText().toString());
                editor.commit();
                finish();
                NavUtils.navigateUpFromSameTask(this);
                break;
        }

    }

}
