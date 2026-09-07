package ru.kiero.nomad.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import ru.kiero.nomad.init.NomadBlockEntities;
import java.util.UUID;

public class TotemBlockEntity extends BlockEntity implements ContainerData {

    private UUID uuid;

    private final ItemStackHandler items = new ItemStackHandler(2){
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return super.isItemValid(slot, stack);
        }
    };

    public TotemBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(NomadBlockEntities.TOTEM.get(), pPos, pBlockState);

    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        //pTag.put("items", items.serializeNBT());
        if(this.uuid != null) pTag.putUUID("uuid", this.uuid);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        //items.deserializeNBT(pTag.getCompound("items"));
        if(pTag.hasUUID("uuid")) this.uuid = pTag.getUUID("uuid");
    }


    //ContainerData
    @Override
    public int get(int pIndex) {
        return 0;
    }

    @Override
    public void set(int pIndex, int pValue) {

    }

    @Override
    public int getCount() {
        return 1;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
        setChanged();
    }
}
