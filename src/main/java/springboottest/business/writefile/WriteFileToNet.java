package springboottest.business.writefile;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.net.URLEncoder;

@Slf4j
public class WriteFileToNet {

    public static void downloadFile(HttpServletResponse response, HttpServletRequest request, String filePath, String fileName) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {

            if(StringUtils.isEmpty(fileName) && StringUtils.isNotEmpty(filePath)) {
                fileName=filePath.substring(filePath.lastIndexOf("/")+1);
            }
            if (BrowserUtils.isIE(request))
                fileName = URLEncoder.encode(fileName, "UTF-8");
            else {
                fileName = new String(fileName.getBytes("UTF-8"),
                        "ISO-8859-1");
            }
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + fileName);
            bis = new BufferedInputStream(new FileInputStream(filePath));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[bis.available()];
            int bytesRead = 0;
            while (-1 != (bytesRead = (bis.read(buff, 0, buff.length)))) {
                bos.write(buff, 0, buff.length);
            }
        } catch (Exception e) {
            log.error("操作失败",e);
        } finally {
            try{
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            }catch (Exception e1){
                log.error("操作失败",e1);
            }

        }
    }
}
