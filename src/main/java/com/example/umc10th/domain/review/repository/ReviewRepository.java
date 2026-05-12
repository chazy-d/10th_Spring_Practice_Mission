package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	boolean existsByMemberMissionId(Long memberMissionId);

	@Query("""
		select r
		from Review r
		join fetch r.store s
		where r.member.id = :memberId
			and (:cursor is null or r.id < :cursor)
		order by r.id desc
		""")
	List<Review> findMyReviewsByIdCursor(
		@Param("memberId") Long memberId,
		@Param("cursor") Long cursor,
		Pageable pageable
	);
}
