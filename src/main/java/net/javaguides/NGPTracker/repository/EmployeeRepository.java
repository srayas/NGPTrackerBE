package net.javaguides.NGPTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.NGPTracker.model.Employee;

@Repository
public interface EmployeeRepository  extends JpaRepository<Employee, Long>{

	public Employee findByEmailId(String email);
	

	
}
