package org.vaadin.johannest.diagnosticservlet;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import com.vaadin.server.ClientConnector;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Component;

public class DiagnosticAndTestServletSession extends VaadinSession {
	private static final long serialVersionUID = 4596901275146146127L;

	public DiagnosticAndTestServletSession(VaadinService service) {
		super(service);
	}

	private Map<Class<? extends ClientConnector>, Integer> sequences = new HashMap<Class<? extends ClientConnector>, Integer>();

	@SuppressWarnings("deprecation")
	@Override
	public String createConnectorId(ClientConnector connector) {
		String connectorId = "";
		if (connector instanceof Component) {
			Component component = (Component) connector;
			connectorId = component.getId() == null ? super
					.createConnectorId(connector) : component.getId();
		} else {
			connectorId = super.createConnectorId(connector);
		}

		Logger.getLogger(DiagnosticAndTestServletSession.class.getName()).fine(
				"Created connector id " + connectorId + " for "
						+ connector.getClass());

		return connectorId;
	}

	private int nextId(Class<? extends ClientConnector> c) {
		Integer nextid = 0;
		if (sequences.get(c)!=null) {
			nextid = sequences.get(c);
		}
		sequences.put(c, nextid+1);
		return nextid;
	}

}