package org.vaadin.thomas.slidemenu.client;

import com.vaadin.shared.communication.ClientRpc;

public interface MenuClientRPC extends ClientRpc {

	public void openMenu();

	public void closeMenu();
}
