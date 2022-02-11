package com.mari.recipes.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @NotBlank
    @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+\\.[A-Za-z]{2,6}$", message = "Email is incorrect")
//    @Pattern(regexp = "", message = "Email is incorrect")
    @Column(name = "USERNAME", unique = true)
    private String email;

    @Size(min = 8)
    @NotBlank
    @Column(name = "PASSWORD")
    private String password;

}
