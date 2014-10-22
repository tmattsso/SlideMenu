package org.vaadin.thomas.slidemenu.example;

import org.vaadin.thomas.slidemenu.SlideMenu;
import org.vaadin.thomas.slidemenu.SlideMenu.SlideMenuListener;
import org.vaadin.thomas.slidemenu.SlideMenuView;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class MenuTestView extends SlideMenuView {

	private static final long serialVersionUID = -2517225823027917775L;

	public MenuTestView() {

		final VerticalLayout root = new VerticalLayout();
		root.setMargin(true);
		root.setSpacing(true);
		setContent(root);

		getNavigationBar().setCaption("header");

		final Button close = new Button("close menu");
		getMenu().addComponent(close);
		close.setWidth(null);
		close.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -1692006683791129470L;

			@Override
			public void buttonClick(ClickEvent event) {
				getMenu().close();
			}
		});

		Label l = new Label("Sections:");
		l.addStyleName(SlideMenu.STYLENAME_SECTIONLABEL);
		getMenu().addComponent(l);

		Button b = new Button("Dashboard");
		b.addStyleName(SlideMenu.STYLENAME_BUTTON);
		getMenu().addComponent(b);

		b = new Button("Inbox");
		b.addStyleName(SlideMenu.STYLENAME_BUTTON);
		getMenu().addComponent(b);

		b = new Button("Admin");
		b.addStyleName(SlideMenu.STYLENAME_BUTTON);
		getMenu().addComponent(b);

		l = new Label("Settings:");
		l.addStyleName(SlideMenu.STYLENAME_SECTIONLABEL);
		getMenu().addComponent(l);

		b = new Button("Options");
		b.addStyleName(SlideMenu.STYLENAME_BUTTON);
		getMenu().addComponent(b);
		b = new Button("Logout");
		b.addStyleName(SlideMenu.STYLENAME_BUTTON);
		getMenu().addComponent(b);

		getMenu().addSlideMenuListener(new SlideMenuListener() {

			@Override
			public void menuOpened() {

				root.addComponent(new Label("opened"));
			}

			@Override
			public void menuClosed() {

				root.addComponent(new Label("closed"));
			}
		});

		// getMenu().setWidth("50%");
	}
}
