package com.incubator.edupayroll.service.token;

import java.util.UUID;

public record UserTokenPayload(UUID userId, String email) {}
