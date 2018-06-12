/*******************************************************************************
 * Copyright (c) 2007-2009 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.vpe.ui.test;

import org.jboss.tools.vpe.ui.test.dialog.VpeResourcesDialogTest;
import org.jboss.tools.vpe.ui.test.editor.CustomSashFormTest;
import org.jboss.tools.vpe.ui.test.editor.MultipleSelectionTest;
import org.jboss.tools.vpe.ui.test.handlers.VpeCommandsTests;
import org.jboss.tools.vpe.ui.test.preferences.VpeEditorPreferencesPageTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author mareshkau
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	VpeCommandsTests.class,
	VpeResourcesDialogTest.class,
	VpeEditorPreferencesPageTest.class,
	CustomSashFormTest.class,
	MultipleSelectionTest.class
})
public class VpeUiTests {
	public static final String IMPORT_PROJECT_NAME = "TestProject"; //$NON-NLS-1$
}
