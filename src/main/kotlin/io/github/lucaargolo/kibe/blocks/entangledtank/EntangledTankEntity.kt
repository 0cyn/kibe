package io.github.lucaargolo.kibe.blocks.entangledtank

import alexiil.mc.lib.attributes.Simulation
import alexiil.mc.lib.attributes.fluid.amount.FluidAmount
import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume
import io.github.lucaargolo.kibe.blocks.getEntityType
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.fluid.Fluids
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.property.Properties
import net.minecraft.util.DyeColor
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class EntangledTankEntity(chest: EntangledTank, pos: BlockPos, state: BlockState): BlockEntity(getEntityType(chest), pos, state), BlockEntityClientSerializable {

    var lastRenderedFluid = 0f
    var fluidInv = object: SimpleFixedFluidInv(1, FluidAmount(16)) {
        override fun getInvFluid(tank: Int): FluidVolume {
            return persistentState?.getOrCreateInventory(colorCode)?.getInvFluid(tank) ?: super.getInvFluid(tank)
        }
        override fun setInvFluid(tank: Int, to: FluidVolume?, simulation: Simulation?): Boolean {
            val bl = persistentState?.getOrCreateInventory(colorCode)?.setInvFluid(tank, to, simulation) ?: super.setInvFluid(tank, to, simulation)
            markDirtyAndSync()
            return bl
        }
    }

    var runeColors = mutableMapOf<Int, DyeColor>()
    var key = EntangledTank.DEFAULT_KEY
    var owner = ""

    init {
        (1..8).forEach {
            runeColors[it] = DyeColor.WHITE
        }
    }

    val colorCode: String
        get() {
            var code = ""
            (1..8).forEach {
                code += runeColors[it]?.id?.let { int -> Integer.toHexString(int) }
            }
            return code
        }

    val persistentState: EntangledTankState?
        get() = (world as? ServerWorld)?.let { serverWorld ->
            serverWorld.server.overworld.persistentStateManager.getOrCreate( {EntangledTankState.createFromTag(it, serverWorld, key )}, { EntangledTankState(serverWorld, key) }, key)
        }

    fun markDirtyAndSync() {
        markDirty()
        if(world?.isClient == false)
            sync()
    }

    override fun markDirty() {
        persistentState?.markDirty(colorCode)
        super.markDirty()
    }

    override fun fromTag(tag: CompoundTag) {
        super.fromTag(tag)
        (1..8).forEach {
            runeColors[it] = DyeColor.byName(tag.getString("rune$it"), DyeColor.WHITE)
        }
        key = tag.getString("key")
        owner = tag.getString("owner")
    }

    override fun fromClientTag(tag: CompoundTag) {
        (1..8).forEach {
            runeColors[it] = DyeColor.byName(tag.getString("rune$it"), DyeColor.WHITE)
        }
        key = tag.getString("key")
        owner = tag.getString("owner")
        fluidInv.fromTag(tag)
    }

    override fun toTag(tag: CompoundTag): CompoundTag {
        super.toTag(tag)
        (1..8).forEach {
            tag.putString("rune$it", runeColors[it]?.getName() ?: "white")
        }
        tag.putString("key", key)
        tag.putString("owner", owner)
        if(persistentState != null) {
            var subTag = CompoundTag()
            subTag = persistentState!!.toTag(subTag)
            subTag = subTag.getCompound(colorCode)
            tag.put("tanks", subTag.get("tanks") ?: ListTag())
        }
        else fluidInv.toTag(tag)
        return tag
    }

    override fun toClientTag(tag: CompoundTag) = toTag(tag)

    companion object {

        fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: EntangledTankEntity) {
            val fluid = if(blockEntity.fluidInv.getInvFluid(0).isEmpty) Fluids.EMPTY else blockEntity.fluidInv.getInvFluid(0).rawFluid ?: Fluids.EMPTY
            val luminance = fluid.defaultState.blockState.luminance
            if(luminance != state[Properties.LEVEL_15]) {
                world.setBlockState(pos, state.with(Properties.LEVEL_15, luminance))
            }
        }

    }
}