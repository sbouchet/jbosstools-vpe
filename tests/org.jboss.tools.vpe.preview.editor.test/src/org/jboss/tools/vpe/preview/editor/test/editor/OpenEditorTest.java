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

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationAdapter;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.jboss.tools.jst.web.ui.internal.editor.editor.IVisualEditor;
import org.jboss.tools.jst.web.ui.internal.editor.jspeditor.JSPMultiPageEditor;
import org.jboss.tools.vpe.preview.editor.VpvEditor;
import org.jboss.tools.vpe.preview.editor.VpvEditorController;
import org.jboss.tools.vpe.preview.editor.VpvPreview;
import org.jboss.tools.vpe.preview.editor.test.util.TestUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Konstantin Marmalyukov (kmarmaliykov)
 *
 */

public class OpenEditorTest extends RefreshTest {
	private static final String PROJECT_NAME = "html5-test"; //$NON-NLS-1$
	private static final String PAGE_NAME = "index.html"; //$NON-NLS-1$
	
	private static LocationListener locationListener;
	
	@Before
	public void initListener() {
		locationListener = new LocationAdapter() {
			@Override
			public void changed(LocationEvent event) {
				assertThat(event.location, not(ABOUT_BLANK));
				setLocationChanged(true);
			}
		};
	}
	
	@Test
	public void openVisualSourceTabTest() {
		setException(null);
		setLocationChanged(false);
		
		try {
			final IFile elementPageFile = (IFile) TestUtil.getComponentPath(PAGE_NAME, PROJECT_NAME);  
			JSPMultiPageEditor editor = openEditor(elementPageFile);
			editor.pageChange(IVisualEditor.VISUALSOURCE_MODE);
	
			VpvEditorController controller = TestUtil.getVpvEditorController(editor);
			final VpvEditor visualEditor = controller.getPageContext().getEditPart().getVisualEditor();
			assertNotNull(visualEditor);		
			Browser editorBrowser = visualEditor.getBrowser();
			assertNotNull(editorBrowser);
			editorBrowser.addLocationListener(locationListener);
	
			waitForRefresh();
			
			editorBrowser.removeLocationListener(locationListener);
		} catch (Exception e) {
			setException(e);
		}
	}
	
	@SuppressWarnings("restriction")
	@Test
	public void openPreviewTabTest() {
		setException(null);
		setLocationChanged(false);
		
		try {
			final IFile elementPageFile = (IFile) TestUtil.getComponentPath(PAGE_NAME, PROJECT_NAME);  
			JSPMultiPageEditor editor = openEditor(elementPageFile);
			editor.pageChange(IVisualEditor.PREVIEW_MODE);
			VpvEditorController controller = TestUtil.getVpvEditorController(editor);
			VpvPreview preview = controller.getPageContext().getEditPart().getPreviewWebBrowser();
			assertNotNull(preview);
			Browser previewBrowser = preview.getBrowser();
			assertNotNull(previewBrowser);
			previewBrowser.addLocationListener(locationListener);
	
			waitForRefresh();
			
			previewBrowser.removeLocationListener(locationListener);
		} catch (Exception e) {
			setException(e);
		}
	}
}
