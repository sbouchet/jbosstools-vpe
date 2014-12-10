package org.jboss.tools.vpe.preview.editor.test.util;

import org.jboss.tools.test.util.xpl.DisplayHelper;

public class RefreshDelayHelper extends DisplayHelper {
	private boolean locationChanged;
	
	@Override
	protected boolean condition() {
		return locationChanged;
	}

	public void setLocationChanged(boolean locationChanged) {
		this.locationChanged = locationChanged;
	}
}
