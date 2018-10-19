package com.eemf.sirgoingfar.retrofittutorial.Util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Helper {

    //Media Types
    public static final int TYPE_PICTURE = 0;
    public static final int TYPE_VIDEO = 1;
    public static final int TYPE_AUDIO = 2;

    //Media Prefixes
    public static final String PREFIX_PICTURE = "IMG_";
    public static final String PREFIX_VIDEO = "VID_";
    public static final String PREFIX_AUDIO = "AUD_";

    //Media Extensions
    public static final String PICTURE_EXTENSION_JPG = ".jpg";
    public static final String PICTURE_EXTENSION_PNG = ".png";
    public static final String VIDEO_EXTENSION_MP4 = ".mp4";
    public static final String VIDEO_EXTENSION_3GP = ".3gp";
    public static final String AUDIO_EXTENSION_MP3 = ".mp3";

    //Directory Name
    public static final String PRIVATE_DIRECTORY_NAME = "APP_NAME";
    public static final String DIRECTORY_NAME_PICTURE = "APP_NAME_Pictures";
    public static final String DIRECTORY_NAME_VIDEO = "APP_NAME_Videos";
    public static final String DIRECTORY_NAME_AUDIO = "APP_NAME_Audios";

    public static File getMediaFile(Context context, int mediaType, boolean isInPublicDirectory) throws IOException {

        if(Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)){

            File mediaStorageDir;

            switch (mediaType){

                case TYPE_PICTURE:

                    if(isInPublicDirectory)
                        mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), DIRECTORY_NAME_PICTURE);
                    else
                        mediaStorageDir = new File(Environment.getExternalStorageDirectory(), PRIVATE_DIRECTORY_NAME);

                    break;

                case TYPE_VIDEO:

                    if(isInPublicDirectory)
                        mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), DIRECTORY_NAME_VIDEO);
                    else
                        mediaStorageDir = new File(Environment.getExternalStorageDirectory(), PRIVATE_DIRECTORY_NAME);

                    break;

                case TYPE_AUDIO:

                    if(isInPublicDirectory)
                        mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), DIRECTORY_NAME_AUDIO);
                    else
                        mediaStorageDir = new File(Environment.getExternalStorageDirectory(), PRIVATE_DIRECTORY_NAME);

                    break;

                default:
                    return null;
            }

            //if Directory does not exist
            if(!mediaStorageDir.exists()){
                boolean isCreated = mediaStorageDir.mkdir();
                boolean isDirectory = mediaStorageDir.isDirectory();

                //if Directory is still not created
                if(!(isCreated || isDirectory))
                    return null;
            }

            //Create the Media File
            File mediaFile;

            //Generate a unique, collision free tag for file
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

            switch (mediaType){

                case TYPE_PICTURE:
                    mediaFile = File.createTempFile(
                            PREFIX_PICTURE.concat(timeStamp),
                            PICTURE_EXTENSION_JPG,
                            mediaStorageDir
                    );
                    break;

                case TYPE_VIDEO:
                    mediaFile = File.createTempFile(
                            PREFIX_VIDEO.concat(timeStamp),
                            VIDEO_EXTENSION_MP4,
                            mediaStorageDir
                    );
                    break;

                case TYPE_AUDIO:
                    mediaFile = File.createTempFile(
                            PREFIX_AUDIO.concat(timeStamp),
                            AUDIO_EXTENSION_MP3,
                            mediaStorageDir
                    );
                    break;

                default:
                    return null;
            }


            //return media file
            return mediaFile;

        }

        return null;
    }
}
