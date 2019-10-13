/**  
 * EntityCloning.java -  This is the main class for entity cloning. With other helper classes it parse, clone and print the data.
 * It accepts json file name with complete path and entity id
 * First parameter is file path and second one is entity ID
 * @author  Deepak Kumar
 */ 


package com.workspan.assignment.entityClone;
import java.util.*;

public class EntityCloning {
	//Static variable needed across the methods.

	static long currentMaxID = Integer.MIN_VALUE;
	static List<Entity> entity =null;
	static List<Link> link = null;
	static Map<Long,Long> clones = new HashMap<Long,Long>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Get the Entity Data
		//Check for input file
		try {
			if (args.length  < 2 ) {
				System.out.println("Empty Agruments");
				System.exit(1);
			}
			System.out.println("Input file :" + args[0]);
			System.out.println("Entity ID to be cloned: "+ args[1]);

			//parse entity data present in Json file
			entity = EntityParser.ParseEntityData(args[0]);
			// Get Link Data present in Json file
			link = EntityParser.ParseLinkData(args[0]);

			// Sort the Entity to get the max ID present in list
			Collections.sort(entity, new SortByEntityID());

			//Get Max entity ID present in entity data
			currentMaxID = maxID();
			System.out.println("max id :" + currentMaxID);

			// Clone the entity
			cloneEntity(Long.parseLong(args[1]));
			// printEntity();
			// printLink();

			// print the data in Json format
			EntityParser.printJsonData(entity,link);
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.exit(0);

	}

	/**  
	 * Method to get MAX entity ID
	 */ 
	
	public static long maxID() {
		return entity.get(entity.size() - 1).getEntityID();	
	}

	/**  
	 * Method to get all the ancestors for given entity ID
	 */ 
	
	public static List<Long> getAncestor(long cloneID) {
		List<Long> ancestor =  new ArrayList<Long>();
		for(Link l : link) {
			if(l.getTo() == cloneID) {
				ancestor.add(l.getFrom());
			}
		}
		return ancestor;
	}

	/**  
	 * Method to get all the predecessor for given entity ID
	 */ 
	
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

	/**  
	 * Method to clone given entity ID
	 */ 
	
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

	/**  
	 * Method to create a clone of entity 
	 */ 
	
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

	/**  
	 * Method to update the entity ID for new clone. It get the clone from findAndCreateClone and update the entity ID
	 */ 
	
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

	/**  
	 * Method to print the entity data
	 */
	
	public static void printEntity()
	{
		for(Entity e:entity) {
			System.out.print(e.getEntityID()+"\t" + e.getEntityName() + "\t" + e.getEntityDesc());
			System.out.println();
		}
	}

	/**  
	 * Method to print the link data
	 */
	
	public static void printLink()
	{
		for(Link ln : link) {
			System.out.print(ln.getFrom()+"\t" + ln.getTo());
			System.out.println();
		}
	}

}
