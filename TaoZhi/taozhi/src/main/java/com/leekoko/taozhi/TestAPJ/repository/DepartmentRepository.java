package com.leekoko.taozhi.TestAPJ.repository;

import com.leekoko.taozhi.TestAPJ.pojo.Deparment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Deparment,Long>{
}
