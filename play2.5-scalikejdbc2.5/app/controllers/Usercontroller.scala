package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.{MessagesApi, I18nSupport}
import javax.inject.Inject
import scalikejdbc._
import models._

class UserController @Inject()(val messagesApi: MessagesApi) extends Controller
    with I18nSupport {

  /**
   * 一覧表示
   */
  def list = TODO

  /**
   * 編集画面表示
   */
  def edit(id: Option[Long]) = TODO

  /**
   * 登録実行
   */
  def create = TODO

  /**
   * 更新実行
   */
  def update = TODO

  /**
   * 削除実行
   */
  def remove(id: Long) = TODO

}
