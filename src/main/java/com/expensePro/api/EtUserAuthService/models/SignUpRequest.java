package com.expensePro.api.EtUserAuthService.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shad Ahmad
 * @since 30-03-2024
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignUpRequest {

    private String name;
    private String email;
    private String password;
}

