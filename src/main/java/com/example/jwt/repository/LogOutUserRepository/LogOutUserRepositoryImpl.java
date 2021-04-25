package com.example.jwt.repository.LogOutUserRepository;
import com.example.jwt.entity.account.Account;
import com.example.jwt.entity.account.LogOutUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class LogOutUserRepositoryImpl implements LogOutUserRepositoryCustom {

    private final EntityManager em;

    @Override
    public LogOutUser findByUsername(String username) {
        return em.createQuery("" +
                "select u " +
                "from LogOutUser u " +
                "where  u.username = :name", LogOutUser.class)
                .setParameter("name", username)
                .getSingleResult();
    }

}
