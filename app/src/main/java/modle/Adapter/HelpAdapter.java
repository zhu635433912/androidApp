package modle.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.R;
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
 * 创建时间：2017-10-28 09:55
 * 修改人：zhuyunjian
 * 修改时间：2017-10-28 09:55
 * 修改备注：
 */


public class HelpAdapter extends ListBaseAdapter {
    private List<Map<String,Object>> list;
    public HelpAdapter(List list, Context context) {
        super(list, context);
        this.list = list;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.help_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.phoneTv.setText(list.get(position).get("tel")+"");
        holder.headImage.setImageURI(Uri.parse(list.get(position).get("headimg")+""));
        holder.nameTv.setText(list.get(position).get("nickname")+"");
        return convertView;
    }

    static class ViewHolder{
        TextView nameTv,phoneTv;
        SimpleDraweeView headImage;
        ViewHolder(View itemView){
            nameTv = (TextView) itemView.findViewById(R.id.help_name_tv);
            phoneTv = (TextView) itemView.findViewById(R.id.help_phone_tv);
            headImage = (SimpleDraweeView) itemView.findViewById(R.id.help_head_image);
        }
    }
}
