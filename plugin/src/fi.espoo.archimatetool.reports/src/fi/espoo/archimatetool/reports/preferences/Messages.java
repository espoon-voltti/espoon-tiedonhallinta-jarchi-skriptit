package fi.espoo.archimatetool.reports.preferences;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

    private static final String BUNDLE_NAME = "fi.espoo.archimatetool.reports.preferences.messages"; //$NON-NLS-1$

    public static String EspooReportsPreferencesPage_0;

    public static String EspooReportsPreferencesPage_1;

    public static String EspooReportsPreferencesPage_2;

    public static String EspooReportsPreferencesPage_3;

    public static String EspooReportsPreferencesPage_4;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
