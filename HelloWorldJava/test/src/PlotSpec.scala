/* cspell: disable-next-line */
package HelloWorld

// cSpell:ignore splotly, munit

/**
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i HelloWorld.test
 * ./mill -i HelloWorld.test.testLocal
 * ./mill -i HelloWorld.test HelloWorld.PlotSpec.*
 * ./mill -i HelloWorld.test HelloWorld.PlotSpec.hello // test("hello")
 *
 * Extending `TestCase` to get access to `setUp`
 *
 * https://github.com/sbt/junit-interface#junit-interface
 * ./mill -i managed.test --tests=test1           Only matches test names (not classes)
 *
 *
 */
class PlotSpec extends munit.FunSuite {

  test("hello") {
    val obtained = 42
    val expected = 43
    assertEquals(obtained, expected)
  }

}


