package ru.kiero.nomad.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import ru.kiero.nomad.Nomad;
import ru.kiero.nomad.api.GuiAPI;
import ru.kiero.nomad.data.CampData;
import ru.kiero.nomad.networking.LevelUpPacket;
import ru.kiero.nomad.networking.NomadNetworking;

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
    private final BlockPos blockPos;

    private final int wood;
    private final int food;
    private final int stone;
    private final int leather;
    private final int rare;

    public LevelUpScreen(Component pTitle, int wood, int food, int stone, int leather, int rare, String title, int level, int exp, int radius, BlockPos blockPos) {
        super(pTitle);

        imageWidth = 176;
        imageHeight = 176;

        this.title = title;
        this.level = level;
        this.exp = exp;
        this.radius = radius;
        this.blockPos = blockPos;

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

        this.addRenderableWidget(Button.builder(Component.literal(""), this::onLevelUp)
                .bounds(leftPos+22, topPos+147, 65, 15)
                .build(button -> new Button(button) {
                    @Override
                    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {

                    }
                }));
    }

    private void onLevelUp(Button button) {
        NomadNetworking.CHANNEL.sendToServer(new LevelUpPacket(blockPos, wood, food, stone, leather, rare, level, exp));
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

        //Resources Progress
        float barWidthTemp = (float) (wood) /CampData.woodForLevel.get(level-1);
        if (barWidthTemp > 1) barWidthTemp = 1;
        int barWidth = Math.round(24*barWidthTemp);
        pGuiGraphics.blit(BG, leftPos+19, topPos+77, 0, 179, barWidth, 4);

        barWidthTemp = (float) (food) /CampData.foodForLevel.get(level-1);
        if (barWidthTemp > 1) barWidthTemp = 1;
        barWidth = Math.round(24*barWidthTemp);
        pGuiGraphics.blit(BG, leftPos+48, topPos+77, 0, 183, barWidth, 4);

        barWidthTemp = (float) (stone) /CampData.stoneForLevel.get(level-1);
        if (barWidthTemp > 1) barWidthTemp = 1;
        barWidth = Math.round(24*barWidthTemp);
        pGuiGraphics.blit(BG, leftPos+77, topPos+77, 0, 187, barWidth, 4);

        barWidthTemp = (float) (leather) /CampData.leatherForLevel.get(level-1);
        if (barWidthTemp > 1) barWidthTemp = 1;
        barWidth = Math.round(24*barWidthTemp);
        pGuiGraphics.blit(BG, leftPos+106, topPos+77, 0, 192, barWidth, 4);

        barWidthTemp = (float) (rare) /CampData.rareForLevel.get(level-1);
        if (barWidthTemp > 1) barWidthTemp = 1;
        barWidth = Math.round(24*barWidthTemp);
        pGuiGraphics.blit(BG, leftPos+134, topPos+77, 0, 196, barWidth, 4);

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
