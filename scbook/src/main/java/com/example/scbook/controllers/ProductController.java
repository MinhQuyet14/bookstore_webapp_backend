package com.example.scbook.controllers;

import com.example.scbook.dtos.ProductDTO;
import com.example.scbook.models.Product;
import com.example.scbook.responses.ProductListResponse;
import com.example.scbook.responses.ProductResponse;
import com.example.scbook.services.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    //DI
    private final IProductService productService;

    @GetMapping("")
    public ResponseEntity<ProductListResponse> getAllProducts(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name = "category_id") Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int limit
    ){
        PageRequest pageRequest = PageRequest.of(page, limit
                ,Sort.by("createdAt").ascending());

        Page<ProductResponse> productPage= productService.getAllProducts(keyword, categoryId, pageRequest);
        int totalPages = productPage.getTotalPages();
        List<ProductResponse> products = productPage.getContent();
        return ResponseEntity.ok(ProductListResponse
                .builder()
                .products(products)
                .totalPages(totalPages)
                .build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long productId){
        try {
            Product existingProduct = productService.getProductById(productId);
            return ResponseEntity.ok(ProductResponse.fromProduct(existingProduct));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Deleted successfully");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createProduct(
            @Valid @RequestBody ProductDTO productDTO,
            @RequestPart("file")MultipartFile file,
            BindingResult result
    ){
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages.toString());
            }
            //check img file
            if(file != null){
                if(file.getSize() > 10 * 1024 * 1024){ // >10 MB
                /*throw new ResponseStatusException(
                        HttpStatus.PAYLOAD_TOO_LARGE, "This file is too large, MAXIMUM is 10MB"
                );*/
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("This file is too large, MAXIMUM is 10MB");
                }
                String contentType = file.getContentType();
                if(contentType == null || !contentType.startsWith("image/")){
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }
                String filename = storeFile(file);
                productDTO.setUrl(filename);
            }
            productService.createProduct(productDTO);
            return ResponseEntity.ok("Product created successfully!!!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName){
        try {
            java.nio.file.Path imagePath = Paths.get("uploads/" + imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());
            if (resource.exists()) {
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-ids")
    public ResponseEntity<?> getProductsByIds(@RequestParam("ids") String ids){
        try{
            List<Long> productIds = Arrays.stream(ids.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            List<Product> products = productService.findProductsByIds(productIds);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public String storeFile(MultipartFile file) throws IOException{
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + "_" + filename;
        Path uploadDir = Paths.get("uploads");
        // Kiểm tra và tạo thư mục nếu chưa tồn tại
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        // Đường dẫn đầy đủ đến file
        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        // Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }
}
