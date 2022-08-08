package fi.espoo.archimatetool.reports.html;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.archimatetool.model.IArchimateRelationship;
import com.archimatetool.model.INameable;

public class EspooProcessNumberingComparator implements Comparator<INameable> {

	
	private int compareRelationships(IArchimateRelationship rel1, IArchimateRelationship rel2) {
		int srcCompare = compare(rel1.getSource(), rel2.getSource());
		if (srcCompare != 0) return srcCompare;
		return compare(rel1.getTarget(), rel2.getTarget());
	}
	
	
	public int compare(INameable o1, INameable o2) {
		
		if (o1 instanceof IArchimateRelationship && o2 instanceof IArchimateRelationship) {
			return compareRelationships((IArchimateRelationship)o1, (IArchimateRelationship)o2); 
		}
		
	    //Pattern pattern = Pattern.compile("^([OT]|Y[APT])?(\\d+(\\.\\d+)*+\\.?)", Pattern.CASE_INSENSITIVE);
	    Pattern pattern = Pattern.compile("^([OT]|Y[AJP]?)?(\\d+\\.(\\d+|\\d+(\\.\\d+)*)?)($|[^0-9\\.])", Pattern.CASE_INSENSITIVE);
	    
	    
	    String name1 = o1.getName();
	    if (name1 != null) name1 = name1.trim().toLowerCase();
	    String name2 = o2.getName();
	    if (name2 != null) name2 = name2.trim().toLowerCase();
	    
	    Matcher matcher1 = pattern.matcher(name1);
	    boolean matchFound1 = matcher1.find();
	    Matcher matcher2 = pattern.matcher(name2);
	    boolean matchFound2 = matcher2.find();
	    
	    if (!matchFound1 && !matchFound2) return name1.compareTo(name2);
	    if (!matchFound1) return 1;
	    if (!matchFound2) return -1;
	    
	    String[] parts1 = matcher1.group(2).split("\\.");
	    String[] parts2 = matcher2.group(2).split("\\.");
	    
	    int i = 0;
	    while (i < parts1.length && i < parts2.length) {
	    	if (Integer.parseInt(parts1[i]) > Integer.parseInt(parts2[i])) return 1;
	    	if (Integer.parseInt(parts1[i]) < Integer.parseInt(parts2[i])) return -1;
	    	i++;
	    }
	    if (parts1.length > i) return 1;
	    if (parts2.length > i) return -1;
	    return 0;
	}
	
}
