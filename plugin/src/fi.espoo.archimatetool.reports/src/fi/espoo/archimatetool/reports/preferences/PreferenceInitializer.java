/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package fi.espoo.archimatetool.reports.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.archimatetool.editor.preferences.IPreferenceConstants;
import fi.espoo.archimatetool.reports.EspooHtmlReportsPlugin;



/**
 * Class used to initialize default preference values
 * 
 * @author Phillip Beauvoir
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer
implements IPreferenceConstants, IEspooPreferenceConstants {

    @Override
    public void initializeDefaultPreferences() {
		IPreferenceStore store = EspooHtmlReportsPlugin.INSTANCE.getPreferenceStore();
        
		store.setDefault(ESPOO_USER_REPORTS_FOLDER, EspooHtmlReportsPlugin.INSTANCE.getDefaultUserTemplatesFolder().getAbsolutePath());
    }
}
