package tomb.minimalmenu.config;

import com.mumfrey.liteloader.client.gui.GuiCheckbox;
import com.mumfrey.liteloader.modconfig.AbstractConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import tomb.minimalmenu.LiteModMinimalMenu;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class GuiConfig extends AbstractConfigPanel { //Thanks Nessie!
    public final Config config = LiteModMinimalMenu.config;

    @Override
    protected void addOptions(ConfigPanelHost host) {
        final int SPACING = 16;
        int id = 0;
        final List<Field> fields = Arrays.asList(Config.class.getFields());
        fields.sort(Comparator.comparing(Field::getName));
        for (Field f : fields) {
            try {
                this.addControl(new GuiCheckbox(id, 0, SPACING * id++, f.getName()), control -> {
                    control.checked = !control.checked;
                    try {
                        f.setBoolean(config, control.checked);
                    } catch (IllegalAccessException ignored) {}
                }).checked = f.getBoolean(config);
            } catch (IllegalAccessException ignored) {}
        }
    }

    @Override
    public String getPanelTitle() {
        return "Minimal Menu Options";
    }

    @Override
    public void onPanelHidden() {
        this.config.save();
    }
}
