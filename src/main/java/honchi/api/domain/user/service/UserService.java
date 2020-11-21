package honchi.api.domain.user.service;

import honchi.api.domain.user.dto.*;

public interface UserService {

    void alone(String email);
    void join(SignUpRequest signUpRequest);
    ProfileResponse getProfile(String nickName);
    void findPassword(FindPasswordRequest findPasswordRequest);
    void changePassword(ChangePasswordRequest changePasswordRequest);
    void updateProfile(ProfileUpdateRequest profileUpdateRequest);
    void star(StarRequest starRequest);
    void deleteUser(String nickName);
}
