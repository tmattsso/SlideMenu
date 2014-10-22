package org.vaadin.thomas.slidemenu;

import com.vaadin.addon.touchkit.ui.NavigationManager.NavigationEvent;
import com.vaadin.addon.touchkit.ui.NavigationManager.NavigationListener;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class SlideMenuView extends NavigationView implements NavigationListener {

	private static final long serialVersionUID = 3952898936850021537L;

	protected SlideMenu menu = new SlideMenu();
	protected Button menuButton;

	public SlideMenuView() {

		menuButton = new Button();
		menuButton.setIcon(FontAwesome.BARS);
		menuButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 6014031942527721065L;

			@Override
			public void buttonClick(ClickEvent event) {
				menu.open();
			}
		});

		getNavigationBar().setLeftComponent(menuButton);

	}

	@Override
	public void attach() {
		super.attach();
		if (getNavigationManager() != null) {
			getNavigationManager().addNavigationListener(this);
		}
	}

	public SlideMenu getMenu() {
		return menu;
	}

	public void setMenuIcon(Resource icon) {
		menuButton.setIcon(icon);
	}

	@Override
	public void navigate(NavigationEvent event) {
		if (getNavigationManager().getCurrentComponent().equals(this)) {
			setLeftComponent(menuButton);
		}
	}
}
