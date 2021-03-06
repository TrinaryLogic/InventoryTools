/**
 * @author Yuuto
 */
package yuuto.inventorytools.network

import io.netty.buffer.ByteBuf
import cpw.mods.fml.common.network.simpleimpl.IMessage

class MessageToolBench(var id:Int, var dim:Int, var x:Int, var y:Int, var z:Int) extends IMessage {
    
    def this()=this(0,0,0,0,0);
  
    override def fromBytes(buf:ByteBuf) {
        id = buf.readInt();
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        dim = buf.readInt();
    }

    override def toBytes(buf:ByteBuf) {
        buf.writeInt(id);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(dim);
    }
}