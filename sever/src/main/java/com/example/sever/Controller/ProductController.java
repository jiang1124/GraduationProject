package com.example.sever.Controller;


import com.example.sever.Entity.Product;
import com.example.sever.Sevice.ProductService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin//跨域所用
@RestController
public class ProductController {


    @Autowired
    private ProductService productService;

    @RequestMapping("/filesUpload")
    public void filesUpload(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam MultipartFile image) throws IOException {
        JsonObject json = new JsonObject();
        byte[] bs = image.getBytes();
        int stateInt = 1;
        if (bs.length > 0) {
            try {
                String filePath = "D:\\Android\\Project\\GraduationProject\\sever\\src\\main\\resources\\static\\image\\product";
                System.out.println("开始上传");
                File validateCodeFolder = new File(filePath);
                if (!validateCodeFolder.exists()) {
                    validateCodeFolder.mkdirs();
                }
                // 将字符串转换成二进制，用于显示图片
                // 将上面生成的图片格式字符串 imgStr，还原成图片显示
                 String type = ".jpg";
                 String uuid = "2015";
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

                 json.addProperty("code", 200);
                 json.addProperty("msg","AAA");
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

    @RequestMapping("/home")
    //select * from table_name limit 10
    public List<Product> homeProductList(){
        System.out.println(productService.findSVTopFive());
        return productService.findSVTopFive();
    }

    @RequestMapping(path = "/search/k")
    public List<Product> searchKeyProductList(String key,int page){
        System.out.println("/search/k key:"+key);
        return productService.findKeyMany(key,page);
    }

    @RequestMapping(path = "/search/s")
    public List<Product> searchSortProductList(String sort,int page){
        System.out.println("/search/s sort:"+sort);
        return productService.findSortMany(sort,page);
    }

    @RequestMapping(path = "/search/p")
    public List<Product> findKeyByPrice(String key,int page){
        System.out.println("/search/p sort:"+key);
        return productService.findKeyByPrice(key,page);
    }

    @RequestMapping(path = "/search/v")
    public List<Product> findKeyBySale(String key,int page){
        System.out.println("/search/p sort:"+key);
        return  productService.findKeyBySale(key,page);
    }

    @RequestMapping(path = "/history")
    public List<Product> findHistory(int user_id){
        System.out.println("/history user_id:"+user_id);
        List<Product> products = new ArrayList<>();
        products = productService.findHistory(user_id);
        System.out.println("/history result："+products.toString());
        return products;
    }

    @RequestMapping(path = "/collection")
    public List<Product> findCollection(int user_id){
        System.out.println("/collection user_id:"+user_id);
        List<Product> products = new ArrayList<>();
        products = productService.findCollection(user_id);
        System.out.println("/collection result："+products.toString());
        return products;
    }

    @RequestMapping("/addCollection")
    public int addCollection(int user_id,int product_id,int store_id){
        return productService.addCollection(user_id, product_id, store_id);
    }

    @RequestMapping("/addHistory")
    public int addHistory(int user_id,int product_id,int store_id){
        return productService.addHistory(user_id,product_id,store_id);
    }

    @RequestMapping("/deleteCollection")
    public int deleteCollection(int user_id,int product_id){
        return productService.deleteCollection(user_id, product_id);
    }

    @RequestMapping("/deleteHistory")
    public int deleteHistory(int user_id,int product_id){
        return productService.deleteHistory(user_id,product_id);
    }

    @RequestMapping("/storeProducts")
    public List<Product> storeProducts(int store_id,int page){
        return productService.findStoreProducts(store_id,page);
    }

    @RequestMapping("/storeSearchProducts")
    public List<Product> storeSearchProducts(int store_id,String product_name,int page){
        return productService.findStoreProducts(store_id,product_name,page);
    }

    @RequestMapping("/updateProduct")
    public String updateProduct(int product_id, String product_name,double product_price,double product_favl,double extra_money,String type){
        return productService.updateProduct(product_id, product_name, product_price, product_favl, extra_money, type);
    }

    @RequestMapping("/addProductDetail")
    public String addProduct(int store_id, String product_name,double product_price,double product_favl,double extra_money,String type){
        return productService.addProduct(store_id, product_name, product_price, product_favl, extra_money, type);
    }

    @RequestMapping("/deleteProduct")
    public String delProduct(int product_id){
        return productService.delProduct(product_id);
    }

}
