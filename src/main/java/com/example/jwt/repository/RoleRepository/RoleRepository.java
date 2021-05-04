package com.example.jwt.repository.RoleRepository;

import com.example.jwt.entity.account.Role;
import com.example.jwt.repository.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CommonRepository<Role, Long>, RoleRepositoryCustom {
    Role findRoleByRoleName(String name);
}
