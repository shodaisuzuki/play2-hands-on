package controllers

import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import scalikejdbc._
import models._

object JsonController {
  // UsersをJSONに変換するためのWritesを定義
  implicit val usersWrites = (
    (__ \ "id"       ).write[Long]   and
    (__ \ "name"     ).write[String] and
    (__ \ "companyId").writeNullable[Int]
  )(unlift(Users.unapply))

  1// ユーザ情報を受け取るためのケースクラス
  case class UserForm(id: Option[Long], name: String, companyId: Option[Int])

  // JSONをUserFormに変換するためのReadsを定義
  implicit val userFormFormat = (
    (__ \ "id"       ).readNullable[Long] and
    (__ \ "name"     ).read[String]       and
    (__ \ "companyId").readNullable[Int]
  )(UserForm)

}

class JsonController extends Controller {
  import JsonController._

  /**
   * 一覧表示
   */
  def list = Action { implicit request =>
    val u = Users.syntax("u")

    DB.readOnly { implicit session =>
      val users = withSQL {
        select.from(Users as u).orderBy(u.id.asc)
      }.map(Users(u.resultName)).list.apply()

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
