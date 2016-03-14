/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author delth
 */
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {
    private static final int PORT_NUMBER = 5050;
    private static final int BUFFER_SIZE = 30;

    public static void main(String[] args) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.socket().bind(new InetSocketAddress(PORT_NUMBER));
        server.socket().setReuseAddress(true);
        server.configureBlocking(false);

        Selector selector = Selector.open();
        server.register(selector, SelectionKey.OP_ACCEPT);

        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        java.util.logging.Logger.getGlobal().info("Server started");
        int cbuff = 0;
        byte[] s = new byte[30];
        while (true) {
            int channelCount = selector.select();
            if (channelCount > 0) {
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if (key.isAcceptable()) {
                        SocketChannel client = server.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ, client.socket().getPort());
                    } else if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                       // p("port: " + key.attachment());
                        cbuff = client.read(buffer);
                        
                        if (cbuff - 2 == 3){                             
                         System.arraycopy(buffer, 0, s, 0, cbuff);
                            //s = buffer.array();
//
////                        byte[] s = new byte[cbuff];
//                        
                        if ((char)s[0] == 'b') {   
                          if ((char)s[1] == 'u'){     
                            if ((char)s[2] == 'e'){   
                             key.cancel();
                             client.close(); 
                            }
                          }
                        }
                        }
//                              
////                        StringBuilder strB = new StringBuilder();
////                        for (int k = 0;  k < cbuff - 2; k++) {
////                            strB.append((char)s[k]);
////                        };
////
////                        String st = strB.toString();
//
//                        
//                            }
//                          }
//                        }
//                        }
                        
                       buffer.flip(); // read from the buffer

              /*
               * byte[] received = new byte[buffer.remaining()];
               * buffer.get(received); buffer.clear(); // write into the buffer
               * buffer.put(received); buffer.flip(); // read from the buffer
               */
                            client.write(buffer);
                            buffer.clear(); // write into the buffer            }
                        
                    }
                }
            }
        }
    }

    private static void p(String s) {
        System.out.println(s);
    }
}
