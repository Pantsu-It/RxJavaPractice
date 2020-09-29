package observable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class TestCondition {
  public static void main(String[] args) throws Exception {
    // only first observable emits all data-stream
//    amb();

//    defaultIfEmpty();
    switchIfEmpty();

//    skip();
//    skipWhileAndUtil();

//    take();
//    takeWhileAndUtil();


    Thread.sleep(100000);
  }

  private static void takeWhileAndUtil() {
    Observable
        .intervalRange(1, 20, 0, 200, TimeUnit.MILLISECONDS)
        .takeWhile(longValue -> longValue < 10)
        .takeUntil(longValue -> longValue == 9)
        .takeUntil(Observable.timer(1, TimeUnit.SECONDS))
        .subscribe(value -> System.out.print(value + " "));
  }

  private static void take() {
    Observable
        .intervalRange(1, 20, 0, 200, TimeUnit.MILLISECONDS)
        // take与takeLast互斥
        .take(5)
//        .takeLast(5)
        .subscribe(value -> System.out.print(value + " "));
  }


  private static void amb() throws Exception {
    Observable
        .amb(
            ((Callable<Iterable<Observable<Object>>>) () -> {
              List<Observable<Object>> list = new ArrayList<>();
              list.add(Observable
                  .range(0, 10)
                  .cast(Object.class)
                  .delay(1, TimeUnit.MILLISECONDS)
              );
              list.add(Observable
                  .range(-10, 10)
                  .cast(Object.class)
                  .delay(2, TimeUnit.MILLISECONDS)
              );
              return list;
            }).call()
        )
        .subscribe(value -> System.out.print(value + " "));
  }

  private static void defaultIfEmpty() {
    Observable
        .empty()
        .defaultIfEmpty("it is default-item")
        .subscribe(value -> System.out.print(value + " "));
  }

  private static void switchIfEmpty() {
    Observable
        .empty()
        .switchIfEmpty(Observable.timer(2000, TimeUnit.MILLISECONDS).map(ignore -> "no data"))
        .subscribe(any -> System.out.println(any));

  }

  private static void skip() {
    Observable
        .intervalRange(1, 20, 0, 1000, TimeUnit.MILLISECONDS)
        .skip(3)
        .skip(5, TimeUnit.SECONDS)
        .skipLast(2)
        .subscribe(value -> System.out.print(value + " "));
  }

  private static void skipWhileAndUtil() {
    Observable
        .intervalRange(1, 20, 0, 1000, TimeUnit.MILLISECONDS)
        .skipUntil(Observable.timer(2, TimeUnit.SECONDS))
        .skipWhile(longValue -> longValue <= 5)
        .subscribe(value -> System.out.print(value + " "));
  }

}