package modle.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.transition.CircularPropagation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.UserxinxiActivty;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import modle.toos.CircleImageView;

/**
 * 教师列表listview适配器
 */

public class Requirdapter extends BaseAdapter {
    List<Map<String,Object>> listmap;
    LayoutInflater layoutInflater;
    ViewHoder viewHoder;
    private int uid;
    private Context context;
    public Requirdapter(List<Map<String,Object>> listmap, Context context){
        this.listmap=listmap;
        layoutInflater=LayoutInflater.from(context);
        this.context=context;
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
        int mydist=0;
        if (convertView==null){
            viewHoder=new ViewHoder();
            convertView=layoutInflater.inflate(R.layout.listview_itme,null);
//            viewHoder.user_headimg= (CircleImageView) convertView.findViewById(R.id.lognhost);
            viewHoder.nickname= (TextView) convertView.findViewById(R.id.text1);
            viewHoder.service_type= (TextView) convertView.findViewById(R.id.text9);
            viewHoder.fee= (TextView) convertView.findViewById(R.id.text3);
            viewHoder.speciality= (TextView) convertView.findViewById(R.id.text4);
            viewHoder.username= (TextView) convertView.findViewById(R.id.text6);
            viewHoder.distance= (TextView) convertView.findViewById(R.id.text7);
            viewHoder.haoping_numtext= (TextView) convertView.findViewById(R.id.text8);
            viewHoder.haoping_num= (ImageView) convertView.findViewById(R.id.imagehaop);
            convertView.setTag(viewHoder);
        }else {
            viewHoder= (ViewHoder) convertView.getTag();
        }

        viewHoder.nickname.setText(""+listmap.get(position).get("nickname"));
        viewHoder.service_type.setText(""+listmap.get(position).get("service_type_txt"));
        viewHoder.speciality.setText(""+listmap.get(position).get("speciality_name"));
        viewHoder.username.setText(""+listmap.get(position).get("signature"));
        viewHoder.haoping_numtext.setText(""+listmap.get(position).get("order_rank").toString());
        switch (listmap.get(position).get("order_rank").toString()){
            case "0.0":
                viewHoder.haoping_num.setBackgroundResource(R.drawable.one);
                break;
            case "1.0":
                viewHoder.haoping_num.setBackgroundResource(R.drawable.one);
                break;
            case "2.0":
                viewHoder.haoping_num.setBackgroundResource(R.drawable.two);
                break;
            case "3.0":
                viewHoder.haoping_num.setBackgroundResource(R.drawable.three);
                break;
            case "4.0":
                viewHoder.haoping_num.setBackgroundResource(R.drawable.four);
                break;
            case "5.0":
                viewHoder.haoping_num.setBackgroundResource(R.drawable.five);
                break;
        }

        String dist= (String) listmap.get(position).get("distance");
        Log.e("aa","dist为"+dist);
        if (!dist.equals("")){
            mydist=Integer.parseInt(dist);
        }
        int lat=mydist/0100;

        viewHoder.distance.setText(lat+"km");

        setbitmap(listmap.get(position).get("user_headimg")+"",viewHoder.user_headimg);

        return convertView;
    }





    class ViewHoder{
        private CircleImageView user_headimg;//用户头像
        private TextView nickname;//昵称
        private TextView  service_type;//服务类型
        private TextView  fee;//课时费
        private TextView  speciality;//课程
        private TextView  username;//内容
        private TextView  distance;//距离
        private ImageView haoping_num;//好评数
        private TextView haoping_numtext;//好评分
    }


    public String geiuiserid(int pioos){
        String uida=listmap.get(pioos).get("user_id").toString();
        uid=Integer.parseInt(uida);

        return uid+"";
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
