/**
 * @author Yuuto
 */
package yuuto.inventorytools.config

import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import net.minecraft.item.{ItemStack, Item}
import net.minecraftforge.common.config.Configuration
import yuuto.inventorytools.api.dolly.DollyHandlerRegistry
import yuuto.inventorytools.api.toolbox.ToolBoxRegistry
import yuuto.inventorytools.until.LogHelperIT

object ConfigurationIT {
  var maxTools:Int=18;
  var blockListAll:Array[String]=null;
  var blockListNormal:Array[String]=null;
  var toolWhiteList:Array[String]=null;
  var toolBlackList:Array[String]=null;
  
  def preInit(event:FMLPreInitializationEvent){
    val config=new Configuration(event.getSuggestedConfigurationFile());
    config.load();
    LogHelperIT.debug= config.getBoolean("DebugLogs", "Debug", false, "Should debug lines be output to the console")
    maxTools = config.getInt("MaxTools", "ToolBox", maxTools, 1, 18, "The number of tools that can be stored in a toolbox");
    toolWhiteList=config.getStringList("WhiteList", "ToolBox", Array("LogisticsPipes:item.remoteOrdererItem", "LogisticsPipes:item.pipeController", "Railcraft:tool.whistle.tuner", "Enchiridion:items", "Steamcraft:wrench"), "A list of items that can be put in a toolbox as tools Formats: ItemName, ModName:ItemName, ModName:ItemName:Meta");
    toolBlackList=config.getStringList("BlackList", "ToolBox", Array(), "A list of items that CANNOT be put in a toolbox as tools Formats: ItemName, ModName:ItemName, ModName:ItemName:Meta");
    blockListAll=config.getStringList("BlackListAll", "Dolly", Array(), "A list of blocks that cannot be picked up by any dolly Formats: ItemName, ModName:ItemName, ModName:ItemName:Meta");
    blockListNormal=config.getStringList("BlackListNormal", "Dolly", Array("mob_spawner"), "A lits of blocks that cannot be picked up by normal dollies Formats: ItemName, ModName:ItemName, ModName:ItemName:Meta");
    config.save();
  }
  def postInit(event:FMLPostInitializationEvent){
    if(blockListAll != null){
      LogHelperIT.Debug("Loaded Dolly Black List with "+blockListAll.length+" entries");
      for(s<-blockListAll){
        DollyHandlerRegistry.addToBlackListAll(s);
      }
      blockListAll=null;
    }
    if(blockListNormal != null){
      LogHelperIT.Debug("Loaded Gold Dolly Black List with "+blockListNormal.length+" entries");
      for(s<-blockListNormal){
        DollyHandlerRegistry.addToBlackListNormal(s);
      }
      blockListNormal=null;
    }
    if(toolWhiteList != null){
      LogHelperIT.Debug("Loaded Tool White List with "+toolWhiteList.length+" entries");
      for(s<-toolWhiteList){
        ToolBoxRegistry.addToWhiteList(s);
      }
      toolWhiteList=null;
    }
    if(toolBlackList != null){
      LogHelperIT.Debug("Loaded Tool Black List with "+toolBlackList.length+" entries");
      for(s<-toolBlackList){
        ToolBoxRegistry.addToBlackList(s);
      }
      toolBlackList=null;
    }
    /*if(Loader.isModLoaded("LogisticsPipes")){
      val remote:Item=LogisticsPipes.LogisticsRemoteOrderer;
      if(remote == null){
        LogHelperIT.Debug("Remote Orderer Not Initialized!");
      }else{
        val id:UniqueIdentifier = GameRegistry.findUniqueIdentifierFor(remote);
        if(id == null){
          LogHelperIT.Debug("No ID for Remote Orderer");
        }else{
          LogHelperIT.Debug("Remote Orderer ID= "+id.modId+":"+id.name);
          val itm:ItemStack= GameRegistry.findItemStack(id.modId, id.name, 1);
          LogHelperIT.Debug("ItemStack Found ? "+(itm!=null)+" && item matches remote orderer ? "+(itm==remote));
        }
      }
    }*/
  }
}