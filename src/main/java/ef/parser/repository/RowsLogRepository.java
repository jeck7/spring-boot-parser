package ef.parser.repository;

import ef.parser.models.RowsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RowsLogRepository extends JpaRepository<RowsLog, Long> {

    @Query(value = "SELECT logs.ip as ip, COUNT(logs.date_created) as requests FROM rows_log logs WHERE logs.date_created BETWEEN ?1 AND ?2 GROUP BY logs.ip", nativeQuery = true)
    public List<Object[]> calculateBlockedIpsByPeriod(Date from, Date to);

}



