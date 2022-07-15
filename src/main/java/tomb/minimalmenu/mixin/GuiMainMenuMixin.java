package tomb.minimalmenu.mixin;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tomb.minimalmenu.LiteModMinimalMenu;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

@Mixin(GuiMainMenu.class)
public class GuiMainMenuMixin extends GuiScreen {

    @Shadow private String splashText;
    @Shadow @Final private static ResourceLocation SPLASH_TEXTS;
    @Shadow private static ResourceLocation field_194400_H;
    @Shadow private int widthCopyrightRest;
    @Shadow private int widthCopyright;

    @Inject(method = "initGui", at = @At("TAIL"))
    public void initGuiTail(CallbackInfo info) {

        if (LiteModMinimalMenu.config.removeRealms) {
            GuiButton realmsButton = LiteModMinimalMenu.getButtonFromList(this.buttonList, 14); //Hide Realms Button
            assert realmsButton != null;
            realmsButton.visible = false;

            int y = this.height / 4 + 48;

            GuiButton singlePlayerButton = LiteModMinimalMenu.getButtonFromList(this.buttonList, 1); // Move SinglePlayer Button
            assert singlePlayerButton != null;
            singlePlayerButton.y = y + 24;
            GuiButton multiPlayerButton = LiteModMinimalMenu.getButtonFromList(this.buttonList, 2); // Move MultiPlayer Button
            assert multiPlayerButton != null;
            multiPlayerButton.y = y + 48;
        }

        if (LiteModMinimalMenu.config.removeCopyright) {
            widthCopyrightRest = 2000000000;
        } else {
            this.widthCopyrightRest = this.width - this.widthCopyright - 2;
        }

        if (LiteModMinimalMenu.config.removeLanguageButton) {
            GuiButton languageButton = LiteModMinimalMenu.getButtonFromList(this.buttonList, 5);
            assert languageButton != null;
            languageButton.visible = false;
        }

        if (LiteModMinimalMenu.config.removeJavaEdition) { //Remove Java edition logo
            field_194400_H = new ResourceLocation("minimalmenu", "textures/gui/title/edition_empty.png");
        } else {
            field_194400_H = new ResourceLocation("textures/gui/title/edition.png");
        }

        if (LiteModMinimalMenu.config.addFolderButtonMainMenu) {
            this.buttonList.add(new GuiButton(1000, this.width / 2 + 104, (this.height / 4 + 48) + 84, 20, 20, I18n.format(".")));
        }

        if (LiteModMinimalMenu.config.removeSplashText) { //Remove and re-add splash text.
            this.splashText = "";
        } else if (this.splashText.equals("")) {
            IResource texts = null;
            Random RANDOM = new Random();
            try {
                List<String> textLists = Lists.newArrayList();
                texts = Minecraft.getMinecraft().getResourceManager().getResource(SPLASH_TEXTS);
                BufferedReader reader = new BufferedReader(new InputStreamReader(texts.getInputStream(), StandardCharsets.UTF_8));

                String currentLine;
                while((currentLine = reader.readLine()) != null) {
                    currentLine = currentLine.trim();
                    if (!currentLine.isEmpty()) {
                        textLists.add(currentLine);
                    }
                }

                if (!textLists.isEmpty()) {
                    do {
                        this.splashText = textLists.get(RANDOM.nextInt(textLists.size()));
                    } while(this.splashText.hashCode() == 125780783);
                }
            } catch (IOException ignored) {
            } finally {
                IOUtils.closeQuietly(texts);
            }
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
        if (!LiteModMinimalMenu.config.dirtBackground && !LiteModMinimalMenu.config.removePanoramaGradient) { //It's not hacky if it works...
            if (!gradientIndex) {
                this.drawGradientRect(0, 0, this.width, this.height, -2130706433, 16777215);
            } else {
                this.drawGradientRect(0, 0, this.width, this.height, 0, -2147483648);
            }
            gradientIndex = !gradientIndex;
        }
    }
}
