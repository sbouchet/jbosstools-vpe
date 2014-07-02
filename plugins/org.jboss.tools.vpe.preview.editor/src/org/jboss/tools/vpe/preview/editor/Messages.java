package org.jboss.tools.vpe.preview.editor;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.jboss.tools.vpe.preview.editor.messages"; //$NON-NLS-1$
	public static String VpvEditorPart_SELECTION_BAR_NOT_INITIALIZED;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
