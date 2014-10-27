package org.vaadin.thomas.slidemenu.client;

import org.vaadin.thomas.slidemenu.SlideMenu;

import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.window.WindowConnector;
import com.vaadin.shared.ui.Connect;

/**
 * Vaadin glue class.
 *
 * @author thomas
 */
@Connect(SlideMenu.class)
public class MenuConnector extends WindowConnector implements MenuServerRPC {

	private static final long serialVersionUID = -2714633284761401664L;

	public MenuConnector() {

		// register RPC so that server-side can send us commands. We can
		// delegate both calls directly to the widget.
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

		// register as a open/close-listener so that we can notify the server
		// side.
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

	@Override
	public void menuOpened() {
		// Proxy method call to server-side
		RpcProxy.create(MenuServerRPC.class, this).menuOpened();
	}

	@Override
	public void menuClosed() {
		// Proxy method call to server-side
		RpcProxy.create(MenuServerRPC.class, this).menuClosed();
	}

}
