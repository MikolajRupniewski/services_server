package pl.rupniewski.service_server.service.email;

import pl.rupniewski.service_server.model.EnabledUsers;

public class ConfirmationEmail extends BaseEmail {
    private static final String SUBJECT = "[Services] Verify account";
    private static final String HEADER = "Account's verification";

    private static String getEmailBody(EnabledUsers enabledUsers) {
        String href = String.format("<a href='http://localhost:8080/authenticate/enable-user?email=%s&uuid=%s'>Confirm your email</a>", enabledUsers.getEmail(), enabledUsers.getUuid());
        return "To confirm your email please follow this link:" + href;
    }
    public static void sendEmail(EnabledUsers enabledUsers) {
        sendEmail(enabledUsers.getEmail(), SUBJECT, HEADER, getEmailBody(enabledUsers));
    }
}