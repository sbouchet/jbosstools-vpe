/*******************************************************************************
 * Copyright (c) 2007-2010 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/ 
package org.jboss.tools.vpe.editor.util;

import java.util.HashSet;
import java.util.Set;


public class VisualDomUtil {
	
	public static String JSF_CORE_URI = "http://java.sun.com/jsf/core"; //$NON-NLS-1$
    public static String JSF_HTML_URI = "http://java.sun.com/jsf/html"; //$NON-NLS-1$
    public static String RICH_FACES_URI = "http://richfaces.org/rich"; //$NON-NLS-1$
    public static String A4J_URI = "http://richfaces.org/a4j"; //$NON-NLS-1$
    public static String FACELETS_URI = "http://java.sun.com/jsf/facelets"; //$NON-NLS-1$
    
    /**
     * Flag for correct JSF components
     */
     public static String FACET_JSF_TAG = "FACET-JSF-TAG"; //$NON-NLS-1$
     /**
      * Flag for RF, FACELETS, A4J tags
      */
     public static String FACET_ODD_TAGS = "FACET-ODD-TAGS"; //$NON-NLS-1$
     /**
      * Flag for plain HTML tags and text nodes
      */
     public static String FACET_HTML_TAGS = "FACET-HTML-TAGS"; //$NON-NLS-1$

	private static final String ACCESSIBILITY_SERVICE_CONTRACT_ID = "@mozilla.org/accessibilityService;1"; //$NON-NLS-1$
//	private static Reference<nsIAccessibilityService> accessibilityServiceCache = null;
	
	private static Set<String> escapedTags;
	
	static {
		escapedTags = new HashSet<String>();
		escapedTags.add("f:facet"); //$NON-NLS-1$
		escapedTags.add("f:selectItem"); //$NON-NLS-1$
		escapedTags.add("f:selectItems"); //$NON-NLS-1$
	}


	
     
}
