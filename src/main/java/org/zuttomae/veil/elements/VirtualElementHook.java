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

    void veil$addTickListener(TickListener listener);

    void veil$removeTickListener(TickListener listener);

    void veil$addAfterSyncListener(AfterSyncListener listener);

    void veil$removeAfterSyncListener(AfterSyncListener listener);

    @FunctionalInterface
    interface StartWatchingListener {
        void onStartWatching(ServerGamePacketListenerImpl connection, Consumer<? super Packet<ClientGamePacketListener>> packetConsumer);
    }

    @FunctionalInterface
    interface StopWatchingListener {
        void onStopWatching(ServerGamePacketListenerImpl connection, Consumer<? super Packet<ClientGamePacketListener>> packetConsumer);
    }

    @FunctionalInterface
    interface TickListener {
        void onTick();
    }

    @FunctionalInterface
    interface AfterSyncListener {
        void onAfterSync();
    }
}
