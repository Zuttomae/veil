package org.zuttomae.veil.elements;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public interface VirtualElementHook {
    void veil$addStartWatchingListener(StartWatchingListener listener);

    void veil$removeStartWatchingListener(StartWatchingListener listener);

    void veil$addStopWatchingListener(StopWatchingListener listener);

    void veil$removeStopWatchingListener(StopWatchingListener listener);

    void veil$addPreTickListener(PreTickListener listener);

    void veil$removePreTickListener(PreTickListener listener);

    void veil$addPostTickListener(PostTickListener listener);

    void veil$removePostTickListener(PostTickListener listener);

    @FunctionalInterface
    interface StartWatchingListener {
        void onStartWatching(ServerGamePacketListenerImpl connection, Consumer<? super Packet<ClientGamePacketListener>> packetConsumer);
    }

    @FunctionalInterface
    interface StopWatchingListener {
        void onStopWatching(ServerGamePacketListenerImpl connection, Consumer<? super Packet<ClientGamePacketListener>> packetConsumer);
    }

    @FunctionalInterface
    interface PreTickListener {
        void onPreTick();
    }

    @FunctionalInterface
    interface PostTickListener {
        void onPostTick();
    }
}
