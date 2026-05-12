package com.example.umc10th.domain.review.entity;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.MemberMission;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.global.entity.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "review")
public class Review extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private Long id;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(nullable = false, precision = 2, scale = 1)
	private BigDecimal rating;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id", nullable = false)
	private Store store;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_mission_id", nullable = false, unique = true)
	private MemberMission memberMission;

	@OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST)
	private List<ReviewPhoto> reviewPhotos = new ArrayList<>();

	protected Review() {
	}

	private Review(String content, BigDecimal rating, Store store, Member member, MemberMission memberMission) {
		this.content = content;
		this.rating = rating;
		this.store = store;
		this.member = member;
		this.memberMission = memberMission;
	}

	public static Review create(
		String content,
		BigDecimal rating,
		Store store,
		Member member,
		MemberMission memberMission
	) {
		return new Review(content, rating, store, member, memberMission);
	}

	public void addPhoto(String imageUrl) {
		reviewPhotos.add(ReviewPhoto.create(imageUrl, this));
	}

	public Long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public MemberMission getMemberMission() {
		return memberMission;
	}

	public Store getStore() {
		return store;
	}

	public List<String> getImageUrls() {
		return reviewPhotos.stream()
			.map(ReviewPhoto::getPhotoUrl)
			.toList();
	}
}
