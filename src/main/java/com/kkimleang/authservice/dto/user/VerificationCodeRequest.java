package com.kkimleang.authservice.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class VerificationCodeRequest {
    private UUID userId;
    private String code;
}
