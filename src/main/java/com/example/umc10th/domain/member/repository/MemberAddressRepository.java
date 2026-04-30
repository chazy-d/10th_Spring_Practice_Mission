package com.example.umc10th.domain.member.repository;

import com.example.umc10th.domain.member.entity.MemberAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAddressRepository extends JpaRepository<MemberAddress, Long> {
}
