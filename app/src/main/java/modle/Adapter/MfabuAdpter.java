package modle.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

//import com.baidu.mapapi.map.MarkerOptions;
import com.deguan.xuelema.androidapp.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modle.toos.CircleImageView;

/**
 * Created by 罗超 on 2017/5/24.
 * 学生我的  需求发布
 */

public class MfabuAdpter extends BaseAdapter {

    private List<Map<String,Object>> listmap;
    private Context context;
    private LayoutInflater layoutInflater;
    private MfabuViewHoder mfabuViewHoder;

    public MfabuAdpter(List<Map<String, Object>> listmap, Context context){
        this.listmap=listmap;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listmap.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            mfabuViewHoder=new MfabuViewHoder();
            convertView=layoutInflater.inflate(R.layout.myindex_list,null);
            mfabuViewHoder.myindeusername= (TextView) convertView.findViewById(R.id.myindeusername);
            mfabuViewHoder.user_xuqiu= (TextView) convertView.findViewById(R.id.user_xuqiu);
            mfabuViewHoder.xuqiuneiron= (TextView) convertView.findViewById(R.id.xuqiuneiron);
            mfabuViewHoder.statre= (TextView) convertView.findViewById(R.id.statre);
            mfabuViewHoder.fuweuleix= (TextView) convertView.findViewById(R.id.fuweuleix);
            mfabuViewHoder.fabushijian= (TextView) convertView.findViewById(R.id.fabushijian);
            mfabuViewHoder.myinde_usertoux= (SimpleDraweeView) convertView.findViewById(R.id.myinde_usertoux);
           convertView.setTag(mfabuViewHoder);
        }else {
            convertView.getTag();
        }
        mfabuViewHoder.myindeusername.setText(""+listmap.get(position).get("publisher_name"));
        mfabuViewHoder.user_xuqiu.setText(""+listmap.get(position).get("course_name"));
        mfabuViewHoder.xuqiuneiron.setText(""+listmap.get(position).get("content"));
        mfabuViewHoder.fabushijian.setText("发布时间:"+listmap.get(position).get("created"));
        if (listmap.get(position).get("service_type").equals("1")) {
            mfabuViewHoder.fuweuleix.setText("教师上门");
        }
        if (listmap.get(position).get("service_type").equals("2")) {
            mfabuViewHoder.fuweuleix.setText("学生上门");
        }
        if (listmap.get(position).get("service_type").equals("3")) {
            mfabuViewHoder.fuweuleix.setText("第三方");
        }
//        setbitmap(listmap.get(position).get("publisher_headimg")+"",mfabuViewHoder.myinde_usertoux);
        mfabuViewHoder.myinde_usertoux.setImageURI(Uri.parse(listmap.get(position).get("publisher_headimg")+""));
        return convertView;
    }



    class MfabuViewHoder{
        private SimpleDraweeView myinde_usertoux;
        private TextView myindeusername;//昵称
        private TextView  user_xuqiu;//需求科目
        private TextView  xuqiuneiron;//需求内容
        private TextView fabushijian;//发布时间
        private TextView  statre;//状态
        private TextView  fuweuleix;//服务类型
    }

    public String getuid(int piont){
        String uida=listmap.get(piont).get("id").toString();
         int uid=Integer.parseInt(uida);

        return uid+"";
    }

    public Map<String,Object> getmap(int piont){
        Map<String,Object> map=listmap.get(piont);
        return map;
    }

    public void setbitmap(final String pate, final CircleImageView circleImageView){
        //创建一个新线程，用于从网络上获取图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                //从网络上获取图片
                final Bitmap bitmap=getPicture(pate);
                //发送一个Runnable对象
                circleImageView.post(new Runnable(){

                    @Override
                    public void run() {
                        circleImageView.setImageBitmap(bitmap);//在ImageView中显示从网络上获取到的图片
                    }

                });

            }
        }).start();//开启线程
    }
    /*
     * 功能:根据网址获取图片对应的Bitmap对象
     * @param path
     * @return Bitmap
     * */
    public Bitmap getPicture(String path){
        Bitmap bm=null;
        URL url;
        try {
            url = new URL(path);//创建URL对象
            URLConnection conn=url.openConnection();//获取URL对象对应的连接
            conn.connect();//打开连接
            InputStream is=conn.getInputStream();//获取输入流对象
            bm= BitmapFactory.decodeStream(is);//根据输入流对象创建Bitmap对象
        } catch (MalformedURLException e1) {
            e1.printStackTrace();//输出异常信息
        }catch (IOException e) {
            e.printStackTrace();//输出异常信息
        }
        return bm;
    }
}
