package modle.JieYse;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/25.
 */

public class ContentModle {
    private List<Map<String,Object>> content;
    private Map<String,Object> content2;
    private String error;
    private String errmsg;
    private String loadMore;

    public Map<String, Object> getContent2() {
        return content2;
    }

    public void setContent2(Map<String, Object> content2) {
        this.content2 = content2;
    }

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

    public String getLoadMore() {
        return loadMore;
    }

    public void setLoadMore(String loadMore) {
        this.loadMore = loadMore;
    }
}
