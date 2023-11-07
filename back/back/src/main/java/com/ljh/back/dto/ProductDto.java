package com.ljh.back.dto;

import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@ToString
public class ProductDto {

    private String name;
    private String imgPath;
    private int price;
    private int discountPer;
    private int adminId;
    private String image;
    private MultipartFile selectedFile;
}
