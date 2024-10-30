package project2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Printer {

    /**
     * Класс для красивого вывода лабиринта в консоль
     */

    private static final String BLACK = "\u001B[40m";
    private static final String RED = "\u001B[41m";
    private static final String WHITE = "\u001B[47m";
    private static final String WIDE = "   ";

    private final static Logger LOGGER = LogManager.getLogger();

    public void printMap(char[][] map) {
        /**
         * Основной метод класса, отрисовывающий лабиринт
         *
         * @param map Двумерный массив-карта лабиринта.
         */
        StringBuilder string = new StringBuilder();
        string.append('\n');
        for (char[] i : map) {
            for (char j : i) {
                if (j == Generator.BLANK) {
                    string.append(WHITE);
                } else if (j == Generator.SPACE) {
                    string.append(BLACK);
                } else if (j == Generator.EXIT) {
                    string.append(RED);
                } else if (j == Solver.PATH) {
                    string.append(RED);
                }
                string.append(WIDE);
            }
            string.append(BLACK);
            string.append(System.lineSeparator());
        }
        LOGGER.info(string);
    }

}
