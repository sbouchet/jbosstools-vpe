/******************************************************************************* 
 * Copyright (c) 2015 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.eclipse.swt.browser;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.swt.browser.messages"; //$NON-NLS-1$
	public static String XULRunnerInitializer_Bundle_doesnt_contain;
	public static String XULRunnerInitializer_Bundle_is_not_found;
	public static String XULRunnerInitializer_Cannot_get_path_to_XULRunner_from_bundle;
	public static String XULRunnerInitializer_Cannot_check_mozilla_profile_availability;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
