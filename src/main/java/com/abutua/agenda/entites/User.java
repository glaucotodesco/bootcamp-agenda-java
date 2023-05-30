package com.abutua.agenda.entites;

public class User extends Person{
    private String password;

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
