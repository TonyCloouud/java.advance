package advance.mapper;

import advance.model.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @autho baifugui
 * @create 2017 08 31 17:49
 */
public interface EmployeesMapper {

    List<Employee> selectByMinSalary(@Param(value="min_salary") Integer min_salary);
}
