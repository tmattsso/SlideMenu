package org.vaadin.thomas.slidemenu;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.thomas.slidemenu.client.MenuClientRPC;
import org.vaadin.thomas.slidemenu.client.MenuServerRPC;
import org.vaadin.thomas.slidemenu.client.SlideMenuState;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * The main menu popup class for the SlideMenu.
 * <p>
 * The menu is a {@link Window} component with stripped functionality. Creating
 * an instance of this class will automatically add the menu to the UI.
 *
 * @author Thomas Mattsson
 */
public class SlideMenu extends Window {

	private static final long serialVersionUID = 6340196558746178064L;

	private static final String STYLENAME = "slidemenu";
	public static final String STYLENAME_BUTTON = "menubutton";
	public static final String STYLENAME_SECTIONLABEL = "menulabel";
	private final List<Component> children = new ArrayList<Component>();
	private final VerticalLayout root;

	private boolean clientDirty = false;

	private final List<SlideMenuListener> listeners = new ArrayList<SlideMenuListener>();

	private Component loadingComponent;

	public SlideMenu() {
		addStyleName(STYLENAME);

		root = new VerticalLayout();
		root.addStyleName(STYLENAME + "-root");
		root.setWidth("100%");
		root.setHeight(null);
		root.setSpacing(true);
		root.setMargin(true);
		setContent(root);

		setClosable(false);
		setDraggable(false);
		setResizable(false);
		setModal(false);// we have our own curtain

		setPositionX(0);
		setPositionY(0);
		setHeight("100%");
		super.setWidth(80, Unit.PERCENTAGE);

		registerRpc(new MenuServerRPC() {

			private static final long serialVersionUID = -1080441563027217523L;

			@Override
			public void menuOpened() {
				sendChildrenToClient();
				fireOpen();
			}

			@Override
			public void menuClosed() {
				fireClose();
			}
		});

		loadingComponent = new Label("Loading...");
		loadingComponent.addStyleName("loading");
		root.addComponent(loadingComponent);

		UI.getCurrent().addWindow(this);
	}

	@Override
	protected SlideMenuState getState() {
		return (SlideMenuState) super.getState();
	}

	/**
	 * Set the component that should be shown while the menu is loading its
	 * content.
	 *
	 * @param comp
	 */
	public void setLoadingComponent(Component comp) {
		loadingComponent = comp;
		root.removeAllComponents();
		root.addComponent(loadingComponent);
		clientDirty = true;
	}

	/**
	 * Returns component that is shown during menu load and animation. By
	 * default a {@link Label}.
	 *
	 * @return
	 */
	public Component getLoadingComponent() {
		return loadingComponent;
	}

	protected void fireOpen() {
		for (final SlideMenuListener l : listeners) {
			l.menuOpened();
		}
	}

	@Override
	protected void fireClose() {
		for (final SlideMenuListener l : listeners) {
			l.menuClosed();
		}
	}

	public void addSlideMenuListener(SlideMenuListener l) {
		if (!listeners.contains(l)) {
			listeners.add(l);
		}
	}

	public void removeSlideMenuListener(SlideMenuListener l) {
		listeners.remove(l);
	}

	/**
	 * Add component into the menu.
	 *
	 * @param c
	 */
	public void addComponent(Component c) {
		// lazy init
		if (!children.contains(c)) {
			children.add(c);
		}
		clientDirty = true;
	}

	/**
	 * Remove Component from the menu.
	 *
	 * @param c
	 */
	public void removeComponent(Component c) {
		children.remove(c);
		clientDirty = true;
	}

	protected void sendChildrenToClient() {
		if (clientDirty) {
			root.removeAllComponents();
			for (final Component c : children) {
				root.addComponent(c);
			}
		}
		clientDirty = false;
	}

	/**
	 * Open the menu popup.
	 */
	public void open() {
		getRpcProxy(MenuClientRPC.class).openMenu();
	}

	/**
	 * Close the menu popup.
	 */
	@Override
	public void close() {
		getRpcProxy(MenuClientRPC.class).closeMenu();
	}

	public interface SlideMenuListener {
		void menuOpened();

		void menuClosed();
	}

	/**
	 * Set width of the popup window
	 */
	@Override
	public void setWidth(String width) {
		super.setWidth(width);
	}

	/**
	 * Set width of the popup window
	 */
	@Override
	public void setWidth(float width, Unit unit) {
		super.setWidth(width, unit);
	}

}
