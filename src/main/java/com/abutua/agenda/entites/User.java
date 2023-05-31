package com.abutua.agenda.entites;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Tbl_User")
public class User extends Person{

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "TBL_USER_ROLE", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    

    public User(Long id, String name, String phone, String email, String comments, String password) {
        super(name, phone, email, comments);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [password=" + password + "]";
    }
   
}
