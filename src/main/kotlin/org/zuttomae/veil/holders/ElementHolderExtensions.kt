package org.zuttomae.veil.holders

import eu.pb4.polymer.virtualentity.api.ElementHolder
import eu.pb4.polymer.virtualentity.api.VirtualEntityUtils
import eu.pb4.polymer.virtualentity.api.attachment.*
import eu.pb4.polymer.virtualentity.api.elements.*
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.ServerGamePacketListenerImpl
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.chunk.LevelChunk
import net.minecraft.world.phys.Vec3
import org.zuttomae.veil.annotations.ExperimentalPolymerApi
import org.zuttomae.veil.annotations.InternalPolymerApi
import org.zuttomae.veil.attachments.*
import org.zuttomae.veil.events.Disposable

public inline fun elementHolder(block: ElementHolder.() -> Unit = {}): ElementHolder = ElementHolder().apply(block)

public val ElementHolder.abstractElements: List<AbstractElement> get() = filterElements()

public val ElementHolder.blockDisplayElements: List<BlockDisplayElement> get() = filterElements()

public val ElementHolder.displayElements: List<DisplayElement> get() = filterElements()

public val ElementHolder.entityElements: List<EntityElement<*>> get() = filterElements()

public val ElementHolder.genericEntityElements: List<GenericEntityElement> get() = filterElements()

public val ElementHolder.interactionElements: List<InteractionElement> get() = filterElements()

public val ElementHolder.itemDisplayElements: List<ItemDisplayElement> get() = filterElements()

public val ElementHolder.markerElements: List<MarkerElement> get() = filterElements()

public val ElementHolder.mobAnchorElements: List<MobAnchorElement> get() = filterElements()

public val ElementHolder.simpleEntityElements: List<SimpleEntityElement> get() = filterElements()

public val ElementHolder.textDisplayElements: List<TextDisplayElement> get() = filterElements()

public inline fun ElementHolder.abstractElements(block: AbstractElement.() -> Unit): Unit = eachElement(block)

public inline fun ElementHolder.blockDisplayElements(block: BlockDisplayElement.() -> Unit): Unit = eachElement(block)

public inline fun ElementHolder.displayElements(block: DisplayElement.() -> Unit): Unit = eachElement(block)

public inline fun ElementHolder.entityElements(block: EntityElement<*>.() -> Unit): Unit = eachElement(block)

public inline fun ElementHolder.genericEntityElements(block: GenericEntityElement.() -> Unit): Unit = eachElement(block)

public inline fun ElementHolder.interactionElements(block: InteractionElement.() -> Unit): Unit = eachElement(block)

public inline fun ElementHolder.itemDisplayElements(block: ItemDisplayElement.() -> Unit): Unit = eachElement(block)

public inline fun ElementHolder.markerElements(block: MarkerElement.() -> Unit): Unit = eachElement(block)

public inline fun ElementHolder.mobAnchorElements(block: MobAnchorElement.() -> Unit): Unit = eachElement(block)

public inline fun ElementHolder.simpleEntityElements(block: SimpleEntityElement.() -> Unit): Unit = eachElement(block)

public inline fun ElementHolder.textDisplayElements(block: TextDisplayElement.() -> Unit): Unit = eachElement(block)

public inline fun <reified T : VirtualElement> ElementHolder.filterElements(): List<T> =
    elements.filterIsInstance<T>()

public inline fun <reified T : VirtualElement> ElementHolder.eachElement(block: T.() -> Unit): Unit =
    elements.forEach { element -> if (element is T) block(element) }

@InternalPolymerApi
public inline fun ElementHolder.blockBoundAttachment(
    chunk: LevelChunk,
    state: BlockState,
    blockPos: BlockPos,
    pos: Vec3,
    isTicking: Boolean,
    block: BlockBoundAttachment.() -> Unit = {}
): BlockBoundAttachment =
    blockBoundAttachment(this, chunk, state, blockPos, pos, isTicking, block)

