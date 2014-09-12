package org.jboss.tools.vpe.preview.core.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

public abstract class VpvPreferencesInitializer extends AbstractPreferenceInitializer {
    public static final String REFRESH_ON_SAVE_PREFERENCES = "org.jboss.tools.vpe.enableRefreshOnSave"; //$NON-NLS-1$
    public static final String REFRESH_ON_CHANGE_PREFERENCES = "org.jboss.tools.vpe.enableRefreshOnChange"; //$NON-NLS-1$
    
    @Override
    public void initializeDefaultPreferences() {
        IEclipsePreferences defaultPreferences = getDefaultPreferences();
        defaultPreferences.putBoolean(REFRESH_ON_CHANGE_PREFERENCES, true);
        defaultPreferences.putBoolean(REFRESH_ON_SAVE_PREFERENCES, false);
    }
    
    protected abstract IEclipsePreferences getDefaultPreferences();
}
