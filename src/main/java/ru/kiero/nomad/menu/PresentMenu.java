package ru.kiero.nomad.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import ru.kiero.nomad.blocks.TotemBlockEntity;
import ru.kiero.nomad.data.CampData;
import ru.kiero.nomad.init.NomadMenuTypes;

import java.util.UUID;

public class PresentMenu extends AbstractContainerMenu implements ContainerData {

    private final TotemBlockEntity be;
    private ServerPlayer serverPlayer;
    private final UUID playerUUID;

    private final ContainerData dataMenu;
    private CampData campData;

    public PresentMenu(int windowId, Inventory inv, TotemBlockEntity be) {
        super(NomadMenuTypes.PRESENT_MENU.get(), windowId);
        this.be = be;
        this.dataMenu = this;
        this.playerUUID = inv.player.getUUID();

        ServerPlayer serverPlayer = (ServerPlayer) inv.player;
        this.campData = CampData.get(serverPlayer.serverLevel());
        this.serverPlayer = serverPlayer;

        addDataSlots(dataMenu);
        addSlots(inv);
    }

    public PresentMenu(int windowId, Inventory inv, FriendlyByteBuf buf) {
        super(NomadMenuTypes.PRESENT_MENU.get(), windowId);
        this.be = (TotemBlockEntity) inv.player.level().getBlockEntity(buf.readBlockPos());
        this.playerUUID = inv.player.getUUID();

        this.dataMenu = new SimpleContainerData(9);

        addDataSlots(dataMenu);
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

    public int getFriendship()              {return dataMenu.get(0);}
    public int getLevelOf()                 {return dataMenu.get(1);}
    public int getExp()                     {return dataMenu.get(2);}
    public int getRadius()                  {return dataMenu.get(3);}

    public int getFood()                    {return dataMenu.get(4);}
    public int getWood()                    {return dataMenu.get(5);}
    public int getStone()                   {return dataMenu.get(6);}
    public int getLeather()                 {return dataMenu.get(7);}
    public int getRare()                    {return dataMenu.get(8);}

    public TotemBlockEntity getBlockEntity() {
        return be;
    }

    @Override
    public int get(int pIndex) {
        UUID camp = campData.getCampAt(be.getBlockPos());
        return switch (pIndex){
            case 0 -> campData.getFriendship(camp, playerUUID);
            case 1 -> campData.getLevelOf(camp);
            case 2 -> campData.getExp(camp);
            case 3 -> campData.getRadius(camp);
            case 4 -> campData.getFood(camp);
            case 5 -> campData.getWood(camp);
            case 6 -> campData.getStone(camp);
            case 7 -> campData.getLeather(camp);
            case 8 -> campData.getRare(camp);
            default -> 0;
        };
    }

    @Override
    public void set(int pIndex, int pValue) {

    }

    @Override
    public int getCount() {
        return 9;
    }
}