@ExperimentalPolymerApi
public inline fun ElementHolder.blockBoundAttachmentOf(
    world: ServerLevel,
    blockPos: BlockPos,
    state: BlockState,
    block: BlockBoundAttachment.() -> Unit = {}
): BlockBoundAttachment? = blockBoundAttachmentOf(this, world, blockPos, state, block)

@ExperimentalPolymerApi
public inline fun ElementHolder.blockBoundAttachmentOf(
    world: ServerLevel,
    chunk: LevelChunk,
    blockPos: BlockPos,
    state: BlockState,
    block: BlockBoundAttachment.() -> Unit = {}
): BlockBoundAttachment? =
    blockBoundAttachmentOf(this, world, chunk, blockPos, state, block)

@ExperimentalPolymerApi
public inline fun ElementHolder.blockBoundAttachmentFromMoving(
    world: ServerLevel,
    pos: BlockPos,
    state: BlockState,
    block: BlockBoundAttachment.() -> Unit = {}
): BlockBoundAttachment? = blockBoundAttachmentFromMoving(this, world, pos, state, block)

public inline fun ElementHolder.chunkAttachment(
    chunk: LevelChunk,
    pos: Vec3,
    isTicking: Boolean,
    block: ChunkAttachment.() -> Unit = {}
): ChunkAttachment = chunkAttachment(this, chunk, pos, isTicking, block)

public inline fun ElementHolder.chunkAttachmentOf(
    world: ServerLevel,
    pos: BlockPos,
    block: HolderAttachment.() -> Unit = {}
): HolderAttachment = chunkAttachmentOf(this, world, pos, block)

public inline fun ElementHolder.chunkAttachmentOfTicking(
    world: ServerLevel,
    pos: BlockPos,
    block: HolderAttachment.() -> Unit = {}
): HolderAttachment = chunkAttachmentOfTicking(this, world, pos, block)

public inline fun ElementHolder.chunkAttachmentOf(
    world: ServerLevel,
    pos: Vec3,
    block: HolderAttachment.() -> Unit = {}
): HolderAttachment = chunkAttachmentOf(this, world, pos, block)

public inline fun ElementHolder.chunkAttachmentOfTicking(
    world: ServerLevel,
    pos: Vec3,
    block: HolderAttachment.() -> Unit = {}
): HolderAttachment = chunkAttachmentOfTicking(this, world, pos, block)

public inline fun ElementHolder.entityAttachment(
    entity: Entity,
    isTicking: Boolean,
    block: EntityAttachment.() -> Unit = {}
): EntityAttachment = entityAttachment(this, entity, isTicking, block)

public inline fun ElementHolder.entityAttachmentOf(
    entity: Entity,
    block: EntityAttachment.() -> Unit = {}
): EntityAttachment = entityAttachmentOf(this, entity, block)

public inline fun ElementHolder.entityAttachmentOfTicking(
    entity: Entity,
    block: EntityAttachment.() -> Unit = {}
): EntityAttachment = entityAttachmentOfTicking(this, entity, block)

public inline fun ElementHolder.identifiedUniqueEntityAttachment(
    id: Identifier,
    entity: Entity,
    isTicking: Boolean,
    block: IdentifiedUniqueEntityAttachment.() -> Unit = {}
): IdentifiedUniqueEntityAttachment =
    identifiedUniqueEntityAttachment(id, this, entity, isTicking, block)

public inline fun ElementHolder.identifiedUniqueEntityAttachmentOf(
    id: Identifier,
    entity: Entity,
    block: IdentifiedUniqueEntityAttachment.() -> Unit = {}
): IdentifiedUniqueEntityAttachment =
    identifiedUniqueEntityAttachmentOf(id, this, entity, block)

public inline fun ElementHolder.identifiedUniqueEntityAttachmentOfTicking(
    id: Identifier,
    entity: Entity,
    block: IdentifiedUniqueEntityAttachment.() -> Unit = {}
): IdentifiedUniqueEntityAttachment =
    identifiedUniqueEntityAttachmentOfTicking(id, this, entity, block)

