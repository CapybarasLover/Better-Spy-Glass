package com.example.mixin.client;

import com.example.ZoomState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MouseHandler.class)
public class MouseUpdateMixin {

    @ModifyVariable(
            method = "turnPlayer",
            at = @At(value = "STORE", ordinal = 0),
            ordinal = 2
    )
    private double modifySensitivityX(double cursorDeltaX) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null) return cursorDeltaX;

        boolean usingSpyglass = minecraft.player.isUsingItem() &&
                minecraft.player.getUseItem().is(Items.SPYGLASS);

        if (usingSpyglass) {
            double zoom = ZoomState.getCurrZoom();

            // Нормализуем zoom: 0.5-10.0 → 0.0-1.0
            double t = (zoom - 0.5) / 9.5;

            // Smoothstep
            double smoothed = t * t * (3.0 - 2.0 * t);

            // Чувствительность: 1.0 (без изменений) → 0.02 (почти неподвижно)
            double sensitivityMultiplier = 1.0 - (smoothed * 0.5);
            return cursorDeltaX * sensitivityMultiplier;
        }

        return cursorDeltaX;
    }


    @ModifyVariable(
            method = "turnPlayer",
            at = @At(value = "STORE", ordinal = 0),
            ordinal = 3
    )
    private double modifySensitivityY(double cursorDeltaY) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null) return cursorDeltaY;

        boolean usingSpyglass = minecraft.player.isUsingItem() &&
                minecraft.player.getUseItem().is(Items.SPYGLASS);

        if (usingSpyglass) {
            double zoom = ZoomState.getCurrZoom();

            // Нормализуем zoom: 0.5-10.0 → 0.0-1.0
            double t = (zoom - 0.5) / 9.5;

            // Smoothstep
            double smoothed = t * t * (3.0 - 2.0 * t);

            // Чувствительность: 1.0 (без изменений) → 0.02 (почти неподвижно)
            double sensitivityMultiplier = 1.0 - (smoothed * 0.5);
            return cursorDeltaY * sensitivityMultiplier;
        }

        return cursorDeltaY;
    }
}