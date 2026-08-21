package org.zuttomae.veil.elements

import eu.pb4.polymer.virtualentity.api.VirtualEntityUtils
import eu.pb4.polymer.virtualentity.api.elements.*
import net.minecraft.network.chat.Component
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.ServerGamePacketListenerImpl
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.state.BlockState
import org.joml.Matrix4f
import org.joml.Matrix4fc
import org.joml.Quaternionf
import org.joml.Quaternionfc
import org.joml.Vector3f
import org.joml.Vector3fc
import org.zuttomae.veil.events.Disposable
import org.zuttomae.veil.math.matrix4f
import org.zuttomae.veil.math.quaternionf
import org.zuttomae.veil.math.vector3f

public inline fun blockDisplayElement(block: BlockDisplayElement.() -> Unit = {}): BlockDisplayElement =
    BlockDisplayElement().apply(block)

public inline fun blockDisplayElement(
    state: BlockState,
    block: BlockDisplayElement.() -> Unit = {}
): BlockDisplayElement = BlockDisplayElement(state).apply(block)

public inline fun <T : Entity> entityElement(
    entity: T,
    world: ServerLevel,
    block: EntityElement<T>.() -> Unit = {}
): EntityElement<T> = EntityElement(entity, world).apply(block)

public inline fun <T : Entity> entityElement(
    entity: T,
    world: ServerLevel,
    handler: VirtualElement.InteractionHandler,
    block: EntityElement<T>.() -> Unit = {}
): EntityElement<T> = EntityElement(entity, world, handler).apply(block)

public inline fun <T : Entity> entityElement(
    type: EntityType<T>,
    world: ServerLevel,
    block: EntityElement<T>.() -> Unit = {}
): EntityElement<T> = EntityElement(type, world).apply(block)

public inline fun <T : Entity> entityElement(
    type: EntityType<T>,
    world: ServerLevel,
    handler: VirtualElement.InteractionHandler,
    block: EntityElement<T>.() -> Unit = {}
): EntityElement<T> = EntityElement(type, world, handler).apply(block)

public inline fun interactionElement(block: InteractionElement.() -> Unit = {}): InteractionElement =
    InteractionElement().apply(block)

public inline fun interactionElement(
    handler: VirtualElement.InteractionHandler,
    block: InteractionElement.() -> Unit = {}
): InteractionElement = InteractionElement(handler).apply(block)

public inline fun interactionElementRedirect(
    redirect: Entity,
    block: InteractionElement.() -> Unit = {}
): InteractionElement = InteractionElement.redirect(redirect).apply(block)

public inline fun itemDisplayElement(block: ItemDisplayElement.() -> Unit = {}): ItemDisplayElement =
    ItemDisplayElement().apply(block)

public inline fun itemDisplayElement(stack: ItemStack, block: ItemDisplayElement.() -> Unit = {}): ItemDisplayElement =
    ItemDisplayElement(stack).apply(block)

public inline fun itemDisplayElement(item: Item, block: ItemDisplayElement.() -> Unit = {}): ItemDisplayElement =
    ItemDisplayElement(item).apply(block)

public inline fun markerElement(block: MarkerElement.() -> Unit = {}): MarkerElement = MarkerElement().apply(block)

public inline fun mobAnchorElement(block: MobAnchorElement.() -> Unit = {}): MobAnchorElement =
    MobAnchorElement().apply(block)

public inline fun simpleEntityElement(
    type: EntityType<*>,
    block: SimpleEntityElement.() -> Unit = {}
): SimpleEntityElement = SimpleEntityElement(type).apply(block)

public inline fun textDisplayElement(block: TextDisplayElement.() -> Unit = {}): TextDisplayElement =
    TextDisplayElement().apply(block)

public inline fun textDisplayElement(text: Component, block: TextDisplayElement.() -> Unit = {}): TextDisplayElement =
    TextDisplayElement(text).apply(block)

public fun VirtualElement.addAsPassengerTo(entity: Entity): Unit =
    VirtualEntityUtils.addVirtualPassenger(entity, *entityIds.toIntArray())

public var DisplayElement.transformation: Matrix4fc
    get() {
        return matrix4f {
            translationRotateScale(translation, leftRotation, scale)
            rotate(rightRotation)
        }
    }
    set(value) {
        setTransformation(value)
    }

public inline fun DisplayElement.transformation(
    from: Matrix4fc = transformation,
    block: Matrix4f.() -> Unit
): Matrix4f {
    return matrix4f(from, block).also { transformation = it }
}

public inline fun DisplayElement.translation(
    from: Vector3fc = translation,
    block: Vector3f.() -> Unit
): Vector3f {
    return vector3f(from, block).also { translation = it }
}

