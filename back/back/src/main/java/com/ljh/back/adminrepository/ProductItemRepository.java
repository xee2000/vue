package com.ljh.back.adminrepository;

import com.ljh.back.entity.Admin;
import com.ljh.back.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemRepository extends JpaRepository<Item, Integer> {

}
