package honchi.api.domain.user.service;

import honchi.api.domain.user.dto.JoinRequest;

public interface UserService {

    void join(JoinRequest joinRequest);
}
