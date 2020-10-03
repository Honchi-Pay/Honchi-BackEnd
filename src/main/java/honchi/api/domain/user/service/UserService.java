package honchi.api.domain.user.service;

import honchi.api.domain.user.dto.*;

public interface UserService {

    void join(SignUpRequest signUpRequest);
    void sendEmail(String email);
    void verifyEmail(VerifyCodeRequest verifyCodeRequest);
    void findPassword(FindPasswordRequest findPasswordRequest);
    void chargePassword(ChargePasswordRequest chargePasswordRequest);
    ProfileResponse getProfile(Integer user_id);
    void updateProfile(ProfileUpdateRequest profileUpdateRequest);
    void star(StarRequest starRequest);
}
