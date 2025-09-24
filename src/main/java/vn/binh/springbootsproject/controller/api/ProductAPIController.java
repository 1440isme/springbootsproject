package vn.binh.springbootsproject.controller.api;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.binh.springbootsproject.entity.Category;
import vn.binh.springbootsproject.entity.Product;
import vn.binh.springbootsproject.model.ProductModel;
import vn.binh.springbootsproject.model.Response;
import vn.binh.springbootsproject.service.ICategoryService;
import vn.binh.springbootsproject.service.IProductService;
import vn.binh.springbootsproject.service.IStorageService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductAPIController {
    @Autowired
    IProductService productService;
    @Autowired
    ICategoryService categoryService;
    @Autowired
    IStorageService storageService;
    @GetMapping
    public ResponseEntity<?> getAllProduct() {
        return new ResponseEntity<Response>(new Response(true, "Thành công", productService.findAll()), HttpStatus.OK);
    }
    @PostMapping(path = "/addProduct")
    public ResponseEntity<?> saveOrUpdate(
            @Validated @RequestParam("productName") String productName,
            @RequestParam("imageFile") MultipartFile productImages,
//@RequestParam("images") String images,
            @Validated @RequestParam("unitPrice") Double productPrice,
            @Validated @RequestParam("discount") Double promotionalPrice,
            @Validated @RequestParam("description") String
                    productDescription,
            @Validated @RequestParam("categoryId") Long categoryId,
            @Validated @RequestParam("quantity") Integer quantity,
            @Validated @RequestParam("status") Short status) {
        Optional<Product> optProduct =
                productService.findByProductName(productName);
        if (optProduct.isPresent()) {
// return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sản phẩm này đã
// tồn tại trong hệ thống");
            return new ResponseEntity<Response>(
                    new Response(false, "Sản phẩm này đã tồn tại trong hệ thống", optProduct.get()),  HttpStatus.BAD_REQUEST);
        } else {
            Product product = new Product();
            Timestamp timestamp = new Timestamp(new
                    Date(System.currentTimeMillis()).getTime());
            try {
                ProductModel proModel = new ProductModel();
//copy từ Model sang Entity
                BeanUtils.copyProperties(proModel, product);
//xử lý category liên quan product
                Category cateEntity = new Category();
                cateEntity.setCategoryId(proModel.getCategoryId());
                product.setCategory(cateEntity);
//kiểm tra tồn tại file, lưu file
                if(!proModel.getImageFile().isEmpty()) {
                    UUID uuid = UUID.randomUUID();
                    String uuString = uuid.toString();
//lưu file vào trường Images
                    product.setImages(storageService.getStorageFilename(proModel.getImageFile(),
                            uuString));
                    storageService.store(proModel.getImageFile(),
                            product.getImages());
                }
                product.setCreateDate(timestamp);
                productService.save(product);
                optProduct = productService.findByCreateDate(timestamp);
            } catch (Exception e) {
                e.printStackTrace();
            }
// return ResponseEntity.ok().body(product);
            return new ResponseEntity<Response>(new Response(true, "Thành công", optProduct.get()),HttpStatus.OK);
        }
    }
}