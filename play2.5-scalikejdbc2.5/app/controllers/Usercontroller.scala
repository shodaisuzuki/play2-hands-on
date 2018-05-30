package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.{MessagesApi, I18nSupport}
import javax.inject.Inject
import scalikejdbc._
import models._

object UserController {

  // フォームの値を格納するケースクラス
  case class UserForm(id: Option[Long], name: String, companyId: Option[Int])

  // formから送信されたデータ ⇔ ケースクラスの変換を行う
  val userForm = Form(
    mapping(
      "id"        -> optional(longNumber),
      "name"      -> nonEmptyText(maxLength = 20),
      "companyId" -> optional(number)
    )(UserForm.apply)(UserForm.unapply)
  )

}

class UserController @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {
  import UserController._

  /**
   * 一覧表示
   */
  def list = Action { implicit request =>
    val u = Users.syntax("u")
    
    DB.readOnly { implicit session =>
      val users = withSQL {
        select.from(Users as u).orderBy(u.id.asc)
      }.map(Users(u.resultName)).list.apply()

      Ok(views.html.user.list(users))
    }
  }

  /**
   * 編集画面表示
   */
  def edit(id: Option[Long]) = Action { implicit request =>
    val c = Companies.syntax("c")

    DB.readOnly { implicit session =>
      val form = id match {
        case Some(id) => {
          val user = Users.find(id).get
          userForm.fill(UserForm(Some(user.id),user.name,user.companyId))
        }
        case None => userForm
      }
      val companies = withSQL {
        select.from(Companies as c).orderBy(c.id.asc)
      }.map(Companies(c.resultName)).list().apply()
      
      Ok(views.html.user.edit(form, companies))
    }
  }

  /**
   * 登録実行
   */
  def create = Action { implicit request =>
    DB.localTx { implicit session =>
      userForm.bindFromRequest.fold(
        error => {
          BadRequest(views.html.user.edit(error, Companies.findAll()))
        },
        form => {
          Users.create(form.name, form.companyId)
          Redirect(routes.UserController.list)
        }
      )
    }
  }

  /**
   * 更新実行
   */
  def update = TODO

  /**
   * 削除実行
   */
  def remove(id: Long) = TODO

}
