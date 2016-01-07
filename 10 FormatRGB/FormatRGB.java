
public class FormatRGB {
	
	public String convertColorToHex(int r, int g, int b){
		return (getHex(r)+getHex(g)+getHex(b));
	}
	
	private String getHex(int color){
		if(color<0 || color >255) return "00";
		int upperNumber = (int) Math.floor(color/16);
		int lowerNumber = color%16;
		char[] hex = new char[2];
		hex[0] =  getHexPerByte(upperNumber);
		hex[1] =  getHexPerByte(lowerNumber);
		return new String(hex);
	}
	private char getHexPerByte(int value){
		if(value<0 || value > 15) return 0;
		char[] hexChars = new char[]{'A', 'B', 'C', 'D', 'E', 'F'};
		char hex;
		if(0<=value && value<10) {
			hex = (String.format("%1d",value)).charAt(0);
		} else {
			hex = hexChars[value-10];
		}
		return hex;
	}
	public static void main(String[] args) {
		FormatRGB formatter = new FormatRGB();
		String hexColor = formatter.convertColorToHex(256, 256, 256);
		System.out.println(hexColor);
	}
}
