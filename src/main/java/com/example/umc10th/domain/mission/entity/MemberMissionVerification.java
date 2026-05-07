package com.example.umc10th.domain.mission.entity;

import com.example.umc10th.global.entity.BaseTimeEntity;
import com.example.umc10th.domain.mission.enums.MemberMissionVerificationStatus;
import com.example.umc10th.domain.store.entity.StoreOwner;
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
@Table(name = "member_mission_verification")
public class MemberMissionVerification extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "verification_id")
	private Long id;

	@Column(name = "verification_code", nullable = false, length = 20)
	private String verificationCode;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private MemberMissionVerificationStatus status = MemberMissionVerificationStatus.REQUESTED;

	@Column(name = "expires_at", nullable = false)
	private LocalDateTime expiresAt;

	@Column(name = "requested_at", nullable = false)
	private LocalDateTime requestedAt;

	@Column(name = "verified_at")
	private LocalDateTime verifiedAt;

	@Column(name = "rejected_at")
	private LocalDateTime rejectedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_mission_id", nullable = false)
	private MemberMission memberMission;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "verified_by_store_owner_id")
	private StoreOwner verifiedByStoreOwner;
}
