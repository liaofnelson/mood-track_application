package au.edu.utas.liaof_jiduoz.assignment2;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import au.edu.utas.liaof_jiduoz.assignment2.util.DBOpenHelper;

import static au.edu.utas.liaof_jiduoz.assignment2.util.Permission.permission;
import static au.edu.utas.liaof_jiduoz.assignment2.util.Resoure.image2;
import static au.edu.utas.liaof_jiduoz.assignment2.util.Resoure.mood;
import static au.edu.utas.liaof_jiduoz.assignment2.util.Resoure.table_info;


public class Add_Entries extends AppCompatActivity implements View.OnClickListener {

    final int  imageWidth=500,imageHight=500;
    private  ImageButton date_time;
    private ImageView mood_image;
    private  EditText time_text,topic_text,entries_text,addr_text;
    private TextView title;

    private int mYear;
    private int mMonth;
    private int mDay;
    private double lat;
    private double lon;

    private boolean flag1=false,flag2=false;
    private int img_num;
    String addr=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Get a map control reference




        setContentView(R.layout.add_entries);


        //Loading page
        init_view();



    }

    //Calendar dialog listener
    private DatePickerDialog.OnDateSetListener datelistener=new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear=year;
            mMonth=monthOfYear;
            mDay=dayOfMonth;
            updateDate();
        }
    };

    //updateDate
    protected void updateDate() {
        time_text.setText(mYear+"-"+(pad(mMonth+1))+"-"+( pad (mDay)));
        Toast.makeText(Add_Entries.this, "current date ："+mYear+"-"+(pad(mMonth+1))+"-"+(pad(mDay)), Toast.LENGTH_SHORT).show();

    }

    private static String pad(int c){

        if(c>=10){
            return String.valueOf(c);
        }
        else{
            return "0"+String.valueOf(c);
        }
    }

    private int index;
    private Button discard,save;
    //Load the base page
    private void init_view() {


        //Application for Location Permission
        if (ContextCompat.checkSelfPermission(Add_Entries.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {   //Privileges have not been granted yet. You need to write the code to apply for privileges here.
            // The second parameter is an array of strings in which the permissions you need to apply for can be set to apply for more than one permission.
            // The last parameter is to mark the permission of your application, which is used in onRequest Permissions Result.
            ActivityCompat.requestPermissions(Add_Entries.this,
                    permission,
                    LOCATION_REQUEST_CODE);

        }
        data_read();
        //Loading page
        discard = findViewById(R.id.discard);
        discard.setOnClickListener(this);
        save = findViewById(R.id.save);
        save.setOnClickListener(this);
        //Get the index value passed from the previous page
        Intent intent = getIntent();
        index = intent.getIntExtra("index", -1);
        title = findViewById(R.id.title);
        title.setText(mood[index]);
        mood_image = findViewById(R.id.mood_image);
        mood_image.setImageResource(image2[index]);
        date_time = findViewById(R.id.date_time);
        time_text = findViewById(R.id.date_hour);
        Calendar c = Calendar.getInstance();
        //Get the Date object of the current date
        Date mdate = new Date();
        //Set the time to the current date for the Calendar object
        c.setTime(mdate);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        date_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dpd = new DatePickerDialog(Add_Entries.this, datelistener, mYear, mMonth, mDay);
                dpd.show();

            }
        });
        dianji = findViewById(R.id.camera);
        entries_text = findViewById(R.id.entries_text);
        topic_text = findViewById(R.id.topic_text);

        dianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show1(view);
            }
        });
        addr_text=findViewById(R.id.addr);




    }

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    //data fetch
    private void data_read(){

        sp=getSharedPreferences("admin",MODE_PRIVATE);
        img_num=sp.getInt("num",0);

    }


    //Click Event Listener
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.camera:

                if (ContextCompat.checkSelfPermission(Add_Entries.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {   // Privileges have not been granted yet. You need to write the code to apply for privileges here.
                    // The second parameter is an array of strings in which the permissions you need to apply for can be set to apply for more than one permission.
                    // The last parameter is to mark the permission of your application, which is used in onRequest Permissions Result
                    ActivityCompat.requestPermissions(Add_Entries.this,
                            permission,
                            CAMERA_REQUEST_CODE);

                }else { // The permission has been granted, so write the corresponding method to be executed directly here.
                    // Skip to the Photo Interface of the System
                    Intent intentToTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Specify photo storage location under SD card catalog
                    // Set it to a fixed name so that there is only one temp chart. If you want all the middle pictures to be saved, you can set the name of the picture by time or adding something else.
                    // File. separator is a fixed constant for the system's own separator
                    mTempPhotoPath = Environment.getExternalStorageDirectory() + File.separator + "photo_"+String.valueOf(img_num)+".jpeg";
                    img_num++;
                    // The Uri Path to Get the Location of the Picture *** Why do you do this here Reference Question 2 ****
                    //        /* imageUri = Uri. fromFile (new File (mTempPhotoPath));*/
                    imageUri = FileProvider.getUriForFile(Add_Entries.this,
                            Add_Entries.this.getApplicationContext().getPackageName() +".my.provider",
                            new File(mTempPhotoPath));
                    // The following sentence specifies the path to the photo storage after calling the camera to take a picture
                    intentToTakePhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intentToTakePhoto, CAMERA_REQUEST_CODE);

                }

                break;
            case R.id.pic:
                if (ContextCompat.checkSelfPermission(Add_Entries.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {   // Privileges have not been granted yet. You need to write the code to apply for privileges here.
                    // The second parameter is an array of strings in which the permissions you need to apply for can be set to apply for more than one permission.
                    // The last parameter is to mark the permission of your application, which is used in onRequest Permissions Result.
                    ActivityCompat.requestPermissions(Add_Entries.this,
                            permission,
                            CAMERA_REQUEST_CODE);

                }else { // The permission has been granted, so write the corresponding method to be executed directly here.
                    Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
                    // If you restrict the type of image uploaded to the server, you can write directly "image / jpeg, image / png, and so on" and "image /*" for all types.
                    intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg");
                    startActivityForResult(intentToPickPic, GALLERY_REQUEST_CODE);

                }

                break;
            case R.id.cancel:
                dialog.dismiss();
                break;
            case R.id.discard:
                NavUtils.navigateUpFromSameTask(this);
                finish();
                break;
            case R.id.save:
                data_write();
                finish();
                break;
        }
    }


        // Photo Return Code
    public final static int CAMERA_REQUEST_CODE = 9999;
    // Album Selection Return Code
    public final static int GALLERY_REQUEST_CODE = 10000;

    //
    //
    //
    //Location Return Code
    public final static int LOCATION_REQUEST_CODE = 1;

    // Storage Location of Photographs
    private String mTempPhotoPath;
    //The Uri address of the photo
    private Uri imageUri;

    private View inflate;
    private TextView camera;
    private TextView pic;
    private TextView cancel;

    private Dialog dialog;
    private ImageButton dianji;

    public void show1(View view){
        final Dialog dialog = new Dialog(this,R.style.DialogTheme);
        //Fill the layout of the dialog
        //
        View inflate = LayoutInflater.from(this).inflate(R.layout.insert_image, null);
        // Initialize control
        TextView insert1,insert2,cancel;
        insert1 = (TextView) inflate.findViewById(R.id.insert_topic);
        insert2 = (TextView) inflate.findViewById(R.id.insert_motivation);
        cancel = (TextView) inflate.findViewById(R.id.no);
        insert1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                flag1=true;
                show2(v);
            }
        });
        insert2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                flag2=true;
                show2(v);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //Set the layout to Dialog
        dialog.setContentView(inflate);
        //Get the form where the current Activity is located
        Window dialogWindow = dialog.getWindow();
        //Set Dialog to pop up from the bottom of the form
        dialogWindow.setGravity( Gravity.BOTTOM);
        //Get the properties of the form
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;
        //Set the distance of the Dialog from the bottom//
        //   Set properties to form
        //
        dialogWindow.setAttributes(lp);
        dialog.show();
        //Display dialog
    }
    public void show2(View view){
        dialog = new Dialog(this,R.style.DialogTheme);
        //Fill the layout of the dialog
        //
        inflate = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        //Initialize control
        camera = (TextView) inflate.findViewById(R.id.camera);
        pic = (TextView) inflate.findViewById(R.id.pic);
        cancel = (TextView) inflate.findViewById(R.id.cancel);
        camera.setOnClickListener(this);
        pic.setOnClickListener(this);
        cancel.setOnClickListener(this);
        //Set the layout to Dialog
        dialog.setContentView(inflate);
        //Get the form where the current Activity is located
        Window dialogWindow = dialog.getWindow();
        //Set Dialog to pop up from the bottom of the form
        dialogWindow.setGravity( Gravity.BOTTOM);
        //Get the properties of the form
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;
        //Set the distance of the Dialog from the bottom//
        //   Set properties to form
        //
        dialogWindow.setAttributes(lp);
        dialog.show();
        //Display dialog
    }

    private Bitmap bit=null;
    // Picture Callback Method
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == test.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE: {
                    // Get the picture
                    try {
                        //The uri is the uri corresponding to the photo folder.
                        bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));

                        // Set the image to the corresponding ImageView Uncut
                        if(flag1){

                            bit=zoomImg(bit,imageWidth/2,imageHight/2);
                            ImageSpan imageSpan = new ImageSpan(getApplicationContext(), bit);
                            String tempUrl = "<img src=\"" + imageUri + "\" />";
                            SpannableString spannableString = new SpannableString(tempUrl);

                            spannableString.setSpan(imageSpan, 0, tempUrl.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            // Append the selected image to the location of the cursor in the EditText
                            int index = topic_text.getSelectionStart();
                            // Get the location of the cursor
                            Editable edit_text = topic_text.getEditableText();
                            if (index < 0 || index >= edit_text.length()) {
                                edit_text.append(spannableString);

                            } else {
                                edit_text.insert(index, spannableString);
                            }
                            flag1=false;
                        }
                        else
                            if(flag2){
                                bit=zoomImg(bit,imageWidth,imageHight);
                                ImageSpan imageSpan = new ImageSpan(getApplicationContext(), bit);
                                String tempUrl = "<img src=\"" + imageUri + "\" />";
                                SpannableString spannableString = new SpannableString(tempUrl);
                                spannableString.setSpan(imageSpan, 0, tempUrl.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                // Append the selected image to the location of the cursor in the EditText
                                int index = entries_text.getSelectionStart();
                                // Get the location of the cursor
                                Editable edit_text = entries_text.getEditableText();
                                if (index < 0 || index >= edit_text.length()) {
                                    edit_text.append(spannableString);
                                } else {
                                    edit_text.insert(index, spannableString);
                                }
                                flag2=false;
                            }
                        //picture.setImageBitmap(bit);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case GALLERY_REQUEST_CODE: {
                    // Get image
                    try {
                        //The uri is returned by the previous Activity
                        imageUri = data.getData();
                        if(imageUri!=null) {
                            bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));

                            Log.i("bit", String.valueOf(bit));
                            //picture.setImageBitmap(bit);
                            if(flag1){
                                flag1=false;
                                bit=zoomImg(bit,imageWidth/2,imageWidth/2);
                                ImageSpan imageSpan = new ImageSpan(getApplicationContext(), bit);
                                String tempUrl = "<img src=\"" + imageUri + "\" />";
                                SpannableString spannableString = new SpannableString(tempUrl);
                                spannableString.setSpan(imageSpan, 0, tempUrl.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                // Append the selected image to the location of the cursor in the EditText
                                int index = topic_text.getSelectionStart();
                                // Get the location of the cursor
                                Editable edit_text = topic_text.getEditableText();
                                if (index < 0 || index >= edit_text.length()) {
                                    edit_text.append(spannableString);
                                } else {
                                    edit_text.insert(index, spannableString);
                                }
                            }
                            else
                            if(flag2){
                                flag2=false;
                                bit=zoomImg(bit,imageWidth,imageWidth);
                                ImageSpan imageSpan = new ImageSpan(getApplicationContext(), bit);
                                String tempUrl = "<img src=\"" + imageUri + "\" />";
                                SpannableString spannableString = new SpannableString(tempUrl);
                                spannableString.setSpan(imageSpan, 0, tempUrl.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                // Append the selected image to the location of the cursor in the EditText
                                int index = entries_text.getSelectionStart();
                                // Get the location of the cursor
                                Editable edit_text = entries_text.getEditableText();
                                if (index < 0 || index >= edit_text.length()) {
                                    edit_text.append(spannableString);
                                } else {
                                    edit_text.insert(index, spannableString);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {

        if (requestCode == CAMERA_REQUEST_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                // Skip to the Photo Interface of the System
                Intent intentToTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Specify photo storage location under SD card catalog
// Set it to a fixed name so that there is only one temp chart. If you want all the middle pictures to be saved, you can set the name of the picture by time or adding something else.
// File. separator is a fixed constant for the system's own separator
                mTempPhotoPath = Environment.getExternalStorageDirectory() + File.separator + "photo_"+String.valueOf(img_num)+".jpeg";
                img_num++;
                // The Uri Path to Get the Location of the Picture *** Why do you do this here Reference Question 2 ****
                /* imageUri = Uri. fromFile (new File (mTempPhotoPath));*/
                imageUri = FileProvider.getUriForFile(Add_Entries.this,
                        Add_Entries.this.getApplicationContext().getPackageName() +".my.provider",
                        new File(mTempPhotoPath));
                // The following sentence specifies the path to the photo storage after calling the camera to take a picture
                intentToTakePhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intentToTakePhoto, CAMERA_REQUEST_CODE);

            } else
            {
                // Permission Denied
                Toast.makeText(Add_Entries.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == GALLERY_REQUEST_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
                // If you restrict the type of image uploaded to the server, you can write directly "image / jpeg, image / png, and so on" and "image /*" for all types.
                intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg");
                startActivityForResult(intentToPickPic, GALLERY_REQUEST_CODE);

            } else
            {
                // Permission Denied
                Toast.makeText(Add_Entries.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    /**

     * Set the center point

     */


    private DBOpenHelper helper;

    private SQLiteDatabase db;
    private void DB_connect(){
         Cursor cursor;
         helper=new DBOpenHelper(this);
         db=helper.getWritableDatabase();
         /*
         if((cursor=db.query("sqlite_master",new String[]{"count(*)"},"type='table' and name = 'entries_info'",null,null,null,null)).moveToFirst())
             Log.i("Table query","The table exists");
         else
         */
         //db.execSQL(create_table);
         //cursor.close();
    }

    private void data_write(){

        DB_connect();
        ContentValues values = new ContentValues();
        values.put(table_info[0],title.getText().toString());
        values.put(table_info[1],index);
        values.put(table_info[2],topic_text.getText().toString());
        values.put(table_info[3],entries_text.getText().toString());
        values.put(table_info[4],time_text.getText().toString());
        values.put(table_info[5],addr_text.getText().toString());

        long i=db.insert("entries_info",null,values);
        if (i == -1) {
            Toast.makeText(this, "Save failed", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Successfully saved", Toast.LENGTH_LONG).show();
        }
        db.close();
        editor=sp.edit();
        editor.clear();
        editor.putInt("num",img_num);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    protected void onPause() {
        super.onPause();

    }
    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}


