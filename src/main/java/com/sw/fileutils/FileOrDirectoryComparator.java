package com.sw.fileutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Compares the content of any two files or directories in a fashion similar to 
 * GNU <code>diff -r</code> as available in various Linux flavors.
 * 
 * <p>Returns an integer as part of comparison result specifying 
 * the degree to which the given paths differ.</p>
 * 
 * <p>Also, generates a readable diff summary as a result of the comparison,
 * which may optionally used to redirect to any log stream or the standard output.</p>
 * 
 * @author Swatantra Jain
 **/
public class FileOrDirectoryComparator implements Comparator<File> {

	public static enum CHANGE_TYPE {REMOVED, ADDED, CHANGED};
	
	StringBuffer sb = new StringBuffer();
	private int level = -1;
	private boolean isNoteForDeletedLinePublishedBefore = false;

	private static class DiffContent { 
		
		private Integer oldIndex;
		private Integer newIndex;
		private String contentFile1;
		private String contentFile2;
		private CHANGE_TYPE type;
		
		public DiffContent(String str1, String str2, int oldIndex, int newIndex, CHANGE_TYPE type) {
			this.contentFile1 = str1;
			this.contentFile2 = str2;
			this.oldIndex = oldIndex;
			this.newIndex = newIndex;
			this.type = type;
		}

		public String toString() {
			StringBuffer result = new StringBuffer();
			result.append("\t");
			
			if (type.equals(CHANGE_TYPE.REMOVED)) {
				result.append("\t").append(contentFile1).append(" [").append(CHANGE_TYPE.REMOVED).append(":").append(oldIndex).append("]");
			} else {
				result.append("line " + newIndex + ": ");
				if (type.equals(CHANGE_TYPE.ADDED)) 
					result.append(contentFile2).append(" [").append(CHANGE_TYPE.ADDED).append("]");
				else
					result.append(contentFile1).append(" | ").append(contentFile2).append(" [").append(CHANGE_TYPE.CHANGED).append("]");
			}
			result.append("\n");
			return result.toString();
		}

	} 

	private void append(StringBuffer sb, Object obj) {
		for (int i = 0; i < level; i++) {
			sb.append("|--");
		}
		sb.append(obj);
	}

	public static void main (String args[]) throws IOException {
		FileOrDirectoryComparator comparator = new FileOrDirectoryComparator();
		comparator.compare(new File("src/test/resources/dir-A"), new File("src/test/resources/dir-B"));
		System.out.println(comparator.getDiffSummary());
	}
	
