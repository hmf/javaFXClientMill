/* cspell: disable-next-line */
package helloworld;

// cSpell:ignore splotly, munit


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i HelloWorldJava.jtest
 * ./mill -i HelloWorldJava.jtest.testLocal
 * ./mill -i HelloWorldJava.jtest --tests=hello.*
 * ./mill -i HelloWorldJava.jtest --tests=he.*o.*
 *
 * Extending `TestCase` to get access to `setUp`
 *
 * https://github.com/sbt/junit-interface#junit-interface
 * https://github.com/sbt/sbt-jupiter-interface
 * ./mill -i managed.test --tests=test1           Only matches test names (not classes)
 *
 *
 */
public class PlotSpec {

  @Test
  public void hello() {
    assertEquals(2, 1);
  }

}

