package problems;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderJunctionBoxes { 

    private static final Pattern boxPattern = Pattern.compile("(.*?)\\s(.*)");    
    
    public static void main(String[] args) {
    	List<String> in = Arrays.asList("br8 eat nim did", "b4 xi me nu", "t2 13 121 98", "f3 52 54 31");
    	System.out.println(new OrderJunctionBoxes().orderedJunctionBoxes(4, in));
	}
    
    public List<String> orderedJunctionBoxes(int numberOfBoxes, 
	        List<String> boxList) {
	   
	   List<String> oldBoxes = new ArrayList<>();
	   List<String> newBoxes = new ArrayList<>();
	   
	   for(int i=0; i<numberOfBoxes; i++) {
	       String box = boxList.get(i);
	       boolean isNew = false;
	       Matcher m = boxPattern.matcher(box);
	       if (m.matches()) {
	           isNew = isNumeric(m.group(2).replaceAll("\\s", ""));
	       }
	       
	       if (isNew) {
	           newBoxes.add(box);
	       } else {
	           oldBoxes.add(box);
	       }
	   }
	   
	   Collections.sort(oldBoxes, this::compareBox);
	   oldBoxes.addAll(newBoxes);
	   return oldBoxes;
	    
	}
	
	private int compareBox(String b1, String b2) {
	    Matcher m1 = boxPattern.matcher(b1);
	    Matcher m2 = boxPattern.matcher(b2);
	    
	    String id1 = null, id2 = null;
	    String v1 = null, v2 = null;
	    if(m1.find()) {
            id1 = m1.group(1);
            v1 = m1.group(2);
	    } else {
	        return 1;
	    }
	    
	    if (m2.find()) {
	        id2 = m2.group(1);
	        v2 = m2.group(2);
	    } else {
	        return -1;
	    }
	    
	    
	    int versionDiff = v1.compareTo(v2);
	    if (versionDiff == 0) {
	        return id1.compareTo(id2);
	    }
	    
	    return versionDiff;
	}
	
	private boolean isNumeric(String str) {
	    return str.chars().allMatch(Character::isDigit);
	}
}