	/**
	 * Compares the given paths for any content differences.
	 *
	 * @param file1 a {@link File} object which may represent either a file or a directory.
	 * @param file2 a {@link File} object which may represent either a file or a directory. 
	 * @return an integer specifying the degree to which paths differ.
	 **/
	@Override
	public int compare(File file1, File file2) {

		int changedCount=0;
		int addedCount=0;
		int deletedCount=0;
		++level;
		append(sb, file1 + " -- " + file2 + " <<RESULT>>\n");

		if (file1.isDirectory() || file2.isDirectory()) {
			//If either is a directory then both should be directory else exists a difference.
			if	( (file1.isDirectory() && file2.isDirectory()) ) {
				List<String> fileSet1 = (List<String>) Arrays.asList(file1.list());
				List<String> fileSet2 = (List<String>) Arrays.asList(file2.list());

				ArrayList<String> comparedFiles = new ArrayList<String>();
				for (String file: fileSet1) {

					File child1 = new File(file1.getAbsolutePath().toString() + "/" + file); 
					File child2 = new File(file2.getAbsolutePath().toString() + "/" + file); 

					if (fileSet2.contains(file)) {

						int diffFiles = compare(child1, child2);
						if (diffFiles > 0) {
							changedCount += diffFiles;
						}

					} else {
						deletedCount += checkDeletedOrAddedFile(child1, "Deleted");
					}
					comparedFiles.add(file);
				}

				for (String file: fileSet2) {
					if(!fileSet1.contains(file)) {
						File child = new File(file2.getAbsolutePath().toString() + "/" + file); 
						addedCount += checkDeletedOrAddedFile(child, "Added");
					}
				}

			} else {
				
				//one is a directory and other is a file
				deletedCount += checkDeletedOrAddedFile(file1, "Deleted");
				addedCount += checkDeletedOrAddedFile(file2, "Added");
				
			}

			if (changedCount == 0 && addedCount == 0 && deletedCount == 0) {
				
				int i = sb.lastIndexOf("<<RESULT>>");
				sb.replace(i, sb.length() -1, "[same]");
				i = sb.lastIndexOf("\n|--");
				if (i >= 0)
					sb.replace(i, sb.length() -1, "");
				
			} else {
				
				int i = sb.lastIndexOf("<<RESULT>>");
				String str = "[" + (changedCount>0 ? " " + changedCount + " file" + (changedCount>1?"s":"") + " changed," : "")
						+  (addedCount>0 ? " " +addedCount + " file" + (addedCount>1?"s":"") + " added," : "")
						+	(deletedCount>0 ? " " +deletedCount + " file" + (deletedCount>1?"s":"") + " deleted,": "");
				sb.replace(i, i+10,  str.substring(0, str.length()-1) + " ]");

			}

		} else {
			
			//If both are not directory then compare the files
			if (compareFiles(file1, file2) > 0)
				changedCount++;
			
		}

		level--;
		return changedCount + addedCount + deletedCount;
		
	}
	
	/**
	 * Returns all files in alphabetically sorted order by traversing the path
	 * 	up to the given nth depth. Traverses all sub-directories if provided depth less than zero.
	 * 
	 * @param path a {@link File} object representing the directory to traverse.
	 * @return a collection of files underneath the given path up to a specific depth.
	 */
	public static Collection<File> sort(File path, int depth)
	{
		Collection<File> result = new LinkedList<File>();
		String[] files = path.list();

		List<String> fileNames = new LinkedList<>();
		for (String file : files) 
			fileNames.add(file);

		Collections.sort(fileNames);
		depth--;
		for (String fileName : fileNames) {
			
			File file = new File(path + "/" + fileName );
			// always continue when depth less than zero
			// otherwise continue until depth reaches zero.
			if (file.isDirectory() && depth != 0)
				result.addAll( sort(file, depth) );
			else
				result.add(file);
		}
			
		return result;
		
	}
	
	/**
	 * @return {@link String} representing a readable summary of the identified differences. 
	 **/
	public String getDiffSummary() {
		return sb.toString();		
	}
	
