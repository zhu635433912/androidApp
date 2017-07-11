package modle.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.entities.TeacherEntity;
import com.deguan.xuelema.androidapp.utils.GlideCircleTransform;
import com.zhy.autolayout.utils.AutoUtils;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * <p>
 * 项目名称：androidApp
 * 类描述：
 * 创建人：zhuyunjian
 * 创建时间：2017-06-14 13:35
 * 修改人：zhuyunjian
 * 修改时间：2017-06-14 13:35
 * 修改备注：
 */
public class TeacherListAdapter extends ListBaseAdapter {
    private List<TeacherEntity> list;

    public TeacherListAdapter(List list, Context context) {
        super(list, context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getItemView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_itme, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            //对于listview，注意添加这一行，即可在item上使用高度
            AutoUtils.autoSize(convertView);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.nickname.setText("" + list.get(position).getNickname());
        holder.service_type.setText("" + list.get(position).getService_type_txt());
//        holder.fee.setText("" + list.get(position).getFee());
        holder.fee.setText("");
//        holder.speciality.setText("" + list.get(position).getSpeciality());
        holder.speciality.setText("");
        holder.username.setText("" + list.get(position).getSignature());

//        holder.user_headimg.setImageURI(Uri.parse(list.get(position).getPublisher_headimg()));
        Glide.with(context).load(list.get(position).getUser_headimg()).transform(new GlideCircleTransform(context)).into(holder.user_headimg);
        String dist = list.get(position).getDistance();
        holder.stats.setText(""+list.get(position).getStatus2());
        double myDist = 0;
        if (!dist.equals("")){
            myDist = Double.parseDouble(dist)/1000;
        }
        DecimalFormat df = new DecimalFormat("#0.0");

//        int myDist = 0;
//        if (!dist.equals("")) {
//            myDist = Integer.parseInt(dist) ;
//        }
//        int lat = myDist / 1000;
        holder.distance.setText(df.format(myDist) + "km");
        if (!TextUtils.isEmpty(list.get(position).getGender())){
            String gender = list.get(position).getGender();
            if (gender.equals("1")) {
                holder.nianji.setText("男");
            } else if (gender.equals("2")) {
                holder.nianji.setText("女");
            }
        }else {
            holder.nianji.setText(" ");
        }
        holder.haoping_numtext.setText("好评: "+list.get(position).getHaoping_num());
//        switch (list.get(position).getOrder_rank().toString()){
//            case "0.0":
//                holder.haoping_num.setBackgroundResource(R.drawable.five);
//                break;
//            case "1.0":
//                holder.haoping_num.setBackgroundResource(R.drawable.one);
//                break;
//            case "2.0":
//                holder.haoping_num.setBackgroundResource(R.drawable.two);
//                break;
//            case "3.0":
//                holder.haoping_num.setBackgroundResource(R.drawable.three);
//                break;
//            case "4.0":
//                holder.haoping_num.setBackgroundResource(R.drawable.four);
//                break;
//            case "5.0":
//                holder.haoping_num.setBackgroundResource(R.drawable.five);
//                break;
//        }

        holder.education.setText(list.get(position).getEducation());
        return convertView;
    }

        static class ViewHolder {
            private ImageView user_headimg;
            //        private CircleImageView user_headimg;//用户头像
            private TextView nickname;//昵称
            private TextView service_type;//服务类型
            private TextView fee;//课时费
            private TextView speciality;//课程
            private TextView username;//内容
            private TextView distance;//距离
            private ImageView haoping_num;//好评数
            private TextView haoping_numtext;//好评分
            private TextView nianji;
            private TextView stats;
            private TextView education;

            public ViewHolder(View itemView) {
                education = (TextView) itemView.findViewById(R.id.education_tv);
                user_headimg = (ImageView) itemView.findViewById(R.id.lognhost);
                nickname = (TextView) itemView.findViewById(R.id.text1);
                service_type = (TextView) itemView.findViewById(R.id.text9);
                fee = (TextView) itemView.findViewById(R.id.text3);
                stats = (TextView) itemView.findViewById(R.id.text5);
                speciality = (TextView) itemView.findViewById(R.id.text4);
                username = (TextView) itemView.findViewById(R.id.text6);
                distance = (TextView) itemView.findViewById(R.id.text7);
                haoping_numtext = (TextView) itemView.findViewById(R.id.text8);
                haoping_num = (ImageView) itemView.findViewById(R.id.imagehaop);
                distance = (TextView) itemView.findViewById(R.id.text7);
                nianji = (TextView) itemView.findViewById(R.id.text2);
            }
        }

}
