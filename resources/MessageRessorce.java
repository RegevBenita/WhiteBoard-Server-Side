//Resource class that support the messages add,remove all and get all actions  

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

import org.regev.benita.whiteboard.model.Message;
import org.regev.benita.whiteboard.service.MessageService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("/groups")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageRessorce {
	MessageService messageService = new MessageService();
	Gson gson = new GsonBuilder().create();

	// method that return a list of all the messages from the database
	@GET
	@Path("/{groupId}/message")
	public List<Message> getAllMessages(@PathParam("groupId") int id) {
		return messageService.getAllMessages(id);
	}

	// method that add message to the database
	@POST
	@Path("/{groupId}/message")
	public Message addMessage(@PathParam("groupId") int groupId, String message) {
		Gson gson = new Gson();
		Message m = gson.fromJson(message, Message.class);

		MessagePostThread messagePostThread = new MessagePostThread(groupId, m);
		messagePostThread.start();
		return messagePostThread.getReturnMessage();
	}

	// method that get an group id and remove all the messages in that group
	@DELETE
	@Path("/{groupId}/message")
	public Integer removeAllMessages(@PathParam("groupId") int groupId) {
		return messageService.removeAllMessages(groupId);
	}

	// inner class that handle the messages
	private class MessagePostThread extends Thread {
		int groupId;
		Message message = new Message();
		Message returnMessage = new Message();

		public MessagePostThread(int groupId, Message message) {
			this.groupId = groupId;
			this.message = message;
		}

		@Override
		public void run() {
			returnMessage = messageService.addMessage(groupId, message);
		}

		public Message getReturnMessage() {
			return returnMessage;
		}
	}

}
