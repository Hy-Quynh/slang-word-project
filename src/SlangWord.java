
public class SlangWord {
	
	
	private SlangWord() {
		
	}
	
	private static class SingletonHelper {
		static final SlangWord INSTANCE = new SlangWord();
	}
	
	public static SlangWord getInstance() {
		return SingletonHelper.INSTANCE;
	}
	
	
	
}
