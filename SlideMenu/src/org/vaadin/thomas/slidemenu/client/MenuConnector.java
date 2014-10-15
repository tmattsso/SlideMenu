package org.vaadin.thomas.slidemenu.client;

import org.vaadin.thomas.slidemenu.SlideMenuWindow;

import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.window.WindowConnector;
import com.vaadin.shared.ui.Connect;

@Connect(SlideMenuWindow.class)
public class MenuConnector extends WindowConnector implements MenuServerRPC {

	private static final long serialVersionUID = -2714633284761401664L;

	public MenuConnector() {

		registerRpc(MenuClientRPC.class, new MenuClientRPC() {

			private static final long serialVersionUID = 8964269498688207231L;

			@Override
			public void openMenu() {
				getWidget().open();
			}

			@Override
			public void closeMenu() {
				getWidget().close();
			}
		});
	}

	@Override
	public SlideMenuState getState() {
		return (SlideMenuState) super.getState();
	}

	@Override
	protected void init() {
		super.init();

		getWidget().setListener(this);
	}

	@Override
	public SlideMenuWidget getWidget() {
		return (SlideMenuWidget) super.getWidget();
	}

	@Override
	public void onStateChanged(StateChangeEvent stateChangeEvent) {
		super.onStateChanged(stateChangeEvent);
	}

	/**
	 * Disabled for MenuConnector.
	 */
	@Override
	protected void updateWindowPosition() {
		// ignore positioning from server
	}

	/**
	 * Proxy menthod to server-side
	 */
	@Override
	public void menuOpened() {
		RpcProxy.create(MenuServerRPC.class, this).menuOpened();
	}

	/**
	 * Proxy menthod to server-side
	 */
	@Override
	public void menuClosed() {
		RpcProxy.create(MenuServerRPC.class, this).menuClosed();
	}

}
