package com.example.umc10th.domain.store.repository;

import com.example.umc10th.domain.store.entity.StoreOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreOwnerRepository extends JpaRepository<StoreOwner, Long> {
}
