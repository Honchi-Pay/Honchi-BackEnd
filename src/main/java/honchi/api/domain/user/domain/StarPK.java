package honchi.api.domain.user.domain;

import java.io.Serializable;
import java.util.Objects;

public class StarPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer targetId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StarPK starPK = (StarPK) o;
        return Objects.equals(userId, starPK.userId) &&
                Objects.equals(targetId, starPK.targetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, targetId);
    }
}