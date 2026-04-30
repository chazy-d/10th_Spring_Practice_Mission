package com.example.umc10th.domain.point.entity;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.point.enums.PointHistoryType;
import com.example.umc10th.domain.point.enums.PointSourceType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "point_history")
public class PointHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "point_history_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private PointHistoryType type;

	@Column(nullable = false)
	private Integer amount;

	@Column(name = "balance_after", nullable = false)
	private Integer balanceAfter;

	@Enumerated(EnumType.STRING)
	@Column(name = "source_type", nullable = false, length = 30)
	private PointSourceType sourceType;

	@Column(name = "source_id")
	private Long sourceId;

	@Column(length = 255)
	private String description;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;
}
