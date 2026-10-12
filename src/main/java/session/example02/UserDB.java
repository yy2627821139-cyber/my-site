package session.example02;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户数据库模拟类（与 BookDB 类似的静态 Map 模拟数据库）
 * 注册时把用户名-密码存入，登录时用于校验，做到“账号密码和注册一致、不写死”
 */
public class UserDB {

    // 用户名 -> 密码
    private static Map<String, String> users = new HashMap<String, String>();

    /**
     * 注册：保存用户。用户名已存在返回 false（注册失败）。
     */
    public static boolean register(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, password);
        return true;
    }

    /**
     * 登录校验：用户名存在且密码匹配才返回 true
     */
    public static boolean validate(String username, String password) {
        String pwd = users.get(username);
        return pwd != null && pwd.equals(password);
    }
}
