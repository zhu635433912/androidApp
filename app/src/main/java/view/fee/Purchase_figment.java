package view.fee;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.Student_Activty;

import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.user_ziliao.User_id;

/**
 * 购买课程
 */

public class Purchase_figment extends Fragment implements View.OnClickListener {
    private int fee;
    private TextView jieshufee;
    private TextView zongfee;
    private TextView jia;
    private TextView jian;
    private TextView keshi;
    private int i=1;
    private Button goumai;
    private int jiaoshiid;
    private int unvisit_fee;
    private int visit_fee;
    private TextView xuessm;
    private TextView laoshism;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.purchase_course,null);
        jieshufee= (TextView) view.findViewById(R.id.jieshufee);
        zongfee= (TextView) view.findViewById(R.id.zongfee);
        jia= (TextView) view.findViewById(R.id.jia);
        jian= (TextView) view.findViewById(R.id.jian);
        keshi= (TextView) view.findViewById(R.id.fee);
        goumai= (Button) view.findViewById(R.id.goumai);
        xuessm= (TextView) view.findViewById(R.id.xuessm);
        laoshism= (TextView) view.findViewById(R.id.laoshism);

        unvisit_fee= (int) getArguments().get("unvisit_fee");
        visit_fee = (int) getArguments().get("visit_fee");

        fee= (int) getArguments().get("visit_fee");
        jiaoshiid= (int) getArguments().get("Requir_id");
        setfee();

        xuessm.setOnClickListener(this);
        laoshism.setOnClickListener(this);
        goumai.setOnClickListener(this);
        jia.setOnClickListener(this);
        jian.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.jia:
                    i = i + 1;
                    keshi.setText(i+"");
                    zongfee.setText((fee*i)+"");
                    break;
                case R.id.jian:
                    if (i!=0){
                        i = i - 1;
                        keshi.setText(i+"");
                        zongfee.setText((fee*i)+"");
                }
                    break;
                case R.id.goumai:
                    new AlertDialog.Builder(getActivity()).setTitle("学了么提示!").setMessage("是否确定下单?")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int uid=Integer.parseInt(User_id.getUid());
                                    Order_init order_init=new Order();
                                    //创建订单
                                    order_init.Establish_Order(uid,jiaoshiid,739,fee,i);

                                    Toast.makeText(getActivity(),"购买课程成功",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getActivity(), Student_Activty.class);
                                    startActivity(intent);
                                }
                            }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity(),"在看看别的老师吧~",Toast.LENGTH_SHORT).show();
                        }
                    }).show();
                    break;
                case R.id.xuessm:
                    laoshism.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                    xuessm.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                    fee=visit_fee;
                    setfee();
                    break;
                case R.id.laoshism:
                    laoshism.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                    xuessm.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                    fee=unvisit_fee;
                    setfee();
                    break;
            }

    }
    public void setfee(){
        jieshufee.setText(fee+"");
        zongfee.setText(fee+"");
    }
}