public inline fun DisplayElement.leftRotation(
    from: Quaternionfc = leftRotation,
    block: Quaternionf.() -> Unit
): Quaternionf {
    return quaternionf(from, block).also { leftRotation = it }
}

public inline fun DisplayElement.scale(
    from: Vector3fc = scale,
    block: Vector3f.() -> Unit
): Vector3f {
    return vector3f(from, block).also { scale = it }
}

public inline fun DisplayElement.rightRotation(
    from: Quaternionfc = rightRotation,
    block: Quaternionf.() -> Unit
): Quaternionf {
    return quaternionf(from, block).also { rightRotation = it }
}

public fun DisplayElement.startInterpolation(duration: Int) {
    startInterpolation()
    interpolationDuration = duration
}

public fun DisplayElement.startInterpolationIfDirty(duration: Int) {
    if (isTransformationDirty) {
        startInterpolation(duration)
    }
}

public class ElementStartWatchingScope @PublishedApi internal constructor(
    disposable: Disposable,
    public val networkHandler: ServerGamePacketListenerImpl,
    private val packetSender: (Packet<ClientGamePacketListener>) -> Unit
) :
    Disposable by disposable
{
    public val player: ServerPlayer get() = networkHandler.player

    public fun sendPacket(packet: Packet<ClientGamePacketListener>): Unit = packetSender(packet)
}

public inline fun VirtualElement.onStartWatching(crossinline block: ElementStartWatchingScope.() -> Unit): Disposable {
    this as VirtualElementHook

    lateinit var listener: VirtualElementHook.StartWatchingListener
    val disposable = Disposable {
        `veil$removeStartWatchingListener`(listener)
    }

    listener = VirtualElementHook.StartWatchingListener { networkHandler, packetConsumer ->
        ElementStartWatchingScope(disposable, networkHandler, packetConsumer::accept).block()
    }

    `veil$addStartWatchingListener`(listener)
    return disposable
}

public class ElementStopWatchingScope @PublishedApi internal constructor(
    disposable: Disposable,
    public val networkHandler: ServerGamePacketListenerImpl,
    private val packetSender: (Packet<ClientGamePacketListener>) -> Unit
) :
    Disposable by disposable
{
    public val player: ServerPlayer get() = networkHandler.player

    public fun sendPacket(packet: Packet<ClientGamePacketListener>): Unit = packetSender(packet)
}

public inline fun VirtualElement.onStopWatching(crossinline block: ElementStopWatchingScope.() -> Unit): Disposable {
    this as VirtualElementHook

    lateinit var listener: VirtualElementHook.StopWatchingListener
    val disposable = Disposable {
        `veil$removeStopWatchingListener`(listener)
    }

    listener = VirtualElementHook.StopWatchingListener { networkHandler, packetConsumer ->
        ElementStopWatchingScope(disposable, networkHandler, packetConsumer::accept).block()
    }

    `veil$addStopWatchingListener`(listener)
    return disposable
}

public class ElementTickScope @PublishedApi internal constructor(disposable: Disposable) :
    Disposable by disposable
{
    public var tickIndex: Int = 0
        private set

    public val tickCount: Int get() = tickIndex + 1

    @PublishedApi
    internal fun update() {
        tickIndex++
    }
}

public inline fun VirtualElement.onTick(crossinline block: ElementTickScope.() -> Unit): Disposable {
    this as VirtualElementHook

    lateinit var listener: VirtualElementHook.TickListener
    val disposable = Disposable {
        `veil$removeTickListener`(listener)
    }

    val scope = ElementTickScope(disposable)
    listener = VirtualElementHook.TickListener {
        scope.block()
        scope.update()
    }

    `veil$addTickListener`(listener)
    return disposable
}

public class ElementAfterSyncScope @PublishedApi internal constructor(disposable: Disposable) :
    Disposable by disposable
{
    public var tickIndex: Int = 0
        private set

    public val tickCount: Int get() = tickIndex + 1

    @PublishedApi
    internal fun update() {
        tickIndex++
    }
}

public inline fun VirtualElement.onAfterSync(crossinline block: ElementAfterSyncScope.() -> Unit): Disposable {
    this as VirtualElementHook

    lateinit var listener: VirtualElementHook.AfterSyncListener
    val disposable = Disposable {
        `veil$removeAfterSyncListener`(listener)
    }

    val scope = ElementAfterSyncScope(disposable)
    listener = VirtualElementHook.AfterSyncListener {
        scope.block()
        scope.update()
    }

    `veil$addAfterSyncListener`(listener)
    return disposable
}