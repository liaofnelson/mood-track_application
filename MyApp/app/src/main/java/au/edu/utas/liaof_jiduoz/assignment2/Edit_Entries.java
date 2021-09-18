package au.edu.utas.liaof_jiduoz.assignment2;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import au.edu.utas.liaof_jiduoz.assignment2.util.DBOpenHelper;

import static au.edu.utas.liaof_jiduoz.assignment2.util.Resoure.image2;
import static au.edu.utas.liaof_jiduoz.assignment2.util.Resoure.mood;
import static au.edu.utas.liaof_jiduoz.assignment2.util.Resoure.table_info;


public class Edit_Entries extends AppCompatActivity {

    private TextView mood_copy,location_copy,export,delete,save_edit,back;
    private EditText topic_copy,entries_copy;
    private ImageView mood_image_copy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_entries);
        init_view();
    }

    private String date;
    private ArrayList<Uri> uris;

    private ArrayList<String> texts;


    public void show(View view){
        final Dialog dialog = new Dialog(this,R.style.DialogTheme);
        //Fill the layout of the dialog
        //
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        // Initialize control
        TextView insert1,insert2,cancel;
        insert1 =  inflate.findViewById(R.id.camera);
        insert2 =  inflate.findViewById(R.id.pic);
        cancel =  inflate.findViewById(R.id.cancel);
        insert1.setText("Export text");
        insert2.setText("Export pictures");
        insert1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putStringArrayListExtra(Intent.EXTRA_TEXT,texts );
                startActivity(Intent.createChooser(intent, "share "));

            }
        });
        insert2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/jpeg");
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,uris);
                startActivity(Intent.createChooser(intent, "share"));
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // Set the layout to Dialog
        dialog.setContentView(inflate);
        // Get the form where the current activity is located
        Window dialogWindow = dialog.getWindow();
        // Set Dialog to pop up from the bottom of the form
        dialogWindow.setGravity( Gravity.BOTTOM);
        // Get the properties of the form
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;
        // Set the distance between Dialog and the bottom//
