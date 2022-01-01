package com.lim.core.domain.entity;

import com.lim.core.util.Encryptor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String name;
    private String email;
    private String password;
    private LocalDateTime birthday;

    public User(
            String name,
            String email,
            String password,
            LocalDateTime birthday
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
    }


    public boolean isMatch(Encryptor encryptor, String password) {
        return encryptor.isMatch(password, this.password);
    }
}
