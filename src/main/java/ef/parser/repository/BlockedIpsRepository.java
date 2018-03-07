package ef.parser.repository;

import ef.parser.models.BlockedIps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockedIpsRepository extends JpaRepository<BlockedIps, Long> {
}
