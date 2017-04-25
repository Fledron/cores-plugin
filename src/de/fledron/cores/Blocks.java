package de.fledron.cores;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Blocks {
	
	byte data;
	Location loc;
	Material mat;
	int id;
	Block block;
	
	public Blocks(byte data, Location loc, Material mat, Block block, int id){
		this.data = data;
		this.loc = loc;
		this.mat = mat;
		this.id = id;
		this.block = block;
	}
	
	public byte getData(){
		return data;
	}
	
	public Location getLoc(){
		return loc;
	}
	
	public Material getMat(){
		return mat;
	}
	
	public int getID(){
		return id;
	}
	
	public Block getBlock(){
		return block;
	}

}
