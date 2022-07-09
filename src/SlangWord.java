import java.util.TreeMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class SlangWord {
	List<String[]> slangMap = new ArrayList<String[]>();
	private static SlangWord obj = new SlangWord();
	private String FILE_SLANGWORD = "slang.txt";
	private String TEMPFILE_SLANGWORD = "tempfile-slang.txt";
	private String FILE_HISTORY = "history.txt";
	private String BACKUP_FILE = "backup-file.txt";
	
	SlangWord() {
		try {
			String current = new java.io.File(".").getCanonicalPath();
			System.out.println("Current dir:" + current);
			FILE_SLANGWORD = current + "//" + FILE_SLANGWORD;
			FILE_HISTORY = current + "//" + FILE_HISTORY;
			TEMPFILE_SLANGWORD = current + "//" + TEMPFILE_SLANGWORD;
			BACKUP_FILE = current + "//" + BACKUP_FILE;
			readFile(FILE_SLANGWORD);
			initBackupFile();
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
	
	void initBackupFile() throws IOException {
		File inputFile = new File(FILE_SLANGWORD);
		File tempFile = new File(BACKUP_FILE);

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		String currentLine;

		while((currentLine = reader.readLine()) != null) {
		    writer.write(currentLine + System.getProperty("line.separator"));
		}
		writer.close(); 
		reader.close(); 
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
	
	String[] checkSlangExist (String key) {
		String s[][] = getData();
		
		for (int i = 0; i< s.length; i++) {
			if ( s[i][1].equals(key) ) {
				String[] slang = new String[2];
				slang[0] = s[i][1];
				slang[1] = s[i][2];
				return slang;
			}
		}
		return null;
	}
	
	void editSlangWord(String keyReplace, String slangWord, String meaning) throws IOException {
		BufferedReader file = new BufferedReader(new FileReader(FILE_SLANGWORD));
        StringBuffer inputBuffer = new StringBuffer();
        String line;

        while ((line = file.readLine()) != null) {
        	String[] slangAndMeaning = line.split("`");
        	
        	if ( slangAndMeaning[0].trim().equals(slangWord.trim())) {
        		inputBuffer.append(keyReplace.trim());
        	}else {
        		inputBuffer.append(line);
        	}
            inputBuffer.append('\n');
        }
        
        file.close();
        FileOutputStream fileOut = new FileOutputStream(FILE_SLANGWORD);
        fileOut.write(inputBuffer.toString().getBytes());
        fileOut.close();
        
        for(int i= 0; i < slangMap.size(); i++ ) {
        	String slangAndMeaning = slangMap.get(i)[0];
        	if ( slangAndMeaning.trim().equals(slangWord.trim())) {
        		System.out.println("aaaaaaaaaaaaaa");
        		String s[] = new String[2];
        		s[0] = slangWord;
        		s[1] = meaning;
        		slangMap.set(i, s);
        	}
        }
	}
	
	void deleteSlangWord(String slangWord) throws IOException {
		File inputFile = new File(FILE_SLANGWORD);
		File tempFile = new File(TEMPFILE_SLANGWORD);

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		String currentLine;

		while((currentLine = reader.readLine()) != null) {
		    // trim newline when comparing with lineToRemove
		    String trimmedLine = currentLine.trim();
		    String[] slangAndMeaning = trimmedLine.split("`");
		    if ( slangAndMeaning[0].trim().equals(slangWord.trim())) {
		    	continue;
		    }
		    writer.write(currentLine + System.getProperty("line.separator"));
		}
		writer.close(); 
		reader.close(); 
		tempFile.renameTo(inputFile);
		
		int indexSlang = 0;
		for(int i= 0; i < slangMap.size(); i++ ) {
	        String slangAndMeaning = slangMap.get(i)[0];
	        if ( slangAndMeaning.trim().equals(slangWord.trim())) {
	        	indexSlang = i;
	        }
		}
		slangMap.remove(indexSlang);
	}
	
	void cloneBackupFileToSlangFile() throws IOException {
		PrintWriter writer1 = new PrintWriter(FILE_SLANGWORD);
		writer1.print("");
		writer1.close();
		
		File inputFile = new File(BACKUP_FILE);
		File tempFile = new File(FILE_SLANGWORD);

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		String currentLine;

		while((currentLine = reader.readLine()) != null) {
		    writer.write(currentLine + System.getProperty("line.separator"));
		}
		writer.close(); 
		reader.close(); 
		
	}
	
	public String[] randomSlangWord() {
		// Random a number
		int minimun = 0;
		int maximun = slangMap.size() - 1;
		int rand = randInt(minimun, maximun);
		// Get slang meaning
		String s[] = new String[2];
		for(int i=0; i<slangMap.size(); i++) {
			// System.out.println(key);
			if (i == rand) {
				s[0] = slangMap.get(i)[0];
				s[1] = slangMap.get(i)[1];
				break;
			}
		}
		return s;
	}
	
	public static int randInt(int minimum, int maximum) {
		return (minimum + (int) (Math.random() * maximum));
	}
	
	void quizSlang() {
		String[] randomString = randomSlangWord();
		List<String[]> listRandomQuiz = new ArrayList<String[]>();
		
		listRandomQuiz.add(randomString);
		for(int i=0; i< 3; i++) {
			listRandomQuiz.add(randomSlangWord());
		}

		System.out.println("Cho biết nghĩa của từ Slang sau " + listRandomQuiz.get(0)[0]);
		for(int i =0 ;i< listRandomQuiz.size(); i++ ) {
			System.out.println((i + 1) + " " + listRandomQuiz.get(i)[1]);
		}	
		System.out.println("Đáp án chính xác là: ");
		int choose = new Scanner(System.in).nextInt();
		if ( choose == 1 ) {
			System.out.println("Bạn trả lời chính xác");
		}else {
			System.out.println("Bạn trả lời sai mất rồi");
		}
		
	}
}
