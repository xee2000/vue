package com.ljh.back.membersrepository;

import com.ljh.back.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member findByMemIdAndPassword(String memId, String password);

    Member findById(int id);

    Member findByMemId(String memId);
}
