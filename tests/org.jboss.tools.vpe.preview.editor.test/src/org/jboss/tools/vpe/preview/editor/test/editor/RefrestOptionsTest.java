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

import static org.jboss.tools.vpe.preview.core.preferences.VpvPreferencesInitializer.REFRESH_ON_CHANGE_PREFERENCES;
import static org.jboss.tools.vpe.preview.core.preferences.VpvPreferencesInitializer.REFRESH_ON_SAVE_PREFERENCES;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationAdapter;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.jboss.tools.jst.web.ui.internal.editor.editor.IVisualEditor;
import org.jboss.tools.jst.web.ui.internal.editor.jspeditor.JSPMultiPageEditor;
import org.jboss.tools.vpe.preview.editor.Activator;
import org.jboss.tools.vpe.preview.editor.VpvEditor;
import org.jboss.tools.vpe.preview.editor.VpvEditorController;
import org.jboss.tools.vpe.preview.editor.test.util.TestUtil;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * 
 * @author Konstantin Marmalyukov (kmarmaliykov)
 *
 */

@FixMethodOrder(MethodSorters.JVM)
public class RefrestOptionsTest extends RefreshTest{
	private static final String PROJECT_NAME = "html5-test"; //$NON-NLS-1$
	private static final String PAGE_NAME = "replace.html"; //$NON-NLS-1$
	
	private JSPMultiPageEditor editor;
	private VpvEditor visualEditor;
	
	@Before
	public void openTestPage() throws CoreException, IOException {
		setLocationChanged(false);
		
		final IFile elementPageFile = (IFile) TestUtil.getComponentPath(PAGE_NAME, PROJECT_NAME);  
		editor = openEditor(elementPageFile);
		editor.pageChange(IVisualEditor.VISUALSOURCE_MODE);
		
		VpvEditorController controller = TestUtil.getVpvEditorController(editor);
		visualEditor = controller.getPageContext().getEditPart().getVisualEditor();
		assertNotNull(visualEditor);
		
		TestUtil.waitForJobs();
	}
	
	@Test
	public void norefreshTest() throws Throwable {
		setException(null);
		
		Browser browser = visualEditor.getBrowser();
		assertNotNull(browser);
		LocationListener norefreshListener = new LocationAdapter() {
			@Override
			public void changed(LocationEvent event) {
				fail(event.location);
			}
		};
		browser.addLocationListener(norefreshListener);
		
		IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();
		preferences.setValue(REFRESH_ON_SAVE_PREFERENCES, false);
		preferences.setValue(REFRESH_ON_CHANGE_PREFERENCES, false);
		visualEditor.getActionBar().updateRefreshItemsAccordingToPreferences();
		
		replaceText(36, 26, "Norefresh replacement text"); //$NON-NLS-1$
		editor.doSave(new NullProgressMonitor());
		browser.removeLocationListener(norefreshListener);
	}
	
	//this test is disabled because of unstable working
	//@Test
	public void refreshOnSaveTest() throws Throwable {
		setException(new Exception("Refresh does not happens")); //$NON-NLS-1$
		Browser browser = visualEditor.getBrowser();
		assertNotNull(browser);				

		IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();
		preferences.setValue(REFRESH_ON_SAVE_PREFERENCES, true);
		preferences.setValue(REFRESH_ON_CHANGE_PREFERENCES, false);
		visualEditor.getActionBar().updateRefreshItemsAccordingToPreferences();
		TestUtil.waitForJobs();
		
		LocationListener locationListener = new LocationAdapter() {
			@Override
			public void changed(LocationEvent event) {
				//refresh happens
				setException(null);
				setLocationChanged(true);
			}
		};
		browser.addLocationListener(locationListener);
		
		TestUtil.waitForJobs();
		
		replaceText(36, 26, "onSaveRefresh replacement1"); //$NON-NLS-1$
		TestUtil.waitForJobs();
		
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		page.saveEditor(editor, false);

		waitForRefresh();
		
		browser.removeLocationListener(locationListener);
		if (getException() != null) {
			throw getException();
		}
	}
	
	//@Test
	//disabled because of random failures on RHEL6. On RHEL7 works fine
	public void refreshOnChangeTest() throws Throwable {
		setException(new Exception("Refresh does not happens")); //$NON-NLS-1$
		Browser browser = visualEditor.getBrowser();
		assertNotNull(browser);
		LocationListener locationListener = new LocationAdapter() {
			@Override
			public void changed(LocationEvent event) {
				//refresh happens
				setException(null);
				setLocationChanged(true);
			}
		};
		browser.addLocationListener(locationListener);
		
		IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();
		preferences.setValue(REFRESH_ON_SAVE_PREFERENCES, false);
		preferences.setValue(REFRESH_ON_CHANGE_PREFERENCES, true);
		visualEditor.getActionBar().updateRefreshItemsAccordingToPreferences();

		replaceText(36, 26, "Onchange1 replacement text"); //$NON-NLS-1$
		
		waitForRefresh();
		
		browser.removeLocationListener(locationListener);
		if (getException() != null) {
			throw getException();
		}
	}
	
	private void replaceText(int end, int start, String text) throws BadLocationException {
		IDocument document = ((JSPMultiPageEditor)editor).getDocumentProvider().getDocument(editor.getEditorInput());
		assertNotNull(document);
		document.replace(end, start, text);
	}
}
