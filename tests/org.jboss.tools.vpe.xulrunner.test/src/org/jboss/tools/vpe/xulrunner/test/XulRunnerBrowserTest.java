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

import static org.junit.Assume.assumeTrue;
import static org.junit.Assert.assertNotNull;
import org.jboss.tools.vpe.base.test.VpeTest;
import org.junit.Test;

public class XulRunnerBrowserTest extends XulRunnerAbstractTest {

	@Test
	public void testXulRunnerBrowser() {
		assumeTrue("Not supported environment", !VpeTest.skipTests);
		assertNotNull(xulRunnerView);
	}
}
