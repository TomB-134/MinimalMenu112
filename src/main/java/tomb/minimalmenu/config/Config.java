package tomb.minimalmenu.config;

import com.google.gson.annotations.Expose;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigStrategy;
import com.mumfrey.liteloader.modconfig.Exposable;
import com.mumfrey.liteloader.modconfig.ExposableOptions;

@ExposableOptions(strategy = ConfigStrategy.Unversioned, filename = "minimalmenu")
public class Config implements Exposable {
    //Main Menu Options
    @Expose public boolean dirtBackground;
    @Expose public boolean addFolderButtonMainMenu;

    //Pause Screen Options
    @Expose public boolean addFolderButtonPauseScreen;

    public Config() {
        LiteLoader.getInstance().registerExposable(this, null);
    }

    void save() {
        LiteLoader.getInstance().writeConfig(this);
    }
}
