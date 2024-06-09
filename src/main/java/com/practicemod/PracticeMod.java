package com.practicemod;

import com.practicemod.listeners.InventoryCheckListener;
import com.practicemod.events.Keybind;
import com.practicemod.commands.PracticeCommand;
import com.practicemod.items.CustomItemDye;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

@Mod(
        modid = PracticeMod.MODID,
        name = PracticeMod.MODNAME,
        version = PracticeMod.VERSION)
public class PracticeMod {

    public static final String MODID = "practicemod";
    public static final String MODNAME = "Practice Mod";
    public static final String VERSION = "1.0";

    public static final Item customDyeItem = new CustomItemDye();
    public static boolean isPMCommandActive = false;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        GameRegistry.registerItem(customDyeItem, "custom_dye_item");

        MinecraftForge.EVENT_BUS.register(new InventoryCheckListener());

        ClientCommandHandler.instance.registerCommand(new PracticeCommand());
        MinecraftForge.EVENT_BUS.register(new Keybind());

        MinecraftForge.EVENT_BUS.register(this);
    }

    private EntityPlayer modPlayer = null;

    @SubscribeEvent
    public void onPlayerLogout(PlayerLoggedOutEvent event) {
        if (modPlayer != null && event.player == modPlayer) {
            PracticeCommand.resetBooleans();
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityPlayer && event.world.isRemote) {
            EntityPlayer player = (EntityPlayer) event.entity;
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.thePlayer == player) {
                modPlayer = player;
                PracticeCommand.resetBooleans();
            }
        }
    }
}
