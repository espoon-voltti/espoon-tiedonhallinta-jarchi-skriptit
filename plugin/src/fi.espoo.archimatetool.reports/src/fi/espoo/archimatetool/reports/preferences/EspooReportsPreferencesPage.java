/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package fi.espoo.archimatetool.reports.preferences;

import java.io.File;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.archimatetool.editor.ui.UIUtils;
import fi.espoo.archimatetool.reports.EspooHtmlReportsPlugin;


/**
 * Canvas Preferences Page
 * 
 * @author Phillip Beauvoir
 */
public class EspooReportsPreferencesPage
extends PreferencePage
implements IWorkbenchPreferencePage, IEspooPreferenceConstants {
    
    private static String HELP_ID = "com.archimatetool.help.prefsEspoo"; //$NON-NLS-1$
    
    private Text fUserReportsFolderTextField;
    
	public EspooReportsPreferencesPage() {
		setPreferenceStore(EspooHtmlReportsPlugin.INSTANCE.getPreferenceStore());
	}
	
    @Override
    protected Control createContents(Composite parent) {
        // Help
        PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, HELP_ID);

        Composite client = new Composite(parent, SWT.NULL);
        GridLayout layout = new GridLayout();
        layout.marginWidth = layout.marginHeight = 0;
        client.setLayout(layout);
                
        Group settingsGroup = new Group(client, SWT.NULL);
        settingsGroup.setText(Messages.EspooReportsPreferencesPage_0);
        settingsGroup.setLayout(new GridLayout(3, false));
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.widthHint = 500;
        settingsGroup.setLayoutData(gd);
        
        Label label = new Label(settingsGroup, SWT.NULL);
        label.setText(Messages.EspooReportsPreferencesPage_1);
        
        fUserReportsFolderTextField = UIUtils.createSingleTextControl(settingsGroup, SWT.BORDER, false);
        fUserReportsFolderTextField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        Button folderButton = new Button(settingsGroup, SWT.PUSH);
        folderButton.setText(Messages.EspooReportsPreferencesPage_2);
        folderButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String folderPath = chooseFolderPath();
                if(folderPath != null) {
                    fUserReportsFolderTextField.setText(folderPath);
                }
            }
        });
        
        setValues();
        
        return client;
    }

    private String chooseFolderPath() {
        DirectoryDialog dialog = new DirectoryDialog(Display.getCurrent().getActiveShell());
        dialog.setText(Messages.EspooReportsPreferencesPage_3);
        dialog.setMessage(Messages.EspooReportsPreferencesPage_4);
        File file = new File(fUserReportsFolderTextField.getText());
        if(file.exists()) {
            dialog.setFilterPath(fUserReportsFolderTextField.getText());
        }
        return dialog.open();
    }

    private void setValues() {
        fUserReportsFolderTextField.setText(getPreferenceStore().getString(ESPOO_USER_REPORTS_FOLDER));
    }
    
    @Override
    public boolean performOk() {
        getPreferenceStore().setValue(ESPOO_USER_REPORTS_FOLDER, fUserReportsFolderTextField.getText());
        return true;
    }
    
    @Override
    protected void performDefaults() {
        fUserReportsFolderTextField.setText(EspooHtmlReportsPlugin.INSTANCE.getDefaultUserTemplatesFolder().getAbsolutePath());
        super.performDefaults();
    }
    
    @Override
    public void init(IWorkbench workbench) {
    }
}