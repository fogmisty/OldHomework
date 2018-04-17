package smallchange;

public class Array {

	@SuppressWarnings("unused")
	public String array(String str) {
		String[] sourceStrArray = str.split("<br>");
		for (int i = 1; i < sourceStrArray.length; i++) {
			return(sourceStrArray[i]);
		}
		return null;
	}
	public String array1(String str) {
		String[] sourceStrArray = str.split("<br>");
		if(sourceStrArray.length >= 2){
			if(sourceStrArray[1] != null){
				return sourceStrArray[1];
			}
			return null;
		}			
		else
			return null;
	}
	public String array2(String str) {
		String[] sourceStrArray = str.split("<br>");
		if(sourceStrArray.length >= 3){
			if(sourceStrArray[2] != null){
				return sourceStrArray[2];
			}
			return null;
		}
		return null;
	}
	public String array3(String str) {
		String[] sourceStrArray = str.split("<br>");
		if(sourceStrArray.length >= 4){
			if(sourceStrArray[3] != null){
				return sourceStrArray[3];
			}
			return null;
		}
		return null;
	}
}