package com.expensePro.api.EtUserAuthService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shad Ahmad
 * @since 30-03-2024
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyTokenResponse {

    private String userId;
}
