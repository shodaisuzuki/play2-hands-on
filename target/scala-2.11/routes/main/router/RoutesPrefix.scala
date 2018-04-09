
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/vagrant/work/scala/biz/play2-hands-on/conf/routes
// @DATE:Fri Apr 06 17:22:22 JST 2018


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
