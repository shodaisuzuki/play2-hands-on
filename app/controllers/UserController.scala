package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.i18n.{MessagesApi, I18nSupport}
import play.api.db.slick._
import slick.driver.JdbcProfile
import models.Tables._
import javax.inject.Inject
import scala.concurrent.Future
import slick.driver.H2Driver.api._

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

class UserController @Inject()( val dbConfigProvider: DatabaseConfigProvider,
                                val messagesApi: MessagesApi
                              ) extends Controller
with HasDatabaseConfigProvider[JdbcProfile]
with I18nSupport {

import UserController._


  /**
   * 一覧表示
   */
  def list = Action.async { implicit rs =>
    // IDの昇順にすべてのユーザ情報を取得
    db.run(Users.sortBy(t => t.id).result).map{ users =>
      // 一覧画面を表示
      Ok(views.html.user.list(users))
    }
  }

  /**
   * 編集画面表示
   */
  def edit(id: Option[Long]) = Action.async { implicit rs =>
    val form  = if(id.isDefined) {
        db.run(Users.filter(t => t.id === id.get.bind).result.head)
          .map{ user =>
            userForm.fill(UserForm(Some(user.id), user.name, user.companyId))
          }
      } else {
        Future { userForm }
      }
    form.flatMap { form =>
      db.run(Companies.sortBy(_.id).result)
        .map { companies =>
          Ok(views.html.user.edit(form, companies))
        }
    }
  }

  /**
   * 登録実行
   */
  def create = Action.async { implicit rs =>
    userForm.bindFromRequest.fold(
      error => {
        db.run(Companies.sortBy(t => t.id).result)
          .map { companies =>
            BadRequest(views.html.user.edit(error, companies))
          }
      },
      form  => {
        val user = UsersRow(0, form.name, form.companyId)
        db.run(Users += user).map { _ => Redirect(routes.UserController.list) }
      }
    )
  }

  /**
   * 更新実行
   */
  def update = Action.async { implicit rs =>
    userForm.bindFromRequest.fold(
      error => {
        db.run(Companies.sortBy(t => t.id).result)
          .map { companies =>
            BadRequest(views.html.user.edit(error, companies))
          }
      },
      form => {
        val user = UsersRow(form.id.get, form.name, form.companyId)
        db.run(Users.filter(t => t.id === user.id.bind).update(user))
          .map { _ => Redirect(routes.UserController.list) }
      }
    )
  }

  /**
   * 削除実行
   */
  def remove(id: Long) = TODO

}

