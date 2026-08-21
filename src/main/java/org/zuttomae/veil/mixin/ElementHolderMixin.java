package org.zuttomae.veil.mixin;

import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.zuttomae.veil.holders.ElementHolderHook;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@Mixin(value = ElementHolder.class)
public abstract class ElementHolderMixin implements ElementHolderHook {
    @Unique
    private @Nullable List<StartWatchingListener> veil$startWatchingListeners = null;
    @Unique
    private @Nullable List<StopWatchingListener> veil$stopWatchingListeners = null;
    @Unique
    private @Nullable List<AttachmentChangeListener> veil$attachmentChangeListeners = null;
    @Unique
    private @Nullable List<TickListener> veil$tickListeners = null;
    @Unique
    private @Nullable List<AfterSyncListener> veil$afterSyncListeners = null;

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
    public void veil$addAttachmentChangeListener(AttachmentChangeListener listener) {
        Objects.requireNonNull(listener);
        if (veil$attachmentChangeListeners == null) {
            veil$attachmentChangeListeners = new CopyOnWriteArrayList<>();
        }

        veil$attachmentChangeListeners.add(listener);
    }

    @Override
    public void veil$removeAttachmentChangeListener(AttachmentChangeListener listener) {
        Objects.requireNonNull(listener);
        if (veil$attachmentChangeListeners != null) {
            veil$attachmentChangeListeners.remove(listener);
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

    @Override
    public void veil$addAfterSyncListener(AfterSyncListener listener) {
        Objects.requireNonNull(listener);
        if (veil$afterSyncListeners == null) {
            veil$afterSyncListeners = new CopyOnWriteArrayList<>();
        }

        veil$afterSyncListeners.add(listener);
    }

    @Override
    public void veil$removeAfterSyncListener(AfterSyncListener listener) {
        Objects.requireNonNull(listener);
        if (veil$afterSyncListeners != null) {
            veil$afterSyncListeners.remove(listener);
        }
    }

    @Inject(
            method = "startWatching(Lnet/minecraft/server/network/ServerGamePacketListenerImpl;)Z",
            at = @At(value = "RETURN")
    )
    private void veil$invokeStartWatchingListeners(ServerGamePacketListenerImpl player, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ()) {
            return;
        }

        if (veil$startWatchingListeners != null) {
            for (StartWatchingListener listener : veil$startWatchingListeners) {
                listener.onStartWatching(player);
            }
        }
    }

    @Inject(
            method = "stopWatching(Lnet/minecraft/server/network/ServerGamePacketListenerImpl;)Z",
            at = @At(value = "RETURN")
    )
    private void veil$invokeStopWatchingListeners(ServerGamePacketListenerImpl player, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ()) {
            return;
        }

        if (veil$stopWatchingListeners != null) {
            for (StopWatchingListener listener : veil$stopWatchingListeners) {
                listener.onStopWatching(player);
            }
        }
    }

    @Inject(
            method = "onAttachmentSet",
            at = @At(value = "TAIL")
    )
    private void veil$invokeAttachmentChangeListenersOnSet(HolderAttachment attachment, @Nullable HolderAttachment oldAttachment, CallbackInfo ci) {
        if (veil$attachmentChangeListeners != null) {
            for (AttachmentChangeListener listener : veil$attachmentChangeListeners) {
                listener.onAttachmentChange(oldAttachment, attachment);
            }
        }
    }

    @Inject(
            method = "onAttachmentRemoved",
            at = @At(value = "TAIL")
    )
    private void veil$invokeAttachmentChangeListenersOnRemove(HolderAttachment oldAttachment, CallbackInfo ci) {
        if (veil$attachmentChangeListeners != null) {
            for (AttachmentChangeListener listener : veil$attachmentChangeListeners) {
                listener.onAttachmentChange(oldAttachment, null);
            }
        }
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Leu/pb4/polymer/virtualentity/api/ElementHolder;onTick()V",
                    shift = At.Shift.AFTER
            )
    )
    private void veil$invokeTickListeners(CallbackInfo ci) {
        if (veil$tickListeners != null) {
            for (TickListener listener : veil$tickListeners) {
                listener.onTick();
            }
        }
    }

    @Inject(
            method = "tick",
            at = @At(value = "TAIL")
    )
    private void veil$invokeAfterSyncListeners(CallbackInfo ci) {
        if (veil$afterSyncListeners != null) {
            for (AfterSyncListener listener : veil$afterSyncListeners) {
                listener.onAfterSync();
            }
        }
    }
}
