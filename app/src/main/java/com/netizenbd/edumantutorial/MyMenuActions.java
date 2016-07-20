package com.netizenbd.edumantutorial;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by touhid on 7/19/2016.
 */
public class MyMenuActions extends Activity {
    /**
     * <p>Intent to open feedback app.</p>
     * <p/>
     * <p>Example usage:</p>
     * <p/>
     * {@code Intent intent = MenuAction.MenuFeedback(this);
     * startActivity(Intent.createChooser(intent, "Send Feedback:"));;
     *
     * @return An intent that will open app list to throw feedback
     */
    public static Intent MenuFeedback() {
        return new Intent(Intent.ACTION_SEND)
                .setType("text/email")
                .putExtra(Intent.EXTRA_EMAIL, new String[]{"springapidev@gmail.com"})
                .putExtra(Intent.EXTRA_SUBJECT, "App Feedback (" + R.string.app_name + ")")
                .putExtra(Intent.EXTRA_TEXT, "Hello Netizen it," + "");

    }

    /**
     * <p>Intent to open the google play app. If the google play app is not installed then toast
     * message will appear.</p>
     * <p/>
     * <p>Example usage:</p>
     * <p/>
     * {@code Intent intent = MenuAction.MenuRate(this);
     * try {
     * startActivity(intent);
     * } catch (ActivityNotFoundException e) {
     * Toast.makeText(this, "Google play not installed on your device.", Toast.LENGTH_LONG).show();
     * }}
     *
     * @param context application context is: this
     * @return An intent that will open google play of this app
     */
    public static Intent MenuRate(Context context) {

        Uri uri = Uri.parse("market://details?id=" + new ContextWrapper(context).getPackageName()); // play store of this app link
        return new Intent(Intent.ACTION_VIEW, uri);

    }

    /**
     * <p>Intent to open the app list to share.</p>
     * <p/>
     * <p>Example usage:</p>
     * <p/>
     * {@code Intent intent = MenuAction.MenuShare(this);
     * startActivity(intent);;
     *
     * @param context application context is: this
     * @return An intent that will open the app list to share
     */
    public static Intent MenuShare(Context context) {

        return new Intent(android.content.Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(android.content.Intent.EXTRA_SUBJECT, "I love this app, You also may try.")
                .putExtra(android.content.Intent.EXTRA_TEXT, "market://details?id=" + new ContextWrapper(context).getPackageName()); // play store this app link

    }

    /**
     * <p>Intent to open the google play app. If the google play app is not installed then toast
     * message show.</p>
     * <p/>
     * <p>Example usage:</p>
     * <p/>
     * {@code Intent intent = MenuAction.MenuMoreApps();
     * try {
     * startActivity(intent);
     * } catch (ActivityNotFoundException e) {
     * Toast.makeText(this, "Google play not installed on your device.", Toast.LENGTH_LONG).show();
     * }}
     * <p/>
     * Use try catch exception if the app is not installed then a message will show.
     *
     * @return An intent that will open the google play store
     */
    public static Intent MenuMoreApps() {

        return new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:\"Netizen it\"")); // TODO change the dev account

    }

    /**
     * <p>Intent to open the about me AlertDialog</p>
     * <p/>
     * <p>Example usage:</p>
     * <p/>
     * {@code AlertDialog alertDialog = MenuAction.MenuAboutMe(this);
     * alertDialog.show();
     * ((TextView)alertDialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());} // this line will make link clickable
     *
     * @param context application context is: this
     * @return An AlertDialog that will open the about me info
     */
    public static AlertDialog MenuAbout(Context context) {

        return new AlertDialog.Builder(context)
                .setTitle(R.string.developer_company_name)
                .setIcon(R.drawable.netizen_logo_small)
                .setMessage(Html.fromHtml("<span>Developed by: Netizen it Ltd.</span>" +
                        "<br/><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Dhaka, Bangladesh</span>" +
                        "<br/><br/><span>Email: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; springapidev@gmail.com</span>" +
                        "<br/><span>Facebook: <a href=\"https://www.facebook.com/netizenitltd\">fb.com/netizenitltd</a></span>" +
                        "<br/><br/><span>Thank you.</span>" +
                        "<br/> "))
                .setCancelable(true)
                .setPositiveButton("Ok", null) // no need to onClickListener so null used
                .create();

    }

    public AlertDialog MenuContactUs(final Context context) {


        LinearLayout layoutMain = new LinearLayout(context);
        layoutMain.setOrientation(LinearLayout.VERTICAL);
        layoutMain.setGravity(Gravity.CENTER);


        ImageView image = new ImageView(context);
        image.setImageResource(R.drawable.netizen_logo_for_alert_dialog);
        image.setScaleType(ImageView.ScaleType.CENTER);

        TextView tv = new TextView(context);
        tv.setText(Html.fromHtml("<span>Flat-6/A, Plot-398/A, Mohakhali DOHS,<br/>Dhaka, Bangladesh.</span>" +
                "<br/><br/><span>Call Us:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href=\"tel:+8801612223601\">+8801612223601</a></span>" +
                "<br/><br/><span>Email: &nbsp;&nbsp; <a href=\"mailto:springapidev@gmail.com?Subject=Hello%20Netizen%20it\">springapidev@gmail.com</a></span>" +
                "<br/><br/><span>Facebook: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href=\"https://www.facebook.com/netizenitltd\">fb.com/netizenitltd</a></span>" +
                "<br/><br/><span></span>" +
                "<br/> "));
        tv.setPadding(0,30,0,0);
        tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tv.setGravity(Gravity.CENTER);
        tv.setMovementMethod(LinkMovementMethod.getInstance());


        layoutMain.addView(image); // direct to main linear layout
        layoutMain.addView(tv);


        return new AlertDialog.Builder(context)
//                .setTitle(R.string.developer_company_name)
//                .setIcon(R.drawable.netizen_logo_small)
                .setView(layoutMain)
                .setCancelable(true)
                .setPositiveButton("Ok", null) // no need to onClickListener so null used
                .setMessage(Html.fromHtml(" "))
                .create();

    }

    public static AlertDialog MenuExit(Context context) {
        return new AlertDialog.Builder(context)
                .setMessage("Want to exit?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Activity a = new Activity();
                        a.finish(); // finish current activity
                        System.exit(0);
                    }
                }).create();

    }

}
