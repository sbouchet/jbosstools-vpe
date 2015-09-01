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


public class WebKitInitializer {

	private static final String USE_WEB_KIT_GTK = "org.eclipse.swt.browser.UseWebKitGTK"; //$NON-NLS-1$
	
	static {
		String useWebKitGTK = System.getProperty(USE_WEB_KIT_GTK);
		if (useWebKitGTK == null) {
			System.setProperty(USE_WEB_KIT_GTK, "false"); //$NON-NLS-1$
		}
	}

}
