package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
