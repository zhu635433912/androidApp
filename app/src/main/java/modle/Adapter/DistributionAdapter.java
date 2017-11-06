package modle.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.R;
//import com.deguan.xuelema.androidapp.utils.GlideCircleTransform;
import com.deguan.xuelema.androidapp.viewimpl.SimilarXuqiuView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import java.util.Map;

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
 * 创建时间：2017-07-15 08:46
 * 修改人：zhuyunjian
 * 修改时间：2017-07-15 08:46
 * 修改备注：
 */


public class DistributionAdapter extends ListBaseAdapter {
    private List<Map<String,Object>> list;


    public DistributionAdapter(List list, Context context) {
        super(list, context);
        this.list = list;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_distribution_list,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
//        Glide.with(context.getApplicationContext()).load(list.get(position).get("user_headimg")+"")
//                .transform(new GlideCircleTransform(context)).into(holder.headImage);
        holder.headImage.setImageURI(Uri.parse(list.get(position).get("user_headimg")+""));
        holder.nameTv.setText(list.get(position).get("user_name")+"");
        holder.moneyTv.setText(list.get(position).get("fee")+"");

        return convertView;
    }

    static class ViewHolder{
        private SimpleDraweeView headImage;
        private TextView moneyTv,nameTv;
        ViewHolder(View itemview){
            headImage = (SimpleDraweeView) itemview.findViewById(R.id.distribution_head_image);
            moneyTv = (TextView) itemview.findViewById(R.id.distribution_money);
            nameTv = (TextView) itemview.findViewById(R.id.distribution_name);
        }
    }
}
