package totc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import totc.entity.Member;

public interface MemberDao extends JpaRepository<Member, Long> {

}