public inline fun ElementHolder.manualAttachment(
    world: ServerLevel,
    noinline posSupplier: () -> Vec3,
    block: ManualAttachment.() -> Unit = {}
): ManualAttachment = manualAttachment(this, world, posSupplier, block)

public inline fun ElementHolder.blockDisplayElement(block: BlockDisplayElement.() -> Unit = {}): BlockDisplayElement =
    addElement(org.zuttomae.veil.elements.blockDisplayElement(block))

public inline fun ElementHolder.blockDisplayElement(
    state: BlockState,
    block: BlockDisplayElement.() -> Unit = {}
): BlockDisplayElement = addElement(org.zuttomae.veil.elements.blockDisplayElement(state, block))

public inline fun <T : Entity> ElementHolder.entityElement(
    entity: T,
    world: ServerLevel,
    block: EntityElement<T>.() -> Unit = {}
): EntityElement<T> = addElement(org.zuttomae.veil.elements.entityElement(entity, world, block))

public inline fun <T : Entity> ElementHolder.entityElement(
    entity: T,
    world: ServerLevel,
    handler: VirtualElement.InteractionHandler,
    block: EntityElement<T>.() -> Unit = {}
): EntityElement<T> = addElement(org.zuttomae.veil.elements.entityElement(entity, world, handler, block))

public inline fun <T : Entity> ElementHolder.entityElement(
    type: EntityType<T>,
    world: ServerLevel,
    block: EntityElement<T>.() -> Unit = {}
): EntityElement<T> = addElement(org.zuttomae.veil.elements.entityElement(type, world, block))

public inline fun <T : Entity> ElementHolder.entityElement(
    type: EntityType<T>,
    world: ServerLevel,
    handler: VirtualElement.InteractionHandler,
    block: EntityElement<T>.() -> Unit = {}
): EntityElement<T> = addElement(org.zuttomae.veil.elements.entityElement(type, world, handler, block))

public inline fun ElementHolder.interactionElement(block: InteractionElement.() -> Unit = {}): InteractionElement =
    addElement(org.zuttomae.veil.elements.interactionElement(block))

public inline fun ElementHolder.interactionElement(
    handler: VirtualElement.InteractionHandler,
    block: InteractionElement.() -> Unit = {}
): InteractionElement = addElement(org.zuttomae.veil.elements.interactionElement(handler, block))

public inline fun ElementHolder.interactionElementRedirect(
    redirect: Entity,
    block: InteractionElement.() -> Unit = {}
): InteractionElement = addElement(org.zuttomae.veil.elements.interactionElementRedirect(redirect, block))

public inline fun ElementHolder.itemDisplayElement(block: ItemDisplayElement.() -> Unit = {}): ItemDisplayElement =
    addElement(org.zuttomae.veil.elements.itemDisplayElement(block))

public inline fun ElementHolder.itemDisplayElement(
    stack: ItemStack,
    block: ItemDisplayElement.() -> Unit = {}
): ItemDisplayElement = addElement(org.zuttomae.veil.elements.itemDisplayElement(stack, block))

public inline fun ElementHolder.itemDisplayElement(
    item: Item,
    block: ItemDisplayElement.() -> Unit = {}
): ItemDisplayElement = addElement(org.zuttomae.veil.elements.itemDisplayElement(item, block))

public inline fun ElementHolder.markerElement(block: MarkerElement.() -> Unit = {}): MarkerElement =
    addElement(org.zuttomae.veil.elements.markerElement(block))

public inline fun ElementHolder.mobAnchorElement(block: MobAnchorElement.() -> Unit = {}): MobAnchorElement =
    addElement(org.zuttomae.veil.elements.mobAnchorElement(block))

public inline fun ElementHolder.simpleEntityElement(
    type: EntityType<*>,
    block: SimpleEntityElement.() -> Unit = {}
): SimpleEntityElement = addElement(org.zuttomae.veil.elements.simpleEntityElement(type, block))

public inline fun ElementHolder.textDisplayElement(block: TextDisplayElement.() -> Unit = {}): TextDisplayElement =
    addElement(org.zuttomae.veil.elements.textDisplayElement(block))

