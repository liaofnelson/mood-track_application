package au.edu.utas.liaof_jiduoz.assignment2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import static au.edu.utas.liaof_jiduoz.assignment2.util.Permission.permission;


public class MainActivity extends AppCompatActivity {
    private int LOCATION_REQUEST_CODE=2;

    @Override
    public void onStart(){
        super.onStart();
        getSupportActionBar().setTitle("");

    }
    //Application for Dangerous Authority
    private void Application_authority (){
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {   // Privileges have not been granted yet. You need to write the code to apply for privileges here.
// The second parameter is an array of strings in which the permissions you need to apply for can be set to apply for more than one permission.
// The last parameter is to mark the permission of your application, which is used in onRequest Permissions Result.
            ActivityCompat.requestPermissions(MainActivity.this,
                    permission,
                    LOCATION_REQUEST_CODE);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice);
        Application_authority();
        Toolbar toolbar=findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        ImageButton button1=findViewById(R.id.ImageButton1);
        ImageButton button2=findViewById(R.id.ImageButton2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this, Choisce.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Entries.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.menu,menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;
        switch(item.getItemId()){
            case R.id.item:
                intent=new Intent(MainActivity.this, help.class);
                startActivity(intent);
                break;
            case R.id.item1:
                intent=new Intent(MainActivity.this, Mood_summary.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
