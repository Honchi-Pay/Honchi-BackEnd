package honchi.api.domain.user.service;

import honchi.api.domain.user.dto.SignUpRequest;

public interface UserService {

    void join(SignUpRequest signUpRequest);
}
