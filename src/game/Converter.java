/**
 * A class to convert x,y coordinates into the alphanumic notation of chess
 */
package game;

public class Converter{
    /**
     * Function to convert Universal Chess Interface to coordinate format
     * @param UCI Universal Chess Interface as a String
     * @return The coordinate converted from the corrosponding UCI as a String
     */
    public static String UCIToCord(String UCI){
        return ""
            + (Integer.parseInt("" + UCI.charAt(1)) - 1)
            + (UCI.charAt(0) - 'a')
            + (Integer.parseInt("" + UCI.charAt(3)) - 1)
            + (UCI.charAt(2) - 'a') ;
    }

    /**
     * Function to convert Coordinate format to Universal Chess Interface
     * @param cord Coordinate as a Cord
     * @return The Universal Chess interface converted from the corrosponding coordinate as a String
     */
    public static String cordToUCI(Cord cord){
        return ""
            + (char) (cord.file() + 'a')
            + ""
            + (cord.rank() + 1);
    }

    /**
     * Function to convert String coordinate to Cord coordinate
     * @param str coordinate as a string
     * @return coordinate as a Cord of the corrosponding String
     */
    public static Cord stringToCord(String str){
        return new Cord(Integer.parseInt("" + str.charAt(1)) - 1, str.charAt(0) - 'a');
    }

     /**
     * Function to convert String coordinate to a move
     * @param str coordinate as a string
     * @return Move of the corrosponding String
     */

    public static Move stringToMove(String str){
        return new Move(stringToCord(str.substring(0,2)), stringToCord(str.substring(2)));
    }
}
