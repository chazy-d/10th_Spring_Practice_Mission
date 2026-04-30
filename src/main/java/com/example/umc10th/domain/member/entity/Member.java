package com.example.umc10th.domain.member.entity;

import com.example.umc10th.domain.category.entity.mapping.MemberFoodCategory;
import com.example.umc10th.domain.member.enums.Gender;
import com.example.umc10th.domain.member.enums.SocialType;
import com.example.umc10th.domain.term.entity.MemberTermAgreement;
import com.example.umc10th.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
public class Member extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@Column(nullable = false, length = 50)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private Gender gender = Gender.NONE;

	private LocalDate birth;

	@Column(name = "social_uid", length = 255)
	private String socialUid;

	@Enumerated(EnumType.STRING)
	@Column(name = "social_provider", nullable = false, length = 20)
	private SocialType socialType = SocialType.LOCAL;

	@Column(nullable = false)
	private Integer point = 0;

	@Column(length = 100, unique = true)
	private String email;

	@Column(name = "phone_number", length = 20)
	private String phoneNumber;

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	private List<MemberFoodCategory> memberFoodCategories = new ArrayList<>();

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	private List<MemberTermAgreement> memberTermAgreements = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
}
