package honchi.api.domain.user.domain;

import java.io.Serializable;
import java.util.Objects;

public class StarPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer starredUserId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStarredUserId() {
        return starredUserId;
    }

    public void setStarredUserId(Integer starredUserId) {
        this.starredUserId = starredUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StarPK starPK = (StarPK) o;
        return Objects.equals(userId, starPK.userId) &&
                Objects.equals(starredUserId, starPK.starredUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, starredUserId);
    }
}
