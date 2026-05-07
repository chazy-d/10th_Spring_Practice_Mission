package com.example.umc10th.domain.region.entity;

import com.example.umc10th.global.entity.BaseTimeEntity;
import com.example.umc10th.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "member_region_goal")
public class MemberRegionGoal extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_region_goal_id")
	private Long id;

	@Column(name = "completed_mission_count", nullable = false)
	private Integer completedMissionCount = 0;

	@Column(name = "is_reward_received", nullable = false)
	private Boolean isRewardReceived = false;

	@Column(name = "reward_received_at")
	private LocalDateTime rewardReceivedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_goal_id", nullable = false)
	private RegionGoal regionGoal;
}
