package org.vaadin.thomas.slidemenu.client;

import com.vaadin.shared.communication.ServerRpc;

public interface MenuServerRPC extends ServerRpc {

	/**
	 * Called when menu is opened from any source
	 */
	public void menuOpened();

	/**
	 * Called when menu is closed from any source
	 */
	public void menuClosed();
}