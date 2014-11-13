package org.jboss.tools.vpe.preview.editor;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.jboss.tools.vpe.preview.core.preferences.VpvPreferencesInitializer;

/**
 * @author Konstantin Marmalyukov (kmarmaliykov)
 */
public class VpvEditorPreferencesInitializer extends VpvPreferencesInitializer {

    @Override
    protected IEclipsePreferences getDefaultPreferences() {
        return ((IScopeContext) DefaultScope.INSTANCE).getNode(Activator.PLUGIN_ID);
    }

}
