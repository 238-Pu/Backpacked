package com.mrcrayfish.backpacked.network.message;

import com.mrcrayfish.backpacked.proxy.ClientProxy;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Author: MrCrayfish
 */
public class MessageUpdateBackpack implements IMessage, IMessageHandler<MessageUpdateBackpack, IMessage>
{
    private int entityId;
    private boolean wearing;

    public MessageUpdateBackpack() {}

    public MessageUpdateBackpack(int entityId, boolean wearing)
    {
        this.entityId = entityId;
        this.wearing = wearing;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entityId);
        buf.writeBoolean(this.wearing);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.entityId = buf.readInt();
        this.wearing = buf.readBoolean();
    }

    @Override
    public IMessage onMessage(MessageUpdateBackpack message, MessageContext ctx)
    {
        FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() ->
        {
            ClientProxy.setPlayerBackpack(message.entityId, message.wearing);
        });
        return null;
    }
}
