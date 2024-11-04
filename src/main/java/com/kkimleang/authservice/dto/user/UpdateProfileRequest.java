package com.kkimleang.authservice.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class UpdateProfileRequest {
    private UUID id;
    @JsonProperty("profile_url")
    private String profileURL;
}
