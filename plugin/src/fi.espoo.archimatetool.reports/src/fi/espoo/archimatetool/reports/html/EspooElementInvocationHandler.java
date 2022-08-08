/**
 * Tämän koodin kirjoittaminen ei ollut kauneus-, vaan pikemminkin nopeuskilpailu...
 * 
 */
package fi.espoo.archimatetool.reports.html;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.common.util.BasicEList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.archimatetool.model.IApplicationComponent;
import com.archimatetool.model.IArchimateConcept;
import com.archimatetool.model.IArchimateRelationship;
import com.archimatetool.model.IBusinessFunction;
import com.archimatetool.model.IBusinessProcess;
import com.archimatetool.model.INameable;
import com.archimatetool.model.IProduct;
import com.archimatetool.model.IProperties;
import com.archimatetool.model.IProperty;


public class EspooElementInvocationHandler implements InvocationHandler {

	private final Map<Method, Method> cachedMethods = new HashMap<>();
	
	private static final Comparator<INameable> espooComparator = new EspooProcessNumberingComparator();
	
	private Object target;
	
	public EspooElementInvocationHandler(Object target) {
		this.target = target;
	}
	
	public static Object wrapEspooProxy(Object original) {
    	Class[] ifaces = original.getClass().getInterfaces();
    	List<Class> ifacelist = new ArrayList<Class>(Arrays.asList(ifaces));
    	ifacelist.add(IEspooElementViewReferences.class);
    	return (EObject)Proxy.newProxyInstance(
    			EspooElementInvocationHandler.class.getClassLoader(),
        		ifacelist.toArray(ifaces),
        		new EspooElementInvocationHandler(original));
		
	}
	
	private EList<IProperty> augmentedGetProperties(EList<IProperty> properties) {
		EList<IProperty> list = new BasicEList<IProperty>();
		for (IProperty prop: properties) {
			if (!prop.getKey().startsWith("_")) list.add(prop);
		}
		return list;
	}
	
	private String getPropertyValue(IProperties obj, String name) {
		EList<IProperty> props = null;
		if (obj instanceof IEspooElementViewReferences) {
			props = ((IEspooElementViewReferences)obj).getAllProperties();
		} else {
			props = obj.getProperties();
		}
		for (IProperty prop: props) {
			if (name.equals(prop.getKey())) {
				return prop.getValue();
			}
		}
		return null;
	}

	private String getPropertyValue(String name) {
		return getPropertyValue((IProperties)target, name);
	}
		
	private String getStrippedDocumentation() {
		String doc = ((IArchimateConcept)target).getDocumentation();
		if (doc == null) return null;
		if (doc.strip().length() == 0) return null;
		return doc.strip();
	}
	
	private EList<IArchimateRelationship> getRelationships() {
		IArchimateConcept ac = (IArchimateConcept)target;
		EList<IArchimateRelationship> rels = new BasicEList<IArchimateRelationship>();
		for (IArchimateRelationship ar: ac.getSourceRelationships()) {
			rels.add((IArchimateRelationship)wrapEspooProxy(ar));
		}
		for (IArchimateRelationship ar: ac.getTargetRelationships()) {
			rels.add((IArchimateRelationship)wrapEspooProxy(ar));
		}
		rels.sort(espooComparator);
		return rels;
	}

