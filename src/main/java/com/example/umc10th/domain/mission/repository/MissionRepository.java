package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.enums.MemberMissionStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission, Long> {

	@Query("""
		select m
		from Mission m
		join fetch m.store s
		where s.region.id = :regionId
			and m.isActive = true
			and (:now is null or m.startedAt is null or m.startedAt <= :now)
			and (:now is null or m.endedAt is null or m.endedAt >= :now)
			and (:cursor is null or m.id < :cursor)
			and not exists (
				select 1
				from MemberMission mm
				where mm.member.id = :memberId
					and mm.mission = m
					and mm.status in :excludedStatuses
			)
		order by m.id desc
		""")
	List<Mission> findAvailableMissionsWithCursor(
		@Param("memberId") Long memberId,
		@Param("regionId") Long regionId,
		@Param("excludedStatuses") List<MemberMissionStatus> excludedStatuses,
		@Param("now") LocalDateTime now,
		@Param("cursor") Long cursor,
		Pageable pageable
	);
}
