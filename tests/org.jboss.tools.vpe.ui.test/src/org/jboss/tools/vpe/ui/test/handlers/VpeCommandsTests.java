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
package org.jboss.tools.vpe.ui.test.handlers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.State;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.handlers.RegistryToggleState;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.jboss.tools.jst.web.ui.WebUiPlugin;
import org.jboss.tools.jst.web.ui.internal.editor.bundle.BundleMap;
import org.jboss.tools.jst.web.ui.internal.editor.jspeditor.JSPMultiPageEditor;
import org.jboss.tools.jst.web.ui.internal.editor.jspeditor.JSPMultiPageEditorPart;
import org.jboss.tools.jst.web.ui.internal.editor.preferences.IVpePreferencesPage;
import org.jboss.tools.vpe.base.test.TestUtil;
import org.jboss.tools.vpe.base.test.VpeTest;
import org.jboss.tools.vpe.editor.IVisualEditor2;
import org.jboss.tools.vpe.editor.VisualController;
import org.jboss.tools.vpe.editor.toolbar.IVpeToolBarManager;
import org.jboss.tools.vpe.handlers.PageDesignOptionsHandler;
import org.jboss.tools.vpe.handlers.PreferencesHandler;
import org.jboss.tools.vpe.handlers.RefreshHandler;
import org.jboss.tools.vpe.handlers.RotateEditorsHandler;
import org.jboss.tools.vpe.ui.test.VpeUiTests;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

/**
 * Class which created for testing VPE commands behavior, see
 * https://jira.jboss.org/browse/JBIDE-7383
 * 
 * @author mareshkau
 * 
 */
public class VpeCommandsTests extends VpeTest {

	private static String[] VPE_COMMAND_ID;
	private static Map<String, Boolean> VPE_PREF_COMMANDS_STATES;
	private Command[] commands;
	private IHandlerService handlerService;
	private static IPreferenceStore preferences;

	private static final int ROTATION_NUM = 4;

	static {
		VPE_COMMAND_ID = new String[] { PageDesignOptionsHandler.COMMAND_ID,
				PreferencesHandler.COMMAND_ID, RefreshHandler.COMMAND_ID,
				RotateEditorsHandler.COMMAND_ID };

		preferences = WebUiPlugin.getDefault().getPreferenceStore();
		VPE_PREF_COMMANDS_STATES = new HashMap<String, Boolean>();

	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		ICommandService commandService = (ICommandService) PlatformUI
				.getWorkbench().getService(ICommandService.class);
		commands = new Command[VPE_COMMAND_ID.length];
		for (int i = 0; i < commands.length; i++) {
			String commandId = VPE_COMMAND_ID[i];
			Boolean commandStateVal = VPE_PREF_COMMANDS_STATES.get(commandId);
			Command command = commandService.getCommand(commandId);
			State commandState = command.getState(RegistryToggleState.STATE_ID);
			if (commandState != null) {
				commandState.setValue(commandStateVal);
			}
			commands[i] = command;
		}
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IViewReference[] views = page.getViewReferences();
		for (IViewReference iViewReference : views) {
			page.hideView(iViewReference);
		}
		handlerService = (IHandlerService) PlatformUI.getWorkbench()
				.getService(IHandlerService.class);
	}

	public VpeCommandsTests() {
	}

	/**
	 * Test VPE command state
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testCommandState() throws Throwable {
		JSPMultiPageEditor multiPageEditor = openInputUserNameJsp();
		checkCommandState(true);
		pageChange(multiPageEditor, 1);
		checkCommandState(false);
		pageChange(multiPageEditor, multiPageEditor.getPreviewIndex());
		checkCommandState(false);
		pageChange(multiPageEditor, 0);
		checkCommandState(true);
	}

	/**
	 * Test 'Rotate editors' toolbar button
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testRotateEditors() throws Throwable {

		JSPMultiPageEditor multiPageEditor = openInputUserNameJsp();
		VisualController vpeController = (VisualController) multiPageEditor
				.getVisualEditor().getController();
		IVisualEditor2 editPart = vpeController.getPageContext().getEditPart();
		int oldVisualOrientation = editPart.getContainer().getOrientation();
		int prevVisualOrientation = oldVisualOrientation;

		String oldPrefOrientation = preferences
				.getString(IVpePreferencesPage.VISUAL_SOURCE_EDITORS_SPLITTING);

		for (int i = 0; i < ROTATION_NUM; i++) {

			handlerService
					.executeCommand(RotateEditorsHandler.COMMAND_ID, null);
			TestUtil.delay();

			int newVisualOrientation = editPart.getContainer().getOrientation();
			String newPrefOrientation = preferences
					.getString(IVpePreferencesPage.VISUAL_SOURCE_EDITORS_SPLITTING);

			if (i != ROTATION_NUM - 1) {
				assertNotSame(oldPrefOrientation, newPrefOrientation);
				assertNotSame(prevVisualOrientation, newVisualOrientation);
			} else {
				assertEquals(oldPrefOrientation, newPrefOrientation);
				assertEquals(oldVisualOrientation, newVisualOrientation);
			}

			if (prevVisualOrientation == SWT.HORIZONTAL) {
				assertEquals(SWT.VERTICAL, newVisualOrientation);
			} else {
				assertEquals(SWT.HORIZONTAL, newVisualOrientation);
			}

			prevVisualOrientation = newVisualOrientation;
		}
	}





	private JSPMultiPageEditor openInputUserNameJsp() throws CoreException,
			IOException, SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		IFile vpeFile = (IFile) TestUtil.getComponentPath("inputUserName.jsp", //$NON-NLS-1$
				VpeUiTests.IMPORT_PROJECT_NAME);
		return openFileInVpe(vpeFile);
	}

	private JSPMultiPageEditor openFileInVpe(IFile fileToOpen)
			throws PartInitException, SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		// Open file in the VPE
		IEditorInput input = new FileEditorInput(fileToOpen);
		JSPMultiPageEditor multiPageEditor = openEditor(input);
		TestUtil.delay();
		// Open the 'Visual/Source' tab
		pageChange(multiPageEditor, 0);
		return multiPageEditor;
	}

	private void pageChange(JSPMultiPageEditor multiPageEditor, int index)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Method pageChange;
		pageChange = MultiPageEditorPart.class.getDeclaredMethod(
				"setActivePage", new Class[] { int.class });
		pageChange.setAccessible(true);
		pageChange.invoke(multiPageEditor, index);
		multiPageEditor.pageChange(index);
		TestUtil.delay();
	}

	// checks command state
	private void checkCommandState(boolean expected) {
		for (Command vpeCommand : commands) {
			assertEquals("Command " + vpeCommand.getId() + " should be active",
					expected, vpeCommand.isEnabled());
		}
	}

	private Command getCommandById(String commandId) throws Exception {

		for (Command vpeCommand : commands) {
			if (vpeCommand.getId().equals(commandId)) {
				return vpeCommand;
			}
		}
		throw new IllegalArgumentException("There is no commands with id " //$NON-NLS-1$
				+ commandId);
	}
}
