package observable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class TestCombine {
  public static void main(String[] args) throws InterruptedException {
//    startWith();

    // Observable.merge(A, B)
    // 等价于 Observable<A> mergeWith Observable<B>
//    merge();
//    mergeDelayError();

    // An + Bn + Cn
//    zip();

    // A(new) + B(new) + C(new)
//    combineLast();

    // A(m) ++ [ B(n) / B(n+1)]
//    join();

    // 与switchMap类似
    switchOnNext();

    Thread.sleep(1000000);
  }

  private static void switchOnNext() {
    Observable
        .switchOnNext(
            Observable
                .just(0, 1, 2)
                .flatMap(
                    integer -> Observable.just(
                        Observable.interval(100, TimeUnit.MILLISECONDS)
                    )
                )
        );
  }

  private static void join() {
    List<String> fruits = new ArrayList<>();
    fruits.add("Apple");
    fruits.add("Banana");

    List<String> colors = new ArrayList<>();
    colors.add("Green");
    colors.add("Red");
    colors.add("Yellow");

    Observable
        .intervalRange(0, 2, 0, 5000, TimeUnit.MILLISECONDS)
        .map(index -> fruits.get(index.intValue() % fruits.size()))
        .join(
            Observable
                .intervalRange(0, 10, 0, 1000, TimeUnit.MILLISECONDS)
                .map(index -> colors.get(index.intValue() % colors.size())),
            // timer决定了Observable数据的有效期
            fruit -> Observable.timer(5000, TimeUnit.MILLISECONDS),
            color -> Observable.timer(100, TimeUnit.MILLISECONDS),
            (fruit, color) -> color + "-" + fruit
        )
        .subscribe(str -> System.out.print(str + " "));
  }

  private static void combineLast() {
    Observable
        .combineLatest(
            Observable.interval(1000, TimeUnit.MILLISECONDS),
            Observable.interval(750, TimeUnit.MILLISECONDS),
            ((integer, integer2) -> "(" + integer + "," + integer2 + ")")
        )
        .take(20)
        .subscribe(str -> System.out.print(str + " "));
  }

  private static void zip() {
    Observable
        .zip(
            Observable.range(0, 5),
            Observable.range(10, 10),
            Observable.range(20, 20),
            ((integer, integer2, integer3) -> "(" + integer + "," + integer2 + "," + integer3 + ")")
        )
        .subscribe(str -> System.out.print(str));
  }

  private static void merge() {
    Observable
        .merge(
            Observable.intervalRange(0, 10, 0, 500, TimeUnit.MILLISECONDS),
            Observable.intervalRange(0, 10, 0, 500, TimeUnit.MILLISECONDS).map(value -> value * -1),
            // Error notifications from the source ObservableSource are not delayed
            // Observable.error(new Exception("error!")).timer(2, TimeUnit.SECONDS)
            // Observable.timer(2, TimeUnit.SECONDS).error(new Exception("error!"))
            Observable.error(new Exception("error!"))
        )
        .subscribe(
            value -> System.out.print(value + " "),
            throwable -> System.out.println(throwable.getMessage())
        );
  }

  private static void mergeDelayError() {
    Observable
        .mergeDelayError(
            Observable.intervalRange(0, 10, 0, 500, TimeUnit.MILLISECONDS),
            Observable.intervalRange(0, 10, 0, 500, TimeUnit.MILLISECONDS).map(value -> value * -1),
            Observable.error(new Exception("error!"))
        )
        .subscribe(
            value -> System.out.print(value + " "),
            throwable -> System.out.println(throwable.getMessage())
        );
  }

  private static void startWith() {
    Observable
        .range(1, 10)
        .doOnNext(value -> System.out.print(value + " "))
        .startWith(0)
        .startWith(Observable.range(-5, 5))
        .cast(Object.class)
        .startWith(Observable.intervalRange(-10, 5, 0, 500, TimeUnit.MILLISECONDS))
        .subscribe(value -> System.out.print(value + " "));
  }
}
