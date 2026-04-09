package com.example.umc10th.domain.mission.entity;

import com.example.umc10th.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "mission")
public class Mission extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mission_id")
	private Long id;

	private LocalDate deadline;

	@Column(name = "conditional", nullable = false, columnDefinition = "TEXT")
	private String conditionText;

	@Column(name = "reward_point", nullable = false)
	private Integer rewardPoint;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id", nullable = false)
	private Store store;

	public Long getId() {
		return id;
	}

	public String getConditionText() {
		return conditionText;
	}

	public Integer getRewardPoint() {
		return rewardPoint;
	}
}
