package com.project.thienphan.timesheet.Model;

public class UserAccount {
    private String Account;
    private String Password;

    public UserAccount() {
    }

    public UserAccount(String account, String password) {
        Account = account;
        Password = password;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
