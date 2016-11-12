package com.survey.util;

import com.sun.org.apache.xpath.internal.operations.String;
import com.tcs.survey.platform.model.entity.CreateAdminUser;


public class AdminUserBuilder {


    private String username;
    private String firstname;
    private String lastname;
    private String emailid;
    private String phone;
    private String status;
    private String userrole;
    private String login_attempts;
    private String password;
    private int loginflag;

    public AdminUserBuilder() {
    }

    public AdminUserBuilder(String username, String firstname, String lastname, String emailid, String phone, String status,
                            String userrole, String login_attempts, String password, int loginflag) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailid = emailid;
        this.phone = phone;
        this.status = status;
        this.userrole = userrole;
        this.login_attempts = login_attempts;
        this.password = this.password;
        this.loginflag = loginflag;
    }


    public int getLoginflag() {
        return loginflag;
    }

    public void setLoginflag(int loginflag) {
        this.loginflag = loginflag;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }

    public String getLogin_attempts() {
        return login_attempts;
    }

    public void setLogin_attempts(String login_attempts) {
        this.login_attempts = login_attempts;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CreateAdminUser defaultValues() {
        CreateAdminUser createAdminUser = new CreateAdminUser();
        createAdminUser.setUsername("Test");
        createAdminUser.setFirstname("First Name");
        createAdminUser.setLastname("Last Name");
        createAdminUser.setUserrole("Coordinator");
        createAdminUser.setPassword("Test");
        return createAdminUser;
    }

}
