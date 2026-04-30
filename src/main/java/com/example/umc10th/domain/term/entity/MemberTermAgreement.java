package com.example.umc10th.domain.term.entity;

import com.example.umc10th.domain.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "member_term_agreement")
public class MemberTermAgreement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberTermAgreementId;

	@Column(name = "is_agreed", nullable = false)
	private Boolean isAgreed;

	@Column(name = "agreed_at", nullable = false)
	private LocalDateTime agreedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "term_id", nullable = false)
	private Term term;
}
