package honchi.api.domain.user.service;

import honchi.api.domain.user.dto.*;

public interface UserService {

    void alone(String email);
    void join(SignUpRequest signUpRequest);
    void sendEmail(String email);
    void verifyEmail(VerifyCodeRequest verifyCodeRequest);
    void findPassword(FindPasswordRequest findPasswordRequest);
    void changePassword(ChangePasswordRequest changePasswordRequest);
    ProfileResponse getProfile(String nickName);
    void updateProfile(ProfileUpdateRequest profileUpdateRequest);
    void star(StarRequest starRequest);
}
