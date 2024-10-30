package project2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PrinterTest {
    @Test
    @DisplayName("Нерешённый/Решённый")
    void test1() throws Exception {
        // given
        Printer printer = new Printer();

        Generator gen = new Generator(25);
        char [][] map = gen.generate();

        printer.printMap(map);

        Solver s = new Solver(map);
        char[][] solved = s.solve();

        printer.printMap(map);
    }
}
