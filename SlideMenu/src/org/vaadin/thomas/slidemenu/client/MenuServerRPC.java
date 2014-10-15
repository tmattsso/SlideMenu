package org.vaadin.thomas.slidemenu.client;

import com.vaadin.shared.communication.ServerRpc;

public interface MenuServerRPC extends ServerRpc {

	public void menuOpened();

	public void menuClosed();
}