package org.vaadin.thomas.slidemenu.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Timer;
import com.vaadin.client.ui.VWindow;

/**
 * Actual menu Widget class. Should be usable as-is from GWT projects too.
 *
 * @author thomas
 *
 */
public class SlideMenuWidget extends VWindow {

	private Element curtain;

	private static final String STYLE_OPENING = "opening";

	private final boolean isOpen = false;
	private final boolean isMoving = false;

	private int width = 90;
	private Unit widthUnit = Unit.PCT;

	private MenuServerRPC listener;

	public SlideMenuWidget() {

		// create and hide curtain
		curtain = DOM.createDiv();
		curtain.addClassName("modalcurtain");
		curtain.getStyle().setVisibility(Visibility.HIDDEN);

		// listen to clicks and swipe starts to close the menu
		Event.sinkEvents(curtain, Event.ONCLICK);
		Event.setEventListener(curtain, new EventListener() {

			@Override
			public void onBrowserEvent(Event event) {
				if (isMoving) {
					return;
				}

				if (Event.ONCLICK == event.getTypeInt()
						|| Event.ONTOUCHEND == event.getTypeInt()) {
					close();
				}
			}
		});

	}

	@Override
	protected void onAttach() {
		super.onAttach();

		// also attach the curtain
		curtain = getElement().appendChild(curtain);

		// fix initial positioning from VWindow.
		getElement().getStyle().setLeft(-width, widthUnit);
	}

	@Override
	public void setWidth(String width) {
		// change the width of the element
		super.setWidth(width);

		// adjust current positioning so that whole menu is still hidden (and
		// opens and closes nicely)

		// start with parsing new width
		if (width.contains("%")) {
			widthUnit = Unit.PCT;
		} else {
			widthUnit = Unit.PX;
		}
		if (width.startsWith("100")) {
			this.width = 100;
		} else {
			width = width.substring(0, 2);
			this.width = Integer.parseInt(width);
		}

		// and then re-position
		getElement().getStyle().setLeft(-this.width, widthUnit);
	}

	public boolean isOpen() {
		return isOpen;
	}

	protected void open() {

		// CSS takes care of moving the element with a 500ms transition
		getElement().addClassName(STYLE_OPENING);

		// We just need to make the curtain visible and send an 'opened' event
		new Timer() {

			@Override
			public void run() {
				curtain.getStyle().setVisibility(Visibility.VISIBLE);
				if (listener != null) {
					listener.menuOpened();
				}
			}

		}.schedule(500);
	}

	protected void close() {

		// CSS takes care of moving the element with a 500ms transition
		getElement().removeClassName(STYLE_OPENING);

		// We just need to make the curtain invisible and send an 'closed' event
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				curtain.getStyle().setVisibility(Visibility.HIDDEN);
				if (listener != null) {
					listener.menuClosed();
				}
			}
		});
	}

	public MenuServerRPC getListener() {
		return listener;
	}

	public void setListener(MenuServerRPC listener) {
		this.listener = listener;
	}

}
