package com.lance.nettydemo.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MyNettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(io.netty.channel.ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyNettyClientHandler.channelActive");
        ctx.writeAndFlush("client channelActive");
    }

    @Override
    public void channelRead(io.netty.channel.ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("MyNettyClientHandler.channelRead");
        System.out.println(msg);

        ByteBuf respBuf = (ByteBuf) msg;
        //声明一个容器
        byte[] respBytes = new byte[respBuf.readableBytes()];
        //往容器里存返回的数据
        respBuf.readBytes(respBytes);

        System.out.println("响应：" + new String(respBytes));

    }

    @Override
    public void wr

}
