package honchi.api.domain.buyList.domain.repository;

import honchi.api.domain.buyList.domain.BuyList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuyListRepository extends JpaRepository<BuyList, Integer> {

    List<BuyList> findByUserIdAndTimeIsNotNull(Integer userId);
}
