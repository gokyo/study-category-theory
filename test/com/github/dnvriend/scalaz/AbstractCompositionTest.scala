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

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{ FlatSpec, Matchers }

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.higherKinds
import scalaz._
import Scalaz._

class AbstractCompositionTest extends FlatSpec with Matchers with ScalaFutures {
  implicit val pc: PatienceConfig = PatienceConfig(timeout = 30.seconds, interval = 300.millis)

  def compose[F[_]: Monad, A: Numeric](effectOne: => F[A], effectTwo: => F[A]): F[A] = for {
    x <- effectOne
    y <- effectTwo
  } yield implicitly[Numeric[A]].plus(x, y)

  it should "compose" in {
    compose[Option, Long](Option(1), Option(2)) shouldBe Option(3)
    compose[Future, Long](Future(3), Future(4)).futureValue shouldBe 7
  }
}
