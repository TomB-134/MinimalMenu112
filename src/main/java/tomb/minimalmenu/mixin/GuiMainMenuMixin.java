package tomb.minimalmenu.mixin;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tomb.minimalmenu.LiteModMinimalMenu;

@Mixin(GuiMainMenu.class)
public class GuiMainMenuMixin extends GuiScreen {

    @Inject(method = "initGui", at = @At("TAIL"))
    public void initGui(CallbackInfo info) {
        if (LiteModMinimalMenu.config.addFolderButtonMainMenu) {
            this.buttonList.add(new GuiButton(1000, this.width / 2 + 104, (this.height / 4 + 48) + 84, 20, 20, I18n.format(".")));
        }
    }

    @Inject(method = "actionPerformed", at = @At("TAIL"))
    protected void actionPerformed(GuiButton p_actionPerformed_1_, CallbackInfo info) {
        if (p_actionPerformed_1_.id == 1000) {
            LiteModMinimalMenu.processButtonFolderClick(this.mc);
        }
    }

    private boolean gradientIndex = false; //Keeps track of which gradient to draw each cycle... this is awful but I'm lazy

    @Inject(method = "renderSkybox", at = @At("HEAD"), cancellable = true) //Draw normal background instead of panorama.
    private void renderSkybox(int p_renderSkybox_1_, int p_renderSkybox_2_, float p_renderSkybox_3_, CallbackInfo info) {
        if (LiteModMinimalMenu.config.dirtBackground) {
            this.drawDefaultBackground();
            info.cancel();
        }
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;drawGradientRect(IIIIII)V")) //Sort out the gradient
    public void doNothing(GuiMainMenu instance, int i, int i2, int i3, int i4, int i5, int i6) {
        if (!LiteModMinimalMenu.config.dirtBackground) { //It's not hacky if it works...
            if (!gradientIndex) {
                this.drawGradientRect(0, 0, this.width, this.height, -2130706433, 16777215);
            } else {
                this.drawGradientRect(0, 0, this.width, this.height, 0, -2147483648);
            }
            gradientIndex = !gradientIndex;
        }
    }
}
