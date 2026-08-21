package org.zuttomae.veil.holders;

import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public interface ElementHolderHook {
    void veil$addStartWatchingListener(StartWatchingListener listener);

    void veil$removeStartWatchingListener(StartWatchingListener listener);

    void veil$addStopWatchingListener(StopWatchingListener listener);

    void veil$removeStopWatchingListener(StopWatchingListener listener);

    void veil$addAttachmentChangeListener(AttachmentChangeListener listener);

    void veil$removeAttachmentChangeListener(AttachmentChangeListener listener);

    void veil$addTickListener(TickListener listener);

    void veil$removeTickListener(TickListener listener);

    void veil$addAfterSyncListener(AfterSyncListener listener);

    void veil$removeAfterSyncListener(AfterSyncListener listener);

    @FunctionalInterface
    interface StartWatchingListener {
        void onStartWatching(ServerGamePacketListenerImpl connection);
    }

    @FunctionalInterface
    interface StopWatchingListener {
        void onStopWatching(ServerGamePacketListenerImpl connection);
    }

    @FunctionalInterface
    interface AttachmentChangeListener {
        void onAttachmentChange(@Nullable HolderAttachment oldAttachment, @Nullable HolderAttachment newAttachment);
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
