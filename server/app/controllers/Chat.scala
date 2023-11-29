package controllers

import javax.inject._
import play.api.i18n._
import play.api.libs.json._
import play.api.mvc._
import models.LoginModel

@Singleton
class Chat  @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc){
  def login = Action {implicit request =>
    Ok(views.html.login2())
  }
  def validateLogin = Action {request =>
    val loginVals = request.body.asFormUrlEncoded
    loginVals.map { args =>
        val username = args ("username").head
        val password = args ("password").head
        if(LoginModel.validateUser(username,password)){
          Redirect(routes.Chat.messageLog).withSession("username"->username)
        }else{
          Redirect(routes.Chat.login)
        }
    }.getOrElse(Redirect(routes.Chat.login))
  }
  
  def createUser = Action{ implicit request =>
     val newUserVals = request.body.asFormUrlEncoded
        newUserVals.map { args =>
        val username = args ("newusername").head
        val password = args ("newpassword").head
        if(LoginModel.createUser(username,password)){
          Redirect(routes.Chat.messageLog).withSession("username"->username)
        } else {
          Redirect(routes.Chat.login)
        }
    }.getOrElse(Redirect(routes.Chat.login))
  }
  def messageLog = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map{ username =>
      val messages = LoginModel.getMessages(username)
      Ok(views.html.ChatPage(messages))
    }.getOrElse(Redirect(routes.Chat.login))
    
  }/*
  def addPrivMessage = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map{username=>
      val postVals = request.body.asFormUrlEncoded
      postVals.map{args=>
        val message = args("newMessage").head
        val recipient = args("recipient").head
        LoginModel.sendPrivateMessage(username,recipient, message);
        Redirect(routes.Chat.messageLog)
      }.getOrElse(Redirect(routes.Chat.messageLog))
      }.getOrElse(Redirect(routes.Chat.login))
  }*/
  def addMessage = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map{username=>
      val postVals = request.body.asFormUrlEncoded
      postVals.map{args=>
        val recipient = args("recipient").head
        val message = args("newMessage").head
        if(LoginModel.getUsers().contains(recipient)){
          LoginModel.addMessage(username, message);
        }else{
          LoginModel.addMessage("everyone", message);
        }
        Redirect(routes.Chat.messageLog)
      }.getOrElse(Redirect(routes.Chat.messageLog))
      }.getOrElse(Redirect(routes.Chat.login))
  }
  def deleteMessage = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map{username=>
      val postVals = request.body.asFormUrlEncoded
      postVals.map{args=>
        val ind = args("ind").head.toInt
        LoginModel.removeMessage(username, ind);
        Redirect(routes.Chat.messageLog)
      }.getOrElse(Redirect(routes.Chat.messageLog))
      }.getOrElse(Redirect(routes.Chat.login))

  }
  def logout = Action {
    Redirect(routes.Chat.login).withNewSession
  }
}
