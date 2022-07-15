package tomb.minimalmenu;

import com.mumfrey.liteloader.Configurable;
import com.mumfrey.liteloader.LiteMod;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import tomb.minimalmenu.config.Config;
import tomb.minimalmenu.config.GuiConfig;
import tomb.minimalmenu.screens.FolderScreen;

import java.io.File;
import java.util.List;

public class LiteModMinimalMenu implements LiteMod, Configurable {

    public static final Config config = new Config();

    public LiteModMinimalMenu() {
    }

    @Override
    public String getName() {
        return "MinimalMenu";
    }

    @Override
    public String getVersion() {
        return "1.0-SNAPSHOT";
    }

    @Override
    public void init(File configPath) {
    }

    @Override
    public void upgradeSettings(String version, File configPath, File oldConfigPath) {
    }

    @Override
    public Class<? extends ConfigPanel> getConfigPanelClass() {
        return GuiConfig.class;
    }

    public static void processButtonFolderClick(Minecraft client) {
        client.displayGuiScreen(new FolderScreen(client.currentScreen));
    }

    public static GuiButton getButtonFromList(List<GuiButton> buttonList, int buttonID) {
        for (GuiButton button : buttonList) {
            if (button.id == buttonID) {
                return button;
            }
        }
        return null;
    }
}