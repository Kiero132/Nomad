package ru.kiero.nomad.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import ru.kiero.nomad.Nomad;
import ru.kiero.nomad.api.GuiAPI;
import ru.kiero.nomad.data.CampData;

public class LevelUpScreen extends Screen {

    public static final ResourceLocation BG = new ResourceLocation(Nomad.MOD_ID, "textures/gui/levelupscreen.png");

    private final int imageWidth;
    private final int imageHeight;
    private int leftPos;
    private int topPos;

    private final String title;
    private final int level;
    private final int exp;
    private final int radius;

    private final int wood;
    private final int food;
    private final int stone;
    private final int leather;
    private final int rare;

    public LevelUpScreen(Component pTitle, int wood, int food, int stone, int leather, int rare, String title, int level, int exp, int radius) {
        super(pTitle);

        imageWidth = 176;
        imageHeight = 176;

        this.title = title;
        this.level = level;
        this.exp = exp;
        this.radius = radius;

        this.food = food;
        this.wood = wood;
        this.stone = stone;
        this.leather = leather;
        this.rare = rare;
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

        //Title
        String text = "Племя " + title;
        GuiAPI.drawSmallString(pGuiGraphics, this.font, text, this.leftPos+(this.imageWidth-this.font.width(text))/2+19, this.topPos+11,0xf5f0e8, false, 0.7f);

        //Level
        text = "Уровень " + level;
        GuiAPI.drawSmallString(pGuiGraphics, this.font, text, this.leftPos+(this.imageWidth-this.font.width(text))/2+13, this.topPos+29,0x372113, false, 0.55f);

        //Exp
        text = exp + "/" + CampData.expForLevel.get(level-1);
        GuiAPI.drawSmallString(pGuiGraphics, this.font, text, this.leftPos+(this.imageWidth-this.font.width(text))/2+68, this.topPos+40,0x261b12, false, 0.6f);

        //Resources
        text = GuiAPI.normalizeText(wood) + "/" + String.valueOf(CampData.woodForLevel.get(level-1));
        GuiAPI.drawSmallString(pGuiGraphics, this.font, text, this.leftPos+(this.imageWidth-this.font.width(text))/2-49, this.topPos+83,0x431c10, false, 0.5f);

        text = GuiAPI.normalizeText(food) + "/" + String.valueOf(CampData.foodForLevel.get(level-1));
        GuiAPI.drawSmallString(pGuiGraphics, this.font, text, this.leftPos+(this.imageWidth-this.font.width(text))/2-20, this.topPos+83,0x431c10, false, 0.5f);

        text = GuiAPI.normalizeText(stone) + "/" + String.valueOf(CampData.stoneForLevel.get(level-1));
        GuiAPI.drawSmallString(pGuiGraphics, this.font, text, this.leftPos+(this.imageWidth-this.font.width(text))/2+8, this.topPos+83,0x431c10, false, 0.5f);

        text = GuiAPI.normalizeText(leather) + "/" + String.valueOf(CampData.leatherForLevel.get(level-1));
        GuiAPI.drawSmallString(pGuiGraphics, this.font, text, this.leftPos+(this.imageWidth-this.font.width(text))/2+34, this.topPos+83,0x431c10, false, 0.5f);

        text = GuiAPI.normalizeText(rare) + "/" + String.valueOf(CampData.rareForLevel.get(level-1));
        GuiAPI.drawSmallString(pGuiGraphics, this.font, text, this.leftPos+(this.imageWidth-this.font.width(text))/2+63, this.topPos+83,0x431c10, false, 0.5f);

        //Radius
        text = "+" + String.valueOf(CampData.getRadiusOf(level+1) - CampData.getRadiusOf(level)) + " блоков";
        GuiAPI.drawSmallString(pGuiGraphics, this.font, text, this.leftPos+(this.imageWidth-this.font.width(text))/2-10, this.topPos+109,0x431c10, false, 0.5f);

        //TODO Nomads

    }

    @Override
    public void renderBackground(GuiGraphics pGuiGraphics) {
        //super.renderBackground(pGuiGraphics);
        RenderSystem.setShaderTexture(0, BG);
        pGuiGraphics.blit(BG, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
    }
}
