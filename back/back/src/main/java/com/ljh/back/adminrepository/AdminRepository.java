package com.ljh.back.adminrepository;

import com.ljh.back.entity.Admin;
import com.ljh.back.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {


    Admin findByPinAndPassword(String pin, String password);

    Admin findById(int id);
}
