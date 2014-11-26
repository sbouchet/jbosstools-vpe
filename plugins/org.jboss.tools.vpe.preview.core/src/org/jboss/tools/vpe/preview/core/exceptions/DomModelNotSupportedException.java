/*******************************************************************************
 * Copyright (c) 2013-2014 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.vpe.preview.core.exceptions;

/**Exceptions shown in HTML preview for editors which does not support dom model
 * 
 * @author kmarmaliykov
 *
 */
public class DomModelNotSupportedException extends Exception {
    private static final long serialVersionUID = 6864980290154145335L;
    
    public DomModelNotSupportedException(String message) {
        super(message, null);
    }
    
    @Override
    /**
     * Disable showing stacktrace for this exception.
     */
    public synchronized Throwable fillInStackTrace() {
        return null;
    }
}
