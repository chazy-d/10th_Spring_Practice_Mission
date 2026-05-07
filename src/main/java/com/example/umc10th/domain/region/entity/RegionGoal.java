package com.example.umc10th.domain.region.entity;

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
import java.time.YearMonth;
import java.time.LocalDateTime;

@Entity
@Table(name = "region_goal")
public class RegionGoal extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "region_goal_id")
	private Long id;

	@Column(name = "target_month", nullable = false, length = 7)
	private YearMonth targetMonth;

	@Column(name = "goal_mission_count", nullable = false)
	private Integer goalMissionCount;

	@Column(name = "reward_point", nullable = false)
	private Integer rewardPoint;

	@Column(name = "started_at", nullable = false)
	private LocalDateTime startedAt;

	@Column(name = "ended_at", nullable = false)
	private LocalDateTime endedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id", nullable = false)
	private Region region;
}
