package controllers

import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import scalikejdbc._
import models._

class JsonController extends Controller {

  /**
   * 一覧表示
   */
  def list = TODO

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
