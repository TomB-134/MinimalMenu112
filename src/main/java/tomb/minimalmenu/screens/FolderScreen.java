package tomb.minimalmenu.screens;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import java.io.File;

public class FolderScreen extends GuiScreen {

    private String[] directories;
    private File file;
    private GuiScreen lastScreen;

    public FolderScreen(GuiScreen lastScreen) {
        this.lastScreen = lastScreen;
    }

    public void initGui() {
        assert mc != null;
        file = mc.gameDir.toPath().toFile();
        directories = file.list((dir, name) -> new File(dir, name).isDirectory());

        assert directories != null;

        int y = (directories.length * 24) / 2;

        for (int i = 0; i <= directories.length; i++) {
            if (i == 0) {
                this.buttonList.add(new GuiButton(i-1, this.width / 2 - 100, (this.height / 2 + (i-1) * 24) - y, 200, 20, I18n.format(file.getName())));
                this.buttonList.add(new GuiButton(i-2, this.width / 2 - 100, (this.height / 2 + (directories.length+1) * 24) - y, 200, 20, I18n.format("gui.done")));
            }
            if (i < directories.length) {
                int x = i;

                this.buttonList.add(new GuiButton(i, this.width / 2 - 100, (this.height / 2 + i * 24) - y, 200, 20, I18n.format(directories[x])));
            }
        }
    }

    protected void actionPerformed(GuiButton p_actionPerformed_1_) {
        if (p_actionPerformed_1_.id == -1) {
            OpenGlHelper.openFile(file);
        }
        else if (p_actionPerformed_1_.id == -2) {
            this.mc.displayGuiScreen(lastScreen);
        }
        else {
            File fileToOpen = new File(file.getAbsolutePath() + File.separator + directories[p_actionPerformed_1_.id]);
            OpenGlHelper.openFile(fileToOpen);
        }
    }

    public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_) {
        this.drawDefaultBackground();
        super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
    }
}
