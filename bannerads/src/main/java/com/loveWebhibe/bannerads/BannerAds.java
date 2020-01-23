package com.loveWebhibe.bannerads;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("AppCompatCustomView")
public class BannerAds extends ImageView {

    private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLORDRAWABLE_DIMENSION = 2;
    private Drawable mPlaceholder, mImage;
    MyInterface myInterface;
    public BannerAds(Context context) {
        super(context);
    }

    public BannerAds(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public BannerAds(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BannerAds(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BannerAds, defStyleRes, 0);


    }

    public void setPlaceholderImage(Drawable drawable) {
        mPlaceholder = drawable;
        if (mImage == null) {
            setImageDrawable(mPlaceholder);
        }
    }

    public void setPlaceholderImage(int resid) {
        mPlaceholder = getResources().getDrawable(resid);
        if (mImage == null) {
            setImageDrawable(mPlaceholder);
        }
    }

    public void adBanner(String token){
        //Toast.makeText(getContext(), ""+token, Toast.LENGTH_SHORT).show();

        myInterface=ApiClint.getApiClient().create(MyInterface.class);

        Call<String> call=myInterface.FetchBannerADS(token);
        final ProgressDialog progressDialog=ProgressDialog.show(getContext(),"","Wait...",false);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String rec=response.body();
               // Toast.makeText(getContext(), ""+rec, Toast.LENGTH_SHORT).show();
                try{
                    JSONObject jsonObject=new JSONObject(rec);
                   String url ="http://czoneindia.in/colfiAd/Ad_provider/image/Ad_image/"+jsonObject.getString("banner_image");
                    setImageUrl(url);
                    progressDialog.dismiss();
                }catch (Exception ex){
                    //Toast.makeText(getContext(), "karabi"+ex.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), "sorry", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public boolean callOnClick() {
        Toast.makeText(getContext(), "hiiii", Toast.LENGTH_SHORT).show();
        return super.callOnClick();
    }

    public void setImageUrl(String url) {
        AsyncTaskRunner task = new AsyncTaskRunner(url);
        task.execute(url);
    }

    private class AsyncTaskRunner extends AsyncTask<String, Void, Bitmap> {
        String Imageurl;

        public AsyncTaskRunner(String Imageurl) {
            this.Imageurl = Imageurl;
        }
        @Override
        protected Bitmap doInBackground(String... strings) {

            try {

                URL url = new URL(Imageurl);
                //thumbnail_r = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                return BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(Bitmap result) {
            mImage = new BitmapDrawable(result);
            if (mImage != null) {
                setImageDrawable(mImage);
            }
        }
    };
}