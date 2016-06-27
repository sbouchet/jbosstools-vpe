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

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationAdapter;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.jboss.tools.jst.web.ui.internal.editor.editor.IVisualEditor;
import org.jboss.tools.jst.web.ui.internal.editor.jspeditor.JSPMultiPageEditor;
import org.jboss.tools.vpe.preview.editor.Activator;
import org.jboss.tools.vpe.preview.editor.VpvEditor;
import org.jboss.tools.vpe.preview.editor.VpvEditorController;
import org.jboss.tools.vpe.preview.editor.test.util.TestUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Konstantin Marmalyukov (kmarmaliykov)
 *
 */
@SuppressWarnings("restriction")
public class RefreshOptionsTest extends RefreshTest{
	private static final String PROJECT_NAME = "html5-test"; //$NON-NLS-1$
	private static final String PAGE_NAME = "replace.html"; //$NON-NLS-1$
	
	private JSPMultiPageEditor editor;
	private VpvEditor visualEditor;
	
	@Before
	public void openTestPage() throws CoreException, IOException {
		setLocationChanged(false);
		try {	
			final IFile elementPageFile = (IFile) TestUtil.getComponentPath(PAGE_NAME, PROJECT_NAME);  
			editor = openEditor(elementPageFile);
			editor.pageChange(IVisualEditor.VISUALSOURCE_MODE);
			
			VpvEditorController controller = TestUtil.getVpvEditorController(editor);
			visualEditor = controller.getPageContext().getEditPart().getVisualEditor();
			assertNotNull(visualEditor);
			
			TestUtil.waitForJobs();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void norefreshTest(){
		setException(null);
		try {
			Browser browser = visualEditor.getBrowser();
			
			setRefreshPreferences(false, false);
			LocationListener norefreshListener = setNoRefreshListener(browser);
			TestUtil.replaceText(editor, 36, 26, "Norefresh replacement text"); //$NON-NLS-1$
			saveEditor();
			waitForRefresh();
			browser.removeLocationListener(norefreshListener);
		} catch (Exception e) {
			setException(e);
		}
	}
	
	@Test
	public void refreshOnSaveTest() throws Throwable {
		setException(new Exception("Refresh does not happens")); //$NON-NLS-1$
		Browser browser = visualEditor.getBrowser();

		setRefreshPreferences(true, false);
		LocationListener locationListener = setNoRefreshListener(browser);
		TestUtil.replaceText(editor, 36, 26, "onSaveRefresh replacement1"); //$NON-NLS-1$
		waitForRefresh();
		browser.removeLocationListener(locationListener);
		
		locationListener = setRefreshListener(browser);
		saveEditor();
		waitForRefresh();
		browser.removeLocationListener(locationListener);
		if (getException() != null) {
			throw getException();
		}
	}
	
	@Test
	public void refreshOnChangeTest() throws Throwable {
		setException(new Exception("Refresh does not happens")); //$NON-NLS-1$
		Browser browser = visualEditor.getBrowser();
		LocationListener locationListener = setRefreshListener(browser);
		browser.addLocationListener(locationListener);
		
		setRefreshPreferences(false, true);

		TestUtil.replaceText(editor, 36, 26, "Onchange1 replacement text"); //$NON-NLS-1$
		
		waitForRefresh();
		browser.removeLocationListener(locationListener);
		if (getException() != null) {
			throw getException();
		}
	}
	
	private void saveEditor() throws Exception{
		ICommandService commandService = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
		commandService.getCommand("org.eclipse.ui.file.save").executeWithChecks(new ExecutionEvent());
	}
	
	private void setRefreshPreferences(boolean onSave, boolean onChange){
		IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();
		preferences.setValue(REFRESH_ON_SAVE_PREFERENCES, onSave);
		preferences.setValue(REFRESH_ON_CHANGE_PREFERENCES, onChange);
		visualEditor.getActionBar().updateRefreshItemsAccordingToPreferences();
	}
	
	private LocationListener setRefreshListener(Browser browser){
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
		return locationListener;
	}
	
	private LocationListener setNoRefreshListener(Browser browser){
		assertNotNull(browser);
		LocationListener locationListener =  new LocationAdapter() {
			@Override
			public void changed(LocationEvent event) {
				fail(event.location);
			}
		};
		browser.addLocationListener(locationListener);
		return locationListener;
	}
}
