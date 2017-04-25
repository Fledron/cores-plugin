package de.fledron.cores;

import org.bukkit.Location;
import org.bukkit.Material;

public class BlockInfos {
	
	Material[] mat;
	byte[] data;
	Location[] loc;
	
	public BlockInfos(Material[] mat, byte[] data, Location[] loc){
		this.data = data;
		this.loc = loc;
		this.mat = mat;
	}
	
	
	
	public Material getMat(int i){
		return mat[i];
	}
	
	public Location getLoc(int i){
		return loc[i];
	}
	
	public byte getData(int i){
		return data[i];
	}
	
	

}
