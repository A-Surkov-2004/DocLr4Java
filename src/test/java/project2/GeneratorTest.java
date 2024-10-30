package project2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GeneratorTest {
    @Test
    @DisplayName("Стресс тест 1")
    void test1() throws Exception {
        for (int i = 0; i < 10000; i++){

            // given
            Generator gen = new Generator(15);

            //when
            char [][] map = gen.generate();
            Solver s = new Solver(map);
            char[][] solved = s.solve();

            // then
            assertThat(s.solved)
                .isEqualTo(true);
        }
    }

    @Test
    @DisplayName("Стресс тест 2, разные размеры")
    void test2() throws Exception {
        for (int i = 1; i < 50; i++){

            // given
            Generator gen = new Generator(i);

            //when
            char [][] map = gen.generate();
            Solver s = new Solver(map);
            char[][] solved = s.solve();

            // then
            assertThat(s.solved)
                .isEqualTo(true);
        }
    }

    @Test
    @DisplayName("Лабиринт 1х1")
    void test3() throws Exception {
        // given
        Generator gen = new Generator(1);

        //when
        char [][] map = gen.generate();
        Solver s = new Solver(map);
        char[][] solved = s.solve();

        // then
        assertThat(s.solved)
            .isEqualTo(true);
    }
}
