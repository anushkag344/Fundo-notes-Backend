package com.fundoonotes.fundoo_notes.service.impl;

import com.fundoonotes.fundoo_notes.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private static final String APP_URL = "http://localhost:4200";

    // -------------------------------------------------------------
    // BASE HTML TEMPLATE GENERATOR
    // -------------------------------------------------------------
    private String buildHtmlEmail(String subtitle, String contentBody) {
        return "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Fundoo Notes</title>"
                + "</head>"
                + "<body style=\"margin:0; padding:20px; background-color:#f4f6f8; font-family:'Segoe UI', Roboto, Helvetica, Arial, sans-serif;\">"
                + "  <div style=\"max-width:560px; margin:0 auto; background-color:#ffffff; border-radius:12px; overflow:hidden; box-shadow:0 4px 16px rgba(0,0,0,0.08); border:1px solid #e0e0e0;\">"
                
                // Header Banner
                + "    <div style=\"background: linear-gradient(135deg, #f4b400 0%, #fbbc04 100%); padding:28px 24px; text-align:center;\">"
                + "      <h1 style=\"margin:0; color:#202124; font-size:26px; font-weight:800; letter-spacing:0.5px; font-family:'Segoe UI', Roboto, sans-serif;\">Fundoo Notes</h1>"
                + "      <p style=\"margin:6px 0 0 0; color:#3c4043; font-size:14px; font-weight:600;\">" + subtitle + "</p>"
                + "    </div>"
                
                // Body Content
                + "    <div style=\"padding:32px 28px; color:#202124; line-height:1.6;\">"
                +        contentBody
                + "    </div>"
                
                // Footer
                + "    <div style=\"background-color:#fafafa; border-top:1px solid #eeeeee; padding:20px 24px; text-align:center; font-size:12px; color:#70757a;\">"
                + "      <p style=\"margin:0 0 4px 0; font-weight:600;\">© 2026 Fundoo Notes. All rights reserved.</p>"
                + "      <p style=\"margin:0;\">Organize your thoughts, wherever you are.</p>"
                + "    </div>"
                
                + "  </div>"
                + "</body>"
                + "</html>";
    }

    @Override
    @Async("taskExecutor")
    public void sendVerificationEmail(String toEmail, String token) {
        String link = APP_URL + "/verify?token=" + token;
        String content = "<h2 style=\"margin-top:0; color:#202124; font-size:20px;\">Verify Your Account</h2>"
                + "<p>Hello,</p>"
                + "<p>Thank you for creating an account on <strong>Fundoo Notes</strong>! Please click the button below to verify your email address:</p>"
                + "<div style=\"text-align:center; margin:28px 0;\">"
                + "  <a href=\"" + link + "\" style=\"background-color:#f4b400; color:#202124; font-weight:bold; text-decoration:none; padding:14px 32px; border-radius:8px; display:inline-block; font-size:15px; box-shadow:0 2px 6px rgba(0,0,0,0.12);\">Verify Email Address</a>"
                + "</div>"
                + "<p style=\"font-size:13px; color:#5f6368;\">This link will expire in 24 hours.</p>";

        sendHtmlEmail(toEmail, "Verify Your Fundoo Notes Account", buildHtmlEmail("Account Verification", content));
    }

    @Override
    @Async("taskExecutor")
    public void sendPasswordResetEmail(String toEmail, String token) {
        String link = APP_URL + "/reset-password?token=" + token;
        String htmlBody = "<!DOCTYPE html>"
                + "<html>"
                + "<head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"></head>"
                + "<body style=\"margin:0; padding:40px 16px; background-color:#ffffff; font-family:'Roboto', 'Google Sans', 'Segoe UI', Arial, sans-serif;\">"
                + "  <div style=\"max-width:480px; margin:0 auto;\">"
                + "    <div style=\"background-color:#ffffff; border:1px solid #dadce0; border-radius:8px; padding:40px 32px 32px 32px;\">"
                + "      <div style=\"margin-bottom:24px; text-align:center;\">"
                + "        <svg width=\"40\" height=\"48\" viewBox=\"0 0 40 48\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\" style=\"display:inline-block;\">"
                + "          <path d=\"M0 4C0 1.79086 1.79086 0 4 0H28L40 12V44C40 46.2091 38.2091 48 36 48H4C1.79086 48 0 46.2091 0 44V4Z\" fill=\"#FBBC04\"/>"
                + "          <path d=\"M28 0L40 12H30C28.8954 12 28 11.1046 28 10V0Z\" fill=\"#F4B400\"/>"
                + "          <path fill-rule=\"evenodd\" clip-rule=\"evenodd\" d=\"M20 14C16.134 14 13 17.134 13 21C13 23.506 14.321 25.703 16.31 26.934C16.976 27.347 17.375 28.087 17.375 28.867V29.75C17.375 30.716 18.159 31.5 19.125 31.5H20.875C21.841 31.5 22.625 30.716 22.625 29.75V28.867C22.625 28.087 23.024 27.347 23.69 26.934C25.679 25.703 27 23.506 27 21C27 17.134 23.866 14 20 14ZM17.375 33.25C17.375 32.767 17.767 32.375 18.25 32.375H21.75C22.233 32.375 22.625 32.767 22.625 33.25V33.688C22.625 34.413 22.038 35 21.313 35H18.688C17.963 35 17.375 34.413 17.375 33.688V33.25Z\" fill=\"white\"/>"
                + "        </svg>"
                + "      </div>"
                + "      <h2 style=\"margin:0 0 24px 0; color:#202124; font-size:22px; font-weight:600; text-align:center;\">Reset Your Password</h2>"
                + "      <p style=\"margin:0 0 16px 0; color:#3c4043; font-size:14px; line-height:1.5;\">Hello,</p>"
                + "      <p style=\"margin:0 0 16px 0; color:#3c4043; font-size:14px; line-height:1.5;\">We received a request to reset the password for your <strong>Fundoo Notes</strong> account.</p>"
                + "      <p style=\"margin:0 0 24px 0; color:#3c4043; font-size:14px; line-height:1.5;\">Click the button below to choose a new password. This link is valid for 24 hours.</p>"
                + "      <div style=\"text-align:center; margin:28px 0;\">"
                + "        <a href=\"" + link + "\" style=\"background-color:#fbbc04; color:#202124; font-weight:600; text-decoration:none; padding:12px 28px; border-radius:4px; display:inline-block; font-size:14px; letter-spacing:0.2px;\">Reset Password</a>"
                + "      </div>"
                + "      <div style=\"border-top:1px solid #dadce0; margin-top:28px; padding-top:20px;\">"
                + "        <p style=\"margin:0; color:#5f6368; font-size:13px; line-height:1.5;\">If you didn't request a password reset, you can safely ignore this email. Your password will remain unchanged.</p>"
                + "      </div>"
                + "    </div>"
                + "    <p style=\"margin:20px 0 0 0; color:#5f6368; font-size:12px;\"><strong>Fundoo Notes</strong> &nbsp;&bull;&nbsp; Save your thoughts, wherever you are.</p>"
                + "  </div>"
                + "</body>"
                + "</html>";

        sendHtmlEmail(toEmail, "Reset Your Fundoo Notes Password", htmlBody);
    }

    @Override
    @Async("taskExecutor")
    public void sendReminderEmail(String toEmail, String noteTitle) {
        String safeTitle = (noteTitle != null && !noteTitle.trim().isEmpty()) ? noteTitle : "Untitled Note";
        String formattedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String htmlBody = "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Fundoo Notes</title>"
                + "</head>"
                + "<body style=\"margin:0; padding:40px 16px; background-color:#ffffff; font-family:'Roboto', 'Google Sans', 'Segoe UI', Arial, sans-serif;\">"
                + "  <div style=\"max-width:480px; margin:0 auto;\">"
                
                + "    <div style=\"background-color:#ffffff; border:1px solid #dadce0; border-radius:8px; padding:40px 32px 32px 32px; text-align:center;\">"
                
                + "      <div style=\"margin-bottom:24px; text-align:center;\">"
                + "        <svg width=\"40\" height=\"48\" viewBox=\"0 0 40 48\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\" style=\"display:inline-block;\">"
                + "          <path d=\"M0 4C0 1.79086 1.79086 0 4 0H28L40 12V44C40 46.2091 38.2091 48 36 48H4C1.79086 48 0 46.2091 0 44V4Z\" fill=\"#FBBC04\"/>"
                + "          <path d=\"M28 0L40 12H30C28.8954 12 28 11.1046 28 10V0Z\" fill=\"#F4B400\"/>"
                + "          <path fill-rule=\"evenodd\" clip-rule=\"evenodd\" d=\"M20 14C16.686 14 14 16.686 14 20V25L12 27V28H28V27L26 25V20C26 16.686 23.314 14 20 14ZM18 29C18 30.105 18.895 31 20 31C21.105 31 22 30.105 22 29H18Z\" fill=\"white\"/>"
                + "        </svg>"
                + "      </div>"
                
                + "      <p style=\"margin:0 0 16px 0; color:#3c4043; font-size:14px; line-height:1.5; text-align:center;\">"
                + "        A reminder has been scheduled for your note."
                + "      </p>"
                
                + "      <h2 style=\"margin:0 0 8px 0; color:#202124; font-size:22px; font-weight:700; text-align:center; word-break:break-word;\">"
                +          safeTitle
                + "      </h2>"
                
                + "      <p style=\"margin:0 0 28px 0; color:#5f6368; font-size:13px; text-align:center;\">"
                + "        Scheduled Time: <strong>" + formattedTime + "</strong>"
                + "      </p>"
                
                + "      <div style=\"margin-bottom:8px; text-align:center;\">"
                + "        <a href=\"" + APP_URL + "\" style=\"background-color:#fbbc04; color:#202124; font-weight:600; text-decoration:none; padding:12px 28px; border-radius:4px; display:inline-block; font-size:14px; letter-spacing:0.2px;\">"
                + "          View Note in Fundoo"
                + "        </a>"
                + "      </div>"
                
                + "    </div>"
                
                + "    <p style=\"margin:20px 0 0 0; color:#5f6368; font-size:12px; text-align:center;\">"
                + "      Fundoo Notes &middot; Save your thoughts wherever you are"
                + "    </p>"
                
                + "  </div>"
                + "</body>"
                + "</html>";

        sendHtmlEmail(toEmail, "New Reminder Set: '" + safeTitle + "'", htmlBody);
    }

    @Override
    @Async("taskExecutor")
    public void sendOtpEmail(String toEmail, String otp) {
        String htmlBody = "<!DOCTYPE html>"
                + "<html>"
                + "<head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"></head>"
                + "<body style=\"margin:0; padding:40px 16px; background-color:#ffffff; font-family:'Roboto', 'Google Sans', 'Segoe UI', Arial, sans-serif;\">"
                + "  <div style=\"max-width:480px; margin:0 auto;\">"
                + "    <div style=\"background-color:#ffffff; border:1px solid #dadce0; border-radius:8px; padding:40px 32px 32px 32px;\">"
                + "      <div style=\"margin-bottom:24px; text-align:center;\">"
                + "        <svg width=\"40\" height=\"48\" viewBox=\"0 0 40 48\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\" style=\"display:inline-block;\">"
                + "          <path d=\"M0 4C0 1.79086 1.79086 0 4 0H28L40 12V44C40 46.2091 38.2091 48 36 48H4C1.79086 48 0 46.2091 0 44V4Z\" fill=\"#FBBC04\"/>"
                + "          <path d=\"M28 0L40 12H30C28.8954 12 28 11.1046 28 10V0Z\" fill=\"#F4B400\"/>"
                + "          <path fill-rule=\"evenodd\" clip-rule=\"evenodd\" d=\"M20 14C16.134 14 13 17.134 13 21C13 23.506 14.321 25.703 16.31 26.934C16.976 27.347 17.375 28.087 17.375 28.867V29.75C17.375 30.716 18.159 31.5 19.125 31.5H20.875C21.841 31.5 22.625 30.716 22.625 29.75V28.867C22.625 28.087 23.024 27.347 23.69 26.934C25.679 25.703 27 23.506 27 21C27 17.134 23.866 14 20 14ZM17.375 33.25C17.375 32.767 17.767 32.375 18.25 32.375H21.75C22.233 32.375 22.625 32.767 22.625 33.25V33.688C22.625 34.413 22.038 35 21.313 35H18.688C17.963 35 17.375 34.413 17.375 33.688V33.25Z\" fill=\"white\"/>"
                + "        </svg>"
                + "      </div>"
                + "      <h2 style=\"margin:0 0 24px 0; color:#202124; font-size:22px; font-weight:600; text-align:center;\">Verify Your Email</h2>"
                + "      <p style=\"margin:0 0 16px 0; color:#3c4043; font-size:14px; line-height:1.5;\">Hello,</p>"
                + "      <p style=\"margin:0 0 16px 0; color:#3c4043; font-size:14px; line-height:1.5;\">Your One-Time Password (OTP) for account verification is:</p>"
                + "      <div style=\"text-align:center; margin:28px 0;\">"
                + "        <div style=\"background-color:#fbbc04; color:#202124; font-weight:700; padding:12px 28px; border-radius:4px; display:inline-block; font-size:22px; letter-spacing:6px; font-family:'Courier New', monospace;\">" + otp + "</div>"
                + "      </div>"
                + "      <p style=\"margin:0 0 20px 0; color:#5f6368; font-size:13px; line-height:1.5; text-align:center;\">This OTP code is valid for 10 minutes.</p>"
                + "      <div style=\"border-top:1px solid #dadce0; margin-top:28px; padding-top:20px;\">"
                + "        <p style=\"margin:0; color:#5f6368; font-size:13px; line-height:1.5;\">If you did not register for a Fundoo Notes account, please ignore this email.</p>"
                + "      </div>"
                + "    </div>"
                + "    <p style=\"margin:20px 0 0 0; color:#5f6368; font-size:12px;\"><strong>Fundoo Notes</strong> &nbsp;&bull;&nbsp; Save your thoughts, wherever you are.</p>"
                + "  </div>"
                + "</body>"
                + "</html>";

        sendHtmlEmail(toEmail, "Verify Your Fundoo Notes Account - OTP", htmlBody);
    }

    @Override
    @Async("taskExecutor")
    public void sendPasswordResetOtpEmail(String toEmail, String otp) {
        String htmlBody = "<!DOCTYPE html>"
                + "<html>"
                + "<head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"></head>"
                + "<body style=\"margin:0; padding:40px 16px; background-color:#ffffff; font-family:'Roboto', 'Google Sans', 'Segoe UI', Arial, sans-serif;\">"
                + "  <div style=\"max-width:480px; margin:0 auto;\">"
                + "    <div style=\"background-color:#ffffff; border:1px solid #dadce0; border-radius:8px; padding:40px 32px 32px 32px;\">"
                + "      <div style=\"margin-bottom:24px; text-align:center;\">"
                + "        <svg width=\"40\" height=\"48\" viewBox=\"0 0 40 48\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\" style=\"display:inline-block;\">"
                + "          <path d=\"M0 4C0 1.79086 1.79086 0 4 0H28L40 12V44C40 46.2091 38.2091 48 36 48H4C1.79086 48 0 46.2091 0 44V4Z\" fill=\"#FBBC04\"/>"
                + "          <path d=\"M28 0L40 12H30C28.8954 12 28 11.1046 28 10V0Z\" fill=\"#F4B400\"/>"
                + "          <path fill-rule=\"evenodd\" clip-rule=\"evenodd\" d=\"M20 14C16.134 14 13 17.134 13 21C13 23.506 14.321 25.703 16.31 26.934C16.976 27.347 17.375 28.087 17.375 28.867V29.75C17.375 30.716 18.159 31.5 19.125 31.5H20.875C21.841 31.5 22.625 30.716 22.625 29.75V28.867C22.625 28.087 23.024 27.347 23.69 26.934C25.679 25.703 27 23.506 27 21C27 17.134 23.866 14 20 14ZM17.375 33.25C17.375 32.767 17.767 32.375 18.25 32.375H21.75C22.233 32.375 22.625 32.767 22.625 33.25V33.688C22.625 34.413 22.038 35 21.313 35H18.688C17.963 35 17.375 34.413 17.375 33.688V33.25Z\" fill=\"white\"/>"
                + "        </svg>"
                + "      </div>"
                + "      <h2 style=\"margin:0 0 24px 0; color:#202124; font-size:22px; font-weight:600; text-align:center;\">Reset Your Password</h2>"
                + "      <p style=\"margin:0 0 16px 0; color:#3c4043; font-size:14px; line-height:1.5;\">Hello,</p>"
                + "      <p style=\"margin:0 0 16px 0; color:#3c4043; font-size:14px; line-height:1.5;\">We received a request to reset the password for your <strong>Fundoo Notes</strong> account.</p>"
                + "      <p style=\"margin:0 0 24px 0; color:#3c4043; font-size:14px; line-height:1.5;\">Use the One-Time Password (OTP) below to choose a new password. This code is valid for 10 minutes.</p>"
                + "      <div style=\"text-align:center; margin:28px 0;\">"
                + "        <div style=\"background-color:#fbbc04; color:#202124; font-weight:700; padding:12px 32px; border-radius:4px; display:inline-block; font-size:22px; letter-spacing:6px; font-family:'Courier New', monospace; box-shadow:0 1px 3px rgba(0,0,0,0.12);\">" + otp + "</div>"
                + "      </div>"
                + "      <div style=\"border-top:1px solid #dadce0; margin-top:28px; padding-top:20px;\">"
                + "        <p style=\"margin:0; color:#5f6368; font-size:13px; line-height:1.5;\">If you didn't request a password reset, you can safely ignore this email. Your password will remain unchanged.</p>"
                + "      </div>"
                + "    </div>"
                + "    <p style=\"margin:20px 0 0 0; color:#5f6368; font-size:12px;\"><strong>Fundoo Notes</strong> &nbsp;&bull;&nbsp; Save your thoughts, wherever you are.</p>"
                + "  </div>"
                + "</body>"
                + "</html>";

        sendHtmlEmail(toEmail, "Reset Your Fundoo Notes Password", htmlBody);
    }

    @Override
    @Async("taskExecutor")
    public void sendWelcomeEmail(String toEmail, String name) {
        String displayName = (name != null && !name.trim().isEmpty()) ? name : "User";
        String content = "<h2 style=\"margin-top:0; color:#202124; font-size:20px;\">Welcome to Fundoo Notes! 🎉</h2>"
                + "<p>Hello <strong>" + displayName + "</strong>,</p>"
                + "<p>Your account has been created successfully. Welcome to the Fundoo Notes family!</p>"
                + "<p>With Fundoo Notes, you can easily:</p>"
                + "<ul style=\"color:#3c4043; padding-left:20px; line-height:1.8;\">"
                + "  <li>Create and format rich color-coded notes</li>"
                + "  <li>Organize with custom labels and pins</li>"
                + "  <li>Set timely reminders for important tasks</li>"
                + "  <li>Collaborate seamlessly with friends and colleagues</li>"
                + "</ul>"
                + "<div style=\"text-align:center; margin:30px 0 10px 0;\">"
                + "  <a href=\"" + APP_URL + "/login\" style=\"background-color:#f4b400; color:#202124; font-weight:bold; text-decoration:none; padding:14px 32px; border-radius:8px; display:inline-block; font-size:15px; box-shadow:0 2px 6px rgba(0,0,0,0.12);\">Get Started Now</a>"
                + "</div>";

        sendHtmlEmail(toEmail, "Welcome to Fundoo Notes!", buildHtmlEmail("Welcome Aboard", content));
    }

    @Override
    @Async("taskExecutor")
    public void sendCollaboratorAddedEmail(String toEmail, String ownerName, String noteTitle) {
        String safeOwner = (ownerName != null && !ownerName.trim().isEmpty()) ? ownerName : "A user";
        String safeTitle = (noteTitle != null && !noteTitle.trim().isEmpty()) ? noteTitle : "Untitled Note";

        String ownerDisplay = safeOwner.contains("@") 
                ? "<a href=\"mailto:" + safeOwner + "\" style=\"color:#1a73e8; text-decoration:none;\">" + safeOwner + "</a>" 
                : safeOwner;

        String htmlBody = "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Fundoo Notes</title>"
                + "</head>"
                + "<body style=\"margin:0; padding:40px 16px; background-color:#ffffff; font-family:'Roboto', 'Google Sans', 'Segoe UI', Arial, sans-serif;\">"
                + "  <div style=\"max-width:480px; margin:0 auto;\">"
                
                + "    <div style=\"background-color:#ffffff; border:1px solid #dadce0; border-radius:8px; padding:40px 32px 32px 32px; text-align:center;\">"
                
                + "      <div style=\"margin-bottom:24px; text-align:center;\">"
                + "        <svg width=\"40\" height=\"48\" viewBox=\"0 0 40 48\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\" style=\"display:inline-block;\">"
                + "          <path d=\"M0 4C0 1.79086 1.79086 0 4 0H28L40 12V44C40 46.2091 38.2091 48 36 48H4C1.79086 48 0 46.2091 0 44V4Z\" fill=\"#FBBC04\"/>"
                + "          <path d=\"M28 0L40 12H30C28.8954 12 28 11.1046 28 10V0Z\" fill=\"#F4B400\"/>"
                + "          <path fill-rule=\"evenodd\" clip-rule=\"evenodd\" d=\"M20 14C16.134 14 13 17.134 13 21C13 23.506 14.321 25.703 16.31 26.934C16.976 27.347 17.375 28.087 17.375 28.867V29.75C17.375 30.716 18.159 31.5 19.125 31.5H20.875C21.841 31.5 22.625 30.716 22.625 29.75V28.867C22.625 28.087 23.024 27.347 23.69 26.934C25.679 25.703 27 23.506 27 21C27 17.134 23.866 14 20 14ZM17.375 33.25C17.375 32.767 17.767 32.375 18.25 32.375H21.75C22.233 32.375 22.625 32.767 22.625 33.25V33.688C22.625 34.413 22.038 35 21.313 35H18.688C17.963 35 17.375 34.413 17.375 33.688V33.25Z\" fill=\"white\"/>"
                + "        </svg>"
                + "      </div>"
                
                + "      <p style=\"margin:0 0 16px 0; color:#3c4043; font-size:14px; line-height:1.5; text-align:center;\">"
                + "        Owner (" + ownerDisplay + ") shared a note with you."
                + "      </p>"
                
                + "      <h2 style=\"margin:0 0 28px 0; color:#202124; font-size:22px; font-weight:700; text-align:center; word-break:break-word;\">"
                +          safeTitle
                + "      </h2>"
                
                + "      <div style=\"margin-bottom:8px; text-align:center;\">"
                + "        <a href=\"" + APP_URL + "\" style=\"background-color:#fbbc04; color:#202124; font-weight:600; text-decoration:none; padding:12px 28px; border-radius:4px; display:inline-block; font-size:14px; letter-spacing:0.2px;\">"
                + "          Open in Fundoo"
                + "        </a>"
                + "      </div>"
                
                + "    </div>"
                
                + "    <p style=\"margin:20px 0 0 0; color:#5f6368; font-size:12px; text-align:center;\">"
                + "      Fundoo Notes &middot; Save your thoughts wherever you are"
                + "    </p>"
                
                + "  </div>"
                + "</body>"
                + "</html>";

        sendHtmlEmail(toEmail, "Note shared with you: '" + safeTitle + "'", htmlBody);
    }

    // -------------------------------------------------------------
    // HELPER METHOD TO SEND HTML MIME MESSAGES
    // -------------------------------------------------------------
    private void sendHtmlEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(fromEmail, "Fundoo Notes");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            mailSender.send(message);
            System.out.println("HTML Email sent successfully to: " + to + " [Subject: " + subject + "]");
        } catch (Exception e) {
            System.err.println("Failed to send HTML email to " + to + ": " + e.getMessage());
        }
    }
}