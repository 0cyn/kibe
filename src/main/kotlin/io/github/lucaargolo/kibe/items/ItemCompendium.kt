package io.github.lucaargolo.kibe.items

import io.github.lucaargolo.kibe.MOD_ID
import io.github.lucaargolo.kibe.items.entangled.EntangledBag
import io.github.lucaargolo.kibe.items.miscellaneous.*
import io.github.lucaargolo.kibe.items.trashcan.PocketTrashCan
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.DyeColor
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

val itemRegistry = mutableMapOf<Identifier, Item>()

val CURSED_DROPLETS = register(Identifier(MOD_ID, "cursed_droplets"), Item(Item.Settings().group(ItemGroup.MISC)))
val CURSED_BOTTLE = register(Identifier(MOD_ID, "cursed_bottle"), Item(Item.Settings().group(ItemGroup.MISC)))
val CURSED_STAR = register(Identifier(MOD_ID, "cursed_star"), Item(Item.Settings().group(ItemGroup.MISC)))

val CURSED_SEEDS = register(Identifier(MOD_ID, "cursed_seeds"), CursedSeeds(Item.Settings().group(ItemGroup.MISC)))

val BLESSED_DROPLETS = register(Identifier(MOD_ID, "blessed_droplets"), Item(Item.Settings().group(ItemGroup.MISC)))
val BLESSED_BOTTLE = register(Identifier(MOD_ID, "blessed_bottle"), Item(Item.Settings().group(ItemGroup.MISC)))
val BLESSED_STAR = register(Identifier(MOD_ID, "blessed_star"), Item(Item.Settings().group(ItemGroup.MISC)))

val BLESSED_SEEDS = register(Identifier(MOD_ID, "blessed_seeds"), CursedSeeds(Item.Settings().group(ItemGroup.MISC)))

val BLANK_RUNE = register(Identifier(MOD_ID, "blank_rune"), Item(Item.Settings().group(ItemGroup.MISC)))
val WHITE_RUNE = register(Identifier(MOD_ID, "white_rune"), Rune(DyeColor.WHITE, Item.Settings().group(ItemGroup.MISC)))
val ORANGE_RUNE = register(Identifier(MOD_ID, "orange_rune"), Rune(DyeColor.ORANGE, Item.Settings().group(ItemGroup.MISC)))
val MAGENTA_RUNE = register(Identifier(MOD_ID, "magenta_rune"), Rune(DyeColor.MAGENTA, Item.Settings().group(ItemGroup.MISC)))
val LIGHT_BLUE_RUNE = register(Identifier(MOD_ID, "light_blue_rune"), Rune(DyeColor.LIGHT_BLUE, Item.Settings().group(ItemGroup.MISC)))
val YELLOW_RUNE = register(Identifier(MOD_ID, "yellow_rune"), Rune(DyeColor.YELLOW, Item.Settings().group(ItemGroup.MISC)))
val LIME_RUNE = register(Identifier(MOD_ID, "lime_rune"), Rune(DyeColor.LIME, Item.Settings().group(ItemGroup.MISC)))
val PINK_RUNE = register(Identifier(MOD_ID, "pink_rune"), Rune(DyeColor.PINK, Item.Settings().group(ItemGroup.MISC)))
val GRAY_RUNE = register(Identifier(MOD_ID, "gray_rune"), Rune(DyeColor.GRAY, Item.Settings().group(ItemGroup.MISC)))
val LIGHT_GRAY_RUNE = register(Identifier(MOD_ID, "light_gray_rune"), Rune(DyeColor.LIGHT_GRAY, Item.Settings().group(ItemGroup.MISC)))
val CYAN_RUNE = register(Identifier(MOD_ID, "cyan_rune"), Rune(DyeColor.CYAN, Item.Settings().group(ItemGroup.MISC)))
val BLUE_RUNE = register(Identifier(MOD_ID, "blue_rune"), Rune(DyeColor.BLUE, Item.Settings().group(ItemGroup.MISC)))
val PURPLE_RUNE = register(Identifier(MOD_ID, "purple_rune"), Rune(DyeColor.PURPLE, Item.Settings().group(ItemGroup.MISC)))
val GREEN_RUNE = register(Identifier(MOD_ID, "green_rune"), Rune(DyeColor.GREEN, Item.Settings().group(ItemGroup.MISC)))
val BROWN_RUNE = register(Identifier(MOD_ID, "brown_rune"), Rune(DyeColor.BROWN, Item.Settings().group(ItemGroup.MISC)))
val RED_RUNE = register(Identifier(MOD_ID, "red_rune"), Rune(DyeColor.RED, Item.Settings().group(ItemGroup.MISC)))
val BLACK_RUNE = register(Identifier(MOD_ID, "black_rune"), Rune(DyeColor.BLACK, Item.Settings().group(ItemGroup.MISC)))

val ANGEL_RING = register(Identifier(MOD_ID, "angel_ring"), Item(Item.Settings().maxCount(1).group(ItemGroup.MISC)))
val GOLDEN_LASSO = register(Identifier(MOD_ID, "golden_lasso"), GoldenLasso(Item.Settings().maxCount(1).group(ItemGroup.MISC)))
val CURSED_LASSO = register(Identifier(MOD_ID, "cursed_lasso"), CursedLasso(Item.Settings().maxCount(1).group(ItemGroup.MISC)))
val BLESSED_LASSO = register(Identifier(MOD_ID, "blessed_lasso"), BlessedLasso(Item.Settings().maxCount(1).group(ItemGroup.MISC)))

val SLIME_BOOTS = register(Identifier(MOD_ID, "slime_boots"), SlimeBoots(Item.Settings().maxCount(1).group(ItemGroup.MISC)))
val SLIME_SLING = register(Identifier(MOD_ID, "slime_sling"), SlimeSling(Item.Settings().maxCount(1).group(ItemGroup.MISC)))

val POCKET_TRASH_CAN = register(Identifier(MOD_ID, "pocket_trash_can"), PocketTrashCan(Item.Settings().group(ItemGroup.MISC)))
val POCKET_CRAFTING_TABLE = register(Identifier(MOD_ID, "pocket_crafting_table"), PocketCraftingTable(Item.Settings().group(ItemGroup.MISC)))
val ENTANGLED_BAG = register(Identifier(MOD_ID, "entangled_bag"), EntangledBag())

val SLEEPING_BAG = register(Identifier(MOD_ID, "sleeping_bag"), SleepingBag(Item.Settings().group(ItemGroup.MISC)))

private fun register(identifier: Identifier, item: Item): Item {
    itemRegistry[identifier] = item
    return item;
}

fun getItemId(item: Item): Identifier? {
    itemRegistry.forEach {
        if(it.value == item) return it.key
    }
    return null
}

fun initItems() {
    itemRegistry.forEach{ Registry.register(Registry.ITEM, it.key, it.value) }
}
