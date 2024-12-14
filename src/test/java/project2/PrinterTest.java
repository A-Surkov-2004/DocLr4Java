package project2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PrinterTest {
    @Test
    @DisplayName("Нерешённый/Решённый")
    void test1() throws Exception {
        // given
        Printer printer = new Printer();

        Generator gen = new Generator(5);
        char [][] map = gen.generate();

        printer.printMap(map);

        Solver s = new Solver(map);
        char[][] solved = s.solve();

        printer.printMap(map);
    }

    @Test
    @DisplayName("BFS")
    void test2() throws Exception {
        // given
        Printer printer = new Printer();

        Generator gen = new Generator(5);
        char [][] map = gen.generate();

        printer.printMap(map);

        Solver s = new Solver(map);
        char[][] solved = s.solveBFS();

        printer.printMap(map);
    }
}
