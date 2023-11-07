package com.ljh.back.membercontroller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ljh.back.adminrepository.AdminRepository;
import com.ljh.back.adminrepository.SaleRepository;
import com.ljh.back.dto.OrderDto;
import com.ljh.back.dto.SaleDto;
import com.ljh.back.entity.Admin;
import com.ljh.back.entity.Member;
import com.ljh.back.entity.Order;
import com.ljh.back.entity.Sale;
import com.ljh.back.membersrepository.MemberRepository;
import com.ljh.back.membersrepository.OrderRepository;
import com.ljh.back.service.JwtService;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    JwtService jwtService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("api/orders")
    public ResponseEntity getOrder(
            @CookieValue(value = "token", required = false) String token
    ) {

        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        } else {
            List<Order> orders = orderRepository.findAll();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }

    }

    @PostMapping("/api/orders")
    public ResponseEntity pushOrder(
            @RequestBody OrderDto dto,
            @CookieValue(value = "token", required = false) String token) {
        try {
            if (!jwtService.isValid(token)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }

            Order neworder = new Order();
            neworder.setMemberId(jwtService.getId(token));
            neworder.setName(dto.getName());
            neworder.setAddress(dto.getAddress());
            neworder.setCardNumber(dto.getCardNumber());
            neworder.setItems(dto.getItems());
            neworder.setPayment(dto.getPayment());
            orderRepository.save(neworder);
            // Parse the 'items' JSON string
            JsonParser parser = new JsonParser();
            JsonArray itemsArray = parser.parse(neworder.getItems()).getAsJsonArray();

            for (JsonElement itemElement : itemsArray) {
                JsonObject itemObject = itemElement.getAsJsonObject();
            System.out.print("###############여긴오나?");
                if (itemObject.has("adminId")) {
                    int adminId = itemObject.get("adminId").getAsInt();
                    Admin admin = adminRepository.findById(adminId);

                    if (admin != null) {
                        Sale sale = new Sale();
                        sale.setAdminId(adminId);
                        sale.setPrice(itemObject.get("price").getAsInt());
                        sale.setName(itemObject.get("name").getAsString());
                        sale.setItemId(itemObject.get("id").getAsInt());
                        sale.setOrderName(neworder.getName());
                        saleRepository.save(sale);
                    }
                }
            }


            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions (e.g., log and return an error response)
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}