/*******************************************************************************
 * Copyright (c) 2016 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.vpe.preview.editor.test.util;

import org.eclipse.jface.action.ActionContributionItem;
import org.jboss.tools.test.util.xpl.DisplayHelper;

public class ActionIsEnabledCondition extends DisplayHelper {
	
	ActionContributionItem action;
	boolean enabled;
	
	public ActionIsEnabledCondition(ActionContributionItem action, boolean enabled){
		this.action=action;
		this.enabled = enabled;
	}
	
	@Override
	protected boolean condition() {
		if(enabled){
		return action.isEnabled();
		} else {
			return !action.isEnabled();
		}
	}

}
