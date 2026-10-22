package dev.example.armorstanddecorator;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;

/** Sends the two vanilla packets, in order, over the same client connection. */
public final class CreativeArmorStandPacketSender {
    // In PlayerScreenHandler, hotbar slots occupy protocol slot IDs 36 through 44.
    private static final int HOTBAR_SLOT_ID_OFFSET = 36;

    private CreativeArmorStandPacketSender() {
    }

    public static void equipFromSelectedSlot(
            MinecraftClient client,
            Entity armorStand,
            ItemStack requestedStack
    ) {
        ClientPlayerEntity player = client.player;
        if (player == null || client.getNetworkHandler() == null
                || !player.getAbilities().creativeMode) {
            return;
        }

        int selectedSlot = player.getInventory().selectedSlot;
        int protocolSlotId = HOTBAR_SLOT_ID_OFFSET + selectedSlot;
        ItemStack stack = requestedStack.copy();

        // Keep the local inventory display consistent with the Creative action.
        player.getInventory().setStack(selectedSlot, stack.copy());

        // Packet ordering is preserved by the same network connection.
        client.getNetworkHandler().sendPacket(
                new CreativeInventoryActionC2SPacket(protocolSlotId, stack)
        );
        client.getNetworkHandler().sendPacket(
                PlayerInteractEntityC2SPacket.interact(
                        armorStand,
                        player.isSneaking(),
                        Hand.MAIN_HAND
                )
        );
    }
}
