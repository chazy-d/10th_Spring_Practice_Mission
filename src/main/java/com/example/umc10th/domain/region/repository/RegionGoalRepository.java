package com.example.umc10th.domain.region.repository;

import com.example.umc10th.domain.region.entity.RegionGoal;
import java.time.YearMonth;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionGoalRepository extends JpaRepository<RegionGoal, Long> {

	Optional<RegionGoal> findByRegionIdAndTargetMonth(Long regionId, YearMonth targetMonth);
}
