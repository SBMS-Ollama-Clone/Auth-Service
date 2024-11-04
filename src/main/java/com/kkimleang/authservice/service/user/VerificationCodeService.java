package com.kkimleang.authservice.service.user;

import com.kkimleang.authservice.exception.ResourceNotFoundException;
import com.kkimleang.authservice.model.User;
import com.kkimleang.authservice.model.VerificationCode;
import com.kkimleang.authservice.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationCodeService {
    private final VerificationCodeRepository verificationCodeRepository;

    public VerificationCode save(User user, String code) {
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setCode(code);
        verificationCode.setUser(user);
        return verificationCodeRepository.save(verificationCode);
    }

    public User verifyCodeAndReturnUser(String code) {
        Optional<VerificationCode> verificationCode = verificationCodeRepository.findByCode(code);
        if (verificationCode.isEmpty()) {
            throw new ResourceNotFoundException("Verification", "code", code);
        }
        VerificationCode codeEntity = verificationCode.get();
        codeEntity.setIsUsed(true);
        verificationCodeRepository.save(codeEntity);
        return codeEntity.getUser();
    }
}