public inline fun ElementHolder.textDisplayElement(
    text: Component,
    block: TextDisplayElement.() -> Unit = {}
): TextDisplayElement = addElement(org.zuttomae.veil.elements.textDisplayElement(text, block))

public fun ElementHolder.addAsPassengerTo(entity: Entity): Unit =
    VirtualEntityUtils.addVirtualPassenger(entity, *entityIds.toIntArray())

public class HolderStartWatchingScope @PublishedApi internal constructor(
    disposable: Disposable,
    public val networkHandler: ServerGamePacketListenerImpl
) :
    Disposable by disposable
{
    public val player: ServerPlayer get() = networkHandler.player
}

public inline fun ElementHolder.onStartWatching(crossinline block: HolderStartWatchingScope.() -> Unit): Disposable {
    this as ElementHolderHook

    lateinit var listener: ElementHolderHook.StartWatchingListener
    val disposable = Disposable {
        `veil$removeStartWatchingListener`(listener)
    }

    listener = ElementHolderHook.StartWatchingListener { networkHandler ->
        HolderStartWatchingScope(disposable, networkHandler).block()
    }

    `veil$addStartWatchingListener`(listener)
    return disposable
}

public class HolderStopWatchingScope @PublishedApi internal constructor(
    disposable: Disposable,
    public val networkHandler: ServerGamePacketListenerImpl
) :
    Disposable by disposable
{
    public val player: ServerPlayer get() = networkHandler.player
}

public inline fun ElementHolder.onStopWatching(crossinline block: HolderStopWatchingScope.() -> Unit): Disposable {
    this as ElementHolderHook

    lateinit var listener: ElementHolderHook.StopWatchingListener
    val disposable = Disposable {
        `veil$removeStopWatchingListener`(listener)
    }

    listener = ElementHolderHook.StopWatchingListener { networkHandler ->
        HolderStopWatchingScope(disposable, networkHandler).block()
    }

    `veil$addStopWatchingListener`(listener)
    return disposable
}

public class HolderAttachmentChangeScope @PublishedApi internal constructor(
    disposable: Disposable,
    public val oldAttachment: HolderAttachment?,
    public val newAttachment: HolderAttachment?
) :
    Disposable by disposable

public inline fun ElementHolder.onAttachmentChange(crossinline block: HolderAttachmentChangeScope.() -> Unit): Disposable {
    this as ElementHolderHook

    lateinit var listener: ElementHolderHook.AttachmentChangeListener
    val disposable = Disposable {
        `veil$removeAttachmentChangeListener`(listener)
    }

    listener = ElementHolderHook.AttachmentChangeListener { oldAttachment, newAttachment ->
        HolderAttachmentChangeScope(disposable, oldAttachment, newAttachment).block()
    }

    `veil$addAttachmentChangeListener`(listener)
    return disposable
}

public class HolderPreTickScope @PublishedApi internal constructor(disposable: Disposable) :
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

public inline fun ElementHolder.onPreTick(crossinline block: HolderPreTickScope.() -> Unit): Disposable {
    this as ElementHolderHook

    lateinit var listener: ElementHolderHook.PreTickListener
    val disposable = Disposable {
        `veil$removePreTickListener`(listener)
    }

    val scope = HolderPreTickScope(disposable)
    listener = ElementHolderHook.PreTickListener {
        scope.block()
        scope.update()
    }

    `veil$addPreTickListener`(listener)
    return disposable
}

public class HolderPostTickScope @PublishedApi internal constructor(disposable: Disposable) :
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

public inline fun ElementHolder.onPostTick(crossinline block: HolderPostTickScope.() -> Unit): Disposable {
    this as ElementHolderHook

    lateinit var listener: ElementHolderHook.PostTickListener
    val disposable = Disposable {
        `veil$removePostTickListener`(listener)
    }

    val scope = HolderPostTickScope(disposable)
    listener = ElementHolderHook.PostTickListener {
        scope.block()
        scope.update()
    }

    `veil$addPostTickListener`(listener)
    return disposable
}