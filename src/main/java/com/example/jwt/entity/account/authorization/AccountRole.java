package com.example.jwt.entity.account.authorization;

import com.example.jwt.entity.account.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ACCOUNT_ROLE")
@Data
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRole {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account account;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public AccountRole(Account account, Role role) {
        this.account = account;
        this.role = role;
    }
}

