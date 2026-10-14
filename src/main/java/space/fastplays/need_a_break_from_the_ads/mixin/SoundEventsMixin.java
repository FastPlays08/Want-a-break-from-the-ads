package space.fastplays.need_a_break_from_the_ads.mixin;

import net.minecraft.sounds.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.fastplays.need_a_break_from_the_ads.ModSounds;

@Mixin(SoundEvents.class)
abstract class SoundEventsMixin {
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void registerModSounds(CallbackInfo callback) {
        ModSounds.initialize();
    }
}
