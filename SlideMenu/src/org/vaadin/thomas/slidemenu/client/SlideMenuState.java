package org.vaadin.thomas.slidemenu.client;

import com.vaadin.shared.annotations.DelegateToWidget;
import com.vaadin.shared.ui.window.WindowState;

public class SlideMenuState extends WindowState {

	private static final long serialVersionUID = 1394023893691759480L;

	@DelegateToWidget
	public int numFramesForAnimation = 15;

}
