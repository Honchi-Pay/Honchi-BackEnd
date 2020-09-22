package honchi.api.domain.user.service;

import honchi.api.domain.user.dto.ChargePasswordRequest;
import honchi.api.domain.user.dto.ProfileResponse;
import honchi.api.domain.user.dto.SignUpRequest;
import honchi.api.domain.user.dto.StarRequest;

public interface UserService {

    void join(SignUpRequest signUpRequest);
    void chargePassword(ChargePasswordRequest chargePasswordRequest);
    ProfileResponse getProfile(Integer user_id);
    void star(StarRequest starRequest);
}
