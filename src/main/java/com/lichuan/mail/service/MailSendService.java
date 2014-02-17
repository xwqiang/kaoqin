package com.lichuan.mail.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.lichuan.attendance.model.AdminUser;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * 以普通文本的格式发送Email
 * @author 1202211
 *
 */
@Service
public class MailSendService {

	public String from;
	private JavaMailSender mailSender;
	@Autowired
	private static  FreeMarkerConfigurer freeMarkerConfigurer;
	
	public FreeMarkerConfigurer getFreeMarkerConfigurer() {
		return freeMarkerConfigurer;
	}

	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.freeMarkerConfigurer = freeMarkerConfigurer;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public static void main(String[] args) {

	}

	public void sendSimpleEmail(String to, String title, String message)
			throws Exception {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom(from);
		msg.setTo(to);
		msg.setSubject(title);
		msg.setText(message);

		try {
			mailSender.send(msg);
			System.out.println(from + ",文本格式内容邮件已发送至:" + to);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 支持HTML脚本的格式发送Email
	 * @param to  发送给谁，对应的email
	 * @param title  邮件主题
	 * @param message  邮件内容  支持HTML脚本
	 * @throws Exception
	 */
	public void sendHtmlEmail(String to, String title,String every_month,AdminUser adminUser)
			throws Exception {
		MimeMessage msg = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
		helper.setTo(to);
		helper.setFrom(from);
		helper.setSubject(title);
		/*String htmlText="<html><head>"+   
        "<meta http-equiv=\"content-type\" content=\"text/html;charset=utf8\">"+   
        "</head><body><img src=\"cid:aaa\"/>" +   
        "邮件发送成功：<font color='red' size='30'>再写一个</font>"+   
        "</body></html>";*/   
		helper.setText(getMailText(every_month,adminUser), true);
		//FileSystemResource img = new FileSystemResource(new File("e:/1234.jpg")); 
		//helper.addInline("aaa", img);//发送嵌套图片
		
		try {
			mailSender.send(msg);
			System.out.println(from + ",内容邮件已发送至:" + to);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	/**
	 * 
	 * 定制邮件模板
	 * @return
	 */
	public static String getMailText(String every_month,AdminUser adminUser) {  
        String html = "";  
        try {  
  
            Map<String,Object> map = new HashMap<String,Object>();  
            map.put("userName", adminUser.getAdmin_name());  
            map.put("userId", adminUser.getAdmin_id());  
//            map.put("password", adminUser.getAdmin_pwd());  
            map.put("every_month", every_month);  
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
  
            map.put("sendTime", sdf.format(new Date()));  
            // 装载模板  
            //Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template tpl = freeMarkerConfigurer.getConfiguration()  
                    .getTemplate("templteMsg.flt");  
            // 加入map到模板中 输出对应变量  
            html = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);  
  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (TemplateException e) {  
            e.printStackTrace();  
        }  
        return html;  
    }  
	
	
}
