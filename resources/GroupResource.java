package org.regev.benita.whiteboard.resources;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.regev.benita.whiteboard.model.Group;
import org.regev.benita.whiteboard.service.GroupService;
import org.regev.benita.whiteboard.service.MessageService;
import org.regev.benita.whiteboard.service.MyShapeService;

import com.google.gson.Gson;


@Path("/groups")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GroupResource {
	GroupService groupService = new GroupService();
	
	@GET
	public List<Group> getAllGroups() {
		return groupService.getAllGroups();
	}
	
	@GET
	@Path("/{groupId}")
	public Group getGroup(@PathParam("groupId") int id) {
		return groupService.getGroup(id);
		
	}
	
	@POST
	public Group addGroup(String group) {
		Gson gson = new Gson();	
		Group g = gson.fromJson(group, Group.class);
		return groupService.addGroup(g);
	}
	
	
	@DELETE
	@Path("/{groupId}")
	public Group removeGroup(@PathParam("groupId") int id) {
		MessageService messageService = new MessageService();
		MyShapeService myShapeService = new MyShapeService();
		messageService.removeAllMessages(id);
		myShapeService.removeAllShapes(id);
		return groupService.removeGroup(id);
	}
}
