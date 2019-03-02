/**
 * A class to convert x,y coordinates into the alphanumic notation of chess
 */

public class Converter{
    public static String UCIToCord(String UCI){
        return ""
            + (Integer.parseInt("" + UCI.charAt(1)) - 1)
            + (UCI.charAt(0) - 'a')
            + (Integer.parseInt("" + UCI.charAt(3)) - 1)
            + (UCI.charAt(2) - 'a') ;
    }

    public static String CordToUCI(Cord cord){
        return ""
            + (char) (cord.getRank() + 'a')
            + ""
            + (cord.getFile() + 1);
    }
}
