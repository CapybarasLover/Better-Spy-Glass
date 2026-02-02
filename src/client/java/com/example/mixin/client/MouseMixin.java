package com.example.mixin.client;

import com.example.ZoomState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value =  MouseHandler.class)
public class MouseMixin {
    @Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
    private void onScroll(long window, double horizontal, double vertical, CallbackInfo ci){
        Minecraft minecraft = Minecraft.getInstance();

        if(minecraft.player == null) return;

        boolean usingSpyGlass = minecraft.player.isUsingItem() &&
                minecraft.player.getUseItem().is(Items.SPYGLASS);

        if(usingSpyGlass && vertical != 0){
            ZoomState.adjust(vertical);
            ci.cancel();
        }
    }
}
