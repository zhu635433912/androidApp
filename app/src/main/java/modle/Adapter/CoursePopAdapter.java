package modle.Adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.entities.TeacherEntity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.autolayout.utils.AutoUtils;

import java.text.DecimalFormat;
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
 * 创建时间：2017-09-26 13:49
 * 修改人：zhuyunjian
 * 修改时间：2017-09-26 13:49
 * 修改备注：
 */


public class CoursePopAdapter extends ListBaseAdapter {

    private List<Map<String,Object>> list;

    public CoursePopAdapter(List list, Context context) {
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
            convertView = inflater.inflate(R.layout.course_pop_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            //对于listview，注意添加这一行，即可在item上使用高度
            AutoUtils.autoSize(convertView);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.courseTv.setText(list.get(position).get("course_name")+"     "+list.get(position).get("grade_name"));
        holder.feeTv.setText(list.get(position).get("unvisit_fee")+"元/小时");

        return convertView;
    }

    static class ViewHolder {

        private TextView courseTv;//昵称
        private TextView feeTv;//服务类型


        public ViewHolder(View itemView) {
            courseTv = (TextView) itemView.findViewById(R.id.course_pop_course);
            feeTv = (TextView) itemView.findViewById(R.id.course_pop_fee);

        }
    }
}
