package com.person.details.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
    
/**
 * Handles Person Information sent as HTTP request
 */
public class PersonDetailsServer {
    
    private int port;
    
    public PersonDetailsServer(int port) {
        this.port = port;
    }
    /**
     * start the server with the port number provided
     */
    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); 
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); 
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class) 
             .childHandler(new ChannelInitializer<SocketChannel>() { 
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                	 ch.pipeline().addLast(new HttpRequestDecoder());
                	 ch.pipeline().addLast(new HttpResponseEncoder());
                     ch.pipeline().addLast(new PersonDetailsServerHandler());
                 }
             })
             .option(ChannelOption.SO_BACKLOG, 128)       
             .childOption(ChannelOption.SO_KEEPALIVE, true); 
    
            // Bind and start to accept incoming connections.
            Channel f = b.bind(port).sync().channel();
    
            // shut down your server.
            f.closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
    
    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8082;
        }
        new PersonDetailsServer(port).run();
    }
}