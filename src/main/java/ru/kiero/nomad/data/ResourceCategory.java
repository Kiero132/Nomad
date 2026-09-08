package ru.kiero.nomad.data;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public enum ResourceCategory {
    NONE(-1),
    FOOD(0),
    WOOD(1),
    STONE(2),
    LEATHER(3),
    RARE(4);

    public final int id;

    ResourceCategory(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ResourceCategory fromStack(ItemStack item){
        if (item.is(ItemTags.FISHES) || item.is(Items.BREAD) || item.is(Items.COOKED_CHICKEN) || item.is(Items.COOKED_BEEF) ||
        item.is(Items.COOKED_COD) || item.is(Items.COOKED_MUTTON) || item.is(Items.COOKED_PORKCHOP) || item.is(Items.COOKED_RABBIT) || item.is(Items.COOKED_SALMON) ||
        item.is(Items.COOKIE) || item.is(Items.CARROT) || item.is(Items.POTATO) || item.is(Items.APPLE) || item.is(Items.MELON)) return FOOD;
        if (item.is(ItemTags.LOGS) || item.is(ItemTags.PLANKS)) return WOOD;
        if (item.is(Items.STONE) || item.is(Items.COBBLESTONE) || item.is(Items.DIORITE) || item.is(Items.GRANITE)) return STONE;
        if (item.is(Items.LEATHER)) return LEATHER;
        if (item.is(ItemTags.COALS) || item.is(Items.COPPER_INGOT) || item.is(Items.IRON_INGOT) || item.is(Items.GOLD_INGOT) || item.is(Items.DIAMOND) || item.is(Items.NETHERITE_INGOT)) return RARE;
        return NONE;
    }

    public static int repOf(ItemStack item){
        if (item.is(ItemTags.FISHES) || item.is(Items.COOKIE) ||
                item.is(Items.CARROT) || item.is(Items.POTATO) ||
                item.is(Items.APPLE) || item.is(Items.MELON) ||
                fromStack(item).getId() == WOOD.getId() || fromStack(item).getId() == STONE.getId() ||
                item.is(ItemTags.COALS) || item.is(Items.COPPER_INGOT)) return 1;
        if (item.is(Items.COOKED_CHICKEN) || item.is(Items.COOKED_BEEF) ||
                item.is(Items.COOKED_COD) || item.is(Items.COOKED_MUTTON) ||
                item.is(Items.COOKED_PORKCHOP) || item.is(Items.COOKED_RABBIT) ||
                item.is(Items.COOKED_SALMON) || item.is(Items.BREAD) || fromStack(item).getId() == LEATHER.getId() ||
                item.is(Items.IRON_INGOT) || item.is(Items.GOLD_INGOT)) return 2;
        if (item.is(Items.DIAMOND)) return 3;
        if (item.is(Items.NETHERITE_INGOT)) return 4;
        return 0;
    }
}
