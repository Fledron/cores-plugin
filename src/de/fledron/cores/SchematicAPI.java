package de.fledron.cores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
 
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
 
public class SchematicAPI {
 
    Plugin plugin;
 
    public SchematicAPI(Plugin plugin){
        this.plugin = plugin;
    }
 
    /**
    * Get all blocks between two points and return a 3d array
    */
 
    @SuppressWarnings("deprecation")
	public int[][][] getStructureType(Block block, Block block2){
        int minX, minZ, minY;
        int maxX, maxZ, maxY;
 
 
        //Couldv'e used Math.min()/Math.max(), but that didn't occur to me until after I finished this :D
        minX = block.getX() < block2.getX() ? block.getX() : block2.getX();
        minZ = block.getZ() < block2.getZ() ? block.getZ() : block2.getZ();
        minY = block.getY() < block2.getY() ? block.getY() : block2.getY();
 
        maxX = block.getX() > block2.getX() ? block.getX() : block2.getX();
        maxZ = block.getZ() > block2.getZ() ? block.getZ() : block2.getZ();
        maxY = block.getY() > block2.getY() ? block.getY() : block2.getY();
 
        int[][][] blocks = new int[maxX-minX+1][maxY-minY+1][maxZ-minZ+1];
 
        for(int x = minX; x <= maxX; x++){
 
            for(int y = minY; y <= maxY; y++){
 
                for(int z = minZ; z <= maxZ; z++){
 
                    Block b = block.getWorld().getBlockAt(x, y, z);
                    blocks[x-minX][y-minY][z-minZ] = b.getTypeId();
                }
            }
        }
 
        return blocks;
 
    }
    
	@SuppressWarnings("deprecation")
	public byte[][][] getStructureData(Block block, Block block2){
        int minX, minZ, minY;
        int maxX, maxZ, maxY;
 
 
        //Couldv'e used Math.min()/Math.max(), but that didn't occur to me until after I finished this :D
        minX = block.getX() < block2.getX() ? block.getX() : block2.getX();
        minZ = block.getZ() < block2.getZ() ? block.getZ() : block2.getZ();
        minY = block.getY() < block2.getY() ? block.getY() : block2.getY();
 
        maxX = block.getX() > block2.getX() ? block.getX() : block2.getX();
        maxZ = block.getZ() > block2.getZ() ? block.getZ() : block2.getZ();
        maxY = block.getY() > block2.getY() ? block.getY() : block2.getY();
 
        byte[][][] data = new byte[maxX-minX+1][maxY-minY+1][maxZ-minZ+1];
 
        for(int x = minX; x <= maxX; x++){
 
            for(int y = minY; y <= maxY; y++){
 
                for(int z = minZ; z <= maxZ; z++){
 
                    Block b = block.getWorld().getBlockAt(x, y, z);
                    data[x-minX][y-minY][z-minZ] = b.getData();
                }
            }
        }
 
        return data;
 
    }
 
 
    /**
    * Pastes a structure to a desired location
    */
 
    @SuppressWarnings("deprecation")
	public void paste(int[][][] blocks, Location l){
        for(int x = 0; x < blocks.length; x++){
 
            for(int y = 0; y < blocks[x].length; y++){
 
                for(int z = 0; z < blocks[x][y].length; z++){
                    Location neww = l.clone();
                    neww.add(x, y, z);
                    Block b = neww.getBlock();
                    //if(blocks[x][y][z] != 0){
                        b.setTypeId(blocks[x][y][z]);
                        b.getState().update(true);
                    //}
                }
 
            }
        }
    }
    
    @SuppressWarnings("deprecation")
	public void pasteData(byte[][][] blocks, Location l){
        for(int x = 0; x < blocks.length; x++){
 
            for(int y = 0; y < blocks[x].length; y++){
 
                for(int z = 0; z < blocks[x][y].length; z++){
                    Location neww = l.clone();
                    neww.add(x, y, z);
                    Block b = neww.getBlock();
                    //if(blocks[x][y][z] != 0){
                        b.setData(blocks[x][y][z]);
                   //}
                }
 
            }
        }
    }
 
    /**
    * Save a structure with a desired name
    */
 
    public void saveType(String name, int[][][] b){
        ObjectOutputStream oos = null;
        FileOutputStream fout = null;
 
        File f = new File(plugin.getDataFolder() + "/schematics/"+ name + ".schem");
        File dir = new File(plugin.getDataFolder() + "/schematics");
 
        try {
                dir.mkdirs();
                f.createNewFile();
        } catch (IOException e1) {
                e1.printStackTrace();
            }
 
        try{
               fout = new FileOutputStream(f);
               oos = new ObjectOutputStream(fout);
               oos.writeObject(b);
        } catch (Exception e) {
               e.printStackTrace();
        }finally {
               if(oos  != null){
                   try {
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
    
    public void saveData(String name, byte[][][] d){
        ObjectOutputStream oos = null;
        FileOutputStream fout = null;
 
        File f = new File(plugin.getDataFolder() + "/schematics/"+ name + "Data.schem");
        File dir = new File(plugin.getDataFolder() + "/schematics");
 
        try {
                dir.mkdirs();
                f.createNewFile();
        } catch (IOException e1) {
                e1.printStackTrace();
            }
 
        try{
               fout = new FileOutputStream(f);
               oos = new ObjectOutputStream(fout);
               oos.writeObject(d);
        } catch (Exception e) {
               e.printStackTrace();
        }finally {
               if(oos  != null){
                   try {
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
 
    /**
    * Load structure from file
    */
 
    public int[][][] loadType(String name){
 
        File f = new File(plugin.getDataFolder() + "/schematics/"+ name + ".schem");
 
        int[][][] loaded = new int[0][0][0];
 
        try {
            FileInputStream streamIn = new FileInputStream(f);
           ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
           loaded = (int[][][])objectinputstream.readObject();
 
           objectinputstream.close();
 
       } catch (Exception e) {
 
           e.printStackTrace();
    }
 
        return loaded;
    }
    
    public byte[][][] loadData(String name){
    	 
        File f = new File(plugin.getDataFolder() + "/schematics/"+ name + "Data.schem");
 
        byte[][][] loaded = new byte[0][0][0];
 
        try {
            FileInputStream streamIn = new FileInputStream(f);
           ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
           loaded = (byte[][][])objectinputstream.readObject();
 
           objectinputstream.close();
 
       } catch (Exception e) {
 
           e.printStackTrace();
    }
 
        return loaded;
    }
 
    /**
    * Some methods I used to test
    *
    */
 
    public void printArray(int[][][] a){
        for(int i = 0; i < a.length; i++){
            System.out.println(toString(a[i]));
        }
    }
 
    public String toString(int[][] a){
        String s = "";
        for (int row=0; row < a.length ; ++row) {
          s += Arrays.toString(a[row]) + "\n";
        }
        return s;
    }
 
 
 
}