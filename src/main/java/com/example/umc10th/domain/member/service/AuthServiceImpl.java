package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.category.entity.FoodCategory;
import com.example.umc10th.domain.category.entity.mapping.MemberFoodCategory;
import com.example.umc10th.domain.category.exception.CategoryException;
import com.example.umc10th.domain.category.exception.code.CategoryErrorCode;
import com.example.umc10th.domain.category.repository.FoodCategoryRepository;
import com.example.umc10th.domain.category.repository.MemberFoodCategoryRepository;
import com.example.umc10th.domain.member.dto.SignupRequestDto;
import com.example.umc10th.domain.member.dto.SignupResponseDto;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.entity.MemberAddress;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberAddressRepository;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.region.entity.Region;
import com.example.umc10th.domain.region.exception.RegionException;
import com.example.umc10th.domain.region.exception.code.RegionErrorCode;
import com.example.umc10th.domain.region.repository.RegionRepository;
import com.example.umc10th.domain.term.entity.MemberTermAgreement;
import com.example.umc10th.domain.term.entity.Term;
import com.example.umc10th.domain.term.exception.TermException;
import com.example.umc10th.domain.term.exception.code.TermErrorCode;
import com.example.umc10th.domain.term.repository.MemberTermAgreementRepository;
import com.example.umc10th.domain.term.repository.TermRepository;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

	private final MemberRepository memberRepository;
	private final RegionRepository regionRepository;
	private final MemberAddressRepository memberAddressRepository;
	private final TermRepository termRepository;
	private final MemberTermAgreementRepository memberTermAgreementRepository;
	private final FoodCategoryRepository foodCategoryRepository;
	private final MemberFoodCategoryRepository memberFoodCategoryRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthServiceImpl(
		MemberRepository memberRepository,
		RegionRepository regionRepository,
		MemberAddressRepository memberAddressRepository,
		TermRepository termRepository,
		MemberTermAgreementRepository memberTermAgreementRepository,
		FoodCategoryRepository foodCategoryRepository,
		MemberFoodCategoryRepository memberFoodCategoryRepository,
		PasswordEncoder passwordEncoder
	) {
		this.memberRepository = memberRepository;
		this.regionRepository = regionRepository;
		this.memberAddressRepository = memberAddressRepository;
		this.termRepository = termRepository;
		this.memberTermAgreementRepository = memberTermAgreementRepository;
		this.foodCategoryRepository = foodCategoryRepository;
		this.memberFoodCategoryRepository = memberFoodCategoryRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public SignupResponseDto signup(SignupRequestDto request) {
		if (memberRepository.existsByEmail(request.email())) {
			throw new MemberException(MemberErrorCode.EMAIL_ALREADY_EXISTS);
		}

		Region region = regionRepository.findById(request.regionId())
			.orElseThrow(() -> new RegionException(RegionErrorCode.REGION_NOT_FOUND));
		Map<Long, Boolean> termAgreementMap = toTermAgreementMap(request.terms());
		List<Term> terms = findTermsAndValidateRequired(termAgreementMap);
		List<FoodCategory> foodCategories = findFoodCategories(request.favoriteFoodCategoryIds());

		String passwordHash = passwordEncoder.encode(request.password());
		Member member = Member.createLocal(
			request.name(),
			request.nickname(),
			request.gender(),
			request.birthDate(),
			request.email(),
			passwordHash,
			request.phoneNumber()
		);

		Member savedMember = memberRepository.save(member);
		memberAddressRepository.save(MemberAddress.create(
			savedMember,
			region,
			request.address(),
			request.addressDetail()
		));
		memberTermAgreementRepository.saveAll(terms.stream()
			.map(term -> MemberTermAgreement.create(savedMember, term, termAgreementMap.get(term.getId())))
			.toList());
		memberFoodCategoryRepository.saveAll(foodCategories.stream()
			.map(foodCategory -> MemberFoodCategory.create(savedMember, foodCategory))
			.toList());

		return new SignupResponseDto(
			savedMember.getId(),
			savedMember.getNickname(),
			request.regionId(),
			savedMember.getEmail(),
			savedMember.getPhoneNumber(),
			savedMember.getCreatedAt()
		);
	}

	private Map<Long, Boolean> toTermAgreementMap(List<SignupRequestDto.TermAgreementRequest> terms) {
		Map<Long, Boolean> termAgreementMap = new LinkedHashMap<>();
		for (SignupRequestDto.TermAgreementRequest term : terms) {
			termAgreementMap.putIfAbsent(term.termId(), term.isAgree());
		}
		return termAgreementMap;
	}

	private List<Term> findTermsAndValidateRequired(Map<Long, Boolean> termAgreementMap) {
		List<Term> allTerms = termRepository.findAll();
		Map<Long, Term> termById = allTerms.stream()
			.collect(Collectors.toMap(Term::getId, Function.identity()));

		for (Long requestedTermId : termAgreementMap.keySet()) {
			if (!termById.containsKey(requestedTermId)) {
				throw new TermException(TermErrorCode.TERM_NOT_FOUND);
			}
		}

		for (Term term : allTerms) {
			if (Boolean.TRUE.equals(term.getIsRequired())
				&& !Boolean.TRUE.equals(termAgreementMap.get(term.getId()))) {
				throw new MemberException(MemberErrorCode.REQUIRED_TERM_NOT_AGREED);
			}
		}

		return termAgreementMap.keySet().stream()
			.map(termById::get)
			.toList();
	}

	private List<FoodCategory> findFoodCategories(List<Long> favoriteFoodCategoryIds) {
		List<Long> distinctFoodCategoryIds = favoriteFoodCategoryIds.stream()
			.distinct()
			.toList();
		List<FoodCategory> foodCategories = foodCategoryRepository.findAllById(distinctFoodCategoryIds);

		if (foodCategories.size() != distinctFoodCategoryIds.size()) {
			throw new CategoryException(CategoryErrorCode.FOOD_CATEGORY_NOT_FOUND);
		}

		Map<Long, FoodCategory> foodCategoryById = foodCategories.stream()
			.collect(Collectors.toMap(FoodCategory::getId, Function.identity()));

		return distinctFoodCategoryIds.stream()
			.map(foodCategoryById::get)
			.toList();
	}
}
