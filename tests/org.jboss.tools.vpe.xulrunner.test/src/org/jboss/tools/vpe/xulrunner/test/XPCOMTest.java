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
package org.jboss.tools.vpe.xulrunner.test;

import java.util.List;

import org.jboss.tools.vpe.base.test.VpeTest;
import org.jboss.tools.vpe.xulrunner.util.XPCOM;
import org.junit.Test;
import org.mozilla.interfaces.jsdIScript;
import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMDocumentView;
import org.mozilla.interfaces.nsIDOMNSDocument;
import org.mozilla.interfaces.nsIDOMNSElement;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.xpcom.XPCOMException;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

/**
 * Tests for {@link XPCOM} class.
 * 
 * @author Yahor Radtsevich (yradtsevich)
 */
public class XPCOMTest extends XulRunnerAbstractTest {

	/**
	 * Test method for
	 * {@link org.jboss.tools.vpe.xulrunner.XPCOM#queryInterface(org.mozilla.interfaces.nsISupports, java.lang.Class)}
	 * .
	 */
	@Test
	public void testQueryInterface() {
		assumeTrue("Not supported environment", !VpeTest.skipTests);
		nsIDOMDocument document = xulRunnerEditor.getDOMDocument();

		assertTrue(XPCOM.queryInterface(document, nsIDOMDocument.class) instanceof nsIDOMDocument);
		assertTrue(XPCOM.queryInterface(document, nsIDOMNSDocument.class) instanceof nsIDOMNSDocument);

		try {
			XPCOM.queryInterface(document, nsIDOMNSElement.class);
			fail("Expected Exception XPCOMException");
		} catch (XPCOMException e) {
			// do nothing, it's OK
		}
	}

	/**
	 * Test method for
	 * {@link org.jboss.tools.vpe.xulrunner.XPCOM#getInterfaceId(java.lang.Class)}
	 * .
	 */
	@Test
	public void testGetInterfaceId() {
		assumeTrue("Not supported environment", !VpeTest.skipTests);
		assertEquals(nsIDOMDocument.NS_IDOMDOCUMENT_IID, XPCOM.getInterfaceId(nsIDOMDocument.class));
		assertEquals(nsIDOMNode.NS_IDOMNODE_IID, XPCOM.getInterfaceId(nsIDOMNode.class));
		assertEquals(jsdIScript.JSDISCRIPT_IID, XPCOM.getInterfaceId(jsdIScript.class));
	}

	/**
	 * Test method for
	 * {@link org.jboss.tools.vpe.xulrunner.XPCOM#getSupportedInterfaces(org.mozilla.interfaces.nsISupports)}
	 * .
	 */
	@Test
	public void testGetSupportedInterfaces() {
		assumeTrue("Not supported environment", !VpeTest.skipTests);
		nsIDOMDocument document = xulRunnerEditor.getDOMDocument();

		List<Class<? extends nsISupports>> supportedInterfaces = XPCOM.getSupportedInterfaces(document);

		assertTrue(supportedInterfaces.contains(nsISupports.class));
		assertTrue(supportedInterfaces.contains(nsIDOMNode.class));
		assertTrue(supportedInterfaces.contains(nsIDOMDocumentView.class));
		assertTrue(supportedInterfaces.contains(nsIDOMDocument.class));
	}
}
