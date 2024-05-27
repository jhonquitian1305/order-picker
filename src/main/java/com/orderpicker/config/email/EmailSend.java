package com.orderpicker.config.email;

import com.orderpicker.user.domain.model.User;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.SendEmailRequest;
import com.resend.services.emails.model.SendEmailResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class EmailSend {

    @Value("${resend.api-key}")
    String apiKeyResend;

    private void sendEmail() throws ResendException {
        Resend resend = new Resend(apiKeyResend);
        Map<String, String> headers= new HashMap<>();
        headers.put("Authorization", "Bearer %s".formatted(apiKeyResend));
        System.out.println(headers);
        SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                .from("onboarding@resend.dev")
                .to("quitian.j2010@gmail.com")
                .subject("Hello World")
                .html("<p>Este es otro correo probando <strong>Guao</strong>!</p>")
                .build();

        SendEmailResponse data = resend.emails().send(sendEmailRequest);
    }

    public void CreatingUser(User user){
        String subject = "Registry Succesful";
    }
}
