package com.kkimleang.authservice.service.user;

import com.kkimleang.authservice.exception.ResourceNotFoundException;
import com.kkimleang.authservice.model.*;
import com.kkimleang.authservice.repository.*;
import java.util.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

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
