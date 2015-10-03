/******************************************************************************* 
 * Copyright (c) 2012 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.vpe.html.test;

import org.jboss.tools.vpe.html.test.jbide.JBIDE3280Test;
import org.jboss.tools.vpe.html.test.jbide.TestNPEinPreviewJbide10178;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.Test;
import junit.framework.TestSuite;
/**
 * Test suite containing all important HTML tests
 * 
 * @author Yahor Radtsevich (yradtsevich)
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({JBIDE3280Test.class,TestNPEinPreviewJbide10178.class})
public class HtmlAllImportantTests {
}