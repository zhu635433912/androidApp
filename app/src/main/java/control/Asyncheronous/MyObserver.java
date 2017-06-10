package control.Asyncheronous;

import android.util.Log;

import rx.Observer;

/**
 * Created by Administrator on 2017/3/23 0023.
 */

public class MyObserver {
      public  Observer<Integer> observer=new Observer<Integer>() {
            @Override
            public void onCompleted() {
                  Log.e("aa", "onCompleted");
            }
            @Override
            public void onError(Throwable e) {
                  Log.e("aa", "onError");
            }

            @Override
            public void onNext(Integer s) {
                  int a=s+1;
                Log.e("aa", "onNext="+a);
            }
        };
}
