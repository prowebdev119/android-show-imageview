package com.unity3d.player;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.squareup.picasso.Picasso;

import java.util.Random;
import java.util.Stack;
import java.util.Vector;


public class Show_Full_ImageView {

    public static ImageButton btnClose;
    public static ImageView imageView;
    private static boolean isSendClickAction = false;
    public static Button txtTimer;

    public static int changeFlag = 0;
    private static int bannerflag=0;
    public static  JSONArray heroArray;

    private static Button ui_btnShow, ui_btnHide, ui_btnFull, ui_btnChange;
    private static ImageButton ui_btnBannerClose, ui_btnAdsClose;
    private static LinearLayout ui_grpLayout;
    private static RelativeLayout ui_baseLayout, ui_fullayout, ui_banLayout;
    private static ImageView bannerImage, adsImage;

    private static Vector<ImageView> bannerVector;
    private static Vector<ImageView> adsVector;
    private static int imgSize;

    private static Picasso pis;
//    private AdView adView;

    private static int sec=0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void showImageview(Activity Ativity, RelativeLayout mainLayout) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        Ativity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int min = Math.min(displayMetrics.heightPixels, displayMetrics.widthPixels);
        int max = Math.max(displayMetrics.heightPixels, displayMetrics.widthPixels);

        ui_baseLayout = new RelativeLayout(Ativity);
        LinearLayout.LayoutParams baselayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        baselayoutParams.setMargins(0,0,0,0);
        ui_baseLayout.setLayoutParams(baselayoutParams);
        ui_baseLayout.setBackgroundColor(Color.WHITE);
        // ui_baseLayout.setOrientation(LinearLayout.VERTICAL);

        ui_grpLayout = new LinearLayout(Ativity);
        ui_banLayout = new RelativeLayout(Ativity);
        ui_fullayout = new RelativeLayout(Ativity);

        bannerImage = new ImageView(Ativity);
        adsImage    = new ImageView(Ativity);

        try {
            Picasso.with(Ativity).load(heroArray.getJSONObject(0).getString("_banner_url")).into(bannerImage);
            Picasso.with(Ativity).load(heroArray.getJSONObject(0).getString("_full_url")).into(adsImage);
        }catch (JSONException e){}

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10,10,10,30);

        ui_btnShow = new Button(Ativity);
        ui_btnShow.setText("Show banner");
        ui_btnShow.setTextColor(Color.WHITE);
        ui_btnShow.setBackgroundColor(Color.BLACK);
        ui_btnShow.setLayoutParams(layoutParams);
        ui_btnShow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ui_banLayout.setVisibility(View.VISIBLE);
            }
        });

        ui_btnHide = new Button(Ativity);
        ui_btnHide.setText("Hide banner");
        ui_btnHide.setTextColor(Color.WHITE);
        ui_btnHide.setBackgroundColor(Color.BLACK);
        ui_btnHide.setLayoutParams(layoutParams);
        ui_btnHide.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ui_banLayout.setVisibility(View.INVISIBLE);
            }
        });

        ui_btnFull = new Button(Ativity);
        ui_btnFull.setText("Show Full Ads");
        ui_btnFull.setTextColor(Color.WHITE);
        ui_btnFull.setBackgroundColor(Color.BLACK);
        ui_btnFull.setLayoutParams(layoutParams);
        ui_btnFull.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ui_banLayout.setVisibility(View.INVISIBLE);
                ui_grpLayout.setVisibility(View.INVISIBLE);
                ui_fullayout.setVisibility(View.VISIBLE);
            }
        });

        ui_btnChange = new Button(Ativity);
        ui_btnChange.setText("Change Photo Every 30seconds");
        ui_btnChange.setTextColor(Color.WHITE);
        ui_btnChange.setBackgroundColor(Color.BLACK);
        ui_btnChange.setLayoutParams(layoutParams);
        ui_btnChange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Handler mHandler=new Handler();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                Thread.sleep(7000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    changeFlag = heroArray.length()*3;
                                    while(changeFlag > heroArray.length()) {
                                        changeFlag = (int) (Math.random()*heroArray.length());
                                    }
                                    try {
                                        Picasso.with(Ativity).load(heroArray.getJSONObject(changeFlag).getString("_full_url")).into(adsImage);
                                    }catch (JSONException e){}
                                }
                            });
                        }
                    }
                }).start();
            }
        });


        Handler mHandler=new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            bannerflag = heroArray.length()*3;
                            sec++;
                            if(sec == 3){
                                ui_banLayout.setVisibility(View.INVISIBLE);
                                ui_grpLayout.setVisibility(View.INVISIBLE);
                                ui_fullayout.setVisibility(View.VISIBLE);
                            }
                            while(bannerflag > heroArray.length()) {
                                bannerflag = (int) (Math.random()*heroArray.length());
                            }
                            try {
                                Picasso.with(Ativity).load(heroArray.getJSONObject(bannerflag).getString("_full_url")).into(adsImage);
                                Picasso.with(Ativity).load(heroArray.getJSONObject(bannerflag).getString("_banner_url")).into(bannerImage);
                            }catch (JSONException e){}
                        }
                    });
                }
            }
        }).start();

        ui_grpLayout.setOrientation(LinearLayout.VERTICAL);
        ui_baseLayout.setGravity(Gravity.CENTER);

