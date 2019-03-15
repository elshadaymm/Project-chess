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
            + (char) (cord.getFile() + 'a')
            + ""
            + (cord.getRank() + 1);
    }

    public static Cord StringToCord(String str){
        return new Cord(str.charAt(0) - 'a', Integer.parseInt("" + str.charAt(3)) - 1);
    }
}
