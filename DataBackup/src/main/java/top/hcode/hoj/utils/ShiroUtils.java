package top.hcode.hoj.utils;

import org.apache.shiro.SecurityUtils;
import top.hcode.hoj.shiro.AccountProfile;


/**
 * @Description:
 */
public class ShiroUtils {

    private ShiroUtils() {
    }

    public static AccountProfile getProfile(){
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }

}