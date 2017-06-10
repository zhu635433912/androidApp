package modle.JieYse;

import java.util.Map;

/**
 * 资料信息映射
 */

public class User_Modle {
    private String error;
    private String errmsg;
    private Map<String,Object> content;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
    public Map<String, Object> getContent() {
        return content;
    }

    public void setContent(Map<String, Object> content) {
        this.content = content;
    }
}
