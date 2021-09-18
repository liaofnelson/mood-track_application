package au.edu.utas.liaof_jiduoz.assignment2.util;


import android.Manifest;

public class Permission {


    //Hazardous Rights Array, Android 6.0 Application
    public static String[] permission=new String[]

            {

                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION,

            };

}
