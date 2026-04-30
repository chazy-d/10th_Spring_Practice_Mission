package com.example.umc10th.domain.mission.entity;

import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.global.entity.BaseTimeEntity;
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
@Table(name = "mission")
public class Mission extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mission_id")
	private Long id;

	@Column(nullable = false, length = 100)
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(name = "reward_point", nullable = false)
	private Integer rewardPoint;

	@Enumerated(EnumType.STRING)
	@Column(name = "verification_type", nullable = false, length = 30)
	private VerificationType verificationType = VerificationType.OWNER_CONFIRM;

	@Column(name = "is_active", nullable = false)
	private Boolean isActive = true;

	@Column(name = "started_at")
	private LocalDateTime startedAt;

	@Column(name = "ended_at")
	private LocalDateTime endedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id", nullable = false)
	private Store store;

	public Long getId() {
		return id;
	}

	public String getConditionText() {
		return content;
	}

	public Integer getRewardPoint() {
		return rewardPoint;
	}

	public enum VerificationType {
		OWNER_CONFIRM,
		AUTO,
		ADMIN_CONFIRM
	}
}
