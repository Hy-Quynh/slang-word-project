import java.util.TreeMap;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class SlangWord {
	List<String[]> slangMap = new ArrayList<String[]>();
	private static SlangWord obj = new SlangWord();
	private String FILE_SLANGWORD = "slang.txt";
	private String FILE_ORIGINAL_SLANGWORD = "slangword-goc.txt";
	private String FILE_HISTORY = "history.txt";
	
	SlangWord() {
		try {
			String current = new java.io.File(".").getCanonicalPath();
			System.out.println("Current dir:" + current);
			FILE_SLANGWORD = current + "//" + FILE_SLANGWORD;
			FILE_ORIGINAL_SLANGWORD = current + "//" + FILE_ORIGINAL_SLANGWORD;
			FILE_HISTORY = current + "//" + FILE_HISTORY;
			readFile(FILE_SLANGWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SlangWord getInstance() {
		if (obj == null) {
			synchronized (SlangWord.class) {
				if (obj == null) {
					obj = new SlangWord();
				}
			}
		}
		return obj;
	}
	
	
	void readFile(String file) throws Exception {
		slangMap.clear();
		File myObj = new File(file);
	    Scanner myReader = new Scanner(myObj);
	    while (myReader.hasNextLine()) {
	        String data = myReader.nextLine();
	        String[] slangAndMeaning = data.split("`");
	        List<String> slangMeaning = new ArrayList<String>();
	        
	        if ( slangAndMeaning.length > 1) {
	        	String meaning[] = slangAndMeaning[1].split("\\|");
	        	if(meaning.length > 0) {
	        		for(int ii=0 ;ii < meaning.length; ii++) {
	        			String[] slang = new String[2];
	        			slang[0] = slangAndMeaning[0];
	        			slang[1] = meaning[ii].trim();
	        			slangMap.add(slang);
	        		}
	        	}
	        }
	        
	        if ( slangAndMeaning.length == 1 ) {
	        	String[] slang = new String[2];
    			slang[0] = slangAndMeaning[0];
    			slang[1] = "";
    			slangMap.add(slang);	
	        }
	    }
	    myReader.close();
	}
	
	public String[][] getData() {
		String s[][] = new String[slangMap.size()][3];
		for (int i = 0; i < slangMap.size(); i++) {
			s[i][0] = String.valueOf(i);
			s[i][1] = (String) slangMap.get(i)[0];
			s[i][2] = (String) slangMap.get(i)[1];
		}
		return s;
	}
	
	boolean checkSlangExistHistory(String[] slang) throws Exception {
		String[][] listHistory = readHistory();
		
		for(int i=0; i< listHistory.length; i++ ) {
			if ( slang[1].trim().equals(listHistory[i][0].trim()) && slang[2].trim().equals(listHistory[i][1].trim())) {
				return true;
			}
		}
		return false;
	}
	
	public String[][] readHistory() throws Exception {
		File myObj = new File(FILE_HISTORY);
	    Scanner myReader = new Scanner(myObj);
	    List<String[]> history = new ArrayList<String[]>();
	    
	    while (myReader.hasNextLine()) {
	        String data = myReader.nextLine();
	        String[] slangAndMeaning = data.split("`");
	        String[] slangWord = new String[2];
	        slangWord[0] = slangAndMeaning[0].trim();
	        slangWord[1] = "";
	        if ( slangAndMeaning.length > 1 ) {
	        	slangWord[1] = slangAndMeaning[1].trim();
	        }	
	        history.add(slangWord);
	    }
	    myReader.close();
	    
	    String s[][] = new String[history.size()][2];
		for (int i = 0; i < history.size(); i++) {
			s[i][0] = (String) history.get(i)[0];
			s[i][1] = (String) history.get(i)[1];
		}
		return s;
	}

	
	public void saveHistory(String[][] slangWord) throws Exception {
		File file = new File(FILE_HISTORY);
		FileWriter fr = new FileWriter(file, true);
		
		for(int i =0; i < slangWord.length; i++ ) {
			boolean checkExist = checkSlangExistHistory(slangWord[i]);
			if ( !checkExist ) {
				fr.write(slangWord[i][1].trim() + "`" + slangWord[i][2].trim() + "\n");
			}
		}
		fr.close();
	}
	
	public String[][] findSlang(String key) throws Exception { 
		String[][] listData = getData();
		List<String[]> dataMap = new ArrayList<String[]>();
		for (int i = 0; i < listData.length; i++) {
			if (listData[i][1].toLowerCase().trim().contains(key.toLowerCase().trim())) {
				dataMap.add(listData[i]);
			}
		}
		int size = dataMap.size();
		String s[][] = new String[size][3];

		for (int i = 0; i < size; i++) {
			s[i][0] = String.valueOf(i);
			s[i][1] = dataMap.get(i)[1];
			s[i][2] = dataMap.get(i)[2];
		}
		return s;		
	}
	
	public String[][] findSlangWithDefinition(String key) { 
		String listData[][] = getData();
		List<String[]> dataMap = new ArrayList<String[]>();
		for (int i = 0; i < listData.length; i++) {
			if (listData[i][2].toLowerCase().trim().contains(key.toLowerCase().trim())) {
				dataMap.add(listData[i]);
			}
		}
		
		int size = dataMap.size();
		String s[][] = new String[size][3];

		for (int i = 0; i < size; i++) {
			s[i][0] = String.valueOf(i);
			s[i][1] = dataMap.get(i)[1];
			s[i][2] = dataMap.get(i)[2];
		}
		return s;		
	}
	
	void addNewSlangWord(String key, String meaning) throws Exception {
		File file = new File(FILE_SLANGWORD);
		FileWriter fr = new FileWriter(file, true);
		fr.write(key.trim() + "`" + meaning.trim() + "\n");
		fr.close();
		
		String[] newSlang = new String[2];
		newSlang[0] = key;
		newSlang[1] = meaning;
		slangMap.add(newSlang);
	}
	
}
