package com.ljh.back.membercontroller;

import com.ljh.back.entity.Item;
import com.ljh.back.entity.Member;
import com.ljh.back.membersrepository.ItemRepository;
import com.ljh.back.membersrepository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;
    @GetMapping("/api/items")
    public ResponseEntity main() {
      List<Member> memberList =   memberRepository.findAll();

      List<Item> itemList =   itemRepository.findAll();
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

}