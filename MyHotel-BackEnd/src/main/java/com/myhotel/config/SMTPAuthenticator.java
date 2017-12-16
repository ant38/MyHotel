package com.myhotel.config;

import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends javax.mail.Authenticator {
    public PasswordAuthentication getPasswordAuthentication() {
       return new PasswordAuthentication(MailConfig.USERNAME, MailConfig.PASSWORD);
    }
}