// Setting properties to forms

        dialogWindow.setAttributes(lp);
        dialog.show();
        // Display dialog box
    }
    //loading page
    private void init_view(){

        uris=new ArrayList<>();
        texts=new ArrayList<>();
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(Edit_Entries.this);
                finish();
            }
        });
        mood_copy=findViewById(R.id.mood_copy);
        topic_copy=findViewById(R.id.topic_copy);
        entries_copy=findViewById(R.id.entries_copy);
        mood_image_copy=findViewById(R.id.mood_image_copy);
        location_copy=findViewById(R.id.location_copy);
        Intent intent=getIntent();
        date=intent.getStringExtra("date");
        data_read();
        export=findViewById(R.id.export);
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            show(v);

            }
        });
        delete=findViewById(R.id.delete_entries);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entries_delete();
                finish();
                Intent intent=new Intent(Edit_Entries.this, MainActivity.class);
                startActivity(intent);
            }
        });
        save_edit=findViewById(R.id.save_edit);
        save_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_edit();
                finish();
                Intent intent=new Intent(Edit_Entries.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private DBOpenHelper helper;
    private SQLiteDatabase db;
    private void data_read() {

        helper = new DBOpenHelper(this);
        db = helper.getReadableDatabase();
        Cursor cursor = db.query("entries_info", table_info, "date=?", new String[]{date}, null, null, null);
        if(cursor.moveToFirst()) {
            while (true) {
                mood_copy.setText(mood[Integer.valueOf(cursor.getString(cursor.getColumnIndex(table_info[1])))]);
                mood_image_copy.setImageResource(image2[Integer.valueOf(cursor.getString(cursor.getColumnIndex(table_info[1])))]);
                text_copy(cursor.getString(cursor.getColumnIndex(table_info[2])),topic_copy);
                //topic_copy.setText(cursor.getString(cursor.getColumnIndex(table_info[2])));
                text_copy(cursor.getString(cursor.getColumnIndex(table_info[3])),entries_copy);
                //entries_copy.setText(cursor.getString(cursor.getColumnIndex(table_info[3])));
                location_copy.setText(cursor.getString(cursor.getColumnIndex(table_info[4]))+"\n"+cursor.getString(cursor.getColumnIndex(table_info[5])));
                if(!cursor.moveToNext()){
                    break;
                }
            }
        }
        cursor.close();
    }

    private  final int  imageWidth=500,imageHight=500;

    //Diary Text Reproduction
    private void text_copy(String s,EditText text){

        String[] s1=s.split("<img[\\s\\S]*/>"),s2;

        if(s.equals("")){
            return;
        }
        for(String a:s1){
            s=s.replaceAll(a,",");
        }
        Log.i("Replaced string",s);
        s2=s.split(",");
        int a = 0, b = 0;

        char[] arr=s.toCharArray();
        if(arr[0]!='<'){

            while(true) {

                try {
                    if (a >= s1.length && b >= s2.length) break;
                    if (a < s1.length) {
                        if (s1[a].equals("")&&(a+1)<=s1.length)
                            a++;
                        //Log.i("text", s1[a]);
                        texts.add(s1[a]);
                        int index = text.getSelectionStart();
                        // Get the location of the cursor
                        Editable edit_text = text.getEditableText();
                        if (index < 0 || index >= edit_text.length()) {
                            edit_text.append(s1[a]);
                        } else {
                            edit_text.insert(index, s1[a]);
                        }
                        a++;
                    }
                    if (b < s2.length) {
                        if (s2[b].equals("")&&(b+1)<=s2.length)
                            b++;
                        String temp[]=s2[b].split("\"[\\s\\S]*\"");
                        for(String c:temp) {
                            s2[b]=s2[b].replaceAll(c,"");
                        }
                        s2[b]=s2[b].replaceAll("\"","");
                        Uri imgUri=Uri.parse(s2[b]);
                        uris.add(imgUri);
                        Log.i("uri",s2[b] );
                        Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(imgUri));
                        bit = zoomImg(bit, imageWidth / 2, imageHight / 2);
                        ImageSpan imageSpan = new ImageSpan(getApplicationContext(), bit);
                        SpannableString spannableString = new SpannableString(s2[b]);
                        spannableString.setSpan(imageSpan, 0, s2[b].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        //Log.i("image", s2[b]);
                        int index = text.getSelectionStart();
                        // Get the location of the cursor
                        Editable edit_text = text.getEditableText();
                        if (index < 0 || index >= edit_text.length()) {
                            edit_text.append(spannableString);
                        } else {
                            edit_text.insert(index, spannableString);
                        }

                        b++;
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }

        }
        else{
            while(true) {

                try {
                    if (a >= s1.length && b >= s2.length) break;
                    if (b < s2.length) {
                        if (s2[b].equals("")&&(b+1)<=s2.length)
                            b++;
                        String temp[]=s2[b].split("\"[\\s\\S]*\"");
                        for(String c:temp) {
                            s2[b]=s2[b].replaceAll(c,"");
                        }
                        s2[b]=s2[b].replaceAll("\"","");
                        Uri imgUri=Uri.parse(s2[b]);
                        uris.add(imgUri);
                        Log.i("uri",s2[b] );
                        Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(imgUri));
                        bit = zoomImg(bit, imageWidth / 2, imageHight / 2);
                        ImageSpan imageSpan = new ImageSpan(getApplicationContext(), bit);
                        SpannableString spannableString = new SpannableString(s2[b]);
                        spannableString.setSpan(imageSpan, 0, s2[b].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        //Log.i("image", s2[b]);
                        int index = text.getSelectionStart();
                        // Get the location of the cursor
                        Editable edit_text = text.getEditableText();
                        if (index < 0 || index >= edit_text.length()) {
                            edit_text.append(spannableString);
                        } else {
                            edit_text.insert(index, spannableString);
                        }

                        b++;
                    }
                    if (a < s1.length) {
                        if (s1[a].equals("")&&(a+1)<=s1.length)
                            a++;
                        //Log.i("text", s1[a]);
                        texts.add(s1[a]);
                        int index = text.getSelectionStart();
                        // Get the location of the cursor
                        Editable edit_text = text.getEditableText();
                        if (index < 0 || index >= edit_text.length()) {
                            edit_text.append(s1[a]);

                        } else {
                            edit_text.insert(index, s1[a]);
                        }
                        a++;
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }
    // Set Picture Size
    public Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // Get the width and height of the picture
        int width = bm.getWidth();
        int height = bm.getHeight();
        // Calculate scaling ratio
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // Get the matrix parameter you want to scale
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // Get new pictures
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }


    //Delete diary
    private void entries_delete(){

        db.delete("entries_info","date=?",new String[]{date});
        db.close();
    }

    //save
    private void save_edit(){
        ContentValues values = new ContentValues();

        values.put(table_info[2],topic_copy.getText().toString());
        values.put(table_info[3],entries_copy.getText().toString());

        db.update("entries_info",values,"date=?",new String[]{date});
        db.close();

    }
}