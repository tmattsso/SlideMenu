package org.vaadin.thomas.slidemenu.client;

import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.animation.client.AnimationScheduler.AnimationCallback;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.ui.VWindow;

public class SlideMenuWidget extends VWindow {

	protected int widthPercentage = 80;

	protected int numFramesForAnimation = 15;

	private Element curtain;

	private Style style;

	private boolean isOpen = false;
	private boolean isMoving = false;

	private final AnimationCallback menuOpenAnimation = new MenuAnimation(true);
	private final AnimationCallback menuCloseAnimation = new MenuAnimation(
			false);
	private int currentPos = -widthPercentage;

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

		style = getElement().getStyle();
		curtain = getElement().appendChild(curtain);

		getElement().getStyle().setMarginLeft(-widthPercentage, Unit.PCT);
	}

	public int getWidthPercentage() {
		return widthPercentage;
	}

	@Override
	public void setWidth(String width) {
		super.setWidth(width);

		int w = -1;

		// only support percentages
		if (width.contains("%")) {
			if (width.startsWith("100")) {
				w = 100;
			} else {
				width = width.substring(0, 2);
				w = Integer.parseInt(width);
			}
			setWidthPercentage(w);
		}
	}

	/**
	 * Set the width of the menu, in percentage of the screen width. Default is
	 * 80.
	 *
	 * @param widthPercentage
	 */
	public void setWidthPercentage(int widthPercentage) {
		this.widthPercentage = widthPercentage;

		// reset for next animation
		if (!isOpen) {
			currentPos = -widthPercentage;
		}
	}

	public int getNumFramesForAnimation() {
		return numFramesForAnimation;
	}

	/**
	 * How many frames the menu animation should be split into. Default is 15.
	 *
	 * @param numFramesForAnimation
	 */
	public void setNumFramesForAnimation(int numFramesForAnimation) {
		this.numFramesForAnimation = numFramesForAnimation;
	}

	public boolean isOpen() {
		return isOpen;
	}

	protected void open() {
		AnimationScheduler.get().requestAnimationFrame(menuOpenAnimation,
				getWidget().getElement());
	}

	protected void close() {
		AnimationScheduler.get().requestAnimationFrame(menuCloseAnimation,
				getWidget().getElement());
	}

	public MenuServerRPC getListener() {
		return listener;
	}

	public void setListener(MenuServerRPC listener) {
		this.listener = listener;
	}

	private final class MenuAnimation implements AnimationCallback {
		private final boolean right;
		private int hasMoved = 0;

		public MenuAnimation(boolean right) {
			this.right = right;
		}

		@Override
		public void execute(double timestamp) {

			curtain.getStyle().setVisibility(Visibility.VISIBLE);
			isMoving = true;

			final boolean shouldMove = right && currentPos < 0 || !right
					&& currentPos > -widthPercentage;

					if (shouldMove) {
						final int movePerFrame = getMovePerFrame(hasMoved);
						hasMoved += movePerFrame;

						if (right) {
							currentPos += movePerFrame;
						} else {
							currentPos -= movePerFrame;
						}

						style.setMarginLeft(currentPos, Unit.PCT);
						AnimationScheduler.get().requestAnimationFrame(this,
								getWidget().getElement());
					} else {
						if (right) {
							style.setMarginLeft(0, Unit.PX);
							currentPos = 0;
						} else {
							currentPos = -widthPercentage;
							style.setMarginLeft(-widthPercentage, Unit.PCT);
						}

						isMoving = false;
						hasMoved = 0;

						if (right) {
							isOpen = true;
							curtain.getStyle().setVisibility(Visibility.VISIBLE);
							listener.menuOpened();
						} else {
							isOpen = false;
							curtain.getStyle().setVisibility(Visibility.HIDDEN);
							listener.menuClosed();
						}
					}
		}

		private int getMovePerFrame(int current) {
			int i = widthPercentage / numFramesForAnimation;
			if (current + i > widthPercentage) {
				i = widthPercentage - current;
			}
			return i;
		}
	}

}
