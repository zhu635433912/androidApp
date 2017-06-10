package control;

import java.util.List;


import control.Asyncheronous.MyObserver;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/3/23 0023.
 */

public class Conterol_test {
    List<String> list;
    /**
     * just发送
     */

    public void test(){
        MyObserver myObserver=new MyObserver();
        final String i="1";
        Observable.create(new Observable.OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(i);
                subscriber.onCompleted();
            }
        }).map(new Func1<String,Integer>() {
            @Override
            public Integer call(String s) {
                int y=Integer.parseInt(i);
                return y;
            }
        })      .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
