import java.util.Scanner;

public class Main {
	public static void renderMenu() {
		Scanner keyboard = new Scanner(System.in);
		SlangWord slangWord = new SlangWord();
		
		int option = 0;
		String[] menu = {"1. Tìm kiếm theo slang word.", "2. Tìm kiếm theo definition.", "3. Hiển thị history.",
				"4. Hiển thị history.", "5. Thêm 1 slang words.", "6. Sửa 1 slang words.", "7. Xoá 1 slang word.",
				"8. Reset danh sách slang words gốc.", "9. Tạo ngẫu nhiên 1 slang word", "10. Đố vui - Hiển thị 1 random slang word", 
				"11. Đố vui - Hiển thị 1 definition"};
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
			String result[][] = slangWord.findSlang(key);
			if ( result.length <= 0) {
				System.out.println("Không có kết quả khớp với từ tìm kiếm");
			}else {
				System.out.println("Từ trùng khớp là ^.^ \n");
				for (int j = 0; j < result.length; j++) {
					System.out.println(j + " " + result[j][1]);
				}
			}
		}
		
		if (option == 2) { 
			keyboard.nextLine();
			System.out.print("Mời bạn nhập vào định nghĩa slang world cần tìm kiếm: ");
			String key  = keyboard.nextLine();
			String result[][] = slangWord.findSlangWithDefinition(key);
			if ( result.length <= 0) {
				System.out.println("Không có kết quả khớp với từ tìm kiếm");
			}else {
				System.out.println("Từ trùng khớp là ^.^ \n");
				for (int j = 0; j < result.length; j++) {
					System.out.println(j + " " + result[j][1]);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println("CHÀO MỪNG BẠN ĐẾN VỚI ỨNG DỤNG SLANG WORD");
		renderMenu();
	}

}
