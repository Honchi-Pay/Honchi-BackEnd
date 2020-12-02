package honchi.api.domain.post.domain.kakaoMap;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class KakaoResponseVO {

    private Map<String, Object> meta;
    private List<KakaoVO> documents;
}
