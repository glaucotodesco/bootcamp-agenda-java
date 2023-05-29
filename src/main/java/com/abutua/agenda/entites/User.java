package com.abutua.agenda.entites;

public class User extends Person{
    private String password;

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
