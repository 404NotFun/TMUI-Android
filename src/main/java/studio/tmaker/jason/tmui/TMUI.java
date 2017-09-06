package studio.tmaker.jason.tmui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jasontsai on 2017/6/15.
 */

public class TMUI {
    private static TMUI instance = null;
    private ProgressDialog progressDialog = null;
    private String STRING_SYSYEM_ERROR = "系統發生異常";
    private TMUI() {
        // ....
    }

    public static TMUI getInstance() {
        if (instance == null) {
            instance = new TMUI();
        }

        return instance;
    }

    public enum Colors {

        LIGHTGREY("#D3D3D3"), BLUE("#33B5E5"), PURPLE("#AA66CC"),
        GREEN("#99CC00"), ORANGE("#FFBB33"), RED("#FF4444");

        private String code;

        private Colors(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public int parseColor() {
            return Color.parseColor(code);
        }
    }

    public Bitmap imageFromBase64(String string) {
        byte[] decodedString = Base64.decode(string, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return  decodedByte;
    }

    public void showProgressDialog(Activity activity,String title, String msg) {
        progressDialog = ProgressDialog.show(activity, title, msg, true);
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void dialog(final Activity activity, String msg){
        final String result = msg == null ? STRING_SYSYEM_ERROR : msg;
        new AlertDialog.Builder(activity)
                .setTitle(R.string.alert_error_title)
                .setMessage(result)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    public void dialog(final Activity activity, String title, String msg){
        final String result = msg == null ? STRING_SYSYEM_ERROR : msg;
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(result)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    public void dialog(final Activity activity, String title, String msg, final DialogAction dialogAction) {
        final String result = msg == null ? STRING_SYSYEM_ERROR : msg;
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(result)
                .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogAction.cancelOnClick();
                    }
                })
                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogAction.confirmOnClick();
                    }
                })
                .show();
    }

    public void toast(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    public void pushToActivity(Activity exit, Class enter) {
        Intent intent = new Intent();
        intent.setClass(exit  , enter);
        exit.startActivity(intent);
//        exit.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    public void popActivity(Activity exit) {
        exit.finish();
//        exit.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    public void downloadImage(String url,final ImageView imageView) {
        //建立一個AsyncTask執行緒進行圖片讀取動作，並帶入圖片連結網址路徑
        new AsyncTask<String, Void, Bitmap>()
        {
            @Override
            protected Bitmap doInBackground(String... params)
            {
                String url = params[0];
                return getBitmapFromURL(url);
            }

            @Override
            protected void onPostExecute(Bitmap result)
            {
                imageView.setImageBitmap(result);
                super.onPostExecute(result);
            }
        }.execute(url);
    }

    //讀取網路圖片，型態為Bitmap
    private static Bitmap getBitmapFromURL(String imageUrl)
    {
        try
        {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
