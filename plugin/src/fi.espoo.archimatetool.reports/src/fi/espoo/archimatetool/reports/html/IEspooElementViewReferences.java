package fi.espoo.archimatetool.reports.html;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.archimatetool.model.IArchimateRelationship;
import com.archimatetool.model.IProperty;

public interface IEspooElementViewReferences extends EObject {

    String getOmaNakyma();
    
    String getKuvattavaKohde();
  
    String getIMSLinkki();
    
    String getJarjestelmasalkkuLinkki();
    
    String getHTMLContent();
	
    String getStrippedDocumentation();
    
    EList<IProperty> getAllProperties();
    
    EList<IArchimateRelationship> getRelationships();
    
    EList<IArchimateRelationship> getLiittymat();
    
    EList<IArchimateRelationship> getRelationshipsWithApplicationComponent();
    
    EList<IArchimateRelationship> getRelationshipsWithBusinessProcess();

    EList<IArchimateRelationship> getRelationshipsWithProduct();

    EList<IArchimateRelationship> getRelationshipsWithBusinessFunction();
    
    EList<IArchimateRelationship> getRelationshipsWithOther();
    
    String getClassName();
    
    String getClassSimpleName();
    
    String getTiedonhallintamalliKohdeNimi();

    String getJulkinenNimi();

    String getAlias();


}
