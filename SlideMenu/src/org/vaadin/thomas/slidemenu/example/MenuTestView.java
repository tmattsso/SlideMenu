package org.vaadin.thomas.slidemenu.example;

import org.vaadin.thomas.slidemenu.SlideMenuView;
import org.vaadin.thomas.slidemenu.SlideMenuWindow.SlideMenuListener;

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

		final Button close = new Button("close");
		getMenu().addComponent(close);
		close.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -1692006683791129470L;

			@Override
			public void buttonClick(ClickEvent event) {
				getMenu().close();

			}
		});

		for (int i = 0; i < 30; i++) {
			final Button d = new Button("dummy");
			getMenu().addComponent(d);
		}

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

		getMenu().setWidth("50%");
	}
}
