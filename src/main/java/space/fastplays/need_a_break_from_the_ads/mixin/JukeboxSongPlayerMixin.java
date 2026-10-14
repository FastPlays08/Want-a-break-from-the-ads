package space.fastplays.need_a_break_from_the_ads.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.JukeboxSongPlayer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.fastplays.need_a_break_from_the_ads.ModSounds;

@Mixin(JukeboxSongPlayer.class)
abstract class JukeboxSongPlayerMixin {
    @Shadow
    @Final
    private BlockPos blockPos;

    @Unique
    private Holder<JukeboxSong> need_a_break_from_the_ads$pendingSong;

    @Unique
    private int need_a_break_from_the_ads$ticksUntilSong;

    @Unique
    private boolean need_a_break_from_the_ads$startingPendingSong;

    @Inject(method = "play", at = @At("HEAD"), cancellable = true)
    private void playAdBeforeSong(LevelAccessor level, Holder<JukeboxSong> song, CallbackInfo callback) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        if (need_a_break_from_the_ads$startingPendingSong) {
            need_a_break_from_the_ads$startingPendingSong = false;
            return;
        }

        if (serverLevel.getRandom().nextInt(10) != 0) {
            return;
        }

        need_a_break_from_the_ads$pendingSong = song;
        need_a_break_from_the_ads$ticksUntilSong = 325;
        serverLevel.playSound(
                null,
                blockPos,
                ModSounds.WANT_A_BREAK_FROM_THE_ADS,
                SoundSource.RECORDS,
                4.0F,
                1.0F
        );
        callback.cancel();
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void startPendingSong(LevelAccessor level, BlockState state, CallbackInfo callback) {
        if (!(level instanceof ServerLevel) || need_a_break_from_the_ads$pendingSong == null) {
            return;
        }

        if (--need_a_break_from_the_ads$ticksUntilSong > 0) {
            return;
        }

        Holder<JukeboxSong> song = need_a_break_from_the_ads$pendingSong;
        need_a_break_from_the_ads$pendingSong = null;
        need_a_break_from_the_ads$startingPendingSong = true;
        try {
            ((JukeboxSongPlayer) (Object) this).play(level, song);
        } finally {
            need_a_break_from_the_ads$startingPendingSong = false;
        }
    }

    @Inject(method = "stop", at = @At("HEAD"))
    private void clearPendingSong(LevelAccessor level, BlockState state, CallbackInfo callback) {
        if (level instanceof ServerLevel) {
            need_a_break_from_the_ads$pendingSong = null;
            need_a_break_from_the_ads$ticksUntilSong = 0;
        }
    }
}
