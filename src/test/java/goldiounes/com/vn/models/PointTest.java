package goldiounes.com.vn.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

class PointTest {

    @Test
    void testPointGettersAndSetters() {
        Point point = new Point();

        User mockUser = Mockito.mock(User.class);

        point.setPointID(1);
        point.setUser(mockUser);
        point.setPoints(100);

        assertEquals(1, point.getPointID());
        assertEquals(mockUser, point.getUser());
        assertEquals(100, point.getPoints());
    }

    @Test
    void testPointConstructor() {
        User mockUser = Mockito.mock(User.class);
        Point point = new Point(100, mockUser);

        assertEquals(100, point.getPoints());
        assertEquals(mockUser, point.getUser());
    }
}
