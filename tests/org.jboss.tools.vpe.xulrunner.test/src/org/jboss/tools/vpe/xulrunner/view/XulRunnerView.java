package org.jboss.tools.vpe.xulrunner.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.jboss.tools.jst.web.ui.WebUiPlugin;
import org.jboss.tools.jst.web.ui.internal.editor.preferences.IVpePreferencesPage;
import org.jboss.tools.vpe.base.test.VpeTest;
import org.jboss.tools.vpe.xulrunner.XulRunnerException;
import org.jboss.tools.vpe.xulrunner.editor.XulRunnerEditor;

public class XulRunnerView extends ViewPart {

	private static final String INIT_URL = "about:blank";
	private XulRunnerEditor xulrunnerEditor;

	static {
		WebUiPlugin.getDefault().getPreferenceStore().setValue(IVpePreferencesPage.USE_VISUAL_EDITOR_FOR_HTML5,
				Boolean.FALSE.toString());
	}

	@Override
	public void createPartControl(Composite parent) {
		if(!VpeTest.skipTests) {
			try {
				xulrunnerEditor = new XulRunnerEditor(parent);
				xulrunnerEditor.setURL(INIT_URL);
				xulrunnerEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			} catch (XulRunnerException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void setFocus() {
		xulrunnerEditor.setFocus();
	}

	public XulRunnerEditor getBrowser() {
		return xulrunnerEditor;
	}

}
