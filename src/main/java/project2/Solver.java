package project2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class Solver {

    /**
     * Класс, решающий лабиринт и добавляющий его на карту.
     */

    private final static Logger LOGGER = LogManager.getLogger();
    private final char[][] map;
    private final int[][] was;
    public final static char PATH = '^';
    private final int n;
    private final int m;
    Boolean solved;

    Solver(char[][] map) {
        /**
         * Конструктор класса
         * @param n двумерный массив-карта лабиринта
         */
        solved = false;
        this.map = map;
        n = map.length;
        m = map[0].length;
        was = new int[n][m];
    }

    public char[][] solve() throws Exception {

        /**
         * Основной метод класса, решающий лабиринт
         *
         * @return двумерный массив-карта лабиринта, с добавленным на нее решением.
         */

        Deque<Dot> stack = new ArrayDeque<>();
        Dot startDot = new Dot(0, 1);
        stack.add(startDot);
        dfs(stack);

        for (int[] i : was) {
            Arrays.fill(i, 0);
        }
        if (!solved) {
            Exception unsolvable = new Exception();
            throw new Exception("Unsolvable puzzle", unsolvable);
        }
        return this.map;
    }

    private void addMove(List<Dot> possibleMoves, Dot mDot) {
        /**
         * Добовляет новую потенциальную клетку решения лабиринта
         * @param possibleMoves массив существующих вариантов решения лабиринта
         * @param mDot Следующая потенциальная клетка решения лабиринта
         */

        if (mDot.i <= n - 1 && mDot.i >= 0 && mDot.j <= m - 1 && mDot.j >= 0
            && map[mDot.i][mDot.j] != Generator.BLANK && was[mDot.i][mDot.j] == 0) {
            possibleMoves.add(mDot);
        }
    }

    private List<Dot> genPossible(Dot cDot) {

        /**
         * Определяет потенциальные продолжения решения лабиринта
         *
         * @param cDot Координаты последней клетки решения лабиринта
         *
         * @return Массив проверенных возможных координат решения лабиринта
         */


        List<Dot> possibleMoves = new ArrayList<>();

        addMove(possibleMoves, new Dot(cDot.i + 1, cDot.j));
        addMove(possibleMoves, new Dot(cDot.i, cDot.j + 1));
        addMove(possibleMoves, new Dot(cDot.i - 1, cDot.j));
        addMove(possibleMoves, new Dot(cDot.i, cDot.j - 1));

        return possibleMoves;
    }

    private void dfs(Deque<Dot> stack) {

        /**
         * Deep First Search алгоритм, осуществляющий решение лабиринта
         *
         * @param stack Стэк для хранения координат, пройденных алгоритмом
         */

        while (!stack.isEmpty()) {

            Dot cDot = stack.peek();
            was[cDot.i][cDot.j] = 1;

            if (map[cDot.i][cDot.j] == Generator.EXIT || solved) {
                solved = true;
                return;
            }

            map[cDot.i][cDot.j] = PATH;

            List<Dot> possibleMoves = genPossible(cDot);

            if (!possibleMoves.isEmpty()) {
                int chosen = (int) (Math.random() * possibleMoves.size());
                stack.push(possibleMoves.get(chosen));
            }

            if (possibleMoves.isEmpty()) {
                if (!solved) {
                    map[cDot.i][cDot.j] = Generator.SPACE;
                }
                stack.pop();
            }
        }
    }

    private record Dot(int i, int j) {
        /**
         * Статичная структура для хранения координат
         *
         * @param i Первая координата
         * @param j Вторая координата
         */
    }
}
