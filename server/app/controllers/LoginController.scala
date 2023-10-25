package controllers

import javax.inject._
import play.api.i18n._

import shared.SharedMessages
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
import models.LoginModel
import scala.concurrent.Future
import views.html.defaultpages.error

class LoginController @Inject() (
    val controllerComponents: ControllerComponents
) extends BaseController
    with I18nSupport {

  val loginForm: Form[LoginModel] = Form(
    mapping(
      "email" -> email,
      "password" -> nonEmptyText
    )(LoginModel.apply)(LoginModel.unapply)
  )

  def loginPage = Action { implicit request =>
    Ok(views.html.Login(loginForm))
  }

  def handleLogin = Action.async { implicit request =>
    loginForm
      .bindFromRequest()
      .fold(
        errorForm => {
          // Handle form errors
          Future.successful(BadRequest(views.html.Login(errorForm)))
        },
        data => {
          // check the submitted email and password against your database, set up a session if the credentials are valid, or render an error message if they're not.
          Future.successful(Ok(views.html.index(SharedMessages.itWorks)))
        }
      )
  }
}
/*

package controllers

import javax.inject._

//TODO make model for memory
import play.api.i18n._
import play.api.libs.json._
import play.api.mvc._

@Singleton
class login  @Inject()(cc: ControllerComponents) extends AbstractController(cc){
  def index = Action { implicit request =>
    Ok(views.html.login())
  }
  
  def load= TODO
  def data = Action {
    Ok(Json.toJson(Seq("a","b","c")))
  }
}
*/