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

    public static String cordToUCI(Cord cord){
        return ""
            + (char) (cord.file() + 'a')
            + ""
            + (cord.rank() + 1);
    }

    public static Cord stringToCord(String str){
        return new Cord(Integer.parseInt("" + str.charAt(1)) - 1, str.charAt(0) - 'a');
    }

    public static Move stringToMove(String str){
        return new Move(stringToCord(str.substring(0,2)), stringToCord(str.substring(2)));
    }
}
