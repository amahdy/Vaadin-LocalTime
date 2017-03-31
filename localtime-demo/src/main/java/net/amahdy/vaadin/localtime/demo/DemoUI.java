package net.amahdy.vaadin.localtime.demo;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Binder;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.UI;
import net.amahdy.vaadin.localtime.Localtime;

import javax.servlet.annotation.WebServlet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Theme("demo")
@Title("Localtime Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
    public static class Servlet extends VaadinServlet {
    }

    private DateTimeField serverTime = new DateTimeField("Time in server");
    private DateTimeField browserTime = new DateTimeField("Time in browser");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");

    @Override
    protected void init(VaadinRequest request) {

        /*** Use the Extension ***/
        Localtime.on(browserTime, formatter);

        // Simulate UTC timezone for the server (local JVM)
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        Person p = new Person();
        p.setDateField(LocalDateTime.now());

        // Another example with SQL Timestamp:
        // Timestamp time = new Timestamp(1490975995000L);
        // p.setDateField(time.toLocalDateTime());

        Binder<Person> binder = new Binder<>();
        binder.forField(browserTime).bind(Person::getDateField, Person::setDateField);
        binder.setBean(p);

        serverTime.setValue(p.getDateField());

        Button btn = new Button("Refresh Time", evt -> {
            p.setDateField(LocalDateTime.now());
            binder.setBean(p);
            serverTime.setValue(p.getDateField());
        });

        final FormLayout layout = new FormLayout();
        layout.addComponents(serverTime, browserTime, btn);
        setContent(layout);
    }
}
