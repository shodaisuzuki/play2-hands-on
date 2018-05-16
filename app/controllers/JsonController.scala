package controllers

import play.api.mvc._

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.db.slick._
import slick.driver.JdbcProfile
import models.Tables._
import javax.inject.Inject
import scala.concurrent.Future
import slick.driver.H2Driver.api._

import play.api.libs.json._
import play.api.libs.functional.syntax._

class JsonController @Inject()(val dbConfigProvider: DatabaseConfigProvider) extends Controller
with HasDatabaseConfigProvider[JdbcProfile] {

  // UsersRowをJSONに変換するためのWritesを定義
  implicit val usersRowWritesFormat = new Writes[UsersRow]{
    def writes(user: UsersRow): JsValue = {
      Json.obj(
        "id"        -> user.id,
        "name"      -> user.name,
        "companyId" -> user.companyId
      )
    }
  }

  // JsonをUsersRowに変換するためのReadsを定義
  implicit val userFormFormat = new Reads[UserForm]{
    def reads(js: JsValue): UserForm = {
      UserForm(
        id        = (js \ "id"       ).asOpt[Long],
        name      = (js \ "name"     ).as[String],
        companyId = (js \ "companyId").asOpt[Int]
      )
    }
  }

  /**
   * 一覧表示
   */
  def list = Action.async { implicit rs =>
    db.run(Users.sortBy(t => t.id).result)
      .map { users =>
        Ok(Json.obj("users" -> users))
      }
  }

  /**
   * ユーザ登録
   */
  def create = TODO

  /**
   * ユーザ更新
   */
  def update = TODO

  /**
   * ユーザ削除
   */
  def remove(id: Long) = TODO
}
