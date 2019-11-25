package springboottest.business.writefile;


import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by hxtxy on 2017/9/7.
 */
public class BrowserUtils {
    public static String getBrowserName(HttpServletRequest request) {
        String agent = request.getHeader("User-Agent").toLowerCase();
        if (agent.indexOf("msie 7") > 0) {
            return "ie7";
        } else if (agent.indexOf("msie 8") > 0) {
            return "ie8";
        } else if (agent.indexOf("msie 9") > 0) {
            return "ie9";
        } else if (agent.indexOf("msie 10") > 0) {
            return "ie10";
        } else if (agent.indexOf("msie") > 0) {
            return "ie";
        } else if (agent.indexOf("opera") > 0) {
            return "opera";
        } else if (agent.indexOf("opera") > 0) {
            return "opera";
        } else if (agent.indexOf("firefox") > 0) {
            return "firefox";
        } else if (agent.indexOf("webkit") > 0) {
            return "webkit";
        } else if (agent.indexOf("gecko") > 0 && agent.indexOf("rv:11") > 0) {
            return "ie11";
        } else {
            return "Others";
        }
    }



    public static boolean isInWechatBrowser(HttpServletRequest request) {
        String ua = request.getHeader("User-Agent");
        if (ua != null && ua.toLowerCase().indexOf("micromessenger") > 10) {
            return true;
        }
        return false;
    }

    public static Map<String, String> getAllRequestHeader(HttpServletRequest request) {
        Enumeration<String> reqHeadInfos = request.getHeaderNames();//获取所有的请求头
        Map<String, String> ret = new LinkedHashMap<String, String>();
        while (reqHeadInfos.hasMoreElements()) {
            String headName = (String) reqHeadInfos.nextElement();
            String headValue = request.getHeader(headName);//根据请求头的名字获取对应的请求头的值
            //System.out.println(headName+","+headValue);
            ret.put(headName, headValue);
        }
        return ret;
    }

    public static boolean isIE(HttpServletRequest request) {
        return ((request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0) || (request
                .getHeader("USER-AGENT").toLowerCase().indexOf("rv:11") > 0));
    }
}
