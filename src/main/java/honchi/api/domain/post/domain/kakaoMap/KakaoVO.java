package honchi.api.domain.post.domain.kakaoMap;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class KakaoVO {

    private Map<String, Object> address;
    private Map<String, Object> road_address;
}
