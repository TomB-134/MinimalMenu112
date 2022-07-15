package tomb.minimalmenu.mixin;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tomb.minimalmenu.LiteModMinimalMenu;

@Mixin(GuiOptions.class)
public class GuiOptionsMixin extends GuiScreen {
    @Inject(method = "initGui", at = @At("TAIL"))
    public void initGui(CallbackInfo info) { //Remove realms notification button and resize FOV slider
        if (LiteModMinimalMenu.config.removeRealms) {
            GuiButton realmsButton = LiteModMinimalMenu.getButtonFromList(this.buttonList, 37);
            assert realmsButton != null;
            realmsButton.visible = false;

            GuiButton FOVButton = LiteModMinimalMenu.getButtonFromList(this.buttonList, 2);
            FOVButton.setWidth(310);
        }
    }
}
