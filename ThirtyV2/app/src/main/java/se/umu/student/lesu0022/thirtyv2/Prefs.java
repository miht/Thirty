package se.umu.student.lesu0022.thirtyv2;

/**
 * Created by leifthysellsundqvist on 2017-07-03.
 */

import se.umu.student.lesu0022.thirtyv2.GameAssets.Game;

/**
 * This class contains static variables used within the game. For instance, the Game object keeping track of
 * the player's progress is stored here.
 */
public class Prefs {

    public static Game GAME;
    public static String GAME_STATE = "Current game";

    public static int REQUESTCODE_COMBOSCREEN = 1;
    public static int RESULTSCODE_COMBOSCREEN_PRESSEDBACKBUTTON = 11;
    public static int RESULTCODE_COMBOSCREEN_ADDEDCOMBO = 12;

    public static int REQUESTCODE_SUMMARYSCREEN = 2;
    public static int RESULTCODE_SUMMARYSCREEN = 21;
}
