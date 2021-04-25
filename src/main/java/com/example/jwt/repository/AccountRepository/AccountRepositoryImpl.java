package com.example.jwt.repository.AccountRepository;
import com.example.jwt.entity.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom {

    private final EntityManager em;

    @Override
    public Account findByUsername(String username) {
        Account account = em.createQuery("" +
                "select a " +
                "from Account a " +
                "join fetch a.accountRoles " +
                "where  a.username = :name", Account.class)
                .setParameter("name", username)
                .getSingleResult();
        return account;
    }

}
