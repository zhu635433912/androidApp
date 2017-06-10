package modle.JieYse;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/1.
 */

public class Courses_Modle {
    private Map<String,Object> courses;
    private String error;
    private String errmsg;

    public Map<String, Object> getCourses() {
        return courses;
    }

    public void setCourses(Map<String, Object> courses) {
        this.courses = courses;
    }

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
}
