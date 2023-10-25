package controllers

import javax.inject._

import shared.SharedMessages
import play.api.mvc._

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    Ok(views.html.index("homepage"))
  }
  def login = Action {
    Redirect(routes.LoginController.loginPage)
  }
}
