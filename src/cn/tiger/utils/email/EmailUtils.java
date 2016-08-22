package cn.tiger.utils.email;  
  
import java.io.IOException;  
import java.io.InputStream;  
import java.util.Date;  
import java.util.Properties;  
  
import javax.mail.Authenticator;  
import javax.mail.Message.RecipientType;  
import javax.mail.PasswordAuthentication;  
import javax.mail.Session;  
import javax.mail.Transport;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeMessage;

import cn.tiger.entity.account.AcctUser;  
  
public class EmailUtils {  
      
    private static final String FROM = "������������ַ";  
      
    /** 
     * ���������������ӵ��ʼ� 
     */  
    public static void sendResetPasswordEmail(AcctUser user,String resetPassHref) {  
        Session session = getSession();  
        MimeMessage message = new MimeMessage(session);  
        try {  
            message.setSubject("�һ������ʻ�������");  
            message.setSentDate(new Date());  
            message.setFrom(new InternetAddress(FROM));  
            message.setRecipient(RecipientType.TO, new InternetAddress(user.getEmail()));  
            message.setContent("Ҫʹ���µ�����, ��ʹ������������������:<br/><a href='"+resetPassHref+"'>���������������</a> SpringBBS","text/html;charset=utf-8");  
            // �����ʼ�  
            Transport.send(message);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
    public static Session getSession() {  
        Properties props = new Properties();  
        props.setProperty("mail.transport.protocol", "smtp");  
        props.setProperty("mail.smtp.host", "smtp.163.com");  
        props.setProperty("mail.smtp.port", "25");  
        props.setProperty("mail.smtp.auth", "true");  
        Session session = Session.getInstance(props, new Authenticator() {  
            @Override  
            protected PasswordAuthentication getPasswordAuthentication() {  
                String password = null;  
                InputStream is = EmailUtils.class.getResourceAsStream("password.dat");  
                byte[] b = new byte[1024];  
                try {  
                    int len = is.read(b);  
                    password = new String(b,0,len);  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
                return new PasswordAuthentication(FROM, password);  
            }  
              
        });  
        return session;  
    }  
}  