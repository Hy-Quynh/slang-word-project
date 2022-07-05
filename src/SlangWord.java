import java.util.TreeMap;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;

public class SlangWord {
	private TreeMap<String, List<String>> map = new TreeMap<>();
	private static SlangWord obj = new SlangWord();// Early, instance will be created at load time
	private int sizeMap;
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
		map.clear();
		String slag = null;
		Scanner scanner = new Scanner(new File(file));
		scanner.useDelimiter("`");
		scanner.next();
		String temp = scanner.next();
		String[] part = temp.split("\n");
		int i = 0;
		int flag = 0;
		sizeMap = 0;
		
		while (scanner.hasNext()) {
			List<String> meaning = new ArrayList<String>();
			slag = part[1].trim();
			temp = scanner.next();
			part = temp.split("\n");
			if (map.containsKey(slag)) {
				meaning = map.get(slag);
			}
			if (part[0].contains("|")) {
				String[] d = (part[0]).split("\\|");
				for (int ii = 0; ii < d.length; ii++)
					System.out.println(d[ii]);
				Collections.addAll(meaning, d);
				sizeMap += d.length - 1;
			} else {
				meaning.add(part[0]);
			}
			// map.put(slag.trim(), meaning);
			map.put(slag, meaning);
			i++;
			sizeMap++;
		}
		scanner.close();
	}
	
	public String[][] getData() {
		String s[][] = new String[sizeMap][3];
		Set<String> slagListSet = map.keySet();
		Object[] slagList = slagListSet.toArray();
		int index = 0;
		
		for (int i = 0; i < sizeMap; i++) {
			s[i][0] = String.valueOf(i);
			s[i][1] = (String) slagList[index];
			List<String> meaning = map.get(slagList[index]);
			s[i][2] = meaning.get(0);
			for (int j = 1; j < meaning.size(); j++) {
				if (i < sizeMap)
					i++;
				s[i][0] = String.valueOf(i);
				s[i][1] = (String) slagList[index];
				s[i][2] = meaning.get(j);
			}
			index++;
		}
		return s;
	}

	public String[][] findSlang(String key) { 
		String listData[][] = getData();
		List<String[]> dataMap = new ArrayList<String[]>();
		for (int i = 0; i < listData.length; i++) {
			if (listData[i][1].toLowerCase().contains(key.toLowerCase())) {
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
		System.out.println("key" + key);
		
		String listData[][] = getData();
		List<String[]> dataMap = new ArrayList<String[]>();
		for (int i = 0; i < listData.length; i++) {
			if (listData[i][2].toLowerCase().contains(key.toLowerCase())) {
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
	
	public String[][] getMeaning(String key) {
		List<String> listMeaning = map.get(key);
		if (listMeaning == null)
			return null;
		int size = listMeaning.size();
		String s[][] = new String[size][3];
		for (int i = 0; i < size; i++) {
			s[i][0] = String.valueOf(i);
			s[i][1] = key;
			s[i][2] = listMeaning.get(i);
		}
		return s;
	}
}
