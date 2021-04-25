package com.example.jwt.entity.accountPost;

import com.example.jwt.entity.account.Account;
import com.example.jwt.entity.account.AccountCode;
import com.example.jwt.entity.post.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ACCOUNT_POST")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountPost {

    @Id
    @GeneratedValue
    @Column(name = "account_post_id")
    private Long id;

    @ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(targetEntity = Post.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "participant")
    private String username;

    @Enumerated(EnumType.STRING)
    private AccountCode code;

    public AccountPost(Post post, Account participant){
        this.account = participant;
        this.post = post;
        account.getAccountPosts().add(this);
        post.getAccountPosts().add(this);
    }

}
