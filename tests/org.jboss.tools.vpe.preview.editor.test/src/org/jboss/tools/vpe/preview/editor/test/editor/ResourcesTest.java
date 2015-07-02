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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationAdapter;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.jboss.tools.jst.web.ui.internal.editor.editor.IVisualEditor;
import org.jboss.tools.jst.web.ui.internal.editor.jspeditor.JSPMultiPageEditor;
import org.jboss.tools.vpe.preview.editor.VpvEditor;
import org.jboss.tools.vpe.preview.editor.VpvEditorController;
import org.jboss.tools.vpe.preview.editor.VpvPreview;
import org.jboss.tools.vpe.preview.editor.test.PreviewEditorTestPlugin;
import org.jboss.tools.vpe.preview.editor.test.util.TestUtil;
import org.junit.Before;
import org.junit.Test;

/** Test that checks if images loaded or not
 * 
 * This test is temporaly disabled because javascript function which tests images loading does not work with SWT.Webkit on Linux
 * both document.images.length and document.getElementsByTagName('img').length returns 0 on Linux
 * 
 * @author Konstantin Marmalyukov (kmarmaliykov)
 *
 */

public class ResourcesTest extends RefreshTest {
	private static final String PROJECT_NAME = "html5-test"; //$NON-NLS-1$
	private static final String PAGE_NAME = "index.html"; //$NON-NLS-1$
	
	private VpvEditorController controller;
	private LocationListener locationListener;
	private JSPMultiPageEditor editor;
	@Before
	public void initLocationListener() {
		locationListener = new LocationAdapter() {
			@Override
			public void changed(LocationEvent event) {
				assertThat(event.location, not(ABOUT_BLANK));
				String imageCheckerScript = 
						"(function(){" + //$NON-NLS-1$
							"var images = document.getElementsByTagName('img');" + //$NON-NLS-1$
							"for (var i = 0; i < images.length; i++) {" +  //$NON-NLS-1$
								"var img = images.item(i);" + //$NON-NLS-1$
								"if (!img.complete || img.naturalWidth === 0) {" + //$NON-NLS-1$	
									"return false;" + //$NON-NLS-1$
								"}" + //$NON-NLS-1$
							"}" + //$NON-NLS-1$
							"return true;" + //$NON-NLS-1$
						"})();"; //$NON-NLS-1$
				Boolean result = (Boolean) ((Browser)event.getSource()).evaluate(imageCheckerScript);
				assertTrue(result);
				
				setException(null);
				setLocationChanged(true);
			}
		};
	}
	
	@Before
	public void openTestPage() throws CoreException, IOException {
		setException(new Exception("Refresh does not happens")); //$NON-NLS-1$
		setLocationChanged(false);
		
		final IFile elementPageFile = (IFile) TestUtil.getComponentPath(PAGE_NAME, PROJECT_NAME);  
		editor = openEditor(elementPageFile);
		editor.pageChange(IVisualEditor.VISUALSOURCE_MODE);
		
		controller = TestUtil.getVpvEditorController(editor);
		
		TestUtil.waitForJobs();
	}
	
	@Test
	public void imagesLoaded() throws Throwable {
		PreviewEditorTestPlugin.logInfo("Images Loaded test for Preview tab started"); //$NON-NLS-1$
		
		try {
			VpvEditor visualEditor = controller.getPageContext().getEditPart().getVisualEditor();
			assertNotNull(visualEditor);
			
			Browser editorBrowser = visualEditor.getBrowser();
			assertNotNull(editorBrowser);
			editorBrowser.addLocationListener(locationListener);
	
			waitForRefresh();
		} catch (Exception e) {
			setException(e);
		}
		PreviewEditorTestPlugin.logInfo("Images Loaded test for Preview tab finished"); //$NON-NLS-1$
	}
	
	@Test
	public void imagesPreviewLoaded() throws Throwable {
		PreviewEditorTestPlugin.logInfo("Images Loaded test for Preview tab started"); //$NON-NLS-1$
		editor.pageChange(IVisualEditor.PREVIEW_MODE);
		TestUtil.waitForJobs();
		VpvPreview visualPreview = controller.getPageContext().getEditPart().getPreviewWebBrowser();
		assertNotNull(visualPreview);
		
		Browser editorBrowser = visualPreview.getBrowser();
		assertNotNull(editorBrowser);
		editorBrowser.addLocationListener(locationListener);

		waitForRefresh();
		
		if (getException() != null) {
			throw getException();
		}
		PreviewEditorTestPlugin.logInfo("Images Loaded test forPreview tab finished"); //$NON-NLS-1$
	}
}
