/*
 * Copyright 2016 Dennis Vriend
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.dnvriend.scalaz

import com.github.dnvriend.TestSpec

import scala.language.{ implicitConversions, postfixOps }
import scalaz.Kleisli._
import scalaz.Scalaz._
import scalaz._

class KleisliTest extends TestSpec {
  /**
   * see: http://www.casualmiracles.com/2012/07/02/a-small-example-of-kleisli-arrows/
   *
   * TL;DR
   * A Kleisli is function composition for Monads. If you have a __function__ that return higher-kinded-types
   * like List, Options etc then you can use a Kleisli to compose those __functions__, so it's all about
   * function composition of this shape 'A => M[A]' where M is the same higher-kinded-type like eg. Option.
   *
   * The problem
   * You have `functions` that take a simple type and return higher kinded types like Options or Lists,
   * and you need to `compose those functions`. You might think you need to get the first result,
   * pattern match on the it, apply it to the second function, repeat. Sounds rather cumbersome.
   *
   */

  // methods that take simple types and return higher-kinded types (HKT)
  // they will be ETA'd to Int => Option[String], String => Option[Int] and Int => Option[Double]
  def toStr(x: Int): Option[String] = Option(x.toString)
  def toInt(x: String): Option[Int] = Option(x.toInt)
  def toDouble(x: Int): Option[Double] = Option(x * 2.0)

  "compose functions that return higher-kinded-types" should "be composed the old fashioned way" in {
    // the process is quite simple,
    // 1. Int => Option[String]
    // 2. String => Option[Int]
    // 3. Int => Option[Double]
    // 4. Return the Option[Double]
    (for {
      x <- toStr(10)
      y <- toInt(x)
      z <- toDouble(y)
    } yield z) shouldBe Option(20.0)
  }

  // all the functions that will be composed must return the same type
  // in this case they all return Options.
  it should "be composed using a kleisli" in {
    // Kleisli of Option, Int to Double
    val kleisliComposition: Kleisli[Option, Int, Double] = kleisli(toStr _) andThenK toInt _ andThenK toDouble _
    val _: Kleisli[Option, Int, Double] = kleisli(toStr _) >==> toInt _ >==> toDouble _
    kleisliComposition(10) shouldBe Option(20.0)
  }

  // define an abstract computation using effects
  def calc[F[_]: Monad](left: Kleisli[F, Int, Int], right: Kleisli[F, Int, Int]) = for {
    x <- left
    y <- right
  } yield x + y

  def maybeX(x: Int) = Option(x + 1)
  def maybeY(x: Int) = Option(x + 100)

  it should "compute using an abstract composition and two effects" in {
    val comp: ReaderT[Option, Int, Int] = calc[Option](Kleisli(maybeX), Kleisli(maybeY))
    comp.apply(1) shouldBe ""
  }

  //  def f2[F[_]: Monad](x: Int) = Kleisli[F, Int, Int](x * 100)
  //  val k1: ReaderT[F, Int, Int] = Kleisli(f1)
  //  val k2: ReaderT[Option, Int, Int] = Kleisli(f2)
  //  val k3: ReaderT[Option, Int, Int] = k1 compose k2
  //  val k4: ReaderT[Option, Int, Int] = k1 <=< k2 // alias for compose
  //  val k5: ReaderT[Option, Int, Int] = k1 andThen k2
  //  val k6: ReaderT[Option, Int, Int] = k1 >=> k2 // alias for andThen

  //  it should "" in {
  //    val result: Option[Int] = for {
  //      nr <- Option(4)
  //      result <- k1(nr)
  //    } yield result
  //
  //    println(result)
  //  }
}
