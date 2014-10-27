package org.vaadin.thomas.slidemenu.example;

import javax.servlet.annotation.WebServlet;

import com.vaadin.addon.touchkit.server.TouchKitServlet;
import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("slidemenutheme")
public class SlidemenuUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = SlidemenuUI.class, widgetset = "org.vaadin.thomas.slidemenu.SlidemenuWidgetset")
	public static class Servlet extends TouchKitServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		final NavigationManager layout = new NavigationManager();
		setContent(layout);

		final MenuTestView view = new MenuTestView();
		layout.navigateTo(view);
	}

}