	private int compareFiles(File f1, File f2) {

		int changedCount=0;
		int addedCount=0;
		int deletedCount=0;
		
		String line, line1 = null, line2 = null;
		ArrayList<String> lineSet1 = new ArrayList<String>();
		ArrayList<String> lineSet2 = new ArrayList<String>();

		try ( BufferedReader r1 = new BufferedReader(new InputStreamReader(new FileInputStream(f1)));
				BufferedReader r2 = new BufferedReader(new InputStreamReader(new FileInputStream(f2))); ) {

			while ( (line = r1.readLine()) != null ) 
				lineSet1.add( line.isEmpty()? line: line.trim() );

			while ( (line = r2.readLine()) != null ) 
				lineSet2.add( line.isEmpty()? line: line.trim() );

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int i=0;
		int j=0;
		for (; i < lineSet1.size() && j < lineSet2.size(); i++,j++) {

			line1 = lineSet1.get(i);
			line2 = lineSet2.get(j);

			if ( line1.equals(line2)) {
				continue;
			} else {
				
				int oldi= i;
				int oldj= j;
				
				boolean isAddedScenario = false;
				boolean isDeletedScenario = false;
					
					int k=j;
					while( ++k<lineSet2.size() ){
						line2 = lineSet2.get(k);
						if(line1.equals(line2)){
							if (!isAddedScenario)
								isAddedScenario = true;
							break;
						}
					}
					
					if (k==lineSet2.size())
						k=0;
					else
						k=k-j;
					
					for (int l=0; l<k; l++) {
						addedCount++;
						line2 = lineSet2.get(j);
						append(sb, new DiffContent("", line2, i, ++j, CHANGE_TYPE.ADDED));
					}
					
					if (!isAddedScenario) {
						line2 = lineSet2.get(oldj);
						j = oldj;
						k=i;
						while( ++k<lineSet1.size() ){
							line1 = lineSet1.get(k);
							if(line1.equals(line2)){
								if (!isDeletedScenario)
									isDeletedScenario = true;
								break;
							}
						}
						
						if (k==lineSet1.size())
							k=0;
						else
							k=k-i;
						
						for (int l=0; l < k; l++) {
							deletedCount++;
							line1 = lineSet1.get(i);
							append(sb, new DiffContent(line1, "", ++i, j, CHANGE_TYPE.REMOVED));
						}
						
					}
					
					if ( isAddedScenario || isDeletedScenario) {
						i--;
						j--;
					} else {
						i= oldi;
						j= oldj;
						changedCount++;
						append(sb, new DiffContent(lineSet1.get(i), lineSet2.get(j), i, j,  CHANGE_TYPE.CHANGED));
					}
					
			}

		}


		for (; i < lineSet1.size(); i++) {
			line = lineSet1.get(i);
			deletedCount++;
			append(sb, new DiffContent(line, "", ++i, j, CHANGE_TYPE.REMOVED));
		}

		for (; j < lineSet2.size(); j++) {
			line = lineSet2.get(j);
			addedCount++;
			append(sb, new DiffContent("", line, i, ++j, CHANGE_TYPE.ADDED));
		}


		if (changedCount == 0 && addedCount == 0 && deletedCount == 0) {
			i = sb.lastIndexOf("\n|--");
			if (i>0)
				sb.replace( i, sb.length() -1, "");
			else
				sb.replace( 0, sb.length() -1, f1.getAbsolutePath() + " -- " + f2.getAbsolutePath() + " [same]");
		} else {
			i = sb.lastIndexOf("<<RESULT>>");
			String str = "[" + (changedCount>0 ? " " + changedCount + " line" + (changedCount>1?"s":"") + " changed," : "")
					+  (addedCount>0 ? " " +addedCount + " line" + (addedCount>1?"s":"") + " added," : "")
					+	(deletedCount>0 ? " " +deletedCount + " line" + (deletedCount>1?"s":"") + " deleted,": "");
			sb.replace(i, i+10,  str.substring(0, str.length()-1) + " ]");
			if (!isNoteForDeletedLinePublishedBefore && deletedCount>0) {
				append(sb, "Note: x in 'Removed:x' represents removed line's index as per occurrence in the old file.");
				isNoteForDeletedLinePublishedBefore = true;
			}
				
		}

		return changedCount + addedCount + deletedCount;
		
	}

	private int checkDeletedOrAddedFile(File child1, String type) {

		int count=0;
		++level;
		if (child1.isDirectory()) {
			for(File f: sort(child1, -1)){

				StringBuffer sb1 = new StringBuffer();
				do {
					sb1.replace(0, 0, f.getName() +"/");
					f = f.getParentFile();
				} while(!f.getName().equals(child1.getName()));

				sb1.replace(0, 0, child1.getName() +"/");
				sb1.deleteCharAt(sb1.length() - 1);

				count++;
				append(sb, sb1.toString() + " [" + type +"]\n");
			}
		} else {
			count++;
			append(sb, child1.getName() + " [" + type + "]\n");
		}
		level--;

		return count;

	}
	
}
