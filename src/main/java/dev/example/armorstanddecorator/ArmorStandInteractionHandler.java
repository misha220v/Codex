package dev.example.armorstanddecorator;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

/** Handles the one-click Creative-mode decoration workflow. */
public final class ArmorStandInteractionHandler {
    private ArmorStandInteractionHandler() {
    }

    public static void register() {
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            // Run only for the local client's main-hand click on an armor stand.
            if (!world.isClient()
                    || hand != Hand.MAIN_HAND
                    || !(entity instanceof ArmorStandEntity armorStand)) {
                return ActionResult.PASS;
            }

            if (!player.getAbilities().creativeMode) {
                return ActionResult.PASS;
            }

            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || client.player != player
                    || client.getNetworkHandler() == null) {
                return ActionResult.PASS;
            }

            ItemStack decoration = Items.DIAMOND_HELMET.getDefaultStack();
            CreativeArmorStandPacketSender.equipFromSelectedSlot(
                    client,
                    armorStand,
                    decoration
            );

            player.swingHand(Hand.MAIN_HAND);

            // Fabric sends an interaction packet itself for accepted results. FAIL only
            // cancels vanilla processing, avoiding a duplicate after our manual packet.
            return ActionResult.FAIL;
        });
    }
}
