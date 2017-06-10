package control.Asyncheronous;

import android.util.Log;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/3/23 0023.
 */

public class MySubscribe {
    Subscriber<String> subscriber=new Subscriber<String>() {
        @Override
        public void onCompleted() {
            Log.e("aa", "onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            Log.e("aa", "Throwable");
        }

        @Override
        public void onNext(String s) {
            Log.e("aa", "Item: " + s);
        }
    };
}
