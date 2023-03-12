package hieu.nv.bytela.model.entity.shortlink;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortLinkRepository extends JpaRepository<ShortLink,String> {

}
