package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.MemberMission;
import com.example.umc10th.domain.mission.enums.MemberMissionStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

	@Query("""
		select mm
		from MemberMission mm
		join fetch mm.mission m
		join fetch m.store s
		where mm.member.id = :memberId
			and mm.status in :statuses
			and (:cursor is null or mm.id < :cursor)
		order by mm.id desc
		""")
	List<MemberMission> findByMemberAndStatusesWithCursor(
		@Param("memberId") Long memberId,
		@Param("statuses") List<MemberMissionStatus> statuses,
		@Param("cursor") Long cursor,
		Pageable pageable
	);
}
