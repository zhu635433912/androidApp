package view.login.Modle;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class Backj_URL {
    /**
     * 用户登录
     * 传递
     *   string $username 用户名
     *   string $password 密码
     * 返回
     * {
     *     error        : "string"  // ok:成功 no:失败
     *     errmsg       : "string"  // 错误信息
     *     user_id      : "int"     // 用户id
     * }
     */
    public final static String USER_URL="http://deguanjiaoyu.com/index.php?s=/Service/Accounts/signin";

    /**
     * 注册用户
     * @param  int    $role     用户角色 1:普通用户 2:教师 3:运营 4:管理员
     * @param  string $username 用户名
     * @param  string $password 密码
     * @param  string $yzm      短信验证码
     *          inv_code 邀请码 实际为推荐人手机号
     * @return
     * {
     *     error        : "string"  // ok:成功 no:失败
     *     errmsg       : "string"  // 错误信息
     *     user_id      : "int"     // 用户id
     * }
     */
    public final static String REGISTER_URL="http://deguanjiaoyu.com/index.php?s=/Service/Accounts/signup";
    //短信验证
    public final static String SMS_URL="http://deguanjiaoyu.com/index.php?s=/Service/Accounts/check_mobile";
    /**
     * 重置密码
     * index.php?s=/Service/Accounts/reset
     * @param  string $username 用户名
     * @param  string $password 密码
     * @param  string $yzm      短信验证码
     * @return
     * {
     *     error        : "string"  // ok:成功 no:失败
     *     errmsg       : "string"  // 错误信息
     *     user_id      : "int"     // 用户id
     * }
     */
    public final static String CZ_URL="http://deguanjiaoyu.com/index.php?s=/Service/Accounts/reset";
    /*
    获取用户资料
     */
    public final static String USER_ZILIAO="http://deguanjiaoyu.com/index.php?s=/Service/Accounts/get_profile";


}
