/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package fi.espoo.archimatetool.reports;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.archimatetool.editor.ArchiPlugin;
import com.archimatetool.editor.utils.StringUtils;

import fi.espoo.archimatetool.reports.preferences.IEspooPreferenceConstants;
import fi.espoo.archimatetool.reports.html.HTMLReportExporter;

/**
 * Activator
 * 
 * @author Phillip Beauvoir
 * @author Jani Mattsson  (modifications for Espoo)
 */
public class EspooHtmlReportsPlugin extends AbstractUIPlugin {
    
    public static final String PLUGIN_ID = "fi.espoo.archimatetool.reports"; //$NON-NLS-1$

    /**
     * The shared instance
     */
    public static EspooHtmlReportsPlugin INSTANCE;

    /**
     * The File location of this plugin folder
     */
    private static File fPluginFolder;

    public EspooHtmlReportsPlugin() {
        INSTANCE = this;
    }
    
    @Override
    public void stop(BundleContext context) throws Exception {
        try {
            HTMLReportExporter.cleanPreviewFiles();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        finally {
            super.stop(context);
        }
    }

    /**
     * @return The plugins folder
     */
    public File getTemplatesFolder() {
    	return getUserTemplatesFolder();
        // return new File(getPluginFolder(), "templates"); //$NON-NLS-1$
    }
        
    /**
     * @return User-set Espoo user template folder
     */
    public File getUserTemplatesFolder() {
        String s = getPreferenceStore().getString(IEspooPreferenceConstants.ESPOO_USER_REPORTS_FOLDER);
        if(StringUtils.isSetAfterTrim(s)) {
            File f = new File(s);
            f.mkdirs();
            if(f.exists() && f.isDirectory()) {
                return f;
            }
        }
        
        return getDefaultUserTemplatesFolder();
    }

    /**
     * @return Default Espoo user template folder
     */
    public File getDefaultUserTemplatesFolder() {
    	return new File(ArchiPlugin.INSTANCE.getUserDataFolder(), "espoo-reports");
    }

    /**
     * @return The File Location of this plugin
     */
    public File getPluginFolder() {
        if(fPluginFolder == null) {
            URL url = getBundle().getEntry("/"); //$NON-NLS-1$
            try {
                url = FileLocator.resolve(url);
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
            fPluginFolder = new File(url.getPath());
        }
        
        return fPluginFolder;
    }
}
