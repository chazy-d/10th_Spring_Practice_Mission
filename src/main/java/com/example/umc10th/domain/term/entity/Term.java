package com.example.umc10th.domain.term.entity;

import com.example.umc10th.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@Entity
@Table(name = "term")
public class Term extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "term_id")
	private Long id;

	@Column(nullable = false, length = 100)
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(nullable = false, length = 20)
	private String version;

	@Column(name = "is_required", nullable = false)
	private Boolean isRequired = true;

	@Column(name = "effective_from", nullable = false)
	private LocalDateTime effectiveFrom;
}
