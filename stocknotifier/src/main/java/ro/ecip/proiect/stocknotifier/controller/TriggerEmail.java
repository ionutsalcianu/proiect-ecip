package ro.ecip.proiect.stocknotifier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.mail.internet.MimeMessage;
import java.util.Map;

@RestController
public class TriggerEmail {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${trigger.mail.receiver}")
    private String receiver;

    @Value("${trigger.email.url}")
    private String email_openwhisk_url;

    @Value("${spring.mail.password}")
    private String password;

    @RequestMapping(path = "/email", method = RequestMethod.POST)
    private String sendEmail(@RequestBody Map<String, Object> content) {
        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail);

        try {

            if (!receiver.isEmpty() && !sender.isEmpty() && !password.isEmpty()) {
                if (email_openwhisk_url.isEmpty()) {
                    helper.setTo(receiver);
                    helper.setFrom(sender);
                    helper.setReplyTo(sender);
                    helper.setSubject("Produs nou aparut in stock");
                    helper.setText("Un nou produs a fost adaugat in stock." +
                            " Numele acestuia este " + content.get("productName") + " " +
                            "si are pretul de " + content.get("price")+" lei");
                    mailSender.send(mail);
                    return "{\"message\": \"OK sent email via client\"}";
                }
                else {
                    RestTemplate rest = new RestTemplate();
                    HttpHeaders headers = new HttpHeaders();
                    String server = email_openwhisk_url;
                    headers.add("Content-Type", "application/json");
                    headers.add("Accept", "*/*");
                    String json = "{\"text\": \"" + "Un nou produs a fost adaugat in stock." +
                            " Numele acestuia este " + content.get("productName") + " " +
                            "si are pretul de " + content.get("price")+" lei\",\"sender\": \"" + sender + "\",\"receiver\": \"" + receiver + "\",\"password\": \"" + password + "\",\"subject\": \"Stock Notification\"}";

                    HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
                    ResponseEntity<String> responseEntity = rest.exchange(server, HttpMethod.POST, requestEntity, String.class);
                    return "{\"message\": \"OK sent email via openwhisk\"}";
                }
            } else {
                return "{\"message\": \"No email configuration specified. No email sent.\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"message\": \"Error in sending email\"}";
        }
    }
}
