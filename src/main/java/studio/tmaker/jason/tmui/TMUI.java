package studio.tmaker.jason.tmui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.widget.Toast;

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

    public void showProgressDialog(Activity activity,String title, String msg) {
        progressDialog = ProgressDialog.show(activity, title, msg, true);
    }

    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    public void dialog(final Activity activity, String msg){
        final String result = msg == null ? STRING_SYSYEM_ERROR : msg;
        new AlertDialog.Builder(activity)
                .setTitle(R.string.alert_error_title)
                .setMessage(result)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
}
