import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class AirLineSeatTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }
    @Test
    void allocate() throws Exception {
        int[][] opArray = {{4, 7, 1, 2, 5},
                {6, 8, 3, 0, 0}

        };
        int[][]ipArray={{3,2},{2,1}};
        AirLineSeat seats=new AirLineSeat(ipArray,8);
        seats.allocate();
        Assert.assertArrayEquals(opArray,seats.visitedArr);
    }

    @Test
    void testForUsersMorethanSeats() throws Exception
    {
        int[][] opArray = {{4, 7, 1, 2, 5},
                {6, 8, 3, 0, 0}

        };
        int[][]ipArray={{3,2},{2,1}};
        AirLineSeat seats=new AirLineSeat(ipArray,9);
        seats.allocate();
        Assert.assertArrayEquals(opArray,seats.visitedArr);
        Assert.assertThat(outputStreamCaptor.toString().trim(), CoreMatchers.containsString("Seats available only for 8"));
    }








}