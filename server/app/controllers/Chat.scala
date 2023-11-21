package controllers

import javax.inject._

//TODO make model for memory
import play.api.i18n._
import play.api.libs.json._
import play.api.mvc._

@Singleton
class Chat  @Inject()(cc: ControllerComponents) extends AbstractController(cc){
  def index = Action { implicit request =>
    Ok(views.html.ChatPage())
  }
  def login = Action {
    Ok(views.html.login2())
  }
  def validateLogin = Action {request =>
    val loginVals = request.body.asFormUrlEncoded
    loginVals.map { args =>
        val username = args ("username").head
        val password = args ("password").head
        Redirect(routes.Chat.load)
    }.getOrElse(Redirect(routes.Chat.login))
  }
  def load = TODO
    /*Action {
    Ok(routes.Chat.index)
  }*/
  def data = Action {
    Ok(Json.toJson(Seq("a","b","c")))
  }
}
