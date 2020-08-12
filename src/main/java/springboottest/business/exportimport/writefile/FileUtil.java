package springboottest.business.exportimport.writefile;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {

    /**
     *
     * @param filePath 文件路径
     * @return 二进制数组
     */
    public static byte[] file2byteBase(String filePath) {
        File file = new File(filePath);

        FileInputStream in = null;
        ByteArrayOutputStream byteOut = null;
        byte[] fileBytes = new byte[0];
        try {
            in = new FileInputStream(file);
            byteOut = new ByteArrayOutputStream();

            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = in.read(bytes)) != -1) {
                byteOut.write(bytes, 0, len);
            }

            fileBytes = byteOut.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(in)) {
                    in.close();
                }
                if (Objects.nonNull(byteOut)) {
                    byteOut.flush();
                }
                if (Objects.nonNull(byteOut)) {
                    byteOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return fileBytes;
    }

    /**
     * 使用工具类
     * @param filePath
     * @return
     */
    public static byte[] file2byteSimple(String filePath) {
        try {
            return IOUtils.toByteArray(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }

    /**
     *
     * @param fileBytes 文件byte数组
     * @param dirPath 路径地址
     * @param fileName 文件名称
     */
    public static void byte2file(byte[] fileBytes, String dirPath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(dirPath);
            if (!dir.exists() && !dir.isDirectory()) {
                dir.mkdirs();
            }
            file = new File(dirPath + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(fileBytes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param response
     * @param fileName 文件名称
     * @param bytes 文件byte[]
     * @throws IOException
     */
    public static void exportByteArrayToWeb(HttpServletResponse response, String fileName, byte[] bytes) {
        response.setCharacterEncoding("UTF-8");

        try {
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName,"UTF-8"));
            ServletOutputStream out = response.getOutputStream();
            out.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 不要在这里关闭流  response本身就会关闭
    }

    /**
     *
     * @param dirPath 文件夹路径  "c://ss/aa/"
     * @param zipName 压缩包名称
     * @param zipPath 压缩包保存路径  "c://ss/aa/"
     * @return
     */
    public static String file2zip(String dirPath, String zipName, String zipPath) {
        File sourceFile = new File(dirPath);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;

        if (!sourceFile.exists()) {
            System.out.println("File catalog:" + dirPath + "not exist!");
        } else {
            try {
                if(!new File(zipPath).exists()){
                    new File(zipPath).mkdirs();
                }
                File zipFile = new File(zipPath  + zipName + ".zip");
                if (zipFile.exists()) {
                    System.out.println(zipPath + "Catalog File: " + zipName + ".zip" + "pack file.");

                } else {
                    File[] sourceFiles = sourceFile.listFiles();
                    if (null == sourceFiles || sourceFiles.length < 1) {
                        System.out.println("File Catalog:" + dirPath + "nothing in there,don't hava to compress!");

                    } else {
                        fos = new FileOutputStream(zipFile);
                        zos = new ZipOutputStream(new BufferedOutputStream(fos));
                        byte[] bufs = new byte[1024 * 10];
                        for (int i = 0; i < sourceFiles.length; i++) {
                            ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
                            zos.putNextEntry(zipEntry);
                            fis = new FileInputStream(sourceFiles[i]);
                            bis = new BufferedInputStream(fis, 1024 * 10);
                            int read = 0;
                            while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                                zos.write(bufs, 0, read);
                            }
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (null != bis) {
                        bis.close();
                    }
                    if (null != zos) {
                        zos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return zipPath+zipName+".zip";
    }
}
