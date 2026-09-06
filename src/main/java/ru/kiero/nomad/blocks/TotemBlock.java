package ru.kiero.nomad.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import ru.kiero.nomad.data.CampData;
import ru.kiero.nomad.networking.MainScreenPacket;
import ru.kiero.nomad.networking.NomadNetworking;

import java.util.UUID;

public class TotemBlock extends Block implements EntityBlock {

    public TotemBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new TotemBlockEntity(pPos, pState);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
        if(!pState.is(pNewState.getBlock())){
            if(pLevel.getBlockEntity(pPos) instanceof TotemBlockEntity be && pLevel instanceof ServerLevel serverLevel){
                CampData data = CampData.get(serverLevel);
                if(data.getCampAt(pPos) != null) data.removeCamp(data.getCampAt(pPos));
            }
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pLevel.isClientSide()) return InteractionResult.SUCCESS;
        if(pLevel.getBlockEntity(pPos) instanceof TotemBlockEntity be && pPlayer instanceof ServerPlayer serverPlayer){
            //NetworkHooks.openScreen(serverPlayer, be, pPos);
            CampData data = CampData.get(serverPlayer.serverLevel());
            if (data.getCampAt(pPos) == null) {
                data.createCamp(pPos);
                be.setUuid(data.getCampAt(pPos));
                be.setChanged();
            }
            UUID camp = data.getCampAt(pPos);
            NomadNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new MainScreenPacket(
                    data.getName(camp), data.getLevelOf(camp), data.getExp(camp), data.getFriendship(camp, pPlayer.getUUID()), data.getRadius(camp),
                    data.getFood(camp), data.getWood(camp), data.getStone(camp), data.getLeather(camp), data.getRare(camp)));
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }
}
