package org.jboss.tools.vpe.preview.core.exceptions;

import org.eclipse.swt.SWTError;

public class BrowserErrorWrapper extends XulRunnerErrorWrapper {
	
	@Override
	protected Throwable wrapXulRunnerError(Throwable originalThrowable) {
		Throwable xulrunnerThrowable = super.wrapXulRunnerError(originalThrowable);
		if (xulrunnerThrowable instanceof SWTError && xulrunnerThrowable.getMessage() != null) {
			String message = xulrunnerThrowable.getMessage(); 
			if (message.contains("No more handles")) {//$NON-NLS-1$
				/*running under GTK3 and no webkit installed
				or Xulrunner disbaled by -Dorg.jboss.tools.vpe.loadxulrunner=false flag and no webkit installed
				this error can be only under Linux. On Windows and OSX default browser always can be created.*/
				xulrunnerThrowable = new NoEngineException(Messages.NO_ENGINE_ERROR, originalThrowable);
			}
		}
		return xulrunnerThrowable;
	}
}
