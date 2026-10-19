package br.edu.fatecpg.reviu.services;

import br.edu.fatecpg.reviu.domain.user.User;
import br.edu.fatecpg.reviu.repositories.VerificationCodesRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String fromAddress = "thiiago.allmeida44@gmail.com";
    private final VerificationCodesRepository verificationCodesRepository;

    public EmailService(JavaMailSender mailSender, VerificationCodesRepository verificationCodesRepository) {
        this.mailSender = mailSender;
        this.verificationCodesRepository = verificationCodesRepository;
    }

    @Async
    public void sendVerificationEmail(User user) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromAddress);
            helper.setTo(user.getEmail());
            helper.setSubject("Verifique seu e-mail - Reviu");

            String htmlContent = buildHtml(user.getName(), verificationCodesRepository.findByUser(user).get().getCode());
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendForgotPasswordEmail(User user) {
        try {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromAddress);
            helper.setTo(user.getEmail());
            helper.setSubject("Verifique seu e-mail - Reviu");

            String htmlContent = buildHtml(user.getName(), verificationCodesRepository.findByUser(user).get().getCode());
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String buildHtml(String name, String code) {
        int year = Year.now().getValue();

        String background = "#07243b";
        String cardBg = "#083a54";
        String accent = "#ff9d2b";
        String soft = "#e6f0f6";
        String codeBg = "#073b5a";

        return String.format("""
        <!doctype html>
        <html>
        <head>
          <meta charset="utf-8">
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <title>Verificação - Reviu</title>
        </head>
        <body style="margin:0; padding:20px; background-color: %s; font-family: 'Poppins', Arial, sans-serif;">
          <table width="100%%" cellpadding="0" cellspacing="0" role="presentation">
            <tr>
              <td align="center">
                <table width="600" cellpadding="0" cellspacing="0" role="presentation" style="max-width:600px;">
                  <tr>
                    <td style="padding:30px 15px;">
                      <table width="100%%" cellpadding="0" cellspacing="0" role="presentation"
                          style="background: linear-gradient(180deg, %s 0%%, %s 100%%); border-radius:12px; padding:28px; box-shadow: 0 6px 18px rgba(0,0,0,0.35);">
                        
                        <tr>
                          <td style="color:%s; text-align:left; padding:0 12px;">

                            <h2 style="margin:0 0 8px 0; font-size:26px; color:#ffffff; font-weight:700;">Verificação de E-mail</h2>
                            
                            <p style="margin:0 0 18px 0; color:%s; font-size:14px; line-height:1.5;">
                              Olá <strong>%s</strong>, obrigado por se cadastrar na <strong>Reviu</strong>! Confirme seu e-mail usando o código abaixo:
                            </p>

                            <div style="text-align:center; margin:18px 0;">
                              <div style="display:inline-block; padding:16px 22px; border-radius:8px; background:%s; color:#fff; font-size:22px; font-weight:700; letter-spacing:4px;">
                                %s
                              </div>
                            </div>

                            <p style="margin:18px 0 0 0; color:%s; font-size:13px;">
                              Este código expira em <strong>1 hora</strong>.
                            </p>

                            <p style="margin:26px 0 0 0; color:%s; font-size:13px; line-height:1.4;">
                              Abraços,<br/>
                              Equipe Reviu
                            </p>

                            <hr style="border:none; border-top:1px solid rgba(255,255,255,0.06); margin:24px 0;" />

                            <p style="font-size:12px; color:rgba(255,255,255,0.6); text-align:center;">
                                Reviu © %d
                            </p>

                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>

                </table>
              </td>
            </tr>
          </table>
        </body>
        </html>
        """,
                background,
                cardBg,
                "#0a3044",
                soft,
                soft,
                name,
                codeBg,
                code,
                soft,
                soft,
                year
        );
    }

}
