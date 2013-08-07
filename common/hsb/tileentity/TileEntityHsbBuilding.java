package hsb.tileentity;


import hsb.lock.ILockTerminal;

import java.util.List;

import nwcore.network.NetworkManager;



public class TileEntityHsbBuilding extends TileEntityHsb{

	
	//render
	public int camoId = -1;
	public int camoMeta = -1;
	
	private int prevCamoId = -1;
	private int prevCamoMeta = -1;

	@Override
	public List<String> getNetworkedFields() {
		List<String> list = super.getNetworkedFields();
	    list.add("camoId");
	    list.add("camoMeta");
	    return list;
	}
	@Override
	public void onNetworkUpdate(String field) {
		 if (field.equals("camoId") && prevCamoId != camoId)
	     {
//			 ModHsb.logger.debug("camoId updated!");
	         worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	         prevCamoId = camoId;
	     }
		 if (field.equals("camoMeta") && prevCamoMeta != camoMeta)
	     {
	         worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	         prevCamoMeta = camoMeta;
	     }
		 super.onNetworkUpdate(field);
	}
	
	@Override
	public boolean receiveSignal(int side, ILockTerminal te, boolean lockSignal,
			String pass, int port) {
		if(super.receiveSignal(side, te, lockSignal, pass, port))
		{
			
			if(this.locked && te != null)
			{
//				ModHsb.logger.debug("camo: " + this.camoId + "||" + te.getCamoBlockId());
				if(camoId != te.getCamoBlockId() || camoMeta != te.getCamoMeta()) {
//					ModHsb.logger.debug("success receive signal!");
					this.camoId = te.getCamoBlockId();
					this.camoMeta = te.getCamoMeta();
					NetworkManager.getInstance().updateTileEntityField(this, "camoId");
					NetworkManager.getInstance().updateTileEntityField(this, "camoMeta");
				}
			}/* else {
				if(this.camoId != -1)
				{
					this.camoId = -1;
					this.camoMeta = -1;
					NetworkManager.getInstance().updateTileEntityField(this, "camoId");
					NetworkManager.getInstance().updateTileEntityField(this, "camoMeta");
				}
			}*/
			return true;
		}
		return false;
	}
}
