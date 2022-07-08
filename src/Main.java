import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void renderMenu() {
		Scanner keyboard = new Scanner(System.in);
		SlangWord slangWord = new SlangWord();
		
		int option = 0;
		String[] menu = {"1. Tìm kiếm theo slang word.", "2. Tìm kiếm theo definition.", "3. Hiển thị history.",
				"4. Thêm 1 slang words.", "5. Sửa 1 slang words.", "6. Xoá 1 slang word.",
				"7. Reset danh sách slang words gốc.", "8. Tạo ngẫu nhiên 1 slang word", "9. Đố vui - Hiển thị 1 random slang word", 
				"10. Đố vui - Hiển thị 1 definition"};
		do{    
			for (int i = 0; i < menu.length; i++) {
				  System.out.println(menu[i]);
				}
			 System.out.print("Mời bạn lựa chọn: ");
			
			option = keyboard.nextInt();
			if (option > menu.length || option < 1) {
				System.out.println("Lựa chọn của bạn không có trong menu, mời bạn chọn lại");
			}
			
		}while (option > menu.length || option < 1 );    
		
		
		if (option == 1) { 
			keyboard.nextLine();
			System.out.print("Mời bạn nhập vào slang world cần tìm kiếm: ");
			String key  = keyboard.nextLine();
			String result[][];
			try {
				result = slangWord.findSlang(key);
				if ( result.length <= 0) {
					System.out.println("Không có kết quả khớp với từ tìm kiếm");
				}else {
					System.out.println("\n----------------------Từ trùng khớp là ^.^ ----------------------\n");
					for (int j = 0; j < result.length; j++) {
						System.out.println(j + "    " + result[j][1].trim() + "    " + result[j][2].trim());
					}
					slangWord.saveHistory(result);
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (option == 2) { 
			keyboard.nextLine();
			System.out.print("Mời bạn nhập vào định nghĩa slang world cần tìm kiếm: ");
			String key  = keyboard.nextLine();
			String result[][] = slangWord.findSlangWithDefinition(key);
			if ( result.length <= 0) {
				System.out.println("Không có từ slang có định nghĩa trùng khớp với từ tìm kiếm");
			}else {
				System.out.println("\n--------Từ slang word có định nghĩa trùng khớp với từ vừa nhập là ^.^ --------\n");
				for (int j = 0; j < result.length; j++) {
					System.out.println(j + "    " + result[j][1].trim() + "    " + result[j][2].trim());
				}
			}
		}
		
		if (option == 3) {
			keyboard.nextLine();
			try {
				String[][] history = slangWord.readHistory();
				
				if ( history.length <= 0) {
					System.out.println("Chưa có kết quả tìm kiếm");
				}else {
					System.out.println("\n--------Từ Slang trong lịch sử tìm kiếm là ^.^ --------\n");
					for (int j = 0; j < history.length; j++) {
						System.out.println(j + "    " + history[j][0].trim() + "    " + history[j][1].trim());
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if ( option == 4 ) {
			keyboard.nextLine();
			try {
				System.out.print("Mời bạn nhập vào từ slang mới: ");
				String key  = keyboard.nextLine();
				String[] checkKey = slangWord.checkSlangExist(key.trim());
				
				if (checkKey == null) {
					System.out.print("Mời bạn nhập vào nghĩa từ slang: ");
					String meaning  = keyboard.nextLine();
					
					slangWord.addNewSlangWord(key, meaning);
					System.out.print("Đã thêm thành công");
				}else {
					System.out.print("Từ Slang đã tồn tại");
				}				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if ( option == 5 ) {
			keyboard.nextLine();
			try {
				String meaning = "";
				System.out.print("Mời bạn nhập vào từ slang cần sửa: ");
				String oldKey  = keyboard.nextLine();
				
				String[] checkOldKey = slangWord.checkSlangExist(oldKey);
				
				if (checkOldKey != null) {
					System.out.print("Mời bạn nhập vào từ slang mới: ");
					String newKey  = keyboard.nextLine();
					
					System.out.print("Bạn có muốn sửa nghĩa từ slang này không (1: Đồng ý, 0: Từ chối): ");
					int choose = keyboard.nextInt();
					
					if ( choose == 1 ) {
						keyboard.nextLine();
						System.out.print("Mời bạn nhập vào nghĩa của từ: ");
						meaning = keyboard.nextLine();
					}else {
						meaning = checkOldKey[1];
					}
					String newSlangWord = newKey.trim() + "`" + meaning.trim() + "\n";
					slangWord.editSlangWord(newSlangWord, oldKey);
					System.out.print("Sửa thành công");
				}else {
					System.out.print("Không có từ slang phù hợp");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if ( option == 6 ) {
			keyboard.nextLine();
			System.out.print("Mời bạn nhập vào từ slang cần xoá: ");
			String oldKey  = keyboard.nextLine();
			
			String[] checkOldKey = slangWord.checkSlangExist(oldKey);
			
			if (checkOldKey != null) {
				try {
					slangWord.deleteSlangWord(oldKey);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				System.out.print("Không có từ slang phù hợp");
			}
			
		}
		
		
	}
	
	public static void main(String[] args) {
		System.out.println("CHÀO MỪNG BẠN ĐẾN VỚI ỨNG DỤNG SLANG WORD");
		renderMenu();
	}

}
