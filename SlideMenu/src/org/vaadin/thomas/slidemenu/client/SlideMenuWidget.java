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

public class SlideMenuWidget extends VWindow {

	private Element curtain;

	private static final String STYLE_OPENING = "opening";

	private final boolean isOpen = false;
	private final boolean isMoving = false;

	private int width = 90;
	private Unit widthUnit = Unit.PCT;

	private MenuServerRPC listener;

	public SlideMenuWidget() {

		curtain = DOM.createDiv();

		curtain.addClassName("modalcurtain");
		curtain.getStyle().setVisibility(Visibility.HIDDEN);

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
		curtain = getElement().appendChild(curtain);

		getElement().getStyle().setLeft(-width, widthUnit);
	}

	@Override
	public void setWidth(String width) {
		super.setWidth(width);

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

		getElement().getStyle().setLeft(-this.width, widthUnit);
	}

	public boolean isOpen() {
		return isOpen;
	}

	protected void open() {

		getElement().addClassName(STYLE_OPENING);

		new Timer() {

			@Override
			public void run() {
				curtain.getStyle().setVisibility(Visibility.VISIBLE);
				listener.menuOpened();
			}

		}.schedule(500);
	}

	protected void close() {

		getElement().removeClassName(STYLE_OPENING);

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				curtain.getStyle().setVisibility(Visibility.HIDDEN);
				listener.menuClosed();
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
