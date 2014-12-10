/*******************************************************************************
 * Copyright (c) 2015 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.vpe.preview.editor.test.editor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.jboss.tools.jst.web.ui.internal.editor.jspeditor.JSPMultiPageEditor;
import org.jboss.tools.vpe.preview.editor.VpvEditorController;
import org.jboss.tools.vpe.preview.editor.test.util.TestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * 
 * @author Konstantin Marmalyukov (kmarmaliykov)
 *
 */

@RunWith(JUnit4.class)
public abstract class VpvTest implements ILogListener {
	protected static final String ABOUT_BLANK = "about:blank"; //$NON-NLS-1$
	/** Editor in which we open visual page. */
	protected final static String EDITOR_ID = "org.jboss.tools.jst.jsp.jspeditor.JSPTextEditor"; //$NON-NLS-1$

	/** Collects exceptions. */
	private Throwable exception;

	/** check warning log. */
	private boolean checkWarning = false;

	@Before
	public void setUp() throws Exception {
		Platform.addLogListener(this);
		closeEditors();
		setException(null);
	}

	@After
	public void tearDown() throws Exception {
		closeEditors();
		Platform.removeLogListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.ILogListener#logging(org.eclipse.core.runtime
	 * .IStatus, java.lang.String)
	 */
	/**
	 * Logging.
	 * 
	 * @param status
	 *            the status
	 * @param plugin
	 *            the plugin
	 */
	public void logging(IStatus status, String plugin) {
		// Not perfect solution but at least now exceptions in other plug-ins aren't going to break VPE tests
		if (org.jboss.tools.vpe.preview.editor.Activator.PLUGIN_ID.equals(status.getPlugin())) {
			switch (status.getSeverity()) {
			case IStatus.ERROR:
				setException(status.getException());
				break;
			case IStatus.WARNING:
				if (isCheckWarning())
					setException(status.getException());
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 * Closes given {@code editor}.
	 */
    protected void closeEditor(IEditorPart editor) {
		boolean closed = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().closeEditor(editor, false);
		assertFalse(!closed);
	}

	/**
	 * close all opened editors.
	 */
	protected static void closeEditors() {
		// clean up defrerred events 
		while (Display.getCurrent().readAndDispatch());
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		page.closeAllEditors(false);
	}

	/**
	 * Open JSPMultiPageEditor editor.
	 * 
	 * @param input
	 *            the input
	 * 
	 * @return the JSP multi page editor
	 * 
	 * @throws PartInitException
	 *             the part init exception
	 */
	protected JSPMultiPageEditor openEditor(IEditorInput input) throws PartInitException {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		JSPMultiPageEditor part = (JSPMultiPageEditor) page.openEditor(input, getEditorID(), true);
		assertNotNull(part);
		// It is needed to fix issues related with deferred messages processing like
		// java.lang.NullPointerException
		//		at org.eclipse.wst.sse.ui.internal.style.SemanticHighlightingReconciler.reconcile(SemanticHighlightingReconciler.java:115)
		//		at org.eclipse.wst.sse.ui.internal.reconcile.DocumentRegionProcessor.endProcessing(DocumentRegionProcessor.java:119)
		//		at org.eclipse.wst.sse.ui.internal.reconcile.DirtyRegionProcessor.run(DirtyRegionProcessor.java:682)
		//		at org.eclipse.core.internal.jobs.Worker.run(Worker.java:54)
		// it happen because test goes so fast and editor is got closed until deferred events are processed
		while (Display.getCurrent().readAndDispatch());

		return part;
	}

	/**
	 * Open JSPMultiPageEditor editor.
	 * 
	 * @param input
	 *            the input
	 * 
	 * @return the JSP multi page editor
	 * 
	 * @throws PartInitException
	 *             the part init exception
	 */
	protected JSPMultiPageEditor openEditor(IFile input) throws PartInitException {
		// get editor
		JSPMultiPageEditor part = (JSPMultiPageEditor) IDE.openEditor(PlatformUI
				.getWorkbench().getActiveWorkbenchWindow().getActivePage(),
				input);

		assertNotNull(part);
		return part;

	}
	
	/**
	 * Gets the exception.
	 * 
	 * @return the exception
	 */
	protected Throwable getException() {
		return exception;
	}

	/**
	 * Sets the exception.
	 * 
	 * @param exception
	 *            the exception to set
	 */
	protected void setException(Throwable exception) {
		this.exception = exception;
	}

	/**
	 * Checks if is check warning.
	 * 
	 * @return the checkWarning
	 */
	protected boolean isCheckWarning() {
		return checkWarning;
	}

	/**
	 * Sets the check warning.
	 * 
	 * @param checkWarning
	 *            the checkWarning to set
	 */
	protected void setCheckWarning(boolean checkWarning) {
		this.checkWarning = checkWarning;
	}

	/**
	 * Opens specified file in the VPE editor.
	 * 
	 * @param projectName
	 *            the name of the project
	 * @param fileName
	 *            the name of the file
	 * 
	 * @return VpeController
	 * @throws CoreException
	 * @throws IOException
	 */
	protected VpvEditorController openInVpv(String projectName, String fileName)
			throws CoreException, IOException {
		// get test page path
		final IFile file = (IFile) TestUtil.getComponentPath(fileName,
				projectName);
		assertNotNull("Could not open specified file." //$NON-NLS-1$
				+ " componentPage = " + fileName //$NON-NLS-1$
				+ ";projectName = " + projectName, file); //$NON-NLS-1$

		final IEditorInput input = new FileEditorInput(file);
		assertNotNull("Editor input is null", input); //$NON-NLS-1$

		// open and get the editor
		final JSPMultiPageEditor part = openEditor(input);

		final VpvEditorController vpeController = TestUtil.getVpvEditorController(part);
		return vpeController;
	}
	
	protected String getEditorID(){
		return EDITOR_ID;
	}
}
