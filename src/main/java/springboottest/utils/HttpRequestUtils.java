package springboottest.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Http请求工具类
 */
@Slf4j
public class HttpRequestUtils {

    /**
     * 全局连接池对象
     */
    private static final PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();

    /**
     * 静态代码块配置连接池信息
     */
    static {
        // 设置最大连接数
        connManager.setMaxTotal(200);
        // 设置每个连接的路由数
        connManager.setDefaultMaxPerRoute(20);

    }

    /**
     * 获取Http客户端连接对象
     *
     * @param timeOut 超时时间
     * @return Http客户端连接对象
     */
    public static CloseableHttpClient getHttpClient(Integer timeOut) {
        // 创建Http请求配置参数
        RequestConfig requestConfig = RequestConfig.custom()
                // 获取连接超时时间
                .setConnectionRequestTimeout(timeOut)
                // 请求超时时间
                .setConnectTimeout(timeOut)
                // 响应超时时间
                .setSocketTimeout(timeOut)
                .build();

        /**
         * 测出超时重试机制为了防止超时不生效而设置
         *  如果直接放回false,不重试
         *  这里会根据情况进行判断是否重试
         */
        HttpRequestRetryHandler retry = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= 3) {// 如果已经重试了3次，就放弃
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                    return false;
                }
                if (exception instanceof InterruptedIOException) {// 超时
                    return true;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                    return false;
                }
                if (exception instanceof SSLException) {// ssl握手异常
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };

        // 创建httpClient
        return HttpClients.custom()
                // 把请求相关的超时信息设置到连接客户端
                .setDefaultRequestConfig(requestConfig)
                // 把请求重试设置到连接客户端
                .setRetryHandler(retry)
                // 配置连接池管理对象
                .setConnectionManager(connManager)
                .build();
    }

    /**
     * GET请求
     *
     * @param url     请求地址
     * @return
     */
    public static String httpGet(String url) {
        String msg = "-1";

        // 获取客户端连接对象
        CloseableHttpClient httpClient = getHttpClient(CodeValue.httpTimeOut);
        // 创建GET请求对象
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = null;

        try {
            // 执行请求
            response = httpClient.execute(httpGet);
            // 获取响应实体
            HttpEntity entity = response.getEntity();
            // 获取响应信息
            msg = EntityUtils.toString(entity, "UTF-8");
        } catch (ClientProtocolException e) {
            log.error("协议错误");
            e.printStackTrace();
        } catch (ParseException e) {
            log.error("解析错误");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("IO错误");
            e.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    log.error("释放链接错误");
                    e.printStackTrace();
                }
            }
        }

        return msg;
    }

    public static String httpPost(String url, Map<String,String> requestValues){
        String msg = "-1";
        // 获取客户端连接对象
        CloseableHttpClient httpClient = getHttpClient(CodeValue.httpTimeOut);
        HttpPost httpPost = new HttpPost(url);

        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        for(Map.Entry<String,String> entry: requestValues.entrySet()){
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        HttpEntity ent = null;
        try {
            ent = new UrlEncodedFormEntity(list, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpPost.setEntity(ent);

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            msg = EntityUtils.toString(entity, "UTF-8");
        } catch (ClientProtocolException e) {
            log.error("协议错误");
            e.printStackTrace();
        } catch (ParseException e) {
            log.error("解析错误");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("IO错误");
            e.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    log.error("释放链接错误");
                    e.printStackTrace();
                }
            }
        }

        return msg;
    }

    /**
     * 图片
     *
     * @param url     请求地址
     * @return
     */
    public static String httpGetImg(String url) {
        String msg = "-1";

        // 获取客户端连接对象
        CloseableHttpClient httpClient = getHttpClient(CodeValue.httpTimeOut);
        // 创建GET请求对象
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = null;

        try {
            // 执行请求
            response = httpClient.execute(httpGet);
            // 获取响应实体
            HttpEntity entity = response.getEntity();
            // 获取响应信息
            msg = Base64.encodeBase64String(EntityUtils.toByteArray(entity));
        } catch (ClientProtocolException e) {
            log.error("协议错误");
            e.printStackTrace();
        } catch (ParseException e) {
            log.error("解析错误");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("IO错误");
            e.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    log.error("释放链接错误");
                    e.printStackTrace();
                }
            }
        }

        return msg;
    }




    public static void main(String[] args) {

//        System.out.println(httpGet("http://www.hhyungu.com/0-61-7-0-0-0-0---0-0-0.html", 6000));
//        System.out.println(httpGet("https://item.jd.com/100003344497.html"));

//        Map<String,String> map = new HashMap<>();
//        map.put("productId","613400");
////        map.put("sellerId","43");
//        map.put("specId","69");
//
//        String json = httpPost("http://www.hhyungu.com/getGoodsInfo.html",map);
//        System.out.println(json);

        String result = httpGetImg("https://img.ciics.com/application/filedata/upload/img/avatar/1567067648026.jpg");

        System.out.println(result);
    }

}