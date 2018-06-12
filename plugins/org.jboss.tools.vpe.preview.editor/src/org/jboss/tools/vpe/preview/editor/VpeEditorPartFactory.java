/*******************************************************************************
 * Copyright (c) 2007-2014 Exadel, Inc. and Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Exadel, Inc. and Red Hat, Inc. - initial API and implementation
 ******************************************************************************/ 
package org.jboss.tools.vpe.preview.editor;

import org.eclipse.ui.part.EditorPart;
import org.eclipse.wst.sse.ui.StructuredTextEditor;
import org.jboss.tools.jst.web.ui.WebUiPlugin;
import org.jboss.tools.jst.web.ui.internal.editor.bundle.BundleMap;
import org.jboss.tools.jst.web.ui.internal.editor.editor.IVisualEditor;
import org.jboss.tools.jst.web.ui.internal.editor.editor.IVisualEditorFactory;
import org.jboss.tools.jst.web.ui.internal.editor.preferences.IVpePreferencesPage;

/**
 * @author Konstantin Marmalyukov (kmarmaliykov)
 */
public class VpeEditorPartFactory implements IVisualEditorFactory {	
	static {
		//Since we do not implement the option of showing Visual toolbar for preview editor into Eclipse toolbar - must always show visual toolbar within the editor
	    WebUiPlugin.getDefault().getPreferenceStore().setValue(IVpePreferencesPage.SHOW_VISUAL_TOOLBAR, true);	    
	}
	
	public IVisualEditor createVisualEditor(final EditorPart multiPageEditor, StructuredTextEditor textEditor, int visualMode, BundleMap bundleMap) {
		return getPreviewEditor(multiPageEditor, textEditor, visualMode, bundleMap);
	}
	
	private IVisualEditor getPreviewEditor(final EditorPart multiPageEditor, StructuredTextEditor textEditor, int visualMode, BundleMap bundleMap) {
		return new VpvEditorPart(multiPageEditor, textEditor, visualMode, bundleMap);
	}
	
	
}
