package com.footballio.viewmodel;

import com.footballio.model.login.User;

public interface ILogin {

    void createRegistration(User user);

    void getLogindetails(String email, String pwd);

    void ForgotPassword(String email);

    void UpdatePassword(String code, String newpassword);

    void ConfirmRegisteredEmail(String code, String email);

}
