package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postUserHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByMsgIdHandler);
        app.delete("messages/{message_id}", this::deleteMessageByMsgIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByMsgIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUserIdHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context){
        context.json("sample text");
    }

    /*Handler to post a new account */
    private void postUserHandler(Context ctx)throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedUser = accountService.addAccount(account);

        if (addedUser == null || addedUser.username == "" || addedUser.password.length() <= 4  ){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(addedUser));
            ctx.status(200);
        }
    }

    /*Handler to verify a new account*/
    private void postLoginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account user = accountService.verifyAccount(account);
        if(user == null){
            ctx.status(401);
        }else{
            user.setAccount_id(1);
            ctx.json(mapper.writeValueAsString(user));
            ctx.status(200);
        }
    }

    /*Handler to post/submit a message */
    //will not contain a message id
    private void postMessagesHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);

        if (addedMessage != null && addedMessage.message_text.length() < 255 && addedMessage.message_text != "" ){
            ctx.json(mapper.writeValueAsString(addedMessage));
            ctx.status(200);
        }else{
            ctx.status(400);
        }
    } 

    /*Handler to get all messages in the db*/
    private void getAllMessagesHandler(Context ctx){
        List <Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    /*Handler to get message by the message id*/
    private void getMessageByMsgIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message requestedMessage = messageService.getMessageByMsgId(message_id);
        System.out.println(requestedMessage);
        if (requestedMessage != null){
            ctx.json(mapper.writeValueAsString(requestedMessage));
            ctx.status(200);
        } else{
            ctx.status(200);
        }
    }

    /*Handler to delete message by the message id */
    private void deleteMessageByMsgIdHandler (Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageByMsgId(message_id); //can also use getMessageByMsgId method
        System.out.println(deletedMessage);
        if (deletedMessage != null){
            ctx.json(mapper.writeValueAsString(deletedMessage));
            ctx.status(200);
        }else{
            ctx.status(200);
        }
    }
    /*Handler to update message by the message id */
    private void updateMessageByMsgIdHandler (Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessage(message_id, message, message.message_text);
        System.out.println(updatedMessage);
        if (updatedMessage == null){
            ctx.status(400);
        } else if (message.message_text.length() > 255 || updatedMessage.message_text == ""){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(updatedMessage));
            ctx.status(200);
        }
    }
    
    /*Handler to get all messages by the account id  */
    private void getAllMessagesByUserIdHandler (Context ctx) throws JsonProcessingException{
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List <Message> userMessages = messageService.getAllMessagesByUserId(account_id);
        ctx.json(userMessages);
        ctx.status(200);
    }
   
}