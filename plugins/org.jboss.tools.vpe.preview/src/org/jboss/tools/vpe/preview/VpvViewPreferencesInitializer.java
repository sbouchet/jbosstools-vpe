/*******************************************************************************
 * Copyright (c) 2014 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.vpe.preview;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.jboss.tools.vpe.preview.core.preferences.VpvPreferencesInitializer;

/**
 * @author Ilya Buziuk (ibuziuk)
 */
public class VpvViewPreferencesInitializer extends VpvPreferencesInitializer{

    @Override
    protected IEclipsePreferences getDefaultPreferences() {
        return ((IScopeContext) DefaultScope.INSTANCE).getNode(Activator.PLUGIN_ID);
    }
}
