package com.example.mixin.client;

import com.example.ZoomState;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    private void getFov(Camera camera, float f, boolean bl, CallbackInfoReturnable<Double> cir){
            Minecraft minecraft = Minecraft.getInstance();

            if(minecraft.player == null) return;

        boolean isUsingSpyglass = minecraft.player.isUsingItem() &&
                minecraft.player.getUseItem().is(Items.SPYGLASS);
        if(isUsingSpyglass){
            double originalFov = cir.getReturnValue();

            double modifiedZoom = originalFov / ZoomState.getCurrZoom();

            cir.setReturnValue(modifiedZoom);
        }
    }


}
