package honchi.api.domain.user.service;

import honchi.api.domain.user.dto.ProfileResponse;
import honchi.api.domain.user.dto.SignUpRequest;

public interface UserService {

    void join(SignUpRequest signUpRequest);

    ProfileResponse getProfile(Integer user_id);
}
