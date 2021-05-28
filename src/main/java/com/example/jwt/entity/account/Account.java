package com.example.jwt.entity.account;

import com.example.jwt.entity.account.authorization.AccountRole;
import com.example.jwt.entity.account.info.Gender;
import com.example.jwt.entity.account.info.Info;
import com.example.jwt.entity.account.info.Major;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ACCOUNT")
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Account {

    @Id
    @Column(name = "account_id")
    private Long id;

    /*
     * 사용자 데이터
     * */

    @Column
    private String username;

    @Column
    private String password;

    @Column(unique = true)
    private String email;

    @Column
    private Integer age;

    @Column
    private Info qualification;

    @OneToMany(mappedBy = "account",fetch = FetchType.LAZY, cascade={CascadeType.ALL})
    List<AccountRole> accountRoles = new ArrayList<>();

    public Account(String username, String password, String email, int age) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
    }

    public Account(String username, String password, String email, int age, String gender, String major, Integer grade) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
        this.qualification = new Info(Major.valueOf(major), Gender.valueOf(gender), grade);
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountRoles(List<AccountRole> accountRoles) {
        this.accountRoles = accountRoles;
        this.accountRoles.stream().forEach(accountRole -> {
            accountRole.setAccount(this);
        });
    }
}
