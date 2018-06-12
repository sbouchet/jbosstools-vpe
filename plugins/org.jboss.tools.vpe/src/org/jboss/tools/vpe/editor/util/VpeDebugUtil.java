/******************************************************************************* 
* Copyright (c) 2007 Red Hat, Inc.
* Distributed under license by Red Hat, Inc. All rights reserved.
* This program is made available under the terms of the
* Eclipse Public License v1.0 which accompanies this distribution,
* and is available at http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*     Red Hat, Inc. - initial API and implementation
******************************************************************************/
package org.jboss.tools.vpe.editor.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.eclipse.core.runtime.Platform;
import org.eclipse.wst.sse.core.internal.provisional.INodeNotifier;
import org.jboss.tools.vpe.VpeDebug;
import org.w3c.dom.Node;

/**
 * @author Max Areshkau (mareshkau@exadel.com)
 *
 *Class created to print debug info
 */
public class VpeDebugUtil {
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat();
	static {
		formatter.applyPattern("hh:mm:ss.SSS"); //$NON-NLS-1$
	}
	/**
	 * Prints debug info on console
	 * @param msg
	 */
	public static void debugInfo(String msg) {
		
		if(Platform.inDebugMode()) {

			System.out.print(formatter.format(new Date())+":"+ msg); //$NON-NLS-1$
		}
	}
	/**
	 * 
	 */
	public static void debugVPEDnDEvents(String msg) {
		if(VpeDebug.PRINT_VISUAL_DRAGDROP_EVENT) {
			
			System.out.println(formatter.format(new Date())+":"+ msg); //$NON-NLS-1$
		}
	}
	// for debug
	public static void printSourceEvent(INodeNotifier notifier, int eventType, Object feature, Object oldValue,
			Object newValue, int pos) {
		
		System.out.println(">>> eventType: " + INodeNotifier.EVENT_TYPE_STRINGS[eventType] + //$NON-NLS-1$
				"  pos: " + pos + "  notifier: " + ((Node) notifier).getNodeName() + //$NON-NLS-1$ //$NON-NLS-2$
				"  hashCode: " + notifier.hashCode()); //$NON-NLS-1$
		if (feature != null) {
			if (feature instanceof Node) {
				System.out.println("     feature: " + ((Node) feature).getNodeType() + //$NON-NLS-1$
						Constants.WHITE_SPACE + ((Node) feature).getNodeName() + "  hashCode: " + feature.hashCode()); //$NON-NLS-1$
			} else {
				System.out.println("     feature: " + feature); //$NON-NLS-1$
			}
		}
		if (oldValue != null) {
			if (oldValue instanceof Node) {
				System.out.println("     oldValue: " + ((Node) oldValue).getNodeName() + //$NON-NLS-1$
						"  hashCode: " + oldValue.hashCode()); //$NON-NLS-1$
			} else {
				System.out.println("     oldValue: " + oldValue); //$NON-NLS-1$
			}
		}
		if (newValue != null) {
			if (newValue instanceof Node) {
				System.out.println("     newValue: " + ((Node) newValue).getNodeName() + //$NON-NLS-1$
						"  hashCode: " + newValue.hashCode() + Constants.WHITE_SPACE + ((Node) newValue).getNodeType()); //$NON-NLS-1$
			} else {
				System.out.println("     newValue: " + newValue); //$NON-NLS-1$
			}
		}
	}
}
