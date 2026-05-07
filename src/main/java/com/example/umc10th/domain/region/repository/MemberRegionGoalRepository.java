package com.example.umc10th.domain.region.repository;

import com.example.umc10th.domain.region.entity.MemberRegionGoal;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRegionGoalRepository extends JpaRepository<MemberRegionGoal, Long> {

	Optional<MemberRegionGoal> findByMemberIdAndRegionGoalId(Long memberId, Long regionGoalId);
}
