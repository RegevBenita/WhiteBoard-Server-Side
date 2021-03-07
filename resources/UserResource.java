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

import org.regev.benita.whiteboard.model.User;
import org.regev.benita.whiteboard.service.UserService;

import com.google.gson.Gson;

@Path("/groups")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
	UserService userService = new UserService();
	
	//method that return a list of all the users from the database
	@GET
	@Path("/{groupId}/user")
	public List<User> getAllUsers(@PathParam("groupId") int groupId) {
		return userService.getAllUsers(groupId);
	}
	
	//method that add user to the database
	@POST
	@Path("/{groupId}/user")
	public User addUser(@PathParam("groupId") int groupId, String user) {
		Gson gson = new Gson();	
		User u = gson.fromJson(user, User.class);
		return userService.addUser(groupId, u);
	}
	
	//method that remove user to the database
	@DELETE
	@Path("/{groupId}/user/{userId}")
	public User removeUser(@PathParam("groupId") int groupId,@PathParam("userId") int userId) {
		return userService.removeUser(groupId, userId);
	}
	
}
