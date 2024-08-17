package goldiounes.com.vn.models;

import goldiounes.com.vn.models.entities.Setting;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class SettingTest {
    @Test
    void testGetterAndSetter() {
        Setting setting = new Setting("bach kim",5000);

        assertEquals(5000,setting.getPrice());
        assertEquals("bach kim",setting.getMaterial());

        setting.setPrice(6000);
        setting.setMaterial("Bac");
        assertEquals(6000,setting.getPrice());
        assertEquals("Bac",setting.getMaterial());
    }

    @Test
    void testConstructor() {
        Setting setting = new Setting("vang",4000);
        assertNotNull(setting);
        assertEquals(4000,setting.getPrice());
        assertNotNull(setting.getMaterial());

    }
    @Test
    void testDefaultConstructor() {
        Setting setting = new Setting();
        assertNotNull(setting);
        assertEquals(0,setting.getPrice());
        assertNull(setting.getMaterial());

    }
}
