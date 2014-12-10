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

import org.eclipse.swt.widgets.Display;
import org.jboss.tools.vpe.preview.editor.test.util.RefreshDelayHelper;
import org.jboss.tools.vpe.preview.editor.test.util.TestUtil;

/**
 * 
 * @author Konstantin Marmalyukov (kmarmaliykov)
 *
 */

public abstract class RefreshTest extends VpvTest{
	private RefreshDelayHelper helper = new RefreshDelayHelper();
	
	protected void waitForRefresh() {
		helper.waitForCondition(Display.getDefault(), TestUtil.MAX_IDLE);
	}
	
	public void setLocationChanged(boolean locationChanged) {
		helper.setLocationChanged(locationChanged);
	}
}
