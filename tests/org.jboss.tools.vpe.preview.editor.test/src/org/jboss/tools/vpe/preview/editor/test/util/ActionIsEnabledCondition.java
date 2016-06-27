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
