package com.practicemod.events;

import net.minecraft.client.Minecraft;
import com.practicemod.commands.PracticeCommand;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class Keybind {

    private static boolean IsOn = false;

    private final KeyBinding PracticeModKeybind = new KeyBinding("Practice Key", Keyboard.KEY_0, "PracticeMod");

    public Keybind() {
        ClientRegistry.registerKeyBinding(PracticeModKeybind);
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {

        if (PracticeModKeybind.isPressed()) {
            Minecraft mc = Minecraft.getMinecraft();

            if (mc.isSingleplayer() && (mc.getIntegratedServer() != null && !mc.getIntegratedServer().getPublic())) {
                mc.thePlayer.addChatMessage(new ChatComponentText("This Keybind can only be used in multiplayer."));
                PracticeCommand.IsSinglePlayer = true;
            }
            if (mc.getCurrentServerData() != null && !(mc.getCurrentServerData().serverIP.equals("linkcraft.mcpro.io"))) {
                mc.thePlayer.addChatMessage(new ChatComponentText("This Keybind can only be used on linkcraft.mcpro.io."));
                PracticeCommand.IsDifferentServer = true;
            }
            if (!PracticeCommand.IsDifferentServer && !PracticeCommand.IsSinglePlayer) {
                if (IsOn == true) {
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/unprac");
                    IsOn = false;
                } else {
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/prac");
                    IsOn = true;
                }
            }

        }
    }

}