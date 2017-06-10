package modle;

import java.util.Map;

/**
 * 短信，举报，审核接口
 */

public interface Onteh_init {

    /**
     获取验证码
     mobile 手机号
     lei 短信类型 signup:注册用户 forget:忘记密码
     */
    public Map<String,Object> getyzm( String mobile, String lei);

    /**
     判断是否审核通过
     user_id    用户id
     */
    public Map<String,Object> Toexamine(String url, String user_id);

    /**
     提交举报
     uid        用户id
     content    举报内容
     */
    public Map<String,Object> Report(int user_id, String content);

    /*
    用户信息反馈接口映射
     */
    public void setuserfeedback(int uid,String content);

}
