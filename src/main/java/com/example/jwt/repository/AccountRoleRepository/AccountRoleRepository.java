package com.example.jwt.repository.AccountRoleRepository;

import com.example.jwt.entity.account.AccountRole;
import com.example.jwt.repository.CommonRepository;
import com.example.jwt.repository.RoleRepository.RoleRepositoryCustom;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRoleRepository extends CommonRepository<AccountRole, Long>, AccountRoleRepositoryCustom {
}
