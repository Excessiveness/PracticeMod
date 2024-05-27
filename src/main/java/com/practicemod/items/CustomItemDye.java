package com.practicemod.items;


import com.practicemod.PracticeMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;

public class CustomItemDye extends Item {

    public CustomItemDye() {
        super();
        this.setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (stack != null && stack.getItem() == PracticeMod.customDyeItem) {
            stack.stackSize--;

            ItemStack newDye = new ItemStack(PracticeMod.customDyeItem);
            if (!player.inventory.addItemStackToInventory(newDye)) {
                EntityItem entityItem = new EntityItem(world, player.posX, player.posY, player.posZ, newDye);
                world.spawnEntityInWorld(entityItem);
            }
        }

        return stack;
    }


}