//        ui_grpLayout.addView(ui_btnShow);
//        ui_grpLayout.addView(ui_btnHide);
//        ui_grpLayout.addView(ui_btnFull);
//        ui_grpLayout.addView(ui_btnChange);

        RelativeLayout.LayoutParams grplayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        grplayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        grplayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        ui_grpLayout.setLayoutParams(grplayoutParams);

        bannerImage.setLayoutParams(new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        bannerImage.setScaleType(ImageView.ScaleType.FIT_XY);
        ui_banLayout.addView(bannerImage);
        RelativeLayout.LayoutParams bannerlayoutParams = new RelativeLayout.LayoutParams(max/7*5, max/17);
        bannerlayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        bannerlayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        bannerlayoutParams.setMargins(max/12,0,max/12,0);
        ui_banLayout.setLayoutParams(bannerlayoutParams);
        ui_banLayout.setGravity(Gravity.CENTER);

        ui_btnBannerClose = new ImageButton(Ativity);
        Context context = Ativity;
        ui_btnBannerClose.setBackground(context.getDrawable(R.drawable.close));
        RelativeLayout.LayoutParams bannerCloseParams = new RelativeLayout.LayoutParams(60, 60);
        bannerCloseParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        bannerCloseParams.setMargins(0,5,10,0);
        ui_btnBannerClose.setLayoutParams(bannerCloseParams);
        ui_btnBannerClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ui_banLayout.setVisibility(View.INVISIBLE);
            }
        });
        ui_banLayout.addView(ui_btnBannerClose);

        ui_banLayout.setClickable(true);
        ui_banLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(heroArray.getJSONObject(bannerflag).getString("banner_website_url")));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Ativity.startActivity(intent);
                }catch (JSONException e){}
            }
        });

        adsImage.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        adsImage.setScaleType(ImageView.ScaleType.FIT_XY);
        ui_fullayout.addView(adsImage);
        ui_fullayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        ui_fullayout.setVisibility(View.INVISIBLE);

        //ui_fullayout.setBackgroundColor(Color.rgb(255,0,0));

        ui_btnAdsClose = new ImageButton(Ativity);
        ui_btnAdsClose.setBackground(context.getDrawable(R.drawable.close));
        RelativeLayout.LayoutParams adsCloseParams = new RelativeLayout.LayoutParams(70, 70);
        adsCloseParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        adsCloseParams.setMargins(0,100,100,0);
        ui_btnAdsClose.setLayoutParams(adsCloseParams);
        ui_btnAdsClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ui_banLayout.setVisibility(View.VISIBLE);
                ui_grpLayout.setVisibility(View.VISIBLE);
                ui_fullayout.setVisibility(View.INVISIBLE);
                sec=0;
            }
        });
        ui_fullayout.addView(ui_btnAdsClose);
        ui_fullayout.setClickable(true);
        ui_fullayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(heroArray.getJSONObject(changeFlag).getString("full_website_url")));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Ativity.startActivity(intent);
                }catch (JSONException e){}
            }
        });

        ui_baseLayout.addView(ui_grpLayout);
        ui_baseLayout.addView(ui_banLayout);
        ui_baseLayout.addView(ui_fullayout);

        ui_baseLayout.getBackground().setAlpha(0);
        mainLayout.addView(ui_baseLayout);
        //Ativity.setContentView(ui_baseLayout);

    }


    public static void get_data (final Activity Ativity, RelativeLayout mainLayout) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://zuzogames.com/ads_test.json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        Log.d("Json Response", response);
                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            heroArray = obj.getJSONArray("R-Play");



                            final Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                @Override
                                public void run() {
                                    showImageview(Ativity, mainLayout);
                                }
                            }, 15000);

//                            for (int i = 0; i < heroArray.length(); i++) {
//                                //getting the json object of the particular index inside the array
//                                JSONObject heroObject = heroArray.getJSONObject(i);
//
//                                final int show_enable = heroObject.getInt("show_enable");
//                                final String _img_url = heroObject.getString("_img_url");
//                                final String _website_url = heroObject.getString("_website_url");
//
//                                if (show_enable >= 1) {
//
//                                    new Handler().postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            showImageview(Ativity, _img_url, _website_url);
//                                        }
//                                    }, 1000);
//                                }
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(Ativity);
        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }


}


