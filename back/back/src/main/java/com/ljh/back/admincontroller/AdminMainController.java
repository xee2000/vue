package com.ljh.back.admincontroller;

import com.ljh.back.adminrepository.AdminOrderRepository;
import com.ljh.back.adminrepository.AdminRepository;
import com.ljh.back.adminrepository.ProductItemRepository;
import com.ljh.back.adminrepository.SaleRepository;
import com.ljh.back.dto.OrderDto;
import com.ljh.back.dto.ProductDto;
import com.ljh.back.entity.*;
import com.ljh.back.membersrepository.ItemRepository;
import com.ljh.back.membersrepository.MemberRepository;
import com.ljh.back.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class AdminMainController {

    @Autowired
    SaleRepository saleRepository;

    @Autowired

    ProductItemRepository productItemRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    MemberRepository memberRepository;

    String baseDirectory = "C:\\Users\\Documents\\project\\frontend\\front\\public\\img";

    @GetMapping("/api/admin/list")
    public ResponseEntity main(
            @CookieValue(value = "token", required = false) String token) {

        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        int adminId = jwtService.getId(token);
        List<Sale> salesList = saleRepository.findByAdminId(adminId);
        return new ResponseEntity<>(salesList, HttpStatus.OK);
    }

    @PostMapping("/api/admin/product")
    public ResponseEntity productupload(
            @CookieValue(value = "token", required = false) String token,
            @RequestParam("name") String name,
            @RequestParam("price") int price,
            @RequestParam("discountPer") int discountPer,
            @RequestParam("selectedFile") MultipartFile selectedFile
    ) throws IOException {
        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String originalName = selectedFile.getOriginalFilename();

        String uuid = UUID.randomUUID().toString();
        //파일 실제 경로 + // + 파일이름
        String utf8FilePath = new String((baseDirectory + File.separator + uuid + "_" + originalName));
        File target = new File(utf8FilePath);
        target.mkdirs();
        selectedFile.transferTo(target);

        int adminId = jwtService.getId(token);
        Item newItem = new Item();
        newItem.setId(adminId);
        newItem.setName(name);
        newItem.setDiscountPer(discountPer);
        newItem.setImgPath(utf8FilePath);

        newItem.setPrice(price);

        productItemRepository.save(newItem);
        return new ResponseEntity(HttpStatus.OK);
    }
}
