package modle.JieYse.Dtu_Modle;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Administrator on 2017/3/17 0017.
 */

public class ProvinceAreaHelper {
    private Context mContext;

    public Map<String, String[]> getsheng(){
        return mCitisDatasMap;
    }
    public Map<String, String[]> getshi(){
        return mDistrictDatasMap;
    }
    public Map<String, String> getqu(){
        return mZipcodeDatasMap;
    }

    public ArrayList<ArrayList<String>> getshilist(){

        return arrlistshi;
    }

    private ArrayList<ArrayList<String>> arrlistshi=new ArrayList<ArrayList<String>>();
    /**
     * 所有省
     */
    protected String[] mProvinceDatas;

    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();

    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;

    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;

    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName ="";

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode ="";


    public ProvinceAreaHelper(Context context){
        mContext = context;
    }

    /**
     * 解析省市区的XML数据
     */
    public void initProvinceData(){
        List<ProvinceModel> provinceModelList;
        AssetManager assetManager = mContext.getAssets();
        try {
            InputStream input = assetManager.open("province_data.xml");

            //创建一个解析xml 的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            XmlParserHelper xmlParserHelper = new XmlParserHelper();

            //开始解析xml
            parser.parse(input,xmlParserHelper);
            input.close();

            //获取解析出来的数据
            provinceModelList = xmlParserHelper.getDataList();
            //Log.e("aa"," provinceModelList"+provinceModelList.get(5));


            //*/ 初始化默认选中的省、市、区
            if (provinceModelList!= null && !provinceModelList.isEmpty()) {
                mCurrentProviceName = provinceModelList.get(0).getName();
                List<CityModel> cityList = provinceModelList.get(0).getCityList();
                if (cityList!= null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }

            if(provinceModelList!=null){
                mProvinceDatas = new String[provinceModelList.size()];

                // 遍历所有省的数据
                for (int i=0; i< provinceModelList.size(); i++) {
                    mProvinceDatas[i] = provinceModelList.get(i).getName();
                    List<CityModel> cityList = provinceModelList.get(i).getCityList();
                    String[] cityNames = new String[cityList.size()];

                    // 遍历省下面的所有市的数据
                    for (int j=0; j< cityList.size(); j++) {
                        cityNames[j] = cityList.get(j).getName();
                        List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                        String[] distrinctNameArray = new String[districtList.size()];

                        // 遍历市下面所有区/县的数据
                        for (int k=0; k<districtList.size(); k++) {
                            DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());

                            // 区/县对于的邮编，保存到mZipcodeDatasMap
                            mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                            distrinctNameArray[k] = districtModel.getName();
                        }
                        // 市-区/县的数据，保存到mDistrictDatasMap
                        mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                    }
                    // 省-市的数据，保存到mCitisDatasMap
                    mCitisDatasMap.put(provinceModelList.get(i).getName(), cityNames);
                }

            }

        }catch (Exception e){
            Log.e("aa", "解析省市区的XML数据 Exception=" + e.getMessage());
            e.printStackTrace();
        }
    }
}
