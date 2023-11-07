package com.ljh.back.membercontroller;

import com.ljh.back.dto.MemberDto;
import com.ljh.back.entity.Member;
import com.ljh.back.membersrepository.MemberRepository;
import com.ljh.back.service.JwtService;
import com.ljh.back.util.Springsecuritypassword;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class AccountController{

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JwtService jwtService;

    @PostMapping("/api/account/login")
    public ResponseEntity login(@RequestBody Map<String, String> params, HttpServletResponse res) {
        String memId = params.get("memId");
        Member member = memberRepository.findByMemId(memId);
        if (member.getMemId() != null && !member.getMemId().isEmpty()) {
            Springsecuritypassword encoder = new Springsecuritypassword();
            if (encoder.matches(params.get("password"), member.getPassword())) {
                int id = member.getId();
                String token = jwtService.getToken("id", id);

                Cookie cookie = new Cookie("token", token);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                res.addCookie(cookie);
                return new ResponseEntity<>(id, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PostMapping("/api/account/logout")
    public ResponseEntity logout(HttpServletResponse res) {
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        res.addCookie(cookie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/account/check")
    public ResponseEntity check(@CookieValue(value = "token", required = false) String token) {
        Claims claims = jwtService.getClaims(token);

        if (claims != null) {
            int id = Integer.parseInt(claims.get("id").toString());
            return new ResponseEntity<>(id, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/api/account/create")
    public ResponseEntity create(@RequestBody MemberDto dto) {

        Member Dto = memberRepository.findByMemId(dto.getMemId());
            //아이디가 존재
            if (Dto == null) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                Member member = new Member();
                member.setMemId(dto.getMemId().trim());
                member.setName(dto.getName().trim());
                member.setEmail(dto.getEmail().trim());
                member.setPassword(passwordEncoder.encode(dto.getPassword()).trim());
                memberRepository.save(member);
                return new ResponseEntity<>(HttpStatus.OK);

            } else {
                return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
            }
    }


}