package email2;
import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.util.Properties;  
 
import javax.mail.Folder;  
import javax.mail.Message;  
import javax.mail.Session;  
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;  
 
/**  
 * 简单的邮件接收程序，打印出邮件的原始内容  
 */ 
public class SimpleStoreMails {  
    public static void main(String[] args) throws Exception {
    	
    	// 发送
    	Properties pro = new Properties();  
        pro.put("mail.smtp.host", "smtp.126.com");//用到的邮件服务器
        
        pro.setProperty("mail.transport.protocol", "smtp");  
        //pro.put("mail.smtp.auth", true);  
        Session mailConnection = Session.getInstance(pro, null);  
        Message msg = new MimeMessage(mailConnection);  
        InternetAddress send = new InternetAddress("guo_jianing@126.com");//发送邮件用的邮箱地址  
        InternetAddress receiver = new InternetAddress("2402645487@qq.com");//收件人的邮箱地址  
        msg.setFrom(send);  
        msg.addRecipient(RecipientType.TO, receiver);  
        msg.setSubject("hehehhe");//设置邮件的主题  
        msg.setText("您已欠费");//设置邮件的内容  
        Transport tr = mailConnection.getTransport();  
        try {  
            tr.connect("guo_jianing@126.com", "gg220120346");//填写你的邮箱地址和密码  
            tr.sendMessage(msg, msg.getAllRecipients());  
            System.out.println("邮件发送成功");  
        } finally {  
            tr.close();//发送成功后关闭链接  
        }  
      

        // 接收
        // 连接pop3服务器的主机名、协议、用户名、密码  
        String pop3Server = "smtp.126.com";  
        String protocol = "smtp";  
        String user = "guo_jianing";  
        String pwd = "gg220120346";  
          
        // 创建一个有具体连接信息的Properties对象  
        Properties props = new Properties();  
        props.setProperty("mail.store.protocol", protocol);  
        props.setProperty("mail.smtp.host", pop3Server);  
          
        // 使用Properties对象获得Session对象  
        Session session = Session.getInstance(props);  
        session.setDebug(true);  
          
        // 利用Session对象获得Store对象，并连接pop3服务器  
        Store store = session.getStore();  
        store.connect(pop3Server, user, pwd);  
          
        // 获得邮箱内的邮件夹Folder对象，以"只读"打开  
        Folder folder = store.getFolder("inbox");  
        folder.open(Folder.READ_ONLY);  
          
        // 获得邮件夹Folder内的所有邮件Message对象  
        Message [] messages = folder.getMessages();  
          
        int mailCounts = messages.length;  
        for(int i = 0; i < mailCounts; i++) {  
              
            String subject = messages[i].getSubject();  
            String from = (messages[i].getFrom()[0]).toString();  
              
            System.out.println("第 " + (i+1) + "封邮件的主题：" + subject);  
            System.out.println("第 " + (i+1) + "封邮件的发件人地址：" + from);  
              
            System.out.println("是否打开该邮件(yes/no)?：");  
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
            String input = br.readLine();  
            if("yes".equalsIgnoreCase(input)) {  
                // 直接输出到控制台中  
                messages[i].writeTo(System.out);  
            }             
        }  
        folder.close(false); 
        store.close();
    }  
} 