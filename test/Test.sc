import scalaz._
import Scalaz._
import scala.language.higherKinds
import scala.language.implicitConversions

case class StringStats(length: Int, palindrome: Boolean)

def stringStats(calcLength: String => Int, isPalindrome: String => Boolean): String => StringStats = for {
  length <- calcLength
  palindrome <- isPalindrome
} yield StringStats(length, palindrome)

def stringStatsCtx[F[_]: Monad]
  (calcLength: ReaderT[F, String, Int],
   isPalindrome: ReaderT[F, String, Boolean]): ReaderT[F, String, StringStats] = for {
  length <- calcLength
  palindrome <- isPalindrome
} yield StringStats(length, palindrome)

def compose[F[_]: Monad, A: Monoid](fx: F[A], fy: F[A]): F[A] = for {
  x <- fx
  y <- fy
} yield x |+| y

compose[Id, Int](1, 2) == 3

val statsFunction: (String) => StringStats =
  stringStats(_ => 4, _ => true)

statsFunction("abba") == StringStats(4, true)

// actually use the function and define the effect
val statsFunctionCtx: ReaderT[Option, String, StringStats] =
  stringStatsCtx[Option](
    ReaderT[Option, String, Int](_ => Option(4)),
    ReaderT[Option, String, Boolean](_ => Option(true))
  )

statsFunctionCtx("abba") == Some(StringStats(4, true))

// there must be an implicit execution context
// available at the call site
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
def callStrLengthService(str: String): Future[Int] =
  Future.successful(4)

def callStrPalindromeService(str: String): Future[Boolean] =
  Future.successful(str.reverse == str)

import scala.concurrent.ExecutionContext.Implicits.global
val statsFunctionAsync: ReaderT[Future, String, StringStats] =
  stringStatsCtx[Future](
    ReaderT[Future, String, Int](callStrLengthService),
    ReaderT[Future, String, Boolean](callStrPalindromeService)
  )

Await.result(statsFunctionAsync("abba"), 1.second) ==
  StringStats(4, true)