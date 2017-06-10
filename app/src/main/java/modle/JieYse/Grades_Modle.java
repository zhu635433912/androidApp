package modle.JieYse;

import java.util.Map;

/**
 * 科目Molde
 */

public class Grades_Modle {
    private Map<String,Object> grades;
    private String error;
    private String errmsg;

    public Map<String, Object> getGrades() {
        return grades;
    }

    public void setGrades(Map<String, Object> grades) {
        this.grades = grades;
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
