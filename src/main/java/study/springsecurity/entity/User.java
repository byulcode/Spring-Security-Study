package study.springsecurity.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import study.springsecurity.entity.enums.EncryptionAlgorithm;

import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private EncryptionAlgorithm algorithm;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Authority> authorities;
}
