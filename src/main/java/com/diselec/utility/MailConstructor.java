package com.diselec.utility;

import com.diselec.entities.Orden;
import com.diselec.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Component
public class MailConstructor {

    @Autowired
    private Environment env;

    @Autowired
    private TemplateEngine templateEngine;

    public SimpleMailMessage enviarMensaje(String contextPath, Locale locale, Usuario usuario, String password){

        String url = contextPath + "/login";
        String message = "Gracias por registrarse, ahora tienes la posibilidad de comprar en linea para que no " +
                "tengas que salir de casa.\n para ingresar dar clic en el siguiente link: ";
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(usuario.getEmail());
        email.setSubject("Bienvenido a la tienda virtual de Diseño Electronico");
        email.setText(message + url);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }


    //ENVIA EMAIL CON PLANTILLA
    public MimeMessagePreparator constructOrderConfirmationEmail(Usuario usuario, Orden orden, Locale locale){
        Context context = new Context();
        context.setVariable("orden",orden);
        context.setVariable("usuario",usuario);
        context.setVariable("cartItemList", orden.getCartItemList());
        String text = templateEngine.process("plantillaConfirmacionEmail",context);

        MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
                email.setTo(usuario.getEmail());
                email.setSubject("Orden de confirmacion - " + orden.getId());
                email.setText(text,true);
                email.setFrom(new InternetAddress("fernantharo@gmail.com"));
            }
        };
        return messagePreparator;
    }

}
