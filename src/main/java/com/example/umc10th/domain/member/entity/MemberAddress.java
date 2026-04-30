package com.example.umc10th.domain.member.entity;

import com.example.umc10th.domain.region.entity.Region;
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
@Table(name = "member_address")
public class MemberAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_address_id")
	private Long id;

	@Column(name = "address_name", length = 30)
	private String addressName;

	@Column(nullable = false, length = 255)
	private String address;

	@Column(name = "detail_address", length = 255)
	private String detailAddress;

	@Column(name = "is_default", nullable = false)
	private Boolean isDefault = false;

	@Column(name = "is_current", nullable = false)
	private Boolean isCurrent = false;

	@Column(name = "last_selected_at")
	private LocalDateTime lastSelectedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id", nullable = false)
	private Region region;
}
