package pl.rupniewski.service_server.service.email;

import pl.rupniewski.service_server.model.Users;

public class ResetPasswordEmail extends BaseEmail {
    private static final String SUBJECT = "[Services] Reset password";
    private static final String HEADER = "Account's new password";

    private static String getEmailBody(String password) {
        return String.format("Your new password is: <b>%s</b>", password);
    }

    public static void sendEmail(Users user, String newPassword) {
        sendEmail(user.getEmail(), SUBJECT, HEADER, getEmailBody(newPassword));
    }
}
