package ru.kiero.nomad.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import ru.kiero.nomad.blocks.TotemBlockEntity;
import ru.kiero.nomad.data.CampData;
import ru.kiero.nomad.init.NomadMenuTypes;

public class PresentMenu extends AbstractContainerMenu {

    private final TotemBlockEntity be;
    private CampData data;

    public PresentMenu(int windowId, Inventory inv, TotemBlockEntity be) {
        super(NomadMenuTypes.PRESENT_MENU.get(), windowId);
        this.be = be;

        ServerPlayer serverPlayer = (ServerPlayer) inv.player;
        this.data = CampData.get(serverPlayer.serverLevel());

        addSlots(inv);
    }

    public PresentMenu(int windowId, Inventory inv, FriendlyByteBuf buf) {
        super(NomadMenuTypes.PRESENT_MENU.get(), windowId);
        this.be = (TotemBlockEntity) inv.player.level().getBlockEntity(buf.readBlockPos());

        addSlots(inv);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        Slot slot = this.slots.get(pIndex);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        if (pIndex < 1) {
            if (!this.moveItemStackTo(stack, 1, 28, true)) return ItemStack.EMPTY;
        } else {
            if (!this.moveItemStackTo(stack, 0, 1, false)) return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return copy;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return pPlayer.distanceToSqr(be.getBlockPos().getX() + 0.5,
                be.getBlockPos().getY() + 0.5,
                be.getBlockPos().getZ() + 0.5) <= 64.0;
    }

    private void addSlots(Inventory inv){
        this.addSlot(new SlotItemHandler(be.getItems(), 0, 87, 147));

        // 27 слотов инвентаря игрока
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inv, col + row * 9 + 9, 33 + col * 18, 178 + row * 18));
            }
        }
    }
}
