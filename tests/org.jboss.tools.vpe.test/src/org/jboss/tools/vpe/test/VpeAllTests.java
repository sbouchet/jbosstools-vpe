/*******************************************************************************
 * Copyright (c) 2007 Exadel, Inc. and Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Exadel, Inc. and Red Hat, Inc. - initial API and implementation
 ******************************************************************************/ 
package org.jboss.tools.vpe.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jboss.tools.vpe.base.test.VpeTestSetup;
import org.jboss.tools.vpe.editor.util.VpeStyleUtilTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Class created for run tests for org.jboss.tools.vpe plugin.
 * 
 * @author Max Areshkau
 * 
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
		VpeStyleUtilTest.class
})
public class VpeAllTests extends TestCase{
}
