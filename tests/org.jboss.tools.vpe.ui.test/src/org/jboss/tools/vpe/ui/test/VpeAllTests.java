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
package org.jboss.tools.vpe.ui.test;

import java.lang.reflect.Method;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.junit.internal.builders.AnnotatedBuilder;
import org.junit.internal.builders.JUnit4Builder;
import org.junit.runners.Suite;
import org.osgi.framework.Bundle;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Max Areshkau
 * 
 * Class created to run all ui tests for VPE together
 */
public class VpeAllTests {

	public static final String TESTS_ELEMENT = "tests"; //$NON-NLS-1$
	public static final String TEST_SUITE_PARAM = "testSuite"; //$NON-NLS-1$	
	public static final String METHOD_SUITE_NAME = "suite"; //$NON-NLS-1$
	public static final String VPE_TEST_PROJECT_NAME = "vpeTest"; //$NON-NLS-1$

	public static Test suite() {

		TestSuite result = new TestSuite();
		IExtension[] extensions = VPETestPlugin.getDefault().getVpeTestExtensions();
		for (IExtension extension : extensions) {
			IConfigurationElement[] confElements = extension
					.getConfigurationElements();
			for (IConfigurationElement configurationElement : confElements) {
				String clazz = configurationElement
						.getAttribute(TEST_SUITE_PARAM);
				if (TESTS_ELEMENT.equals(configurationElement.getName())) {
					try {
						Bundle bundle = Platform.getBundle(configurationElement
								.getNamespaceIdentifier());
						Class<?> testObject = bundle.loadClass(clazz);
						JUnit4TestAdapter adapter = new JUnit4TestAdapter(testObject);
						// null -because static method
						result.addTest(adapter);

					} catch (Exception e) {
						VPETestPlugin.getDefault().logError(e);
					}
				}
			}
		}
		return result;

	}
}
