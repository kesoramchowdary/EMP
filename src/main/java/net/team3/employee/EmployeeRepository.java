package net.team3.employee;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
 
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    public Employee findByEmail(String email); 
    public List<Employee> findAll();
    public Employee findAllById(Long empid);
//	public boolean checkIfValidOldPassword(Employee employee, String oldPassword);
//	public void changePassword(Employee employee, String password);
}