package org.vaadin.thomas.slidemenu.client;

import com.vaadin.shared.communication.ClientRpc;

public interface MenuClientRPC extends ClientRpc {

	/**
	 * Call this method to open the menu from the server side
	 */
	public void openMenu();

	/**
	 * Call this method to close the menu from the server side
	 */
	public void closeMenu();
}
