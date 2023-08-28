package totc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import totc.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
