package sem2.inf101.v18.sem2.FourInARow.Tests;

import sem2.inf101.v18.sem2.FourInARow.Elements.*;
import org.junit.jupiter.api.Test;

class ElementsTest {

    @Test
    void symbolSetGet() {
        IGameElement chip = new Chip('?', 0, 0);

        assert chip.getTempSymb() == '?';
        assert chip.getOrigSymb() == '?';

        chip.setSymb('!');

        assert chip.getOrigSymb() == '?';
        assert chip.getTempSymb() == '!';

        chip.resetSymb();

        assert chip.getOrigSymb() == '?';
        assert chip.getTempSymb() == '?';
    }

    @Test
    void positionSetGet() {
        IGameElement chip = new Chip('?', 0, 0);

        assert chip.getXPos() == 0;
        assert chip.getYPos() == 0;

        chip.setXPos(1);
        chip.setYPos(1);

        assert chip.getXPos() == 1;
        assert chip.getYPos() == 1;
    }

    @Test
    void colorInteraction() {
        IGameElement chip = new Chip('?', 0, 0);

        chip.setColor("RED");

        assert chip.getColorCode().equals("\033[0;31m");
        assert chip.getColorCode("BOLD").equals("\033[1;31m");

        chip.setColor("BLUE");

        assert chip.getColorCode().equals("\033[0;34m");
        assert chip.getColorCode("UNDERLINED").equals("\033[4;34m");
        assert chip.getColor().equals("BLUE");

        chip.setColor("WHITE");

        assert chip.getColorCode().equals("\033[0;37m");
        assert chip.getColorCode("BACKGROUND_BRIGHT").equals("\033[0;107m");
        assert chip.getColor().equals("WHITE");
    }
}
