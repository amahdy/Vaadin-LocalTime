package net.amahdy.vaadin.localtime;

import com.vaadin.server.AbstractExtension;
import com.vaadin.server.Page;
import com.vaadin.ui.AbstractField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by amahdy (www.amahdy.net).
 */
public class Localtime extends AbstractExtension {

    DateTimeFormatter formatter;
    boolean manualUpdate=false;

    protected Localtime(AbstractField<LocalDateTime> field,
                        DateTimeFormatter formatter) {
        this.formatter = formatter;
        extend(field);

        final int offset =
                // Client browser timezone
                Page.getCurrent().getWebBrowser().getTimezoneOffset()
                        // Convert to minutes
                        / 1000 / 60;

        field.addValueChangeListener(evt -> {
            if(manualUpdate) {
                return;
            }
            manualUpdate = true;
            field.setValue(evt.getValue().plusMinutes(offset));
            manualUpdate = false;
        });
    }

    /**
     *
     * Vaadin extension that is when applied on a given field,
     * will show its LocalDateTime value in the client's browser timezone
     * instead of the server's timezone.
     *
     * @param field The field will display LocalDateTime in client's localtime.
     * @param formatter DateTimeFormatter of the original `field`.
     * @return New instance of Localtime extension attached to the `field`.
     */
    public static Localtime on(AbstractField<LocalDateTime> field,
                               DateTimeFormatter formatter) {
        return new Localtime(field, formatter);
    }
}
