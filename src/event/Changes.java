package event;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Changes {
	private final PropertyChangeSupport changes;

	public Changes(Object sourceBean) {
		changes = new PropertyChangeSupport(sourceBean);
	}

	public void addBeanPropertyChangeListener(PropertyChangeListener listener) {
		changes.addPropertyChangeListener(listener);
	}

	public void removeBeanPropertyChangeListener(PropertyChangeListener listener) {
		changes.removePropertyChangeListener(listener);
	}
}
