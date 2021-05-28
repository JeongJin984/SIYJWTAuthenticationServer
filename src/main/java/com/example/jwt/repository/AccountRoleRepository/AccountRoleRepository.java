package com.example.jwt.repository.AccountRoleRepository;

import com.example.jwt.entity.account.authorization.AccountRole;
import com.example.jwt.repository.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRoleRepository extends CommonRepository<AccountRole, Long>, AccountRoleRepositoryCustom {
}
