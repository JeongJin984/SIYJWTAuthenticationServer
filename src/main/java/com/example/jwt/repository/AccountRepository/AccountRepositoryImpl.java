package com.example.jwt.repository.AccountRepository;
import com.example.jwt.entity.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.UUID;

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

    @Override
    public void updateAccount(Account account) {
        Long account_id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        em.createQuery("" +
                "update Account a set " +
                "a.username = :username, " +
                "a.age = :age, " +
                "a.email = :email, " +
                "a.password = :password " +
                "where a.id = :id")
                .setParameter("username", account.getUsername())
                .setParameter("age", account.getAge())
                .setParameter("email", account.getEmail())
                .setParameter("password", account.getPassword())
                .getFirstResult();
    }
}
