package modle;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.deguan.xuelema.androidapp.init.Bitmap_init;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import modle.toos.CircleImageView;

/**
 * //从网络获取图片并保存
 */



public class GetBitmap_Url  {
    private CircleImageView circleImageView;
    private Bitmap bitmap;


    public GetBitmap_Url(String url, CircleImageView circleImageView){
        this.circleImageView=circleImageView;
        new Myasyn().execute(url);
    }


    class Myasyn extends AsyncTask<String,Void,Void>{
        @Override
        protected Void doInBackground(String... params) {
           bitmap=getBitmap(params[0]);


            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            circleImageView.setImageBitmap(bitmap);
        }
    }
    public  Bitmap getBitmap(String path){
        try {
            URL url = new URL(path);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if(conn.getResponseCode() == 200){
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
