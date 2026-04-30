package com.example.umc10th.domain.term.repository;

import com.example.umc10th.domain.term.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Long> {
}
