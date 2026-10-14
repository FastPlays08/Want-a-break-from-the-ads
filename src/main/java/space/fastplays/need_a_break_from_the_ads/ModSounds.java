package space.fastplays.need_a_break_from_the_ads;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.core.Registry;

public final class ModSounds {
    public static final Identifier WANT_A_BREAK_FROM_THE_ADS_ID =
            Identifier.fromNamespaceAndPath("need_a_break_from_the_ads", "want_a_break_from_the_ads");
    public static SoundEvent WANT_A_BREAK_FROM_THE_ADS;

    private ModSounds() {
    }

    public static void initialize() {
        WANT_A_BREAK_FROM_THE_ADS = Registry.register(
                BuiltInRegistries.SOUND_EVENT,
                WANT_A_BREAK_FROM_THE_ADS_ID,
                SoundEvent.createVariableRangeEvent(WANT_A_BREAK_FROM_THE_ADS_ID)
        );
    }
}
