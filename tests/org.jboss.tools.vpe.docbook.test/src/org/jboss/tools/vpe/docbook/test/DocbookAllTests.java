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
package org.jboss.tools.vpe.docbook.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Class for testing all Docbook components
 * 
 * @author Denis Vinnichek (dvinnichek)
 * 
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({DocbookComponentContentTest.class})
public class DocbookAllTests {

	/*
	 * Import project name
	 */
	public static final String IMPORT_PROJECT_NAME = "DocbookTest"; //$NON-NLS-1$
}
