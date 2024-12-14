package project2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.*;

public class Solver {

    /**
     * Класс, решающий лабиринт и добавляющий его на карту.
     */

    private final static Logger LOGGER = LogManager.getLogger();
    private final char[][] map;
    private final int[][] was;
    public final static char PATH = '^';
    public final static char CURRENT = 'C';
    public final static char WAS = 'W';
    public final static char TRACE = 'T';
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
        Dot startDot = new Dot(0, 1, null);
        stack.add(startDot);
        dfs(stack);


        for (int[] i : was) {
            Arrays.fill(i, 0);
        }
        if (!solved) {
            Exception unsolvable = new Exception();
            throw new Exception("Unsolvable puzzle", unsolvable);
        }
        for (char[] str : map) {
            for (char ch : str) {
                System.out.print(ch);
            }
            System.out.println('\n');
        }
        return this.map;
    }

    public char[][] solveBFS() throws Exception {

        /**
         * Основной метод класса, решающий лабиринт
         *
         * @return двумерный массив-карта лабиринта, с добавленным на нее решением.
         */

        Deque<Dot> queue = new ArrayDeque<>();
        Dot startDot = new Dot(0, 1, null);
        queue.add(startDot);
        bfs(queue);

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

        addMove(possibleMoves, new Dot(cDot.i + 1, cDot.j, cDot));
        addMove(possibleMoves, new Dot(cDot.i, cDot.j + 1, cDot));
        addMove(possibleMoves, new Dot(cDot.i - 1, cDot.j, cDot));
        addMove(possibleMoves, new Dot(cDot.i, cDot.j - 1, cDot));

        return possibleMoves;
    }

    private void dfs(Deque<Dot> stack) {

        /**
         * Deep First Search алгоритм, осуществляющий решение лабиринта
         *
         * @param stack Стэк для хранения координат, пройденных алгоритмом
         */

        while (!stack.isEmpty()) {
            Printer printer = new Printer();
            Scanner scanner = new Scanner(System.in);
            // scanner.nextLine();
            printer.printMap(this.map);

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
                map[cDot.i][cDot.j] = WAS;
                stack.pop();
            }
        }
    }


    private void dfsr(Dot cDot) {


        Printer printer = new Printer();
        Scanner scanner = new Scanner(System.in);
        // scanner.nextLine();
        printer.printMap(this.map);

        was[cDot.i][cDot.j] = 1;

        if (map[cDot.i][cDot.j] == Generator.EXIT) {
            solved = true;
            backtrack(cDot);
            return;
        }
        if (solved) {
            return;
        }
        map[cDot.i][cDot.j] = PATH;

        List<Dot> possibleMoves = genPossible(cDot);


        for (Dot d : possibleMoves) {
            dfsr(d);
            if (!solved) {
                map[cDot.i][cDot.j] = Generator.SPACE;
            }
            map[cDot.i][cDot.j] = WAS;
        }
    }

    private void bfs(Deque<Dot> queue) {

        while (!queue.isEmpty()) {


            Dot cDot = queue.poll();
            was[cDot.i][cDot.j] = 1;

            if (map[cDot.i][cDot.j] == Generator.EXIT || solved) {
                solved = true;
                backtrack(cDot);
                return;
            }

            map[cDot.i][cDot.j] = CURRENT;

            Printer printer = new Printer();
            printer.printMap(this.map);

            map[cDot.i][cDot.j] = PATH;

            List<Dot> possibleMoves = genPossible(cDot);
            queue.addAll(possibleMoves);


            if (possibleMoves.isEmpty()) {
                if (!solved) {
                    map[cDot.i][cDot.j] = Generator.SPACE;
                }
                map[cDot.i][cDot.j] = WAS;
                //queue.poll();
            }
        }
    }

    private void backtrack(Dot cDot) {
        Printer p = new Printer();
        while (!Objects.isNull(cDot.parent)) {
            map[cDot.i][cDot.j] = TRACE;
            cDot = cDot.parent;
            p.printMap(map);
        }
        map[cDot.i][cDot.j] = TRACE;


    }


    private record Dot(int i, int j, Dot parent) {

        /**
         * Статичная структура для хранения координат
         *
         * @param i Первая координата
         * @param j Вторая координата
         */
    }
}
