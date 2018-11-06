package com.leekoko.taozhi.TestAPJ.repository;

import com.leekoko.taozhi.TestAPJ.pojo.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
}
