package com.practicemod.commands;

import net.minecraft.command.CommandBase;
import com.practicemod.listeners.InventoryCheckListener;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.client.Minecraft;
import com.practicemod.PracticeMod;

import java.util.List;

public class PracticeCommand extends CommandBase {

    private static boolean IsSinglePlayer = false;
    private static boolean IsDifferentServer = false;

    @Override
    public String getCommandName() {
        return "pm";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + this.getCommandName() + " <on|off>";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.isSingleplayer() && (mc.getIntegratedServer() != null && !mc.getIntegratedServer().getPublic())) {
            sender.addChatMessage(new ChatComponentText("This command can only be used in multiplayer."));
            IsSinglePlayer = true;
        }

        if (mc.getCurrentServerData() != null && !(mc.getCurrentServerData().serverIP.equals("linkcraft.mcpro.io"))) {
            sender.addChatMessage(new ChatComponentText("This command can only be used on linkcraft.mcpro.io."));
            IsDifferentServer = true;
        }
        return true;
    }

    private static boolean IsItOn = false;

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (!IsDifferentServer && !IsSinglePlayer) {
            if (args.length < 1) {
                sender.addChatMessage(new ChatComponentText(EnumChatFormatting.WHITE + "Usage: " + getCommandUsage(sender)));
                return;
            }

            if (!(sender instanceof EntityPlayer)) {
                sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "This command can only be executed by players."));
                return;
            }

            EntityPlayer player = (EntityPlayer) sender;

            if (args[0].equalsIgnoreCase("on")) {
                if (IsItOn) {
                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "PM is Already ON!"));
                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + " type /pm off to exit from practice mode!"));

                } else {
                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "PM is now ON"));
                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Red Rose will be given to you! Click it to run /prac, click it again to run /unprac!"));
                    player.inventory.setInventorySlotContents(1, new ItemStack(Items.dye, 1, 1));
                    IsItOn = true;
                    PracticeMod.isPMCommandActive = true;
                }
            } else if (args[0].equalsIgnoreCase("off")) {
                if (IsItOn) {
                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.GRAY + "PM is now OFF"));
                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.GRAY + "The Red Rose has been removed from your inventory!"));
                    player.inventory.setInventorySlotContents(1, null);
                    IsItOn = false;
                    PracticeMod.isPMCommandActive = false;
                } else {
                    sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "PM is already OFF."));
                }
            } else {
                sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Unknown subcommand. Usage: " + getCommandUsage(sender)));
            }
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        final String[] options = new String[]{"on", "off"};
        return getListOfStringsMatchingLastWord(args, options);
    }

    public static void resetBooleans() {
        IsSinglePlayer = false;
        IsDifferentServer = false;
        IsItOn = false;
        PracticeMod.isPMCommandActive = false;
        InventoryCheckListener.prac = false;
    }
}
