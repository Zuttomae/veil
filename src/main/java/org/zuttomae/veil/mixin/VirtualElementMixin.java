package org.zuttomae.veil.mixin;

import eu.pb4.polymer.virtualentity.api.elements.VirtualElement;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.zuttomae.veil.elements.VirtualElementHook;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Mixin(
        targets = {
                "eu.pb4.polymer.virtualentity.api.elements.EntityElement",
                "eu.pb4.polymer.virtualentity.api.elements.GenericEntityElement"
        }
)
public abstract class VirtualElementMixin implements VirtualElement, VirtualElementHook {
    @Unique
    private @Nullable List<StartWatchingListener> veil$startWatchingListeners = null;
    @Unique
    private @Nullable List<StopWatchingListener> veil$stopWatchingListeners = null;
    @Unique
    private @Nullable List<TickListener> veil$tickListeners = null;

    @Override
    public void veil$addStartWatchingListener(StartWatchingListener listener) {
        Objects.requireNonNull(listener);
        if (veil$startWatchingListeners == null) {
            veil$startWatchingListeners = new CopyOnWriteArrayList<>();
        }

        veil$startWatchingListeners.add(listener);
    }

    @Override
    public void veil$removeStartWatchingListener(StartWatchingListener listener) {
        Objects.requireNonNull(listener);
        if (veil$startWatchingListeners != null) {
            veil$startWatchingListeners.remove(listener);
        }
    }

    @Override
    public void veil$addStopWatchingListener(StopWatchingListener listener) {
        Objects.requireNonNull(listener);
        if (veil$stopWatchingListeners == null) {
            veil$stopWatchingListeners = new CopyOnWriteArrayList<>();
        }

        veil$stopWatchingListeners.add(listener);
    }

    @Override
    public void veil$removeStopWatchingListener(StopWatchingListener listener) {
        Objects.requireNonNull(listener);
        if (veil$stopWatchingListeners != null) {
            veil$stopWatchingListeners.remove(listener);
        }
    }

    @Override
    public void veil$addTickListener(TickListener listener) {
        Objects.requireNonNull(listener);
        if (veil$tickListeners == null) {
            veil$tickListeners = new CopyOnWriteArrayList<>();
        }

        veil$tickListeners.add(listener);
    }

    @Override
    public void veil$removeTickListener(TickListener listener) {
        Objects.requireNonNull(listener);
        if (veil$tickListeners != null) {
            veil$tickListeners.remove(listener);
        }
    }

    @Inject(
            method = "startWatching",
            at = @At(value = "TAIL")
    )
    private void veil$invokeStartWatchingListeners(ServerPlayer player, Consumer<Packet<ClientGamePacketListener>> packetConsumer, CallbackInfo ci) {
        if (veil$startWatchingListeners != null) {
            for (StartWatchingListener listener : veil$startWatchingListeners) {
                listener.onStartWatching(player.connection, packetConsumer);
            }
        }
    }

    @Inject(
            method = "stopWatching",
            at = @At(value = "TAIL")
    )
    private void veil$invokeStopWatchingListeners(ServerPlayer player, Consumer<Packet<ClientGamePacketListener>> packetConsumer, CallbackInfo ci) {
        if (veil$stopWatchingListeners != null) {
            for (StopWatchingListener listener : veil$stopWatchingListeners) {
                listener.onStopWatching(player.connection, packetConsumer);
            }
        }
    }

    @Inject(
            method = "tick",
            at = @At(value = "HEAD")
    )
    private void veil$invokeTickListeners(CallbackInfo ci) {
        if (veil$tickListeners != null) {
            for (TickListener listener : veil$tickListeners) {
                listener.onTick();
            }
        }
    }
}
