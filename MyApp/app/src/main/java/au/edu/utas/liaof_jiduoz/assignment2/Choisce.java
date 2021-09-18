package au.edu.utas.liaof_jiduoz.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import static au.edu.utas.liaof_jiduoz.assignment2.util.Resoure.image1;
import static au.edu.utas.liaof_jiduoz.assignment2.util.Resoure.image2;
import static au.edu.utas.liaof_jiduoz.assignment2.util.Resoure.image_id;


public class Choisce extends AppCompatActivity implements View.OnClickListener {


    private final ImageButton[] temp=new ImageButton[8];


    int i,j,k;

    @Override
    public void onStart(){
        super.onStart();
        getSupportActionBar().setTitle("what is your feeling?");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_mood);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        if (NavUtils.getParentActivityName(Choisce.this) != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Button write_details=findViewById(R.id.Write_Details);
        Button cancel=findViewById(R.id.Cancel);
        Button save=findViewById(R.id.button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "It's just a button. ", Toast.LENGTH_LONG).show();
            }
        });
        write_details.setOnClickListener(this);
        cancel.setOnClickListener(this);
        for(k=0;k<8;k++) {


            i = image1[k];
            j = image2[k];
            temp[k] = findViewById(image_id[k]);
        }
        Init_Listener();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){



        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Click events
    @Override
    public void onClick(View view){

        switch(view.getId()){

            case R.id.Cancel:
                NavUtils.navigateUpFromSameTask(this);
                break;
            case R.id.Write_Details:
                int p=-1;
                for(k=0;k<8;k++){
                    if(temp[k].isSelected()){
                         p=k;
                    }
                }
                if(p==-1){
                    Toast.makeText(this, "How are you feeling today? ", Toast.LENGTH_LONG).show();
                    break;
                }
                Intent intent=new Intent(Choisce.this, Add_Entries.class);
                intent.putExtra("index",p);
                startActivity(intent);
                break;
                default:

                    break;
        }
    }


    //Registered listener
    private void Init_Listener(){

        temp[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k;
                if(!temp[0].isSelected()){
                    temp[0].setImageResource(image2[0]);
                    temp[0].setSelected(true);
                    for(k=0;k<8;k++){
                        if(k==0)continue;
                            temp[k].setImageResource(image1[k]);

                    }
                }
                else{
                    temp[0].setImageResource(image1[0]);
                    temp[0].setSelected(false);
                }
            }
        });
        temp[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k;
                if(!temp[1].isSelected()){
                    temp[1].setImageResource(image2[1]);
                    temp[1].setSelected(true);
                    for(k=0;k<8;k++){
                        if(k==1)continue;
                        temp[k].setImageResource(image1[k]);


                    }
                }
                else{
                    temp[1].setImageResource(image1[1]);
                    temp[1].setSelected(false);
                }
            }
        });
        temp[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k;
                if(!temp[2].isSelected()){
                    temp[2].setImageResource(image2[2]);
                    temp[2].setSelected(true);
                    for(k=0;k<8;k++){
                        if(k==2)continue;
                        temp[k].setImageResource(image1[k]);

                    }
                }
                else{
                    temp[2].setImageResource(image1[2]);
                    temp[2].setSelected(false);
                }
            }
        });
        temp[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k;
                if(!temp[3].isSelected()){
                    temp[3].setImageResource(image2[3]);
                    temp[3].setSelected(true);
                    for(k=0;k<8;k++){
                        if(k==3)continue;
                        temp[k].setImageResource(image1[k]);

                    }
                }
                else{
                    temp[3].setImageResource(image1[3]);
                    temp[3].setSelected(false);
                }
            }
        });
        temp[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k;
                if(!temp[4].isSelected()){
                    temp[4].setImageResource(image2[4]);
                    temp[4].setSelected(true);
                    for(k=0;k<8;k++){
                        if(k==4)continue;
                        temp[k].setImageResource(image1[k]);

                    }
                }
                else{
                    temp[4].setImageResource(image1[4]);
                    temp[4].setSelected(false);
                }
            }
        });
        temp[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k;
                if(!temp[5].isSelected()){
                    temp[5].setImageResource(image2[5]);
                    temp[5].setSelected(true);
                    for(k=0;k<8;k++){
                        if(k==5)continue;
                        temp[k].setImageResource(image1[k]);

                    }
                }
                else{
                    temp[5].setImageResource(image1[5]);
                    temp[5].setSelected(false);
                }
            }
        });
        temp[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k;
                if(!temp[6].isSelected()){
                    temp[6].setImageResource(image2[6]);
                    temp[6].setSelected(true);
                    for(k=0;k<8;k++){
                        if(k==6)continue;
                        temp[k].setImageResource(image1[k]);

                    }
                }
                else{
                    temp[6].setImageResource(image1[6]);
                    temp[6].setSelected(false);
                }
            }
        });
        temp[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k;
                if(!temp[7].isSelected()){
                    temp[7].setImageResource(image2[7]);
                    temp[7].setSelected(true);
                    for(k=0;k<8;k++){
                        if(k==7)continue;
                        temp[k].setImageResource(image1[k]);

                    }
                }
                else{
                    temp[7].setImageResource(image1[7]);
                    temp[7].setSelected(false);
                }
            }
        });
    }
}
