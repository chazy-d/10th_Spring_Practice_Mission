package com.example.umc10th.domain.mission.entity;

import com.example.umc10th.global.entity.BaseTimeEntity;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.enums.MemberMissionStatus;
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
@Table(name = "member_mission")
public class MemberMission extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_mission_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private MemberMissionStatus status = MemberMissionStatus.IN_PROGRESS;

	@Column(name = "started_at", nullable = false)
	private LocalDateTime startedAt;

	@Column(name = "success_requested_at")
	private LocalDateTime successRequestedAt;

	@Column(name = "completed_at")
	private LocalDateTime completedAt;

	@Column(name = "rejected_at")
	private LocalDateTime rejectedAt;

	@Column(name = "canceled_at")
	private LocalDateTime canceledAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mission_id", nullable = false)
	private Mission mission;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	public Long getId() {
		return id;
	}

	public MemberMissionStatus getStatus() {
		return status;
	}

	public Mission getMission() {
		return mission;
	}

	public Member getMember() {
		return member;
	}
}
