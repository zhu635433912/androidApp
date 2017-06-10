package modle.JieYse;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/1.
 */

public class UploadEntity {
    @SerializedName("error")
    private String error;
    @SerializedName("errmsg")
    private String errmsg;
    @SerializedName("content")
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
