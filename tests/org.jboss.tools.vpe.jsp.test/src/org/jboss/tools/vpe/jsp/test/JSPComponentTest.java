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
package org.jboss.tools.vpe.jsp.test;

import org.eclipse.core.resources.IFile;
import org.jboss.tools.vpe.base.test.TestUtil;
import org.jboss.tools.vpe.base.test.VpeTest;
import org.junit.Test;

/**
 * 
 * Class for testing all jsp components
 * 
 * @author dsakovich@exadel.com
 * 
 */
public class JSPComponentTest extends VpeTest {

	public JSPComponentTest() {
		setCheckWarning(false);
	}

	/**
	 * Test for jsp:declaration
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testDeclaration() throws Throwable {
		performTestForVpeComponent(
				(IFile) TestUtil.getComponentPath("components/declaration.jsp", JSPAllTests.IMPORT_PROJECT_NAME)); //$NON-NLS-1$
	}

	/**
	 * Test for jsp:expression
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testExpression() throws Throwable {
		performTestForVpeComponent(
				(IFile) TestUtil.getComponentPath("components/expression.jsp", JSPAllTests.IMPORT_PROJECT_NAME)); //$NON-NLS-1$
	}

	/**
	 * Test for jsp:scriptlet
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testScriptlet() throws Throwable {
		performTestForVpeComponent(
				(IFile) TestUtil.getComponentPath("components/scriptlet.jsp", JSPAllTests.IMPORT_PROJECT_NAME)); //$NON-NLS-1$
	}

	/**
	 * Test for jsp:directive.attribute
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testDirectiveAttribute() throws Throwable {
		performTestForVpeComponent(
				(IFile) TestUtil.getComponentPath("../WEB-INF/tags/catalog.tag", JSPAllTests.IMPORT_PROJECT_NAME)); //$NON-NLS-1$
	}

	/**
	 * Test for jsp:directive.include
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testDirectiveInclude() throws Throwable {
		performTestForVpeComponent((IFile) TestUtil.getComponentPath("components/directive_include_absolute.jsp", //$NON-NLS-1$
				JSPAllTests.IMPORT_PROJECT_NAME));
		performTestForVpeComponent((IFile) TestUtil.getComponentPath("components/directive_include_relative.jsp", //$NON-NLS-1$
				JSPAllTests.IMPORT_PROJECT_NAME));

	}

	/**
	 * Test for jsp:include
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testInclude() throws Throwable {
		performTestForVpeComponent(
				(IFile) TestUtil.getComponentPath("components/include_absolute.jsp", JSPAllTests.IMPORT_PROJECT_NAME)); //$NON-NLS-1$
		performTestForVpeComponent(
				(IFile) TestUtil.getComponentPath("components/include_relative.jsp", JSPAllTests.IMPORT_PROJECT_NAME)); //$NON-NLS-1$
	}

	/**
	 * Test for jsp:directive.page
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testDirectivePage() throws Throwable {
		performTestForVpeComponent(
				(IFile) TestUtil.getComponentPath("components/directive_page.jsp", JSPAllTests.IMPORT_PROJECT_NAME)); //$NON-NLS-1$
	}

	/**
	 * Test for jsp:directive.tag
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testDirectiveTag() throws Throwable {
		performTestForVpeComponent(
				(IFile) TestUtil.getComponentPath("components/directive_tag.jsp", JSPAllTests.IMPORT_PROJECT_NAME)); //$NON-NLS-1$
	}

	/**
	 * Test for jsp:directive.taglib
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testDirectiveTaglib() throws Throwable {
		performTestForVpeComponent(
				(IFile) TestUtil.getComponentPath("components/directive_taglib.jsp", JSPAllTests.IMPORT_PROJECT_NAME)); //$NON-NLS-1$
	}

	/**
	 * Test for jsp:directive.variable
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testDirectiveVariable() throws Throwable {
		performTestForVpeComponent(
				(IFile) TestUtil.getComponentPath("../WEB-INF/tags/catalog.tag", JSPAllTests.IMPORT_PROJECT_NAME)); //$NON-NLS-1$
	}

	/**
	 * Test for jsp:attribute
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testAttribute() throws Throwable {
		performTestForVpeComponent(
				(IFile) TestUtil.getComponentPath("components/attribute.jsp", JSPAllTests.IMPORT_PROJECT_NAME)); //$NON-NLS-1$
	}

	/**
	 * Test for jsp:body
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testBody() throws Throwable {
		performTestForVpeComponent(
				(IFile) TestUtil.getComponentPath("components/body.jsp", JSPAllTests.IMPORT_PROJECT_NAME)); //$NON-NLS-1$
	}

	/**
	 * Test for jsp:element
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testElement() throws Throwable {
		performTestForVpeComponent(
				(IFile) TestUtil.getComponentPath("components/element.jsp", JSPAllTests.IMPORT_PROJECT_NAME)); //$NON-NLS-1$
	}

	/**
	 * Test for jsp:doBody
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testDoBody() throws Throwable {
		performTestForVpeComponent(
				(IFile) TestUtil.getComponentPath("../WEB-INF/tags/double.tag", JSPAllTests.IMPORT_PROJECT_NAME)); //$NON-NLS-1$
	}

	/**
	 * Test for jsp:forward
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testForward() throws Throwable {
		performTestForVpeComponent(
				(IFile) TestUtil.getComponentPath("components/forward.jsp", JSPAllTests.IMPORT_PROJECT_NAME)); //$NON-NLS-1$
	}

	/**
	 * Test for jsp:getProperty
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testGetProperty() throws Throwable {
		performTestForVpeComponent(
				(IFile) TestUtil.getComponentPath("components/get_property.jsp", JSPAllTests.IMPORT_PROJECT_NAME)); //$NON-NLS-1$
	}

	/**
	 * Test for jsp:invoke
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testInvoke() throws Throwable {
		performTestForVpeComponent(
				(IFile) TestUtil.getComponentPath("../WEB-INF/tags/catalog.tag", JSPAllTests.IMPORT_PROJECT_NAME)); //$NON-NLS-1$
	}

	/**
	 * Test for jsp:output
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testOutput() throws Throwable {
		performTestForVpeComponent(
				(IFile) TestUtil.getComponentPath("components/output.jsp", JSPAllTests.IMPORT_PROJECT_NAME)); //$NON-NLS-1$
	}

	/**
	 * Test for jsp:plugin
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testPlugin() throws Throwable {
		performTestForVpeComponent(
				(IFile) TestUtil.getComponentPath("components/plugin.jsp", JSPAllTests.IMPORT_PROJECT_NAME)); //$NON-NLS-1$
	}

	/**
	 * Test for jsp:root
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testRoot() throws Throwable {
		performTestForVpeComponent(
				(IFile) TestUtil.getComponentPath("components/root.jsp", JSPAllTests.IMPORT_PROJECT_NAME)); //$NON-NLS-1$
	}

	/**
	 * Test for jsp:setProperty
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testSetProperty() throws Throwable {
		performTestForVpeComponent(
				(IFile) TestUtil.getComponentPath("components/set_property.jsp", JSPAllTests.IMPORT_PROJECT_NAME)); //$NON-NLS-1$
	}

	/**
	 * Test for jsp:text
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testText() throws Throwable {
		performTestForVpeComponent(
				(IFile) TestUtil.getComponentPath("components/text.jsp", JSPAllTests.IMPORT_PROJECT_NAME)); //$NON-NLS-1$
	}

	/**
	 * Test for jsp:useBean
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testUseBean() throws Throwable {
		performTestForVpeComponent(
				(IFile) TestUtil.getComponentPath("components/useBean.jsp", JSPAllTests.IMPORT_PROJECT_NAME)); //$NON-NLS-1$
	}

}
