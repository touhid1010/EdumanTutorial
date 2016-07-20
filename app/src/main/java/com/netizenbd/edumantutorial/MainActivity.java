package com.netizenbd.edumantutorial;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    MyMenuActions myMenuActions;

    private BottomBar mBottomBar;

    WebView webView_main;

    final String url = "file:///android_asset/tutorial_scratch.html";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /**
         * Initialization
         */
        myMenuActions = new MyMenuActions();

        webView_main = (WebView) findViewById(R.id.webView_main);


        webView_main.loadUrl(url);

        WebSettings webSettings = webView_main.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Improve WebView performance
        webView_main.setWebChromeClient(new WebChromeClient());
        webView_main.setInitialScale(1);
        webView_main.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView_main.setScrollbarFadingEnabled(false);
        webView_main.canGoBack();
        webView_main.canGoForward();
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);

        webView_main.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("market://") || url.startsWith("tel:") || url.startsWith("mailto:") || url.startsWith("vnd:youtube")) { //url.startsWith("vnd:youtube")||
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;
                } else {
                    view.loadUrl(url);
                    return true;
                }
            }
        });
        webView_main.loadUrl(url); // To access assets folder file:///android_asset/*

        //  Progress bar
        final ProgressBar Pbar;
        Pbar = (ProgressBar) findViewById(R.id.progressBar1);

        webView_main.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100 && Pbar.getVisibility() == ProgressBar.GONE) {
                    Pbar.setVisibility(ProgressBar.VISIBLE);
                }
                Pbar.setProgress(progress);
                if (progress == 100) {
                    Pbar.setVisibility(ProgressBar.GONE);
                }
            }
        });



        /**
         * Bottom bar
         */
        myBottomBarMethod(savedInstanceState);





    } // end of onCreate


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Want to exit?");
        builder.setNegativeButton("No", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.show();
        TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
        dialog.show();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_clear_cache) {
////            Toast.makeText(MainActivity.this, "hi", Toast.LENGTH_SHORT).show();
////
////
////
////            ClearAppData.getInstance().clearApplicationData();
////
////            startActivity(new Intent(this, MainActivity.class));
//
//
//
//
//            return true;
//        }

        if (id == R.id.action_feedback) {
            Intent intent = MyMenuActions.MenuFeedback();
            startActivity(Intent.createChooser(intent, "Send Feedback:"));

            return true;
        }

        if (id == R.id.action_rate) {
            Intent intent = MyMenuActions.MenuRate(MainActivity.this);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(), R.string.toast_google_play_not_installed, Toast.LENGTH_LONG).show();
            }

            return true;
        }

        if (id == R.id.action_share) {
            startActivity(MyMenuActions.MenuShare(MainActivity.this));

            return true;
        }

        if (id == R.id.action_moreApps) {
            Intent intent = MyMenuActions.MenuMoreApps();
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(), R.string.toast_google_play_not_installed, Toast.LENGTH_LONG).show();
            }

            return true;
        }

        if (id == R.id.action_about) {

            AlertDialog alertDialog = MyMenuActions.MenuAbout(this);
            alertDialog.show();
            ((TextView) alertDialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance()); // to make clickable link

            return true;
        }

        if (id == R.id.action_exit) {
            MyMenuActions.MenuExit(this).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void myBottomBarMethod(Bundle savedInstanceState) {

        // bottom bar
        mBottomBar = BottomBar.attach(this, savedInstanceState);


        // Disable the left bar on tablets and behave exactly the same on mobile and tablets instead.
        mBottomBar.noTabletGoodness();


        // Show all titles even when there's more than three tabs.
        mBottomBar.useFixedMode();
        mBottomBar.noTopOffset();



        mBottomBar.setItems(R.menu.bottombar_menu);

        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {

            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItem_rateUs) {
                    Intent intent = myMenuActions.MenuRate(MainActivity.this);
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(), R.string.toast_google_play_not_installed, Toast.LENGTH_LONG).show();
                    }
                }

                if (menuItemId == R.id.bottomBarItem_moreApps) {
                    Intent intent = myMenuActions.MenuMoreApps();
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(), R.string.toast_google_play_not_installed, Toast.LENGTH_LONG).show();
                    }
                }

                if (menuItemId == R.id.bottomBarItem_aboutUs) {
                    AlertDialog alertDialog = myMenuActions.MenuContactUs(MainActivity.this);
                    alertDialog.show();
                    ((TextView) alertDialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance()); // to make clickable link
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItem_rateUs) {
                    Intent intent = MyMenuActions.MenuRate(MainActivity.this);
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(), R.string.toast_google_play_not_installed, Toast.LENGTH_LONG).show();
                    }
                }

                if (menuItemId == R.id.bottomBarItem_moreApps) {
                    Intent intent = MyMenuActions.MenuMoreApps();
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(), R.string.toast_google_play_not_installed, Toast.LENGTH_LONG).show();
                    }
                }

                if (menuItemId == R.id.bottomBarItem_aboutUs) {
                    AlertDialog alertDialog = myMenuActions.MenuContactUs(MainActivity.this);
                    alertDialog.show();
                    ((TextView) alertDialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance()); // to make clickable link
                }
            }
        });

        // Setting colors for different tabs when there's more than three of them.
        // You can set colors for tabs in three different ways as shown below.
        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorAccent));
        mBottomBar.mapColorForTab(1, 0xFF5D4037);
        mBottomBar.mapColorForTab(2, "#7B1FA2");
//        mBottomBar.mapColorForTab(3, "#FF5252");
//        mBottomBar.mapColorForTab(4, "#FF9800");




        // Use the dark theme.
        mBottomBar.useDarkTheme();
        // Set the color for the active tab. Ignored on mobile when there are more than three tabs.
        mBottomBar.setActiveTabColor("#009688");

        // Use custom text appearance in tab titles.
        mBottomBar.setTextAppearance(R.style.MyTextAppearance);

        // Use custom typeface that's located at the "/src/main/assets" directory. If using with
        // custom text appearance, set the text appearance first.
//        mBottomBar.setTypeFace("MyFont.ttf");



    }









    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.i("TAG", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }




}
