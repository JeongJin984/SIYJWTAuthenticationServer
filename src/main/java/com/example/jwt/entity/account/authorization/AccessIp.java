package com.example.jwt.entity.account.authorization;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ACCESS_IP")
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AccessIp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ip_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;
}
