package net.amahdy.vaadin.localtime.client;

import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

import net.amahdy.vaadin.localtime.LocalTime;

@SuppressWarnings("serial")
@Connect(LocalTime.class)
public class LocalTimeConnector extends AbstractComponentConnector {

    public LocalTimeConnector() {

    }

    @Override
    public VLocalTime getWidget() {
        return (VLocalTime) super.getWidget();
    }

    @Override
    public LocalTimeState getState() {
        return (LocalTimeState) super.getState();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        // 1. Convert offset to seconds (multiply by 60)
        // 2. Get local time in seconds (subtract from serverTime)
        Long clientTime = getState().serverTime - (60 * getOffset());

        getWidget().setText(clientTime.toString());
    }

    // Read the offset using JS from browser to guarantee local time
    public native int getOffset() /*-{
    		return (new Date()).getTimezoneOffset();
    }-*/;
}