	private EList<IArchimateRelationship> getRelationshipsWith(String methodname) {
		String targetId = ((IArchimateConcept)target).getId();
		EList<IArchimateRelationship> rels = new BasicEList<IArchimateRelationship>();
		for (IArchimateRelationship ar: getRelationships()) {
			IArchimateConcept otherEnd = (IArchimateConcept)ar.getSource();
			if (otherEnd.getId() == targetId) {
				otherEnd = (IArchimateConcept)ar.getTarget();
			}
		    if (target instanceof IApplicationComponent 
					&& otherEnd instanceof IApplicationComponent
					&& "1".equals(getPropertyValue(ar, "_Tiedonhallintamalli"))) {
		    	if ("getLiittymat".equals(methodname)) {
					rels.add(ar);
		    	}
			} else if (otherEnd instanceof IApplicationComponent
					&& "1".equals(getPropertyValue(otherEnd, "_Tiedonhallintamalli"))) {
				if ("getRelationshipsWithApplicationComponent".equals(methodname)) {
					rels.add(ar);
				}
			} else if (otherEnd instanceof IBusinessProcess
					&& "1".equals(getPropertyValue(otherEnd, "_Tiedonhallintamalli"))) {
				if ("getRelationshipsWithBusinessProcess".equals(methodname)) {
					rels.add(ar);
				}
			} else if (otherEnd instanceof IProduct
					&& "1".equals(getPropertyValue(otherEnd, "_Tiedonhallintamalli"))) {
				if ("getRelationshipsWithProduct".equals(methodname)) {
					rels.add(ar);
				}
			} else if (otherEnd instanceof IBusinessFunction
					&& "1".equals(getPropertyValue(otherEnd, "_Tiedonhallintamalli"))) {
				if ("getRelationshipsWithBusinessFunction".equals(methodname)) {
					rels.add(ar);
				}
			} else if ("getRelationshipsWithOther".equals(methodname)) {
				rels.add(ar);
			} 
		}
		return rels;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		if (proxy instanceof IProperties && "getProperties".equals(method.getName())) {
			return augmentedGetProperties(((IProperties)target).getProperties());
		}
		if (proxy instanceof IProperties && "getAllProperties".equals(method.getName())) {
			return ((IProperties)target).getProperties();
		}
		if (proxy instanceof IProperties && "getOmaNakyma".equals(method.getName())) {
			return getPropertyValue("_OmaNäkymä"); 			
		} 
		if (proxy instanceof IProperties &&  "getKuvattavaKohde".equals(method.getName())) {
			return getPropertyValue("_KuvattavaKohde"); 			
		}
		if (proxy instanceof IProperties && "getIMSLinkki".equals(method.getName())) {
			return getPropertyValue("IMS-linkki");
		}
		if (proxy instanceof IProperties && "getJarjestelmasalkkuLinkki".equals(method.getName())) {
			return getPropertyValue("Järjestelmäsalkku-linkki");
		}
		if (proxy instanceof IProperties && "getHTMLContent".equals(method.getName())) {
			return getPropertyValue("_HTMLContent");
		}
		if (proxy instanceof IArchimateConcept && "getRelationships".equals(method.getName())) {
			return getRelationships();
		}
		if (proxy instanceof IArchimateConcept && ("getLiittymat".equals(method.getName()) || method.getName().startsWith("getRelationshipsWith"))) {
			return getRelationshipsWith(method.getName());
		}
		if (proxy instanceof IArchimateConcept && "getStrippedDocumentation".equals(method.getName())) {
			return getStrippedDocumentation();
		}
		if (proxy instanceof INameable && "compareTo".equals(method.getName())
				&& args[0] instanceof INameable) {
			return espooComparator.compare((INameable)proxy, (INameable)args[0]);
		}
		if ("getClassSimpleName".equals(method.getName())) {  // vähän meni hankalaksi näiden kanssa...  
			return target.getClass().getSimpleName();
		}
		if ("getClassName".equals(method.getName())) {   // vähän meni hankalaksi näiden kanssa...
			return target.getClass().getName();
		}
		if ("getTiedonhallintamalliKohdeNimi".equals(method.getName())) {
			if ("BusinessProcess".equals(target.getClass().getSimpleName())) return "Prosessi";
			else if ("Product".equals(target.getClass().getSimpleName())) return "Tietovaranto";
			else if ("BusinessFunction".equals(target.getClass().getSimpleName())) return "Tehtäväluokka";
			else if ("ApplicationCompoment".equals(target.getClass().getSimpleName())) return "Tietojärjestelmä";
			else if ("BusinessActor".equals(target.getClass().getSimpleName())) return "Organisaatio";
			else return "tuntematon luokka";
		}
		Method targetMethod = null;
		if (!cachedMethods.containsKey(method)) {
			targetMethod = target.getClass().getMethod(method.getName(), 
					method.getParameterTypes());
			cachedMethods.put(method, targetMethod);
		} else {
			targetMethod = cachedMethods.get(method);
		}
		Object retval = targetMethod.invoke(target, args);
		if (retval instanceof IProperties && method.getName().startsWith("get")) {  // wräpätään getterien palauttamatkin arvot tarvittaessa proxylla...
			retval = wrapEspooProxy(retval);
		} else if (retval instanceof EList && method.getName().startsWith("get")) {
			EList<INameable> list = new BasicEList<INameable>();
			for (INameable obj: (EList<INameable>)retval) {
	        	list.add((INameable)wrapEspooProxy(obj));
			}
			if ("getFolders".equals(method.getName()) || "getElements".equals(method.getName())) {
				list.sort(espooComparator);
				// ((EList<INameable>)list).sort(new EspooProcessNumberingComparator());
			}
			retval = list;
		}
		return retval;
	}

}
