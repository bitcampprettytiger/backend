package com.example.bitcamptiger.jwt.jwtdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtDto {

    private String accessToken;

    private String refreshToken;
}
