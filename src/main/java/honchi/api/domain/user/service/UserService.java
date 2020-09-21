package honchi.api.domain.user.service;

import honchi.api.domain.user.dto.ChargePasswordRequest;
import honchi.api.domain.user.dto.ProfileResponse;
import honchi.api.domain.user.dto.SignUpRequest;

public interface UserService {

    void join(SignUpRequest signUpRequest);
    void chargePassword(ChargePasswordRequest chargePasswordRequest);
    ProfileResponse getProfile(Integer user_id);
}
