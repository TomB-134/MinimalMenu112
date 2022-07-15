package tomb.minimalmenu.mixin;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tomb.minimalmenu.LiteModMinimalMenu;

@Mixin(GuiIngameMenu.class)
public class GuiIngameMenuMixin extends GuiScreen {
    @Inject(method = "initGui", at = @At("TAIL"))
    public void initGui(CallbackInfo info) {
        if (LiteModMinimalMenu.config.addFolderButtonPauseScreen) {
            this.buttonList.add(new GuiButton(1000, this.width / 2 + 104, this.height / 4 + 120 + -16, 20, 20, I18n.format(".")));
        }
    }

    @Inject(method = "actionPerformed", at = @At("TAIL"))
    protected void actionPerformed(GuiButton p_actionPerformed_1_, CallbackInfo info) {
        if (p_actionPerformed_1_.id == 1000) {
            LiteModMinimalMenu.processButtonFolderClick(this.mc);
        }
    }
}
