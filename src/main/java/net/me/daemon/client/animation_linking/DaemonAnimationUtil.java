package net.me.daemon.client.animation_linking;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationAccess;
import net.me.daemon.Main;
import net.me.daemon.client.animation_linking.enums.DaemonLoop;
import net.me.daemon.client.animation_linking.records.DaemonLink;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;

import java.util.function.Supplier;

public class DaemonAnimationUtil {

    public static final Identifier PAL_LAYER = Main.get_path("pal_layer");

    public static <T extends GeoAnimatable> void addLinks(AnimationController<T> controller, Identifier... ids) {
        for (Identifier id : ids) {
            DaemonLink link = DaemonAnimationBuilder.get(id); // plain lookup
            if (link == null) { Main.LOGGER.warn("Unknown link {}", id); continue; }
            controller.triggerableAnim(id.getPath(),
                    RawAnimation.begin().then(link.name(), toGecko(link.loopType().geckolib_loop())));
        }
    }

    private static Animation.LoopType toGecko(DaemonLoop l) {
        return switch (l) {
            case PLAY_ONCE -> Animation.LoopType.PLAY_ONCE;
            case LOOP -> Animation.LoopType.LOOP;
            case HOLD_LAST_FRAME -> Animation.LoopType.HOLD_ON_LAST_FRAME;
        };
    }

    public static com.zigythebird.playeranimcore.animation.Animation.LoopType toPal(DaemonLoop l) {
        return switch (l) {
            case PLAY_ONCE -> com.zigythebird.playeranimcore.animation.Animation.LoopType.PLAY_ONCE;
            case LOOP -> com.zigythebird.playeranimcore.animation.Animation.LoopType.LOOP;
            case HOLD_LAST_FRAME -> com.zigythebird.playeranimcore.animation.Animation.LoopType.HOLD_ON_LAST_FRAME;
        };
    }

    public static void play(AbstractClientPlayerEntity player, Identifier linkId) {
//        Main.LOGGER.info("[pal] play called for {}", linkId);

        Supplier<com.zigythebird.playeranimcore.animation.RawAnimation> anim = PalAnimationRegistry.byId(linkId);
//        Main.LOGGER.info("[pal] registry entry: {}", anim);

        var layer = PlayerAnimationAccess.getPlayerAnimationLayer(player, PAL_LAYER);
//        Main.LOGGER.info("[pal] layer: {}", layer);

        if (anim == null || !(layer instanceof PlayerAnimationController c)) return;

        c.triggerAnimation(anim.get());
//        Main.LOGGER.info("[pal] triggered {}", linkId);
    }
}
