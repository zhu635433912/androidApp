package modle.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import modle.toos.CircleImageView;

/**
 * Created by Administrator on 2017/6/18 0018.
 */

public class GrideViewadbut extends BaseAdapter {
    private List<Map<String,Object>> listmap;
    private Context context;
    private LayoutInflater layoutInflater;
    private Viewhode viewhode;


    public  GrideViewadbut(Context context, List<Map<String,Object>> listmap){
        this.listmap=listmap;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listmap.size();
    }

    @Override
    public Object getItem(int i) {
        return listmap.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            view=layoutInflater.inflate(R.layout.pick_singleitme,null);
            viewhode=new Viewhode(view);
            viewhode.liebiaoname= (CircleImageView) view.findViewById(R.id.liebiaoname);
            viewhode.listname= (TextView) view.findViewById(R.id.listname);
            viewhode.coursename= (TextView) view.findViewById(R.id.coursename);
            viewhode.xiadans= (TextView) view.findViewById(R.id.xiadans);
            viewhode.address = (TextView) view.findViewById(R.id.address_tv);
            view.setTag(viewhode);
        }else {
            viewhode= (Viewhode) view.getTag();
        }
        viewhode.listname.setText(listmap.get(i).get("teacher_name")+"");
        String course_name=listmap.get(i).get("course_name")+"";
        if (course_name.equals("")) {
            viewhode.coursename.setText("不限");
        }else {
            viewhode.coursename.setText(course_name);
        }
        viewhode.address.setText(listmap.get(i).get("address")+"");
        Date d = new Date(Long.parseLong(listmap.get(i).get("created")+"")*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        viewhode.xiadans.setText(sdf.format(d));

//        viewhode.xiadans.setText(listmap.get(i).get("created")+"");
        setbitmap(listmap.get(i).get("teacher_headimg").toString(),viewhode.liebiaoname);

        return view;
    }

    class Viewhode {
        TextView address;
        CircleImageView liebiaoname;
        TextView listname;
        TextView coursename;
        TextView xiadans;
        public Viewhode(View view){

        }
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
