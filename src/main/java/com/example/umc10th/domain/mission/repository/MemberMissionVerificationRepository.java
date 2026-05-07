package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.MemberMissionVerification;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberMissionVerificationRepository extends JpaRepository<MemberMissionVerification, Long> {

	Optional<MemberMissionVerification> findTopByMemberMissionIdOrderByRequestedAtDesc(Long memberMissionId);
}
