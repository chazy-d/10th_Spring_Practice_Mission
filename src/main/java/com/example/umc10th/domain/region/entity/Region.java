package com.example.umc10th.domain.region.entity;

import com.example.umc10th.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "region")
public class Region extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "region_id")
	private Long id;

	@Column(nullable = false, length = 100, unique = true)
	private String name;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
