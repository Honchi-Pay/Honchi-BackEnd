package honchi.api.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {

    private String accessToken;

    private String refreshToken;

    private String tokenType;
}
