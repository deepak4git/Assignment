package com.workspan.assignment.entityClone;
import java.util.*;
public class EntityCloning {
    
	static long currentMaxID = Integer.MIN_VALUE;
	static List<Entity> entity =null;
	static List<Link> link = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        // Get the Entity Data
		if (args.length  == 0) {
			System.out.println("Empty Agruments");
			System.exit(1);
		}
		System.out.println(args[0]);
		entity = EntityParser.ParseEntityData(args[0]);
		// Get Link Data
		link = EntityParser.ParseLinkData(args[0]);
		
		// Sort the Entity to get the max ID present in list
		Collections.sort(entity, new SortByEntityID());
		
		for(Entity e: entity) {
			System.out.print(e.getEntityID()+ "\t"+ e.getEntityName()+"\t"+ e.getEntityDesc());
			System.out.println();
		}
		
		for(Link l: link) {
			System.out.print(l.getFrom()+ "\t"+ l.getTo());
			System.out.println();
		}
		
		currentMaxID = maxID();
		System.out.println("max id :" + currentMaxID);
		
		// create new entity for clone
		cloneEntity(5);
		printEntity();
		
	}
 
	public static long maxID() {
		return entity.get(entity.size() - 1).getEntityID();	
	}
	
	public static void cloneEntity(int cloneID) {
		// Add the clone Entity
		addEntity(cloneID);
	}
	
	public static Entity findAndCreateClone(int cloneID) {
		for(Entity ent : entity) {
			if(ent.getEntityID() == cloneID )
			{
				try {
					return (Entity) ent.clone();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static void addEntity(int cloneID) {
		Entity e = findAndCreateClone(cloneID);
		if(e== null) {
            System.out.println("Not a valid clonable entity id");
		}
		else {
			currentMaxID = currentMaxID+1;
			e.setEntityID(currentMaxID);
			entity.add(e);
		}
	}
	
	public static void printEntity()
	{
		for(Entity e:entity) {
			System.out.print(e.getEntityID()+"\t" + e.getEntityName() + "\t" + e.getEntityDesc());
			System.out.println();
		}
	}
	
	public static void addLinks(int cloneID) {
		ArrayList<Entity> entitylist = new ArrayList<Entity>();
		ArrayList<Link> linklist = new ArrayList<Link>();
		
		for(Link ln : link) {
			if(ln.getFrom() == cloneID) {
				// add two entities for from and to
				
			}
		}
		
	}
}
