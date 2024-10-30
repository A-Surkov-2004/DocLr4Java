package project2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SolverTest {
    @Test
    @DisplayName("Нерешаемый")
    void test1(){
        // given
        char B = Generator.BLANK;
        char S = Generator.SPACE;
        char E = Generator.EXIT;
        char[][] map = new char[][] {
            {B,S,B,B,B,B,B,B},
            {B,S,B,B,B,B,B,B},
            {B,S,B,B,B,B,B,B},
            {B,S,B,B,B,B,B,B},
            {B,S,B,B,B,B,B,B},
            {B,S,B,B,B,S,S,B},
            {B,S,S,S,S,S,B,B},
            {B,B,B,B,B,B,E,B},
        };

        //when
        Solver s = new Solver(map);
        try {
            char[][] solved = s.solve();
        }catch (Exception ignored){}


        // then
        assertThat(s.solved)
            .isEqualTo(false);
    }

    @Test
    @DisplayName("Пустой")
    void test2() throws Exception {
        // given
        char B = Generator.BLANK;
        char S = Generator.SPACE;
        char E = Generator.EXIT;
        char[][] map = new char[][] {
            {S,S,S,S,S,S,S,S},
            {S,S,S,S,S,S,S,S},
            {S,S,S,S,S,S,S,S},
            {S,S,S,S,S,S,S,S},
            {S,S,S,S,S,S,S,S},
            {S,S,S,S,S,S,S,S},
            {S,S,S,S,S,S,S,S},
            {S,S,S,S,S,S,E,S},
        };

        //when
        Solver s = new Solver(map);
        char[][] solved = s.solve();


        // then
        assertThat(s.solved)
            .isEqualTo(true);
    }
}
