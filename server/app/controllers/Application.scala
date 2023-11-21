package controllers

import javax.inject._

import shared.SharedMessages
import play.api.mvc._

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    Ok(views.html.index("homepage"))
  }
  def task3 = Action {
     Ok(views.html.task3())
  }
  def login = Action {
    Redirect(routes.Chat.login)
  }
}
