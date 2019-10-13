package com.workspan.assignment.entityClone;
import java.util.*;
public class EntityCloning {
    
	static long currentMaxID = Integer.MIN_VALUE;
	static List<Entity> entity =null;
	static List<Link> link = null;
	static Map<Long,Long> clones = new HashMap<Long,Long>();
	
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
		printLink();
		
	}
 
	public static long maxID() {
		return entity.get(entity.size() - 1).getEntityID();	
	}
	
	public static List<Long> getAncestor(long cloneID) {
		List<Long> ancestor =  new ArrayList<Long>();
		for(Link l : link) {
			if(l.getTo() == cloneID) {
				ancestor.add(l.getFrom());
			}
		}
		return ancestor;
	}
	
	public static List<Long> getpredecessor(long cloneID) {
		List<Long> predecessor =  new ArrayList<Long>();
		for(Link l : link) {
			if(l.getFrom() == cloneID) {
				predecessor.add(l.getTo());
				predecessor.addAll(getpredecessor(l.getTo()));
			}
		}
		return predecessor;
	}
	
	public static void cloneEntity(long cloneID) {
		// get the ancestor
		List<Long> ancestor =  getAncestor(cloneID);
  
       // get the predecessor 	
		List<Long> predecessor =  getpredecessor(cloneID);
		
	   // Add a new clone entity for parent clone ID
		long newclone = Integer.MIN_VALUE;
		
		if(clones.containsKey(cloneID)) {
			newclone = clones.get(cloneID);
		  }
		  else {
			Entity e = getNewEntity(cloneID);
			entity.add(e);
			newclone = e.getEntityID();
			clones.put(cloneID, e.getEntityID());
		  }
		 for(long parent : ancestor) {
			 link.add(new Link(parent, newclone));
		 }
		 
		 for(long child : predecessor) {
			 long childclone =  Integer.MIN_VALUE;
			if(clones.containsKey(child)) {
				childclone = clones.get(child);
			}
			else {
			    Entity e = getNewEntity(child);
				entity.add(e);
				childclone = e.getEntityID();
				clones.put(newclone, childclone);
			}
			 link.add(new Link(newclone, childclone));
			 newclone = childclone;
		 }
	}
	
	public static Entity findAndCreateClone(long cloneID) {
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
	
	public static Entity getNewEntity(long cloneID) {
		Entity e = findAndCreateClone(cloneID);
		if(e== null) {
            System.out.println("Not a valid clonable entity id");
            System.exit(1);
		}
		else {
			currentMaxID = currentMaxID+1;
			e.setEntityID(currentMaxID);
		}
		return e;
	}
	
	public static void printEntity()
	{
		for(Entity e:entity) {
			System.out.print(e.getEntityID()+"\t" + e.getEntityName() + "\t" + e.getEntityDesc());
			System.out.println();
		}
	}
	
	public static void printLink()
	{
		for(Link ln : link) {
			System.out.print(ln.getFrom()+"\t" + ln.getTo());
			System.out.println();
		}
	}
	
}
