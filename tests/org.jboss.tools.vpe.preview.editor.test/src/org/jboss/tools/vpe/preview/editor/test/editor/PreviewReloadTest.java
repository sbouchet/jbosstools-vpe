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

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationAdapter;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.jboss.tools.jst.web.ui.internal.editor.editor.IVisualEditor;
import org.jboss.tools.jst.web.ui.internal.editor.jspeditor.JSPMultiPageEditor;
import org.jboss.tools.vpe.preview.editor.VpvEditorController;
import org.jboss.tools.vpe.preview.editor.VpvPreview;
import org.jboss.tools.vpe.preview.editor.test.PreviewEditorTestPlugin;
import org.jboss.tools.vpe.preview.editor.test.util.TestUtil;
import org.junit.Before;
import org.junit.Test;

/**Test for JBIDE-18975
 * 
 * @author Konstantin Marmalyukov (kmarmaliykov)
 *
 */

public class PreviewReloadTest extends RefreshTest {
	private static final String PROJECT_NAME = "html5-test"; //$NON-NLS-1$
	private static final String PAGE_NAME = "index.html"; //$NON-NLS-1$
	
	private JSPMultiPageEditor editor;
	private VpvPreview visualPreview;
	
	@Before
	public void openTestPage() throws CoreException, IOException {
		setLocationChanged(false);
		
		final IFile elementPageFile = (IFile) TestUtil.getComponentPath(PAGE_NAME, PROJECT_NAME);  
		editor = openEditor(elementPageFile);
		editor.pageChange(IVisualEditor.PREVIEW_MODE);
		
		VpvEditorController controller = TestUtil.getVpvEditorController(editor);
		visualPreview = controller.getPageContext().getEditPart().getPreviewWebBrowser();
		assertNotNull(visualPreview);
		
		TestUtil.waitForJobs();
	}
	
	@Test
	public void externalUrlReloadTest() throws Throwable {
		PreviewEditorTestPlugin.logInfo("External Url Reload Test started"); //$NON-NLS-1$
		
		setException(new Exception("Refresh does not happens")); //$NON-NLS-1$
		Browser browser = visualPreview.getBrowser();
		assertNotNull(browser);
		LocationListener externalUrlListener = new LocationAdapter() {
			@Override
			public void changed(LocationEvent event) {
				setLocationChanged(true);
				setException(null);
			}
		};
		browser.addLocationListener(externalUrlListener);
		browser.setUrl("redhat.com"); //$NON-NLS-1$
		waitForRefresh();
		
		browser.removeLocationListener(externalUrlListener);
		setLocationChanged(false);
		
		if (getException() != null) {
			throw getException();
		}
		
		setException(new Exception("Refresh does not happens")); //$NON-NLS-1$
		LocationListener pageUrlListener = new LocationAdapter() {
			@Override
			public void changed(LocationEvent event) {
				String url = event.location;
				assertThat(url, not(ABOUT_BLANK));
				assertThat(url, startsWith("http://localhost")); //$NON-NLS-1$
				setLocationChanged(true);
				setException(null);
			}
		};
		browser.addLocationListener(pageUrlListener);
		editor.pageChange(IVisualEditor.SOURCE_MODE);
		TestUtil.waitForJobs();
		editor.pageChange(IVisualEditor.PREVIEW_MODE);
		
		waitForRefresh();
		browser.removeLocationListener(pageUrlListener);
		
		if (getException() != null) {
			throw getException();
		}
		PreviewEditorTestPlugin.logInfo("External Url Reload Test finished"); //$NON-NLS-1$
	}
	
}
