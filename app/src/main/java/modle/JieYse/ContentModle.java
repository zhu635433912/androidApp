package modle.JieYse;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/25.
 */

public class ContentModle {
    private List<Map<String,Object>> content;
    private String error;
    private String errmsg;

    public List<Map<String, Object>> getContent(){
        return content;
    }

    public void setContent(List<Map<String, Object>> content) {
        this.content = content;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrmsg(){
        return errmsg;
    }

    public void setErrmsg(String errmsg){
        this.errmsg = errmsg;
    }
}
