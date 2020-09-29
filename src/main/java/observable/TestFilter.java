package observable;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;

public class TestFilter {
  public static void main(String[] args) throws InterruptedException {
    allOrAny();
    sequenceEqual();

    Thread.sleep(100000);
  }

  private static void sequenceEqual() {
    Observable
        .sequenceEqual(
            Observable.range(1, 10).map(intValue -> intValue.longValue()),
            Observable.intervalRange(1, 10, 0, 200, TimeUnit.MILLISECONDS)
        )
        .delay(500, TimeUnit.MILLISECONDS)
        .subscribe(result -> System.out.println("sequenceEqual? " + result));
  }

  private static void allOrAny() {
    Observable<Integer> observable = Observable.range(1, 10);
    Single
        .merge(
            observable.all(value -> value > 0),
            observable.any(value -> value * value == 25),
            observable.contains(5)
        )
        .all(result -> result)
        .subscribe(result -> System.out.println("all >0 && contain(5) ? " + result));
  }

}
