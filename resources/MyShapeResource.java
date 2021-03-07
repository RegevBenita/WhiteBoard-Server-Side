package org.regev.benita.whiteboard.resources;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.regev.benita.whiteboard.model.MyShape;
import org.regev.benita.whiteboard.service.MyShapeService;

import com.google.gson.Gson;

@Path("/groups")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MyShapeResource {
	MyShapeService myShapeService = new MyShapeService();
	boolean isPosting = false;

	// method that return a list of all the shapes from the database
	@GET
	@Path("/{groupId}/shape")
	public List<MyShape> getAllShapes(@PathParam("groupId") int groupId) {
		return myShapeService.getAllShapes(groupId);
	}

	// method that update all the shapes that in a specific group
	@POST
	@Path("/{groupId}/shape")
	public List<MyShape> updateShapes(String newShapes, @PathParam("groupId") int groupId) {
		Gson gson = new Gson();
		List<MyShape> lShape = new ArrayList<>();
		JSONArray jsonArray = new JSONArray(newShapes);
		for (int i = 0; i < jsonArray.length(); i++) {
			MyShape shape = gson.fromJson(jsonArray.getJSONObject(i).toString(), MyShape.class);
			lShape.add(shape);
		}
		MyShapePostThread myShapePostThread = new MyShapePostThread(groupId, lShape);
		myShapePostThread.start();
		return myShapePostThread.getlShape();
	}

	// method that get an group id and remove all the shapes in that group,
	// return the group id that been remove
	@DELETE
	@Path("/{groupId}/shape")
	public int removeAllShapes(@PathParam("groupId") int groupId) {
		return myShapeService.removeAllShapes(groupId);
	}

	// inner class that handle the myshape post
	private class MyShapePostThread extends Thread {
		int groupId;
		List<MyShape> lShape = new ArrayList<>();
		List<MyShape> lNewShape = new ArrayList<>();

		public MyShapePostThread(int groupId, List<MyShape> lShape) {
			this.groupId = groupId;
			this.lShape = lShape;
		}

		@Override
		public void run() {
			lNewShape = myShapeService.updateShapes(lShape, groupId);		//create new list of all the shapes
		}

		public List<MyShape> getlShape() {
			return lNewShape;
		}

	}

}
