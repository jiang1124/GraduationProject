package com.example.sever.Controller;

import java.io.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin//跨域所用
@RestController
public class FileUploadController {

    @RequestMapping("/filesUpload")
    public void filesUpload(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam MultipartFile image,@RequestParam String uuid,
                            @RequestParam String imageType) throws IOException {
        JsonObject json = new JsonObject();
        byte[] bs = image.getBytes();
        int stateInt = 1;
        if (bs.length > 0) {
            try {
                String filePath = "D:\\Android\\Project\\GraduationProject\\sever\\src\\main\\resources\\static\\image\\"+imageType;
                System.out.println("开始上传");
                File validateCodeFolder = new File(filePath);
                if (!validateCodeFolder.exists()) {
                    validateCodeFolder.mkdir();
                }
                // 将字符串转换成二进制，用于显示图片
                // 将上面生成的图片格式字符串 imgStr，还原成图片显示
                String type = ".jpg";
                String filename = uuid + type;
                InputStream in = new ByteArrayInputStream(bs);
                File file = new File(filePath, filename);// 可以是任何图片格式.jpg,.png等
                FileOutputStream fos = new FileOutputStream(file);
                byte[] b = new byte[1024];
                int nRead = 0;
                while ((nRead = in.read(b)) != -1) {
                    fos.write(b, 0, nRead);
                }
                fos.flush();
                fos.close();
                in.close();
                System.out.println("上传成功");
                ;

                json.addProperty("code", 200);
                json.addProperty("msg", "AAA");
                String str = json.toString();
                response.setCharacterEncoding("utf-8");
                response.setHeader("content-type", "text/html;charset=UTF-8");
                response.getWriter().write(str);
                response.getWriter().flush();
                response.getWriter().close();
            } catch (Exception e) {
                stateInt = 0;
                e.printStackTrace();
            } finally {

            }
        } else {
            System.out.println("上传失败");
        }
    }
}
