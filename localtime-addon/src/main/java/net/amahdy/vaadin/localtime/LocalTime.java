package net.amahdy.vaadin.localtime;

import com.vaadin.ui.AbstractComponent;

import net.amahdy.vaadin.localtime.client.LocalTimeState;

@SuppressWarnings("serial")
public class LocalTime extends AbstractComponent {

    // Construct the component with server-side timestamp
    public LocalTime(Long timestamp) {

        getState().serverTime = timestamp;
    }

    @Override
    protected LocalTimeState getState() {
        return (LocalTimeState) super.getState();
    }
}
