package totc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import totc.entity.Church;

public interface ChurchDao extends JpaRepository<Church, Long> {

}
