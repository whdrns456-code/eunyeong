package com.eunyeong.care_system.service;

import com.eunyeong.care_system.model.*;
import com.eunyeong.care_system.repository.SeniorRelationsRepository;
import com.eunyeong.care_system.repository.SeniorRepository;
import com.eunyeong.care_system.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeniorService {

    private final SeniorRepository seniorRepository;
    private final UserRepository userRepository;
    private final SeniorRelationsRepository relationsRepository;

    // 1. 언더바(_)를 추가하여 리포지토리 메서드명과 일치시켰습니다.
    public boolean hasRegisteredSenior(String userNo) {
        return seniorRepository.existsByRegisterUser_UserNo(userNo);
    }

    @Transactional
    public void registerNewSenior(SeniorRequestDTO dto, User guardian) {
        String birth = dto.getSeniorBirth().replace("-", "");

        // 1. 어르신 존재 확인
        Seniors senior = seniorRepository.findBySeniorNameAndBirthDate(dto.getSeniorName(), birth)
                .orElseThrow(() -> new RuntimeException("입소자 명단에 일치하는 어르신이 없습니다."));

        if (senior.getRegisterUser() != null) {
            throw new RuntimeException("이미 등록이 완료된 어르신입니다.");
        }

        // 2. 보호자(User) 테이블의 전화번호 업데이트
        // 회원가입 시 비어있던 번호를 이때 채워줍니다.
        if (guardian.getUserPhone() == null || guardian.getUserPhone().isEmpty()) {
            guardian.setUserPhone(dto.getSeniorPhone());
            userRepository.save(guardian);
        }

        // 3. 어르신 테이블에 보호자 연결
        senior.setRegisterUser(guardian);
        seniorRepository.save(senior);

        // 4. 관계 테이블에 연락처와 함께 저장
        SeniorRelations relation = SeniorRelations.builder()
                .user(guardian)
                .senior(senior)
                .relationType(dto.getRelationship())
                .contactPhone(dto.getSeniorPhone()) // 비상 연락처로 저장
                .build();

        relationsRepository.save(relation);
    }

    public SeniorDTO getMyPageData(String userNo) {
        User user = userRepository.findByUserId(userNo)
                .orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));

        // 1. 어르신 목록 가져오기
        List<Seniors> seniorList = seniorRepository.findByRegisterUser_UserNo(user.getUserNo());

        // 2. 어르신 정보를 리스트로 변환
        List<SeniorDTO.SeniorInfo> seniorInfos = seniorList.stream()
                .map(s -> SeniorDTO.SeniorInfo.builder()
                        .seniorName(s.getSeniorName())
                        .relationship(relationsRepository.findByUser_UserNoAndSenior_SeniorNo(user.getUserNo(), s.getSeniorNo())
                                .map(SeniorRelations::getRelationType).orElse("-"))
                        .build())
                .collect(Collectors.toList());

        return SeniorDTO.builder()
                .userName(user.getUserName())
                .userEmail(user.getUserEmail())
                .userPhone(user.getUserPhone())
                .createdAt(user.getCreatedAt())
                .seniors(seniorInfos) // 목록 통째로 전달!
                .build();
    }
}