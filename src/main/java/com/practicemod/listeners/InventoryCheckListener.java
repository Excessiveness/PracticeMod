package com.practicemod.listeners;

import com.practicemod.PracticeMod;
import com.practicemod.items.CustomItemDye;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class InventoryCheckListener {

    @SubscribeEvent
    public void onPlayerTick(PlayerEvent.LivingUpdateEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            checkRedRoseDye(player);
        }
    }

    private void checkRedRoseDye(EntityPlayer player) {
        if (PracticeMod.isPMCommandActive && player.inventory.currentItem == 1) {
            ItemStack redRoseDye = findRedRoseDye(player);
            if (redRoseDye == null || redRoseDye.stackSize == 0) {
                giveRedRoseDye(player);
            }
        }
    }


    private ItemStack findRedRoseDye(EntityPlayer player) {
        ItemStack itemStack = player.inventory.getStackInSlot(1);
        if (itemStack != null && itemStack.getItem() instanceof CustomItemDye) {
            return itemStack;
        }
        return null;
    }


    private void giveRedRoseDye(EntityPlayer player) {
        player.inventory.setInventorySlotContents(1, new ItemStack(Items.dye, 1, 1));
    }

    public static boolean prac = false;

    @SubscribeEvent
    public void onPlayerRightClick(PlayerInteractEvent event) {
        EntityPlayer player = event.entityPlayer;
        ItemStack heldItem = player.getHeldItem();

        if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
            if (heldItem != null && PracticeMod.isPMCommandActive == true && player.inventory.currentItem == 1) {
                if (prac == true) {
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/unprac");
                    prac = false;
                }
                else {
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/prac");
                    prac = true;
                }
            }
        }
    }

}
