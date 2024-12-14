@file:Suppress("DEPRECATION", "UnstableApiUsage")

package io.github.lucaargolo.kibe.blocks.miscellaneous

import io.github.lucaargolo.kibe.blocks.getEntityType
import io.github.lucaargolo.kibe.utils.SyncableBlockEntity
import io.github.lucaargolo.kibe.utils.readTank
import io.github.lucaargolo.kibe.utils.writeTank
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiCache
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.Storage
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.minecraft.block.BlockState
import net.minecraft.block.HopperBlock
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import kotlin.math.min

class FluidHopperBlockEntity(block: FluidHopper, pos: BlockPos, state: BlockState): SyncableBlockEntity(getEntityType(block), pos, state) {

    companion object {
        private const val CAPACITY = FluidConstants.BUCKET
        private const val TRANSFER_RATE = CAPACITY / 20

        fun serverTick(world: World, pos: BlockPos, state: BlockState, blockEntity: BlockEntity) {
            (blockEntity as FluidHopperBlockEntity).tick(world as ServerWorld, pos, state)
        }
    }

    private var topContainerFinder: BlockApiCache<Storage<FluidVariant>, Direction?>? = null
    private var toContainerFinder: BlockApiCache<Storage<FluidVariant>, Direction?>? = null
    private var cachedDirection: Direction? = null

    private var extractionBump = CAPACITY
    private var insertionBump = CAPACITY

    val tank = object : SingleVariantStorage<FluidVariant>() {
        override fun getBlankVariant(): FluidVariant = FluidVariant.blank()
        override fun getCapacity(variant: FluidVariant?): Long = CAPACITY

        override fun onFinalCommit() {
            markDirtyAndSync()
        }
    }

    fun markDirtyAndSync() {
        markDirty()
        if(world?.isClient == false)
            sync()
    }

    override fun writeNbt(tag: NbtCompound) {
        super.writeNbt(tag)
        writeTank(tag, tank)
    }

    override fun readNbt(tag: NbtCompound) {
        super.readNbt(tag)
        readTank(tag, tank)
    }

    override fun writeClientNbt(tag: NbtCompound) = tag.also { writeNbt(it) }

    override fun readClientNbt(tag: NbtCompound) = readNbt(tag)

    private fun tick(world: ServerWorld, pos: BlockPos, state: BlockState) {
    }
}
