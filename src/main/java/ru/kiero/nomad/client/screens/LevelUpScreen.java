package ru.kiero.nomad.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import ru.kiero.nomad.Nomad;

public class LevelUpScreen extends Screen {

    private final ResourceLocation BG = new ResourceLocation(Nomad.MOD_ID, "textures/gui/levelUpMenu.png");

    private final int imageWidth;
    private final int imageHeight;
    private int leftPos;
    private int topPos;

    public LevelUpScreen(Component pTitle) {
        super(pTitle);

        imageWidth = 256;
        imageHeight = 256;
    }

    @Override
    protected void init() {
        super.init();
        this.leftPos = width/2-imageWidth/2;
        this.topPos = (this.height-imageHeight)/2;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public void renderBackground(GuiGraphics pGuiGraphics) {
        super.renderBackground(pGuiGraphics);
        RenderSystem.setShaderTexture(0, BG);
        pGuiGraphics.blit(BG, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
    }
}
